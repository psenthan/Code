package javawebservicerest1;


import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;


public class javaWebserviceRest {
	
	public static void main(String args[]) throws  IOException {
		HttpClient client;
		client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet("http://api.fixer.io/latest");
		request.addHeader("content-type","application/json");
		HttpResponse response = client.execute(request);
		
		String json = EntityUtils.toString(response.getEntity(), "UTF-8");
		 
		System.out.println(json);
		
			
			
			
			
		
		
		
		
	}

}


