package services;

import org.json.JSONObject;

import tools.MessagesTool;


public class Messages {
	
	
	public static JSONObject addPost(String username, String content, String key) {
		return  MessagesTool.addPost(username, content, key);
	}
	
	
	public static JSONObject deletePost(String idPost,  String key) {
		return MessagesTool.deletePost(idPost, key);
	}
	
	
	public static JSONObject getPosts(String username, String key) {
		return MessagesTool.getPosts(username, key);
	}
	
	public static JSONObject getPosts(String key) {
		return MessagesTool.getPosts(key);
	}

}
