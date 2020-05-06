package services;

import org.json.JSONObject;

import tools.FriendsTool;

public class Friends {
	
	public static JSONObject addFriend(String username, String friendUsername, String key) {
		return FriendsTool.addFriend(username, friendUsername, key);
	}
	
	public static JSONObject getFriends(String username, String key) {
		return FriendsTool.getFriends(username, key);
	}
	
	public static JSONObject deletFriend(String username, String friendUsername, String key) {
		return FriendsTool.deletFriend(username, friendUsername, key);
	}
	

}
