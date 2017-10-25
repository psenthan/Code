package webservicerest;



import com.google.gson.Gson;

public class Json_manipulate {

	
	public static void main(String[] args) {
		
		 
		         
		        Employee emp = new Employee();
		        emp.setEmpId(007);
		        emp.setName("Senthan");
		        emp.setSalary(20000);
		        emp.setDesignation("Software Engineering intern");
		        
		         
		        Gson gsonObj = new Gson();
		        // converts object to json string
		        String jsonStr = gsonObj.toJson(emp);
		        System.out.println(jsonStr);
		    }
		
		

	}


