package webservicerest;


	import java.io.IOException;
	import org.apache.http.HttpResponse;
	import org.apache.http.client.methods.HttpGet;
	import org.apache.http.impl.client.CloseableHttpClient;
	import org.apache.http.impl.client.HttpClientBuilder;
	import org.apache.http.util.EntityUtils;
	import com.google.gson.JsonArray;
	import com.google.gson.JsonElement;
	import com.google.gson.JsonObject;
	import com.google.gson.JsonParser;
	 
	public class Github1 {
	 
		public static void main(String[] args) {
			for (int i = 0; i < 1000000;) {
				getEach(i);
				i = i + 200; 
			}
		}
	 
		public static void getEach(int since) {
			String url = "https://api.github.com/orgs/wso2/repos";
			//String url = "https://api.github.com/repositories?since=" + since;
			try {
				CloseableHttpClient httpClient = HttpClientBuilder.create().build();
				HttpGet request = new HttpGet(url);
				request.addHeader("content-type", "application/json");
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
				}
	 
			} catch (IOException ex) {
				System.out.println(ex.getStackTrace());
			}
		}
	}
	


