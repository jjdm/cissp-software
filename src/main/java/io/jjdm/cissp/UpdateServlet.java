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
public class UpdateServlet extends HttpServlet {

	/**
	 * Update an item and return the view page.
	 *
	 * @param request The request.
	 * @param response The response.
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Item item = new Item();
		item.setId(Integer.valueOf(request.getParameter("id")));
		item.setName(request.getParameter("name"));
		item.setDescription(request.getParameter("description"));
		ItemDao dao = ItemDao.getInstance();
		dao.updateItem(item);
		request.setAttribute("item", item);
		response.sendRedirect("/view?id=" + item.getId());
	}

}
