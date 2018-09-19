/**
 * 
 */
package org.iita.accessions2.service;

import org.iita.ordering.SmtaProfileModel;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.google.gson.Gson;

/**
 * @author ken
 *
 */
public class SmtaProfileServiceImpl implements SmtaProfileService {

	@Override
	public SmtaProfileModel getProfile(String pid, String password) {
		SmtaProfileModel models = new SmtaProfileModel();
		boolean loginFailed = true;
		
		try {
			URL url = new URL("https://www.itworks.it/itt/index.php?r=extsys/userinfo");
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
			String esnm="iita";
			String espwd="Passw0rd";
			//String mypid="00AD80";
			//String pwd="Passw0rd";
			writer.write("esUsername="+esnm+"&esPassword="+espwd+"&pid="+pid+"&password="+password+"");
	         
			writer.flush();
			String jsonObject;
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			while ((jsonObject = reader.readLine()) != null) {
				//System.out.println(jsonObject); //Print out the Jsonformat here if you need to 
				//I convert to human readable object here using Google Gson conveter.
				Gson gson = new Gson();
				
				models = gson.fromJson(jsonObject, SmtaProfileModel.class);
				
				if(models.getPid()!=null)
					loginFailed = false;
				
			  /*System.out.println(models.getPid());
			  System.out.println(models.getType());
			  System.out.println(models.getLegalStatus());
			  System.out.println(models.getEmail());
			  System.out.println(models.getOrgname());
			  System.out.println(models.getOrgAddress());
			  System.out.println(models.getAoFax());
			  System.out.println(models.getOrgCountryName());
			  System.out.println(models.getAoName());
			  System.out.println("");
			  System.out.println("");
			  System.out.println(models.toString());*/
			}
			writer.close();
			reader.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("");
		//System.out.println("LoginFailed: " + loginFailed);
		if(loginFailed == true)
			return null;
		else
			return models;
	}
	
}
