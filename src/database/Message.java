package database;

import java.util.Date;

public class Message {
    int id;
    Date dt;
    String fromUser, toUser, text;

    public Message(int id, Date dt, String fromUser, String toUser, String text) {
        this.id = id;
        this.dt = dt;
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public Date getDt() {
        return dt;
    }

    public String getFromUser() {
        return fromUser;
    }

    public String getToUser() {
        return toUser;
    }

    public String getText() {
        return text;
    }
}
