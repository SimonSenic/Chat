package sample;

import database.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class CoreController {
    @FXML
    Button btn_logout;
    @FXML
    Label lab_login;

    public void btn_logout_click(ActionEvent actionEvent){
        btn_logout.getScene().getWindow().hide();
        //
    }

    public void setUser(User user){ //unfinished
        Label lbl = this.lab_login;
        lbl.setText(user.getLogin());
        this.lab_login = lbl;
    }
}
