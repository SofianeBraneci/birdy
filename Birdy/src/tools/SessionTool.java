package tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

public class SessionTool {
	
	public static boolean isSessionKeyValid(String key, Connection conn) {
		try {
			PreparedStatement statement = conn.prepareStatement("Select * from sessions where sess_key = ?");
			statement.setString(1, key);
			ResultSet set = statement.executeQuery();
			
			if (set.next()) {
				Timestamp old = set.getTimestamp("date_created");
				Timestamp current = new Timestamp(System.currentTimeMillis());
				System.out.println(old.toString());
				if (DateConverter.getDateDiff(old, current, TimeUnit.MINUTES) < 120) {
					return true;
				}else return false;
				
			}else {
				return false;
			}
		} catch (SQLException e) {
			// TODO: handle exception
			return false;
		}
		
	}
	
	public static boolean updateSession(String key, Connection conn) {
		try {
			PreparedStatement statement = conn.prepareStatement("UPDATE sessions set date_created = CURRENT_TIMESTAMP WHERE sess_key = ?");
			statement.setString(1, key);
			
			return statement.executeUpdate() > 0;
			
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		
		
	}
	
	
	
	

}
