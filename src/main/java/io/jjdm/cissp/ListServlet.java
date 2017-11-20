package io.jjdm.cissp;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Lists the items currently in the database.
 *
 * @author Josh Martin (josh.martin@gmail.com)
 */
public class ListServlet extends HttpServlet {

	/**
	 * Lists the items currently in the database.
	 *
	 * @param request The request.
	 * @param response The response.
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ItemDao dao = ItemDao.getInstance();
		List<Item> items = dao.getItems();
		request.setAttribute("items", items);
		request.getRequestDispatcher("WEB-INF/pages/list.jsp").forward(request, response);
	}

}
