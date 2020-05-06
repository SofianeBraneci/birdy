package tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import db.Database;

public class AuthentificationTool {

	public static JSONObject login(String username, String password) {
		Connection conn = null;
		if (username == null || username == "" || password == null || password == "" ) {
			return ErrorJSON.serviceRefused("username or password is invalid", 1000);
		}
		
		try {
			conn = Database.getMySQLConnection();
			PreparedStatement statement = conn
					.prepareStatement("Select * from users where user_name = ? and pass_word = ?");
			statement.setString(1, username);
			statement.setString(2, password);
			System.out.println(username + " " + password);

			ResultSet resultSet = statement.executeQuery();

			int counter = 0;
			while (resultSet.next())
				counter++;
			if (counter == 1) {
				String key = KeyGenerator.generateKey();
				statement = conn.prepareStatement("insert into sessions values(?,?, CURRENT_TIMESTAMP())");
				statement.setString(1, key);
				statement.setString(2, username);
				
				statement.execute();
				JSONObject sessionKey = new JSONObject();
				sessionKey.put("key", key);
				return sessionKey;

			} else {
				return ErrorJSON.serviceRefused("user not found", -1);
			}
		}catch (JSONException e) {
				// TODO: handle exception
				return ErrorJSON.serviceRefused("JSON error", -1);
			
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

	public static JSONObject logout(String key) {

		Connection conn = null;
		try {
			if (key.equals(null)) {
				return ErrorJSON.serviceRefused("Illegale argument", -1);
			}
			
			conn = Database.getMySQLConnection();
			PreparedStatement statement = conn.prepareStatement("DELETE FROM sessions WHERE sess_key = ?");
			statement.setString(1, key);
			if (statement.executeUpdate() > 0) {
				return ErrorJSON.serviceAccepted();
			} else {
				return ErrorJSON.serviceRefused("Session error", -1);
			}

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

}
