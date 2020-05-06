package tools;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.json.JSONObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import db.Database;

public class CommentsTool {
	
	public static JSONObject addComment(String postId, String username,String comment, String key) {
			Connection conn = null;
			
			try {
				conn = Database.getMySQLConnection();
				if (! SessionTool.isSessionKeyValid(key, conn)) {
					return ErrorJSON.serviceRefused("Session expired", -1);
				}
				MongoDatabase db = Database.getMongoDBConnection();
				MongoCollection<Document> collection = db.getCollection("posts");
				FindIterable<Document> docs = collection.find(new Document("_id", new ObjectId(postId)));
				Document doc = new Document();
				Document document = docs.iterator().next();
				doc.append("username", username).append("comment", comment).append("date", DateConverter.getCurrentDate()).append("_id", new ObjectId());
				System.out.println(document.toString());
				if (document.get("comments") == null) {
					List<Document> array = new ArrayList<Document>();
					array.add(doc);
					collection.findOneAndUpdate(new Document("_id", new ObjectId(postId)),
							(Bson) new Document("$set", new Document("comments", array)));
				
				}else {
					@SuppressWarnings("unchecked")
					List<Document> array = (List<Document>) document.get("comments");
					array.add(doc);
					collection.findOneAndUpdate(new Document("_id", new ObjectId(postId)),
							(Bson) new Document("$set", new Document("comments", array)));
				
				
				}
				return ErrorJSON.serviceAccepted();
			} catch (SQLException e) {
				// TODO: handle exception
				return ErrorJSON.serviceRefused(e.getMessage(), 1);
			}
	}
	
	public static JSONObject getComments(String postId, String key) {
		Connection conn = null;
		
		try {
			conn = Database.getMySQLConnection();
			if (! SessionTool.isSessionKeyValid(key, conn)) {
				return ErrorJSON.serviceRefused("Session expired", -1);
			}
			MongoDatabase db = Database.getMongoDBConnection();
			MongoCollection<Document> collection = db.getCollection("posts");
			FindIterable<Document> docs = collection.find(new Document("_id", new ObjectId(postId)));
			
			Document document = docs.first();
			@SuppressWarnings("unchecked")
			List<Document> comments = (List<Document>) document.get("comments");
			JSONObject object = new JSONObject();
			object.put("comments", comments);
			return object;
		} catch (Exception e) {
			// TODO: handle exception
			return ErrorJSON.serviceRefused(e.getMessage(),-1);
		}
	}
	
	
	public static JSONObject deleteComment(String postId, String commentId, String key) {
		Connection conn = null;
		
		try {
			conn = Database.getMySQLConnection();
			if (! SessionTool.isSessionKeyValid(key, conn)) {
				return ErrorJSON.serviceRefused("Session expired", -1);
			}
			MongoDatabase db = Database.getMongoDBConnection();
			MongoCollection<Document> collection = db.getCollection("posts");
			FindIterable<Document> doc = collection.find(new Document("_id", new ObjectId(postId)));
			Document document = doc.first();
			@SuppressWarnings("unchecked")
			List<Document> comments = (List<Document>) document.get("comments");
			for(Document comment: comments) {
				System.out.println(comment.getString("_id"));
				if (comment.getString("_id").equals(commentId)) {
					comments.remove(comment);
				}
			}
			collection.findOneAndUpdate(new Document("_id", new ObjectId(postId)),
					(Bson) new Document("$set", new Document("comments", comments)));
			return ErrorJSON.serviceAccepted();
		
			
		}catch (SQLException e) {
			// TODO: handle exception
			return   ErrorJSON.serviceRefused(e.getMessage(), -1);
		}finally {
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

