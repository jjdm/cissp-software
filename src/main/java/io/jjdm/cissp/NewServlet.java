package io.jjdm.cissp;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Update an item and return the view page.
 *
 * @author Josh Martin (josh.martin@gmail.com)
 */
public class NewServlet extends HttpServlet {

	/**
	 * Update an item and return the view page.
	 *
	 * @param request The request.
	 * @param response The response.
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("WEB-INF/pages/new.jsp").forward(request, response);
	}

}
