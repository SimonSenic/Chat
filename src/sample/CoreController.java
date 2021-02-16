package sample;

import database.Database;
import database.Message;
import database.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class CoreController {
    User user;
    @FXML
    Button btn_logout;
    @FXML
    Label lab_login;
    @FXML
    TextField txt_message;
    @FXML
    TextField txt_to;
    @FXML
    Button btn_send;
    @FXML
    Label lab_warning;
    @FXML
    TextArea txt_archive;

    public void btn_logout_click(ActionEvent actionEvent){
        btn_logout.getScene().getWindow().hide();
    }

    public void setUser(User user){
        this.user = user;
    }

    public void setLab_login() {
        lab_login.setText(user.getLogin());
    }

    public void btn_send_click(ActionEvent actionEvent){
        String message = txt_message.getText().trim();
        String to = txt_to.getText().trim();
        Database db = new Database();
        if(db.sendMessage(user.getId(), to, message)==true) {
            txt_message.clear();
            txt_to.clear();
            lab_warning.setVisible(false);
        }else lab_warning.setVisible(true);
    }

    public void btn_refresh_click(ActionEvent actionEvent){
        ArrayList<Message> list = new Database().getMyMessages(user.getLogin());
        txt_archive.setText(list.toString());
    }

}
