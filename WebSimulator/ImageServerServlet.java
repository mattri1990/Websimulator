
package com.dds.simulator;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ImageServerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ImageServerServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException {
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		BufferedOutputStream output = null;
		try {
			ServletContext servletContext = getServletContext();
			String fileName = request.getParameter("image");
			ServletContext context = getServletConfig().getServletContext(); 
			String pathname =context.getRealPath("/images/" + fileName); 
			
			fis = new FileInputStream(new File(pathname));
			bis = new BufferedInputStream(fis);
			response.setContentType("image/jpeg");
			output = new BufferedOutputStream(response.getOutputStream());
			for (int data; (data = bis.read()) > -1;) {
				output.write(data);
			}
		} catch (IOException e) {
			
		}
		finally{
			try {
				output.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				bis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
