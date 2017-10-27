/*
*  Copyright (c) Oct 27, 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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

import java.sql.*;

public class ProjectSQL {
   

        
        public static void main(String[] args) {
            
            try {
                Connection myConn=DriverManager.getConnection("jdbc:mysql://localhost:3306/GitHubManipulate?useSSL=false","root","wso2123"); //Get a connection to employee
                Statement myst=myConn.createStatement(); //create a statement object
                
                
                String query = " select Repo_name,NumberOfOpenPulls,max(NumberOfOpenDaysOfPull),min(NumberOfOpenDaysOfPull),avg(NumberOfOpenDaysOfPull) from table1 group by Repo_name,NumberOfOpenPulls;" ;
                
                
                ResultSet result=myst.executeQuery(query);
                		// Execute sql query
                
                //Process the result set
                while(result.next())
                {
                   System.out.println("Repository-name: "+result.getString("Repo_name"));
                   System.out.println("Number of open pull_request: "+result.getInt("NumberOfOpenPulls"));
                   System.out.println("Number of  maximum days pull request in open state: "+result.getInt("max(NumberOfOpenDaysOfPull)")+" days");
                   System.out.println("Number of  minimum days pull request in open state: "+result.getInt("min(NumberOfOpenDaysOfPull)")+" days");
                   System.out.println("Number of  average days pull request in open state: "+result.getInt("avg(NumberOfOpenDaysOfPull)")+" days");
                   System.out.println();
                
                   
                }
                
                
                
                
                
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }


