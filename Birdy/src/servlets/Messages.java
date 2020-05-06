package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

/**
 * Servlet implementation class Messages
 */
@WebServlet("/Messages")
public class Messages extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Messages() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String username = request.getParameter("username");
		String key = request.getParameter("key");
		JSONObject json = new JSONObject();
		if (username.equals(null)) {
			json = services.Messages.getPosts(key);
			
		}
		else {
			json = services.Messages.getPosts(username, key);
		}
		response.getWriter().print(json.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String username, content, key;
		username = request.getParameter("username");
		content = request.getParameter("content");
		key = request.getParameter("key");
		
		JSONObject json = services.Messages.addPost(username, content, key);
		response.getWriter().print(json.toString());
		
	}
	
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String idPost, key;
		idPost = request.getParameter("idPost");
		key = request.getParameter("key");
		JSONObject object = services.Messages.deletePost(idPost, key);
		response.getWriter().print(object.toString());
		
	}
	

}
