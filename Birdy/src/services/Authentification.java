package services;

import org.json.JSONObject;

import tools.AuthentificationTool;

public class Authentification {
	
	public static JSONObject login(String username, String password) {
		return AuthentificationTool.login(username, password);
	}
	
	public static JSONObject logout(String key) {
		return AuthentificationTool.logout(key);
	}

}
