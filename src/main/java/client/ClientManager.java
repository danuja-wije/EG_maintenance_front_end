//import models
package client; 

//import packages
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;

public class ClientManager {

	public String getCurrentUser(String auth) {
		
		//Preprocess encoded String
		
		String[] authParts = auth.split("\\s+");
		String token = authParts[1]; //take encoded token
		String output = "";
		try {
			//create jersey client object
			
			Client client = Client.create();

			Builder webResource = client
					.resource("http://localhost:8080/Electro_Grid_Power_Login/Validation/Login/Validate").header(HttpHeaders.AUTHORIZATION, "Basic " + token);

			
			ClientResponse response = webResource.type(MediaType.TEXT_PLAIN).get(ClientResponse.class);


			System.out.println("Output from Server .... \n");
			output = response.getEntity(String.class);
			
			//check whether current user login details are correct or not
			
			if(output.equals("True")) {
				Builder res = client
						.resource("http://localhost:8080/Electro_Grid_Power_Login/Validation/Login/User").header(HttpHeaders.AUTHORIZATION, "Basic " + token);


				ClientResponse c_res = res.type(MediaType.TEXT_PLAIN).get(ClientResponse.class);
				output = c_res.getEntity(String.class);
				//System.out.println(output);
			}else {
				output = "login failed";
			}


		} catch (Exception e) {

			e.printStackTrace();

		}
		return output;

	}

}
