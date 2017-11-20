package io.jjdm.cissp;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Drops the database table and reinitializes it.
 *
 * @author Josh Martin (josh.martin@gmail.com)
 */
public class ResetServlet extends HttpServlet {

	/**
	 * List the current items in the database.
	 *
	 * @param request The request.
	 * @param response The response.
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ItemDao dao = ItemDao.getInstance();
		dao.dropTable();
		dao.initialize();
		response.sendRedirect("/list");
	}

}
