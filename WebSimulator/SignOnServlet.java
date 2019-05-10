package com.dds.simulator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SignOnServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest reqest, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/html");
		
		PrintWriter out = response.getWriter();
		out.print("Sign On Simulator");
		out.flush();
		out.close();
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			StringBuilder sb = new StringBuilder();
		    BufferedReader br = request.getReader();
		    String str;
		    while( (str = br.readLine()) != null ){
		        sb.append(str);
		    }    
		    JSONObject jObj = new JSONObject(sb.toString());
		    
		    JSONArray arr = jObj.getJSONArray("driverApps");
			JSONObject jO = (JSONObject) arr.get(0);
			String userId = jO.getString("driverId");
			String passwd = jO.getString("password");
			String version = jO.getString("version");
			String protocol = jO.getString("protocol");
			String type = jO.getString("type");
			String vehicleId = jO.getString("vehicleId");
			String companyId = jO.getString("companyId");
		    
			System.out.format("driverId: %s\n password: %s\n version: %s\n protocol: %s\n type: %s\n vehicleId: %s\n companyId: %s\n",userId, passwd,version,protocol,type,vehicleId,companyId);
//			JSONObject newObj = new JSONObject(request.getParameter("driverApps"));
//			JSONArray arr = newObj.getJSONArray("driverApps");
//			JSONObject jObj = (JSONObject) arr.get(0);
//			String userId = jObj.getString("driverId");
			
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//String[] driverApp = request.getParameter("driverApps");


		try{
		    Thread.sleep(2000);
		} catch (Exception ex){
		    Thread.interrupted();
		}
		JSONObject jObject = new JSONObject();
		JSONObject finalObject = new JSONObject();
		try {
			jObject.put("status", "500");
			
			jObject.put("secDeviceId", "de5bbde8-8d1d-438b-afc4-a44dfe4eb6f8");
			jObject.put("driverId", "12");
			jObject.put("company", "DDS Wireless");

			JSONArray jArray = new JSONArray();
			jArray.put(jObject);
			
			finalObject.put("driverApps", jArray);
			finalObject.put("linked", "");
			finalObject.put("meta", "");
				
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.setContentType("application/json");
		response.getWriter().write(finalObject.toString());
		
	}
}
