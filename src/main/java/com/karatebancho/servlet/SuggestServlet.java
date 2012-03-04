package com.karatebancho.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.arnx.jsonic.JSON;

public class SuggestServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("readings", "hoge");
		map.put("basicform", "basic");
		resp.getWriter().write("<html>" + JSON.encode(map) + "</html>");
	}

}
