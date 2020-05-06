package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet implementation class Users
 */
@WebServlet("/Users")
public class Users extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Users() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String username, key;
		username = request.getParameter("username");
		key = request.getParameter("key");
		
		JSONObject object = services.Users.getUser(username, key);
		
		response.setContentType("json");
		response.getWriter().print(object.toString());
	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String fullname, username, password;
		fullname = request.getParameter("fullname");
		username = request.getParameter("username");
		password = request.getParameter("password");
		JSONObject object = services.Users.addUser(fullname, username, password);
		
		response.setContentType("json");
		response.getWriter().print(object.toString());
		
		
	}

		
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String username, key;
		username = request.getParameter("username");
		key = request.getParameter("key");
		JSONObject object = services.Users.deleteUser(username, key);
		response.setContentType("json");
		response.getWriter().print(object.toString());
	}
}
