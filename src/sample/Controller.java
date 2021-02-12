package sample;

import database.Database;
import database.User;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class Controller {
    public TextField txt_login;
    public PasswordField txt_password;
    public Text label_warning;
    public Button btn_login;

    public void btn_click(ActionEvent actionEvent){
        String login = txt_login.getText().trim();
        String password = txt_password.getText().trim();
        Database db = new Database();
        User user = db.loginUser(login, password);
        if(user==null) label_warning.setVisible(true);
        else System.out.println("succes");
    }
}
