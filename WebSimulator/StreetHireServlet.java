package com.dds.simulator;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet implementation class StreetHireServlet
 */
public class StreetHireServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StreetHireServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		
		PrintWriter out = response.getWriter();
		out.print("Street Hire Simulator");
		out.flush();
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String token = request.getParameter("token");
		String jobId = request.getParameter("jobId");
		String currentTime = request.getParameter("currentTime");
		
		System.out.format("token: %s\n", token);
		System.out.format("jobId: %s\n", jobId);
		System.out.format("currentTime: %s\n", currentTime);
		
		try{
		    Thread.sleep(2000);
		} catch (Exception ex){
		    Thread.interrupted();
		}
		
		JSONObject jObject = new JSONObject();
		try {
			jObject.put("status", "500");
		
			if(token != null && jobId != null && currentTime != null){
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
