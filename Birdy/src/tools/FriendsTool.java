package tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import db.Database;

public class FriendsTool {

	public static JSONObject addFriend(String username, String friendUsername, String key) {
		Connection conn = null;

		try {
			conn = Database.getMySQLConnection();
			if (!SessionTool.isSessionKeyValid(key, conn)) {
				return ErrorJSON.serviceRefused("session expired", -1);
			}
			SessionTool.updateSession(key, conn);
			PreparedStatement statement = conn
					.prepareStatement("INSERT INTO friends(user_name, friend_user_name) VALUES(?,?)");
			PreparedStatement stat = conn
					.prepareStatement("SELECT * FROM friends WHERE user_name = ? and friend_user_name=?");
			stat.setString(1, username);
			stat.setString(2, friendUsername);
			ResultSet set = stat.executeQuery();
			int count = 0;
			while (set.next()) {
				count++;
			}
			System.out.println(count);
			if (count >= 1) {

				return ErrorJSON.serviceRefused("duplicats", -1);
			}

			statement.setString(1, username);
			statement.setString(2, friendUsername);
			if (statement.executeUpdate() > 0)
				return ErrorJSON.serviceAccepted();
			else
				return ErrorJSON.serviceRefused("could not insert to friends", -1);

		} catch (SQLException e) {
			return ErrorJSON.serviceRefused(e.getMessage(), 100);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e2) {
					// TODO: handle exception
				}
			}
		}

	}

	public static JSONObject getFriends(String username, String key) {
		Connection conn = null;
		try {
			conn = Database.getMySQLConnection();
			if (!SessionTool.isSessionKeyValid(key, conn)) {
				return ErrorJSON.serviceRefused("session expired", -1);
			}
			SessionTool.updateSession(key, conn);
			PreparedStatement statement = conn
					.prepareStatement("SELECT full_name, user_name FROM users WHERE user_name in "
							+ "(SELECT friend_user_name FROM friends WHERE user_name = ?)");
			statement.setString(1, username);
			ResultSet set = statement.executeQuery();
			JSONArray array = new JSONArray();
			JSONObject json = new JSONObject();
			while (set.next()) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("full_name", set.getString("full_name")).put("user_name", set.getString("user_name"));
				array.put(jsonObject);
			}
			json.put("friends", array);
			return json;

		} catch (JSONException e) {
			// TODO: handle exception
			return ErrorJSON.serviceRefused("json error", -1);

		} catch (SQLException e) {
			// TODO: handle exception
			return ErrorJSON.serviceRefused(e.getMessage(), 100);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}

	}

	public static JSONObject deletFriend(String username, String friendUsername, String key) {
		Connection conn = null;
		try {
			conn = Database.getMySQLConnection();
			if (!SessionTool.isSessionKeyValid(key, conn)) {
				return ErrorJSON.serviceRefused("session expired", -1);
			}
			SessionTool.updateSession(key, conn);
			PreparedStatement statement = conn
					.prepareStatement("DELETE FROM friends where user_name = ? and friend_user_name = ?");
			statement.setString(1, username);
			statement.setString(2, friendUsername);
			if (statement.executeUpdate() > 0) {

				return ErrorJSON.serviceAccepted();
			} else {
				return ErrorJSON.serviceRefused("could not delet the friend", -1);
			}
		} catch (Exception e) {
			// TODO: handle exception
			return ErrorJSON.serviceRefused(e.getMessage(), 100);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}

	}

}
