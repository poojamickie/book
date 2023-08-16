package net.codejava.javaee.bookstore;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.codejava.javaee.bookstore.*;

/**
 * ControllerServlet.java
 * This servlet acts as a page controller for the application, handling all
 * requests from the user.
 * @author www.codejava.net
 */
public class ControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BookDAO bookDAO;
	public static String jdbcURL  = "jdbc:mysql://localhost:3306/bookstore";

	public static String jdbcUsername = "root1";

	public static String jdbcPassword = "P@ssw0rd";
	public static ThreadLocal<User> userCredentialsThreadLocal = new ThreadLocal<>();


	public void init() {
		bookDAO = new BookDAO(jdbcURL, jdbcUsername, jdbcPassword);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();

		try {
			switch (action) {
			case "/new":
				showNewForm(request, response);
				break;
			case "/insert":
				insertBook(request, response);
				break;
			case "/delete":
				deleteBook(request, response);
				break;
			case "/edit":
				showEditForm(request, response);
				break;
			case "/update":
				updateBook(request, response);
				break;
			case "/Auth":
				authentication(request, response);
				break;
			case "/Login":
				login(request, response);
				break;
			case "/Register":
				register(request, response);
				break;
			case "/RegisterDO":
				registerDO(request, response);
				break;
			default:
				listBook(request, response);
				break;
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}

	private void listBook(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<Book> listBook = bookDAO.listAllBooks();
		request.setAttribute("listBook", listBook);
		RequestDispatcher dispatcher = request.getRequestDispatcher("BookList.jsp");
		dispatcher.forward(request, response);
	}

	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("BookForm.jsp");
		dispatcher.forward(request, response);
	}

	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		Book existingBook = bookDAO.getBook(id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("BookForm.jsp");
		request.setAttribute("book", existingBook);
		dispatcher.forward(request, response);

	}

	private void insertBook(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		String title = request.getParameter("title");
		String author = request.getParameter("author");
		float price = Float.parseFloat(request.getParameter("price"));

		Book newBook = new Book(title, author, price);
		bookDAO.insertBook(newBook);
		response.sendRedirect("list");
	}

	private void updateBook(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		String title = request.getParameter("title");
		String author = request.getParameter("author");
		float price = Float.parseFloat(request.getParameter("price"));

		Book book = new Book(id, title, author, price);
		bookDAO.updateBook(book);
		response.sendRedirect("list");
	}

	private void authentication(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		String username = "";
		String password = "";
		try {
			username = request.getParameter("username");
			password = request.getParameter("password");
		}
		catch (Exception e){
//			// Do nothing
		}
		User user = new User("","",0L);
		if (user.auth(username,password)) {
			userCredentialsThreadLocal.set(user);

			RequestDispatcher dispatcher = request.getRequestDispatcher("Auth.jsp");
			request.setAttribute("userId", UserThreadLocal.getUserId());
			dispatcher.forward(request, response);
		}
		else {
			RequestDispatcher dispatcher = request.getRequestDispatcher("Error.jsp");
			dispatcher.forward(request, response);
		}

	}

	private void login(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {

		RequestDispatcher dispatcher = request.getRequestDispatcher("Login.jsp");
		dispatcher.forward(request, response);
	}

	private void register(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {

		RequestDispatcher dispatcher = request.getRequestDispatcher("Register.jsp");
		dispatcher.forward(request, response);
	}

	private void registerDO(HttpServletRequest request, HttpServletResponse response)
			throws  IOException, ServletException, SQLException {
		String uname = request.getParameter("username");
		String uemail = request.getParameter("uemail");
		String upass = request.getParameter("password");
		User user = new User("","",0L);
		Boolean status = user.addUser(uname,upass);
		user = null;
		RequestDispatcher dispatcher = request.getRequestDispatcher("Login.jsp");
		dispatcher.forward(request, response);
	}

	private void deleteBook(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));

		Book book = new Book(id);
		bookDAO.deleteBook(book);
		response.sendRedirect("list");

	}

}
