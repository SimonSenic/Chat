package sample;

import database.Message;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import database.Database;

import java.util.ArrayList;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Chat");
        primaryStage.setScene(new Scene(root, 600, 300));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
        Database db = new Database();
        //db.insertNewUser();
        //db.changePassword();
        db.sendMessage(4, "", "");
        ArrayList<Message> list = db.getMyMessages("Simon");
        for(Message temp : list) System.out.println(temp.getId() +" " +temp.getDt() +" " +temp.getFromUser() +" " +temp.getToUser() +" " +temp.getText());
    }
}
