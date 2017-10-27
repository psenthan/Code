/*
*  Copyright (c) Oct 26, 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.*;
import org.joda.time.*;




import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class CalculatePull {
                  
                private static String Tokenkey = "61b60cc1994a1fdcbe76f36d4a91666d34dc9e34";
                
                public static void main(String[] args) throws  ParseException {
                    try {
                        getPull1();
                    } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                   
                }
             
                public static void getPull1() throws ParseException, SQLException {
                    String baseURL = "https://api.github.com";
                    String url = "https://api.github.com/orgs/wso2/repos?page=2&per_page=100'";
                    String url2="jdbc:mysql://localhost:3306/GitHubManipulate?useSSL=false";
                    String usr="root";
                    String psw="wso2123";
                    
                
                    try {
                        
                        CloseableHttpClient httpClient1 = HttpClientBuilder.create().build();
                        HttpGet request1 = new HttpGet(url);
                        request1.addHeader("content-type", "application/json");
                        request1.addHeader("Authorization", "Bearer " + Tokenkey);
                        HttpResponse result1 = httpClient1.execute(request1);
                        String repo_json = EntityUtils.toString(result1.getEntity(), "UTF-8");
             
                        //System.out.println(repo_json);
             
                        JsonElement jelement1 = new JsonParser().parse(repo_json);
                        JsonArray jarr1 = jelement1.getAsJsonArray();
                        
                                for (int i = 0; i < jarr1.size(); i++) {
                                    JsonObject jo1 = (JsonObject) jarr1.get(i);
                                  
                                    String RepoName = jo1.get("name").toString();
                                    RepoName = RepoName.substring(1, RepoName.length()-1);
                                    System.out.println("Name of the repository   :"+RepoName);
                                    
                                    
                                    
                                    
                                    CloseableHttpClient httpClient2 = HttpClientBuilder.create().build();
                                    HttpGet request2 = new HttpGet(baseURL + "/repos/wso2/"+RepoName+"/pulls");
                                    request2.addHeader("Authorization", "Bearer " + Tokenkey);
                                   HttpResponse result2 = httpClient2.execute(request2);
                                    String pulls_json = EntityUtils.toString(result2.getEntity(), "UTF-8");
                                    
                                    //System.out.println(pulls_json);
                                    
                                    JsonElement jelement2 = new JsonParser().parse(pulls_json);
                                    JsonArray jarr2 = jelement2.getAsJsonArray();
                                    
                                    int arraySize = jarr2.size();
                                    System.out.println("No of open pull_request  :"+arraySize);
                                    System.out.println();
                                   
                            

                            
                            
                                        for (int y = 0; y < jarr2.size(); y++) {
                                                JsonObject jo2 = (JsonObject) jarr2.get(y);
                                    
                                    
                                    
                                                    String pullsCreatedOn = jo2.get("created_at").toString();
                                                    pullsCreatedOn = pullsCreatedOn.substring(1, pullsCreatedOn.length()-1);
                                                    System.out.print("Pull created on  :"+pullsCreatedOn);
                                   
                              
                               
                               
                           
                                                   SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                                                   Date date1 = new Date();
                                                   String dateStr = df.format(date1);
                                                   //System.out.println("Current Date     :"+dateStr);
                                                   System.out.println();
                                                   
                                                   
                                                   
                                                    Date d1 = null;
                                                    Date d2 = null;
                                                   
                                             
                                                                                 
                                                   d1 = df.parse(pullsCreatedOn);
                                                   d2 = df.parse(dateStr);
                                                   DateTime dt1 = new DateTime(d1);
                                                   DateTime dt2 = new DateTime(d2); 
                                                   System.out.print("Is open for  : ");
                                                   System.out.print(Days.daysBetween(dt1, dt2).getDays() + " days, ");
                                                   System.out.print(Hours.hoursBetween(dt1, dt2).getHours() % 24 + " hours, ");
                                                   System.out.print(Minutes.minutesBetween(dt1, dt2).getMinutes() % 60 + " minutes, ");
                                                   System.out.println(Seconds.secondsBetween(dt1, dt2).getSeconds() % 60 + " seconds.");
                                                   System.out.println();
                                                   
//                                                   String Date=System.out.println(Days.daysBetween(dt1, dt2).getDays());
                                                   
                                                   Connection con=DriverManager.getConnection(url2,usr,psw);
                                                   String sql="insert into table1(Repo_name,NumberOfOpenPulls,NumberOfOpenDaysOfPull) values(?,?,?)";
                                                   PreparedStatement st1=con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
                                                   st1.setString(1,RepoName);
                                                   st1.setInt(2,(arraySize));
                                                   st1.setInt(3,(Days.daysBetween(dt1, dt2).getDays()));
                                                   
                                                   st1.executeUpdate();
                                                   
                                                   

                                                        

                                                   
                                                   
                                                                            }
                            


                        
                                                                    }
                    
                    }
                        
             
                    catch (IOException ex) {
                        System.out.println(ex.getStackTrace());
                    }
                }
        

    }


