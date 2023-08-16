package net.codejava.javaee.bookstore;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Authentication {
    private String username;
    private String password;
    public Authentication() {

    }

    public Long getUserId(String username, String password) throws SQLException {
        Long userid = 0L;
        String sql = "SELECT userid FROM user WHERE username = ? & password = ?";
        BookDAO bookDAO = new BookDAO(ControllerServlet.jdbcURL, ControllerServlet.jdbcUsername, ControllerServlet.jdbcPassword);
        Connection jdbcConnection = bookDAO.connect();
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setString(1, username);
        statement.setString(2, password);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            userid = Long.parseLong(resultSet.getString("userid"));
        }
        resultSet.close();
        statement.close();
        bookDAO.disconnect();
        return userid;
    }

    public Long getOtp(){
        return 0L;
    }
    protected void finalize()
    {
        System.out.println("Object is destroyed by the Garbage Collector");
    }

}
