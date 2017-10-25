package javawebservicerest1;

import java.io.IOException;
import java.sql.*;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
 
public class GitHub {
 
	public static void main(String[] args) throws SQLException {
		for (int i = 0; i < 1000;) {
			getEach(i);
			i = i + 200;
		}
	}
 
	public static void getEach(int x) throws SQLException {
		String url = "https://api.github.com/orgs/wso2/repos";
	String url2="jdbc:mysql://localhost:3306/git?useSSL=false";
	String usr="root";
	String psw="wso2123";
		try {
			
			CloseableHttpClient httpClient = HttpClientBuilder.create().build();
			HttpGet request = new HttpGet(url);
			request.addHeader("content-type", "application/json");
			HttpResponse result = httpClient.execute(request);
			String json = EntityUtils.toString(result.getEntity(), "UTF-8");
 
			System.out.println(json);
 
			JsonElement jelement = new JsonParser().parse(json);
			JsonArray jarr = jelement.getAsJsonArray();
			for (int i = 0; i < jarr.size(); i++) {
				JsonObject jo = (JsonObject) jarr.get(i);
				String fullName = jo.get("full_name").toString();
				fullName = fullName.substring(1, fullName.length()-1);
				System.out.println(fullName);
				
				String name1 = jo.get("name").toString();
				name1 = name1.substring(1, name1.length()-1);
				System.out.println(name1);
				
				String id1 = jo.get("id").toString();
				id1 = id1.substring(1, id1.length()-1);
				System.out.println(id1);
				
				String url1 = jo.get("subscription_url").toString();
				url1 = url1.substring(1, url1.length()-1);
				System.out.println(url1);
				
				
				
				
					
				
			Connection con=DriverManager.getConnection(url2,usr,psw);
			String sql="insert into git1(fullname,name,id,url) values(?,?,?,?)";
			PreparedStatement st1=con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			st1.setString(1,fullName);
			st1.setString(2, name1);
			st1.setString(3,id1);
			st1.setString(4,url1);
			st1.executeUpdate();
			}
 
		} catch (IOException ex) {
			System.out.println(ex.getStackTrace());
		}
	}
}

