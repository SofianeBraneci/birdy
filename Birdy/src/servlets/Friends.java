package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

/**
 * Servlet implementation class Friends
 */
@WebServlet("/Friends")
public class Friends extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Friends() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String username, key;
		username = request.getParameter("username");
		key = request.getParameter("key");
		response.setContentType("json");
		JSONObject object = services.Friends.getFriends(username, key);
		response.getWriter().append(object.toString());
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String username, friendUsername, key;
		username = request.getParameter("username");
		key = request.getParameter("key");
		friendUsername = request.getParameter("friendUsername");
		response.setContentType("json");
		JSONObject object = services.Friends.addFriend(username, friendUsername, key);
		response.getWriter().append(object.toString());
	}
	
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String username, friendUsername, key;
		username = request.getParameter("username");
		key = request.getParameter("key");
		friendUsername = request.getParameter("friendUsername");
		JSONObject json = services.Friends.deletFriend(username, friendUsername, key);
		response.getWriter().append(json.toString());
		
	}

}
