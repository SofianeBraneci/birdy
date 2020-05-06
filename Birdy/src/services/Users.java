package services;

import org.json.JSONObject;

import tools.UsersTool;

public class Users {
	
	public static JSONObject addUser(String fullName, String username, String password) {
		return  UsersTool.addUser(fullName, username, password);
	}
	
	public static JSONObject getUser(String username, String key) {
		return UsersTool.getUser(username, key);
	}
	
	
	public static JSONObject deleteUser(String username, String key) {
		return UsersTool.deleteUser(username, key);
	}
	
	

}
