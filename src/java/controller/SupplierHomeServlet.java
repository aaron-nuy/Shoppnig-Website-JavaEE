package controller;


import java.io.IOException;
import java.util.ArrayList;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Manager;
import model.dataTypes.Product;

@WebServlet("/SupplierHomeServlet")
public class SupplierHomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SupplierHomeServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("WEB-INF/view/index.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int userID = Integer.parseInt((String) request.getAttribute("userID"));
		String email = (String)request.getAttribute("email");
		String password = (String)request.getAttribute("password");

		try {
			ArrayList<Product> productList = Manager.getUserProducts(userID);

			request.setAttribute("email", email);
			request.setAttribute("password", password);
			request.setAttribute("productList", productList);
			request.getRequestDispatcher("WEB-INF/view/supplierhome.jsp").forward(request,response);

			
		}
		catch(RuntimeException e) {
			response.getWriter().write(e.getMessage());
		}

		
		
	}

}
