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
 * Servlet implementation class Comments
 */
@WebServlet("/Comments")
public class Comments extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Comments() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String postId, key;
		postId = request.getParameter("postId");
		key = request.getParameter("key");
		JSONObject object = services.Comments.getComments(postId, key);
		response.setContentType("json");
		response.getWriter().print(object.toString());
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String postId, key, username, comment;
		postId = request.getParameter("postId");
		key = request.getParameter("key");
		comment = request.getParameter("comment");
		username = request.getParameter("username");
		JSONObject object = services.Comments.addComment(postId, username, comment, key);
		response.setContentType("json");
		response.getWriter().print(object.toString());
	}
	
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String postId, commentId, key;
		postId = request.getParameter("postId");
		key = request.getParameter("key");
		commentId = request.getParameter("commentId");
		JSONObject object = services.Comments.deleteComment(postId, commentId, key);
		response.setContentType("json");
		response.getWriter().print(object.toString());
	}

}
