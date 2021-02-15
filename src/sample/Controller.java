package sample;

import database.Database;
import database.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller {
    public TextField txt_login;
    public PasswordField txt_password;
    public Text txt_warning;
    public Button btn_login;

    public void btn_click(ActionEvent actionEvent){
        try{
            String login = txt_login.getText().trim();
            String password = txt_password.getText().trim();
            Database db = new Database();
            User user = db.loginUser(login, password);
            if(user==null) txt_warning.setVisible(true);
            else{
                btn_login.getScene().getWindow().hide();
                openFinalWindow();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void openFinalWindow() throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("core.fxml"));
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Chat");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
