package database;

import java.sql.*;

public class Database {
    private static String url="jdbc:mysql://itsovy.sk:3306/chat2021";
    private static String username="mysqluser";
    private static String password="Kosice2021!";

    private static Connection getConnection() throws SQLException {
        Connection con = DriverManager.getConnection(url, username, password);
        return con;
    }

    public boolean insertNewUser(String login, String password){
        if(login==null || login.equals("") || password==null || password.equals("")) return false;
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

        return false;
    }

    public boolean sendMessage(int id, String toWho, String text){

        return false;
    }

}
