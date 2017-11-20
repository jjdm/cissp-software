package io.jjdm.cissp;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Delete an item from the database.
 *
 * @author Josh Martin (josh.martin@gmail.com)
 */
public class DeleteServlet extends HttpServlet {

	/**
	 * Delete an item from the database.
	 *
	 * @param request The request.
	 * @param response The response.
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer id = Integer.valueOf(request.getParameter("id"));
		ItemDao dao = ItemDao.getInstance();
		dao.deleteItem(id);
		response.sendRedirect("/list");
	}

}
