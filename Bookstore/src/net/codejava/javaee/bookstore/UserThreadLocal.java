package net.codejava.javaee.bookstore;

public class UserThreadLocal {

    public static long getUserId(){
        User user = ControllerServlet.userCredentialsThreadLocal.get();
        return user.userId;
    }
}
