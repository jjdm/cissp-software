package io.jjdm.cissp;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * View a particular item.
 *
 * @author Josh Martin (josh.martin@gmail.com)
 */
public class ViewServlet extends HttpServlet {

	/**
	 * View a particular item.
	 *
	 * @param request The request.
	 * @param response The response.
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer id = Integer.valueOf(request.getParameter("id"));
		ItemDao dao = ItemDao.getInstance();
		Item item = dao.getItem(id);
		request.setAttribute("item", item);
		request.getRequestDispatcher("WEB-INF/pages/view.jsp").forward(request, response);
	}

}
