package com.dds.simulator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ResponseJobOfferServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public void doGet(HttpServletRequest reqest, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/html");
		
		PrintWriter out = response.getWriter();
		out.print("Response on Job Offer Simulator");
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
		    
		    JSONArray arr = jObj.getJSONArray("jobs");
			JSONObject jO = (JSONObject) arr.get(0);
			String secDeviceId = jO.getString("secDeviceId");
			String action = jO.getString("action");
		    
			System.out.format("secDeviceId: %s\n action: %s\n", secDeviceId,action);
				
			
			JSONObject jObject = new JSONObject();
			try {
				jObject.put("status", "200");
			
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			response.setContentType("application/json");
			response.getWriter().write(jObject.toString());
			
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
//		String token = request.getParameter("token");
//		String jobId = request.getParameter("jobId");
//		String currentTime = request.getParameter("currentTime");
//		String clientResp = request.getParameter("response");
//		String latitude = request.getParameter("latitude");
//		String longitude = request.getParameter("longitude");
//		
//		System.out.format("token: %s\n", token);
//		System.out.format("jobId: %s\n", jobId);
//		System.out.format("currentTime: %s\n", currentTime);
//		System.out.format("client Response: %s\n", clientResp);
//		System.out.format("latitude: %s\n", latitude);
//		System.out.format("longitude: %s\n", longitude);
//
//		try{
//		    Thread.sleep(2000);
//		} catch (Exception ex){
//		    Thread.interrupted();
//		}
//		
//		JSONObject jObject = new JSONObject();
//		try {
//			jObject.put("status", "500");
//		
//			if(token != null && jobId != null && currentTime != null
//					&& clientResp != null && latitude != null && longitude != null){
//				jObject.put("status", "200");
//			}
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		response.setContentType("application/json");
//		response.getWriter().write(jObject.toString());
		
	}
}
