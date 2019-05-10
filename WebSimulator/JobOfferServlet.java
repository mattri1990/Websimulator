package com.dds.simulator;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JobOfferServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest reqest, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/html");
		
		PrintWriter out = response.getWriter();
		out.print("Job Offer Simulator");
		out.flush();
		out.close();
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String token = request.getParameter("token");
		String jobId = request.getParameter("jobId");

		System.out.format("token: %s\n", token);
		System.out.format("jobId: %s\n", jobId);

		try{
		    Thread.sleep(2000);
		} catch (Exception ex){
		    Thread.interrupted();
		}
		
		JSONObject jObject = new JSONObject();
		try {
			jObject.put("status", "500");
		
			if(token != null && jobId != null ){
				
				JSONArray jobOfferArray = new JSONArray();
				JSONObject item1 = new JSONObject();
				item1.put("label", "Job Type");
				item1.put("data", "SAP");
				JSONObject item2 = new JSONObject();
				item2.put("label", "Area");
				item2.put("data", "119");
				JSONObject item3 = new JSONObject();
				item3.put("label", "Pickup Address");
				item3.put("data", "11932 Forge Place, Richmond");
				JSONObject item4 = new JSONObject();
				item4.put("label", "Pickup Address");
				item4.put("data", "11932 Forge Place, Richmond");
				JSONObject item5 = new JSONObject();
				item5.put("label", "Pickup Address");
				item5.put("data", "11932 Forge Place, Richmond");
				JSONObject item6 = new JSONObject();
				item6.put("label", "Pickup Address");
				item6.put("data", "11932 Forge Place, Richmond");
				JSONObject item7 = new JSONObject();
				item7.put("label", "Pickup Address");
				item7.put("data", "11932 Forge Place, Richmond");
				JSONObject item8 = new JSONObject();
				item8.put("label", "Pickup Address");
				item8.put("data", "11932 Forge Place, Richmond");
				JSONObject item9 = new JSONObject();
				item9.put("label", "Pickup Address");
				item9.put("data", "11932 Forge Place, Richmond");
				JSONObject item10 = new JSONObject();
				item10.put("label", "Pickup Address");
				item10.put("data", "11932 Forge Place, Richmond");
				
				jobOfferArray.put(item1);
				jobOfferArray.put(item2);
				jobOfferArray.put(item3);
				jobOfferArray.put(item4);
				jobOfferArray.put(item5);
				jobOfferArray.put(item6);
				jobOfferArray.put(item7);
				jobOfferArray.put(item8);
				jobOfferArray.put(item9);
				jobOfferArray.put(item10);
				
				jObject.put("jobId", "1324");
				jObject.put("rejectButton", true);
				jObject.put("jobOffer", jobOfferArray);
				String now = Long.toString(new Date().getTime());
				jObject.put("currentTime", now);
				jObject.put("autoRejectTimeout", "60");
				jObject.put("backInVehicleTimeout", "120");
				jObject.put("streetHireButton", true);
				jObject.put("status", "200");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.setContentType("application/json");
		response.getWriter().write(jObject.toString());
		
	}
}
