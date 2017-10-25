package javawebservicerest1;

import org.json.simple.JSONObject;

public class JsonEncode {
	public static void main(String[] args){
	      JSONObject obj = new JSONObject();

	      obj.put("name", "Senthan");
	      obj.put("age", new Integer(25));
	      obj.put("salary", new Double(20000));
	      obj.put("is_vip", new Boolean(true));

	      System.out.print(obj);
	   }
	

}
