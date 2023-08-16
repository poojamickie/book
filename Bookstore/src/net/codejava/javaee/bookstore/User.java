package net.codejava.javaee.bookstore;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    public String username;
    public String password;
    public String email;
    public  Long userId;
    public User(String username, String password, Long userid) {
        this.username = username;
        this.password = password;
//        this.email = email;
        this.userId = userid;
    }
    public Boolean auth(String username, String password) throws SQLException {
        Long userid = 0L;
        Boolean status = false;
        String sql = "SELECT * FROM user WHERE username = ? AND password = ?";
        BookDAO bookDAO = new BookDAO(ControllerServlet.jdbcURL, ControllerServlet.jdbcUsername, ControllerServlet.jdbcPassword);
        Connection jdbcConnection = bookDAO.connect();
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setString(1, username);
        statement.setString(2, password);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            userid = Long.parseLong(resultSet.getString("userid"));
        }

        if (userid!=0L){
            this.username = resultSet.getString("username");
            this.password = resultSet.getString("password");
//            this.email = resultSet.getString("userid");
            this.userId = Long.parseLong(resultSet.getString("userid"));
            status = true;
        }

        resultSet.close();
        statement.close();
        bookDAO.disconnect();
        return status;
    }

    public boolean addUser(String username,String password) throws SQLException {
        Boolean status = false;
        String sql = "INSERT INTO user (username, password) VALUES (\'"+username+"\',\'"+password+"\')";
        BookDAO bookDAO = new BookDAO(ControllerServlet.jdbcURL, ControllerServlet.jdbcUsername, ControllerServlet.jdbcPassword);
        Connection jdbcConnection = bookDAO.connect();
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        int insertRow = statement.executeUpdate();
        if (insertRow==1) {
            status = auth(username, password);
        }
        statement.close();
        bookDAO.disconnect();
        return status;
    }
    protected void finalize()
    {
        System.out.println("Object is destroyed by the Garbage Collector");
    }
}
