package services;

import org.json.JSONObject;

import tools.CommentsTool;

public class Comments {

		public static JSONObject addComment(String postId, String username,String comment, String key) {
			return CommentsTool.addComment(postId, username, comment, key);
		}
		
		public static JSONObject getComments(String postId, String key) {
			return CommentsTool.getComments(postId, key);
		}
		
		public static JSONObject deleteComment(String postId, String commentId, String key) {
			return CommentsTool.deleteComment(postId, commentId, key);
		}
}
