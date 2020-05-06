package tools;

import org.json.JSONException;
import org.json.JSONObject;

public class ErrorJSON {
	
	
	public static JSONObject serviceAccepted() {
		JSONObject json = new JSONObject();
		try {
			json.put("code", Integer.valueOf(0)).put("message", "service excuté avec succès");
			return json;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
	}
	
	
	public static JSONObject serviceRefused(String errorMessage, int errorCode) {
		try {
			JSONObject json = new JSONObject();
			json.put("code", Integer.valueOf(errorCode)).put("message", errorMessage);
			return json;
		}catch(JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

}
