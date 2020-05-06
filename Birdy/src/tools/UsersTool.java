package tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import db.Database;

public class UsersTool {

	public static JSONObject addUser(String fullName, String username, String password) {
		Connection conn = null;
		try {
			if (fullName == null || fullName == "" || username == null || username == "" || password == null
					|| password == "") {
				return ErrorJSON.serviceRefused("Illegale argument", -1);
			}

			conn = Database.getMySQLConnection();
			PreparedStatement statement = conn.prepareStatement(
					"INSERT INTO users(full_name, user_name, pass_word, date_added) VALUES(?,?,?, CURRENT_TIMESTAMP())");
			statement.setString(1, fullName);
			statement.setString(2, username);
			statement.setString(3, password);
			if (statement.executeUpdate() > 0) {
				return ErrorJSON.serviceAccepted();
			} else {
				return ErrorJSON.serviceRefused("Could not add new user", -1);

			}

		} catch (SQLException e) {
			// TODO: handle exception
			return ErrorJSON.serviceRefused(e.getMessage(), -1);
		} finally {
			// TODO: handle finally clause
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}

			}
		}
	}

	public static JSONObject getUser(String username, String key) {
		Connection conn = null;
		try {
			if (username == null || username == "") {
				return ErrorJSON.serviceRefused("Illegale argument", -1);
			}
			

			conn = Database.getMySQLConnection();
			if (!SessionTool.isSessionKeyValid(key, conn)) {
				return ErrorJSON.serviceRefused("Session expired", -1);
			}
			PreparedStatement statement = conn.prepareStatement("SELECT full_name FROM users WHERE user_name = ?");
			statement.setString(1, username);
			ResultSet set = statement.executeQuery();
			if (set.next()) {
				JSONObject user = new JSONObject();
				user.put("full_name", set.getString("full_name")).put("user_name", username);
				return user;
			} else {
				return ErrorJSON.serviceRefused("User not found", -1);
			}

		} catch (JSONException e) {
			// TODO: handle exception
			return ErrorJSON.serviceRefused("JSON error", -1);
		} catch (SQLException e) {
			// TODO: handle exception
			return ErrorJSON.serviceRefused(e.getMessage(), -1);
		} finally {
			// TODO: handle finally clause
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}

			}
		}
	}

	public static JSONObject deleteUser(String username, String key) {
		Connection conn = null;
		try {
			if (username == null || username == "" || key == null || key == "") {
				return ErrorJSON.serviceRefused("Illegale argument", -1);
			}

			if (!SessionTool.isSessionKeyValid(key, conn)) {
				return ErrorJSON.serviceRefused("Session Expired", -1);
			}

			conn = Database.getMySQLConnection();
			PreparedStatement statement = conn.prepareStatement("DELETE FROM users WHERE user_name = ?");
			statement.setString(1, username);
			if (statement.executeUpdate() > 0) {
				return ErrorJSON.serviceAccepted();
			} else {
				return ErrorJSON.serviceRefused("Could not delete your account", -1);
			}

		} catch (SQLException e) {
			// TODO: handle exception
			return ErrorJSON.serviceRefused(e.getMessage(), -1);
		} finally {
			// TODO: handle finally clause
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