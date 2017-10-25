

/*
*  Copyright (c) Oct 19, 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
*  WSO2 Inc. licenses this file to you under the Apache License,
*  Version 2.0 (the "License"); you may not use this file except
*  in compliance with the License.
*  You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied.  See the License for the
* specific language governing permissions and limitations
* under the License.
*/
package github_analysis;

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

public class PullReq_Manipulate {
          
        private static String Tokenkey = "d7d3fadfe2613e5214d6e8fcb0e4f1c699fe021f";
        
        public static void main(String[] args) {
            getPull();         
        }
        
     
        public static void getPull(){
            String baseURL = "https://api.github.com";
            String url = "https://api.github.com/orgs/wso2/repos";
            try {
                
                CloseableHttpClient httpClient = HttpClientBuilder.create().build();
                HttpGet request = new HttpGet(url);
                request.addHeader("content-type", "application/json");
                request.addHeader("Authorization", "Bearer " + Tokenkey);
                HttpResponse result = httpClient.execute(request);
                String RepoJson = EntityUtils.toString(result.getEntity(), "UTF-8");
     
               // System.out.println(RepoJson);
     
                JsonElement jelement = new JsonParser().parse(RepoJson);
                JsonArray jarr = jelement.getAsJsonArray();
                
                for (int i = 0; i < jarr.size(); i++) {
                    JsonObject jo = (JsonObject) jarr.get(i);
                  
                    String RepoName = jo.get("name").toString();
                    RepoName = RepoName.substring(1, RepoName.length()-1);
                    System.out.println("Name of the repository :"+RepoName);
                   
                    
   
                   
                    HttpGet request1 = new HttpGet(baseURL + "/repos/wso2/"+RepoName+"/pulls?state=open");
                    //But with out put the state=open default also state is open
                    request1.addHeader("Authorization", "Bearer " + Tokenkey);
                    HttpResponse result1 = httpClient.execute(request1);
                    String json1 = EntityUtils.toString(result1.getEntity(), "UTF-8");
                    
                    JsonElement jelement1 = new JsonParser().parse(json1);
                   //System.out.println(request1.getURI());
                    JsonArray jarr1 = jelement1.getAsJsonArray();
                    
                    
                    
                    
                    int arraySize = jarr1.size();
                    System.out.println("No of open pull :"+arraySize);
                    System.out.println();

   
            }
            }
                
     
            catch (IOException ex) {
                System.out.println(ex.getStackTrace());
            }
        }
}