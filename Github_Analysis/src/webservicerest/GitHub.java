package webservicerest;

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
    
 
    private static String Tokenkey = "d7d3fadfe2613e5214d6e8fcb0e4f1c699fe021f";
    
	public static void main(String[] args) throws SQLException {
	
			getEach();
			
		}
	
 
	public static void getEach() throws SQLException {
		String url = "https://api.github.com/orgs/wso2/repos";
	String url2="jdbc:mysql://localhost:3306/git?useSSL=false";
	String user="root";
	String password="wso2123";
		try {
			
			CloseableHttpClient httpClient = HttpClientBuilder.create().build();
			HttpGet request = new HttpGet(url);
			request.addHeader("content-type", "application/json");
			request.addHeader("Authorization", "Bearer " + Tokenkey); //Passing a header to increase API calls
			HttpResponse result = httpClient.execute(request);
			String json = EntityUtils.toString(result.getEntity(), "UTF-8");
 
			//System.out.println(json);
 
			JsonElement jelement = new JsonParser().parse(json);
			JsonArray jarr = jelement.getAsJsonArray();
			
			
			for (int i = 0; i < jarr.size(); i++) {
				JsonObject jo = (JsonObject) jarr.get(i);
				String fullName = jo.get("full_name").toString();
				fullName = fullName.substring(1, fullName.length()-1);
				System.out.println(fullName);
				
				String name = jo.get("name").toString();
				name = name.substring(1, name.length()-1);
				System.out.println(name);
				
				String id = jo.get("id").toString();
				id = id.substring(1, id.length()-1);
				System.out.println(id);
				
				String url1 = jo.get("subscription_url").toString();
				url1 = url1.substring(1, url1.length()-1);
				System.out.println(url1);
				
				
				
				
					
				
			Connection con=DriverManager.getConnection(url2,user,password);
			String sql="insert into git1(fullname,name,id,url) values(?,?,?,?)";
			PreparedStatement st1=con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			st1.setString(1,fullName);
			st1.setString(2, name);
			st1.setString(3,id);
			st1.setString(4,url1);
			st1.executeUpdate();
			}
 
		} catch (IOException ex) {
			System.out.println(ex.getStackTrace());
		}
	}
}

