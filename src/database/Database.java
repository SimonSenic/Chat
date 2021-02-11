package database;

import java.sql.*;
import java.util.ArrayList;

public class Database {
    private static String url="jdbc:mysql://itsovy.sk:3306/chat2021";
    private static String username="mysqluser";
    private static String password="Kosice2021!";

    private static Connection getConnection() throws SQLException {
        Connection con = DriverManager.getConnection(url, username, password);
        return con;
    }

    private static int getUserID(String login){
        if(login==null || login.equals("")) return 0;
        String query = "SELECT id FROM user WHERE login LIKE ?";
        try{
            Connection con = getConnection();
            if(con!=null){
                PreparedStatement ps = con.prepareStatement(query);
                ps.setString(1, login);
                ResultSet rs = ps.executeQuery();
                if(rs.next())
                    return rs.getInt("id");
            }
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    public boolean insertNewUser(String login, String password){
        if(login==null || login.equals("") || password==null || password.equals("") || password.length()<6) return false;
        String query = "INSERT INTO user (login, password) VALUES (?, ?)";
        String hash = new Hash().getMd5(password);
        try{
            Connection con = getConnection();
            if(con!=null){
                PreparedStatement ps = con.prepareStatement(query);
                ps.setString(1, login);
                ps.setString(2, hash);
                int result = ps.executeUpdate();
                if(result==2) return true;
            }
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public User loginUser(String login, String password){
        if(login==null || login.equals("") || password==null || password.equals("")) return null;
        String query = "SELECT * FROM user WHERE login LIKE ? AND password LIKE ?";
        String hash = new Hash().getMd5(password);
        try{
            Connection con = getConnection();
            if(con!=null){
                PreparedStatement ps = con.prepareStatement(query);
                ps.setString(1, login);
                ps.setString(2, hash);
                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    int id = rs.getInt("id");
                    return new User(login, hash, id);
                }
            }
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean changePassword(String login, String oldPassword, String newPassword){
        if(oldPassword.equals(newPassword) || loginUser(login, oldPassword)==null || login==null || login.equals("") ||
                oldPassword==null || oldPassword.equals("") || newPassword==null || newPassword.equals("") ||
                newPassword.length()<6) return false;
        String query = "UPDATE user SET password = ? WHERE login LIKE ?";
        String hash = new Hash().getMd5(newPassword);
        try{
            Connection con = getConnection();
            if(con!=null){
                PreparedStatement ps = con.prepareStatement(query);
                ps.setString(1, hash);
                ps.setString(2, login);
                int result = ps.executeUpdate();
                if(result==1) return true;
            }
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean sendMessage(int fromUser, String toUser, String text){
        int toUserID = getUserID(toUser);
        if(text==null || text.equals("") || fromUser==toUserID) return false;
        String query = "INSERT INTO message (fromUser, toUser, text) VALUES(?, ?, ?)";
        try{
            Connection con = getConnection();
            if(con!=null){
                PreparedStatement ps = con.prepareStatement(query);
                ps.setInt(1, fromUser);
                ps.setInt(2, toUserID);
                ps.setString(3, text);
                int result = ps.executeUpdate();
                if(result>0) return true;
            }
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<Message> getMyMessages(String login){
        ArrayList<Message> list = new ArrayList<>();
        String query = "SELECT * FROM message WHERE fromUser LIKE ?";
        try{
            Connection con = getConnection();
            if(con!=null){
                PreparedStatement ps = con.prepareStatement(query);
                ps.setString(1, login);
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    int id = rs.getInt("id");
                    Date dt = rs.getDate("dt");
                    String fromUser = rs.getString("fromUser"); //unfinished
                    String toUser = rs.getString("toUser"); //unfinished
                    String text = rs.getString("text");
                    Message message = new Message(id, dt, fromUser, toUser, text);
                    list.add(message);
                }
            }
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

}
