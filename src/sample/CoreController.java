package sample;

import database.Database;
import database.Message;
import database.User;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
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
    @FXML
    TextField txt_oldPassword;
    @FXML
    TextField txt_newPassword;
    @FXML
    TextField txt_repeatPassword;
    @FXML
    Label lab_warning2;
    @FXML
    Button btn_save;

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

    public void lab_password_click(){
        try{
            openPasswordWindow();
            //btn_logout.getScene().getWindow().hide();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void btn_save_click(ActionEvent actionEvent){
        String oldPassword = txt_oldPassword.getText().trim();
        String newPassword = txt_newPassword.getText().trim();
        String repeatPassword = txt_repeatPassword.getText().trim();
        if(!newPassword.equals(repeatPassword) || newPassword.length()<6 || newPassword==null || newPassword.equals("") ||
        !oldPassword.equals(user.getPassword()) || oldPassword==null || oldPassword.equals("") ||
        repeatPassword==null || repeatPassword.equals("") || repeatPassword.length()<6) lab_warning2.setVisible(true);
        Database db = new Database();
        if(db.changePassword(user.getLogin(), oldPassword, newPassword)){
            btn_save.getScene().getWindow().hide();
        }else lab_warning2.setVisible(true);
    }

    public void btn_cancel_click(ActionEvent actionEvent){
        btn_save.getScene().getWindow().hide();
    }

    public void openPasswordWindow() throws Exception{
        FXMLLoader root = new FXMLLoader();
        root.setLocation(getClass().getResource("password.fxml"));
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Change Password");
        primaryStage.setScene(new Scene(root.load(), 405, 252));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /*public void aha(){
        myListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<MyDataModel>() {

            @Override
            public void changed(ObservableValue<? extends MyDataModel> observable, MyDataModel oldValue, MyDataModel newValue) {
                // Your action here
                System.out.println("Selected item: " + newValue);
            }
        });
    }*/

}
