package tools;

import java.sql.Connection;
import java.sql.SQLException;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import db.Database;

// post = {username, content, comments, likes}
public class MessagesTool {

	public static JSONObject addPost(String username, String content, String key) {
		Connection conn = null;

		try {
			conn = Database.getMySQLConnection();
			if (!SessionTool.isSessionKeyValid(key, conn)) {
				return ErrorJSON.serviceRefused("Session expired", -1);
			}
			SessionTool.updateSession(key, conn);

			MongoDatabase db = Database.getMongoDBConnection();
			Document document = new Document();
			document.append("username", username).append("post", content);
			MongoCollection<Document> collection =  db.getCollection("posts");
			collection.insertOne(document);
			return ErrorJSON.serviceAccepted();

		} catch (SQLException e) {
			// TODO: handle exception
			return ErrorJSON.serviceRefused(e.getMessage(), -1);
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

	public static JSONObject getPosts(String key) {
		Connection conn = null;
		
		try {
			conn = Database.getMySQLConnection();
			if (!SessionTool.isSessionKeyValid(key, conn)) {
				return ErrorJSON.serviceRefused("Session expired", -1);
				
			}
			SessionTool.updateSession(key, conn);
			MongoDatabase db = Database.getMongoDBConnection();
			FindIterable<Document> iterable = db.getCollection("posts").find().limit(20);
			JSONObject json = new JSONObject();
			JSONArray array = new JSONArray();
			for(Document document :iterable) {
					array.put(document);
			}
			json.put("posts", array);
			return json;
			
		} catch (SQLException e) {
			// TODO: handle exception
			return ErrorJSON.serviceRefused(e.getMessage(), -1);
		}catch (JSONException e) {
			// TODO: handle exception
			return ErrorJSON.serviceRefused(e.getMessage(), -1);
		}
		
	}

	
	public static JSONObject getPosts(String username, String key) {
		Connection conn = null;
		
		try {
			conn = Database.getMySQLConnection();
			if (!SessionTool.isSessionKeyValid(key, conn)) {
				return ErrorJSON.serviceRefused("Session expired", -1);
				
			}
			SessionTool.updateSession(key, conn);
			MongoDatabase db = Database.getMongoDBConnection();
			FindIterable<Document> iterable = db.getCollection("posts").find(new Document("username", username)).limit(20);
			JSONObject json = new JSONObject();
			JSONArray array = new JSONArray();
			for(Document document :iterable) {
					array.put(document);
			}
			json.put("posts", array);
			return json;
			
		} catch (SQLException e) {
			// TODO: handle exception
			return ErrorJSON.serviceRefused(e.getMessage(), -1);
		}catch (JSONException e) {
			// TODO: handle exception
			return ErrorJSON.serviceRefused(e.getMessage(), -1);
		}
		
	}

	
	public static JSONObject deletePost(String postId, String key) {
		Connection conn = null;
		
		try {
			conn = Database.getMySQLConnection();
			if (! SessionTool.isSessionKeyValid(key, conn)) {
				return ErrorJSON.serviceRefused("Session expired", -1);
			}
			System.out.println(postId);
			SessionTool.updateSession(key, conn);
			ObjectId id = new ObjectId(postId);
			MongoDatabase db = Database.getMongoDBConnection();
			MongoCollection<Document> collection = db.getCollection("posts");
			Document document = new Document();
			document.put("_id", id);
			collection.deleteOne(document);
			return ErrorJSON.serviceAccepted();
			
		} catch (SQLException e) {
			// TODO: handle exception
			return ErrorJSON.serviceRefused(e.getMessage(), -1);
		}
	}

}
