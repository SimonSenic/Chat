package sample;

import database.Database;
import database.Message;
import database.User;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Duration;

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
    ListView lv_archive;
    @FXML
    ListView lv_users;
    @FXML
    Label lab_password;

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

    public void refreshApp(){
        ArrayList<Message> list = new Database().getMyMessages(user.getLogin());
        ArrayList<String> list2 = new Database().getAllUsers();
        lv_archive.getItems().clear();
        lv_users.getItems().clear();
        for(Message temp : list)
            lv_archive.getItems().add(temp.getDt().toString() +" " +temp.getFromUser() +": " +temp.getText());
        for(String temp : list2)
            lv_users.getItems().add(temp);
    }

    public void btn_refresh_click(ActionEvent actionEvent){
        refreshApp();
    }

    public void timer(){
        Timeline oneMinuteWonder = new Timeline(
                new KeyFrame(Duration.seconds(60),
                        new EventHandler<ActionEvent>() {

                            @Override
                            public void handle(ActionEvent event) {
                                refreshApp();
                            }
                        }));
        oneMinuteWonder.setCycleCount(Timeline.INDEFINITE);
        oneMinuteWonder.play();
    }

    public void lab_password_click(ActionEvent actionEvent){

    }

}
