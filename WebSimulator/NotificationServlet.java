package com.dds.simulator;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;






import java.util.Random;

import javax.net.ssl.SSLHandshakeException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Message.Builder;
import com.google.android.gcm.server.Sender;
import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;
import com.notnoop.apns.BinaryInterfaceApnsNotification;
import com.notnoop.apns.EnhancedApnsNotification;
import com.notnoop.apns.SimpleApnsNotification;
import com.relayrides.pushy.apns.ApnsEnvironment;
import com.relayrides.pushy.apns.DeliveryPriority;
import com.relayrides.pushy.apns.ExpiredToken;
import com.relayrides.pushy.apns.ExpiredTokenListener;
import com.relayrides.pushy.apns.FailedConnectionListener;
import com.relayrides.pushy.apns.PushManager;
import com.relayrides.pushy.apns.PushManagerConfiguration;
import com.relayrides.pushy.apns.RejectedNotificationListener;
import com.relayrides.pushy.apns.RejectedNotificationReason;
import com.relayrides.pushy.apns.util.ApnsPayloadBuilder;
import com.relayrides.pushy.apns.util.MalformedTokenStringException;
import com.relayrides.pushy.apns.util.SSLContextUtil;
import com.relayrides.pushy.apns.util.SimpleApnsPushNotification;
import com.relayrides.pushy.apns.util.TokenUtil;

/**
 * Servlet implementation class NotificationServlet
 */
public class NotificationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	static ArrayList<HashMap<String, String>> deviceTokenList = new ArrayList<HashMap<String,String>>();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NotificationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO: testing purpose.
			
		String action = request.getParameter("action");
		String jobId = request.getParameter("jobId");
		
		String msg = "";
		if(deviceTokenList.size() <= 0){
			msg = "Error! There is no device id.";
		}
		else if(sendNotification(action, jobId)){
			msg = "sending notification " + action +" success.";
		}
		else{
			msg = "sending notification " + action + " failed.";
		}
		
		response.setContentType("text/html");
		
		PrintWriter out = response.getWriter();
		out.print("Notification Simulator: " + msg);
		out.flush();
		out.close();
		
	}
	
	private boolean sendNotification(String action, String jobId){
		Random rand = new Random();
		int  JOB_ID = 0;
		if(jobId == null || jobId.isEmpty())
			JOB_ID = rand.nextInt(35350) + 1;
		else
			JOB_ID = Integer.parseInt(jobId);
		for(HashMap<String,String> aMap : NotificationServlet.deviceTokenList){
			String deviceToken = aMap.get("deviceToken");
			String platform = aMap.get("platform");
	
			if(action.equalsIgnoreCase("connectionStarted")){
				
				JSONObject jObject = new JSONObject();
				String message = "";
				try {
					jObject.put("eId", 3);
					
					 message = jObject.toString();
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if(platform.equalsIgnoreCase("ios")){
					sendIOSNotification2(deviceToken,"Connection Started", message,3, jobId);
				}
				else if(platform.equalsIgnoreCase("android")){
					sendAndroidNotification(deviceToken,"Connection Started", message);
				
				}
			}
			else if(action.equalsIgnoreCase("connectionClose")){
				JSONObject jObject = new JSONObject();
				String message = "";
				try {
					jObject.put("eId", 4);
					
					 message = jObject.toString();
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if(platform.equalsIgnoreCase("ios")){
					sendIOSNotification2(deviceToken,"Connection Close",message,4, jobId);

				}
				else if(platform.equalsIgnoreCase("android")){
					sendAndroidNotification(deviceToken,"Connection Close", message);

				}
			}
			else if(action.equalsIgnoreCase("jobOffer")){
				JSONObject jObject = new JSONObject();
				JSONArray jArray = new JSONArray();
				String message = "";
				try {
					jArray.put(new JSONObject("{\"label\": \"Type\", \"data\":\"SAP\"}"));
					jArray.put(new JSONObject("{\"label\": \"Zone\", \"data\":\"546\"}"));
					jArray.put(new JSONObject("{\"label\": \"Area\", \"data\":\"119\"}"));
					jArray.put(new JSONObject("{\"label\": \"Number of Passenger\", \"data\":\"3\"}"));
					jArray.put(new JSONObject("{\"label\": \"Number of Stop\", \"data\":\"1\"}"));
					jArray.put(new JSONObject("{\"label\": \"Pickup Address\", \"data\":\"12345678901234567890123456789012345678901234567890123456789012345678901234567890, Richmond\"}"));
					jArray.put(new JSONObject("{\"label\": \"Dropoff Address\", \"data\":\"7798 No.3 Road, Richmond\"}"));
					jArray.put(new JSONObject("{\"label\": \"Attributes\", \"data\":\"V_1, V_2\"}"));
					jArray.put(new JSONObject("{\"label\": \"Payment Type\", \"data\":\"CASH\"}"));
					jArray.put(new JSONObject("{\"label\": \"Layout Amount\", \"data\":\"0.0\"}"));
					jArray.put(new JSONObject("{\"label\": \"Fixed Price\", \"data\":\"0.0\"}"));
					
					jObject.put("jobOffer", jArray);
					jObject.put("autoRejectTO", 30);
					jObject.put("backInVehTO", 30);
					jObject.put("streetHire", true);
					jObject.put("cId", 3);
					jObject.put("eId", 1);
					jObject.put("jobId", JOB_ID);
					
					 message = jObject.toString();
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				if(platform.equalsIgnoreCase("ios")){
					sendIOSNotification2(deviceToken,"New Job Received", message, 1, jobId);

				}
				else if(platform.equalsIgnoreCase("android")){
					sendAndroidNotification(deviceToken,"New Job Received", message);

				}
			}
			else if(action.equalsIgnoreCase("jobCancel")){
				JSONObject jObject = new JSONObject();
				String message = "";
				try {
					jObject.put("eId", 2);
					jObject.put("jobId", jobId);
					
					 message = jObject.toString();
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if(platform.equals("ios")){
					sendIOSNotification2(deviceToken,"Job has Canceled",message, 2,jobId);

				}
				else if(platform.equals("android")){
					sendAndroidNotification(deviceToken,"Job has Canceled", message);

				}
			}
			else{
				return false;
			}
		}
		return true;
		
	}
	private static String combine(String path1, String path2)
	{
	    File file1 = new File(path1);
	    File file2 = new File(file1, path2);
	    return file2.getPath();
	}
	private void sendIOSNotification2(String deviceToken, String title, String message, int eId, String jobId){
		
		String filename = "/WEB-INF/PushNotificationProductionCertificates.p12";
		//String filename = "/WEB-INF/DDSCertificates.p12" ; 
		
		ServletContext context = getServletConfig().getServletContext(); 
		String pathname =context.getRealPath(filename); 
		
		
		//if(pathname == null) return;
		
		
		ApnsService service =
			    APNS.newService()
			    //.withCert(pathname, "summer12")
			    .withCert(pathname, "ses#UTetu4?E")
			    //.withSandboxDestination()
			    .withProductionDestination() 
			    .build();
		String payload = null;
		if(eId == 1){
			//payload = APNS.newPayload().alertBody(message)
			//	      .badge(0)
				      //.sound("/alert.mp3")
			//	      .build();
			Random rand = new Random();
			int  JOB_ID = 0;
			if(jobId == null || jobId.isEmpty())
				JOB_ID = rand.nextInt(35350) + 1;
			else
				JOB_ID = Integer.parseInt(jobId);
			
			payload = "{\"aps\":{\"alert\":\"New job received, ID:" + JOB_ID + "\",\"badge\":1,\"sound\":\"default\",\"content-available\":1},\"eId\":1,\"cId\":2,\"jobId\":" + JOB_ID + ",\"autoRejectTO\":30," +
						"\"backInVehTO\":60,\"streetHire\":true,\"jobOffer\":" +
						"[{\"label\":\"Pickup\",\"data\":\"11920 ForgePlace, Richmond\"}," +
						"{\"label\":\"Dropoff\",\"data\":\"As Directed\"}," +
						"{\"label\":\"Job Type\",\"data\":\"SAP\"}," +
						"{\"label\":\"Payment\",\"data\":\"CASH\"}," +
						"{\"label\":\"Zone\",\"data\":\"46\"}," +
						"{\"label\":\"Area\",\"data\":\"Area\"}," + 
						"{\"label\":\"Passenger\",\"data\":\"1\"}," +
						"{\"label\":\"Stops\",\"data\":\"1\"}," +
						"{\"label\":\"Layout\",\"data\":\"1.00\"}," +
						"{\"label\":\"Fixed Price\",\"data\":\"2.00\"}]}";
		}
		else if(eId == 2){
			payload ="{\"aps\":{\"badge\":0,\"sound\":\"default\",\"content-available\":1},\"eId\":" + eId + " ,\"jobId\":" + jobId + "}";
		}
		else{
			payload ="{\"aps\":{\"badge\":0,\"sound\":\"default\",\"content-available\":1},\"eId\":" + eId + "}";
		}
		
		
		int now =  (int)(new Date().getTime()/1000);

//		 EnhancedApnsNotification notification = new EnhancedApnsNotification(EnhancedApnsNotification.INCREMENT_ID() /* Next ID */,
//		     now + 60 * 60 /* Expire in one hour */,
//		     deviceToken /* Device Token */,
//		     payload);
//		 
//		BinaryInterfaceApnsNotification notification = new BinaryInterfaceApnsNotification(BinaryInterfaceApnsNotification.INCREMENT_ID(),-1,-1,
//				deviceToken, payload);

		// service.push(notification);
		service.push(deviceToken,payload);
		//SimpleApnsNotification notification = new SimpleApnsNotification(deviceToken, payload);
		//service.push("83e84fd79484a1f3ec9ea620456e23497212ee3d0e91158063f6b5e82ee3869d",payload);
		
	}


	
	private void sendAndroidNotification(String deviceToken, String title, String message){
		Sender sender = new Sender("AIzaSyAJoo7ysCxjSNP8VCykuXqZlYmcKbvYRvs");
		
		//jObject.put(arg0, arg1)
		Message msg =  new Message.Builder()
			.addData("json", message)
			.build();
		try {
			sender.send(msg, deviceToken, 0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String s = request.getHeader("Authorization");
			java.util.Enumeration<String> headerNames= request.getHeaderNames();
			while(headerNames.hasMoreElements()){
				String name = headerNames.nextElement();
				System.out.println(name);
				System.out.println(request.getHeader(name));
			}
			System.out.println(s);
			
			StringBuilder sb = new StringBuilder();
		    BufferedReader br = request.getReader();
		    String str;
		    while( (str = br.readLine()) != null ){
		        sb.append(str);
		    }    
		    JSONObject jObj = new JSONObject(sb.toString());
		    
		    JSONArray arr = jObj.getJSONArray("driverApps");
			JSONObject jO = (JSONObject) arr.get(0);
			String notiftoken = jO.getString("notiftoken");
			String secDeviceId = jO.getString("secDeviceId");
			String type = jO.getString("type");
		    
			System.out.format("notiftoken: %s\n secDeviceId: %s\n type: %s\n",notiftoken, secDeviceId,type);
//			JSONObject newObj = new JSONObject(request.getParameter("driverApps"));
//			JSONArray arr = newObj.getJSONArray("driverApps");
//			JSONObject jObj = (JSONObject) arr.get(0);
//			String userId = jObj.getString("driverId");
			HashMap<String, String> map = new HashMap<String,String>();
			map.put("deviceToken", notiftoken);
			if(type.contains("android") || type.contains("Android")){
				map.put("platform", "android");
			}
			else{
				map.put("platform", "ios");
			}
			
			
			boolean found = false;
			for(HashMap<String,String> aMap : deviceTokenList){
				if(aMap.get("deviceToken").equals(notiftoken)){
					found = true;
					break;
				}
			}
			if(!found){
				
				deviceTokenList.add(map);
			}
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
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
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
			
			if(!userId.equalsIgnoreCase("2") || !passwd.equalsIgnoreCase("2")) return;
		    
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
			jObject.put("driver", "John Hook");	
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
