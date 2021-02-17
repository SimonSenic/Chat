package sample;

import database.Database;
import database.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller {
    public TextField txt_login;
    public PasswordField txt_password;
    public Text lab_warning;
    public Button btn_login;
    User user;

    public void btn_click(ActionEvent actionEvent){
        try{
            String login = txt_login.getText().trim();
            String password = txt_password.getText().trim();
            Database db = new Database();
            user = db.loginUser(login, password);
            if(user==null) lab_warning.setVisible(true);
            else{
                btn_login.getScene().getWindow().hide();
                openFinalWindow();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void openFinalWindow() throws Exception{
        FXMLLoader root = new FXMLLoader();
        root.setLocation(getClass().getResource("core.fxml"));
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Chat");
        primaryStage.setScene(new Scene(root.load(), 800, 400));
        primaryStage.setResizable(false);
        primaryStage.show();
        CoreController cc = root.getController();
        cc.setUser(user);
        cc.setLab_login();
        cc.refreshApp();
        cc.timer();
    }
}
