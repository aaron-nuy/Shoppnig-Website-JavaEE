package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dbManager.Manager;


@WebServlet("/UpdateProduct")
public class UpdateProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UpdateProduct() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("WEB-INF/view/index.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String productName = request.getParameter("productName");
		String productDescription = request.getParameter("productDescription");
		String productPrice = request.getParameter("productPrice");
		String productID = request.getParameter("productID");

		try {
			Manager.updateProduct(productName,productDescription,productPrice,productID);
		}
		catch(RuntimeException e) {
			response.getWriter().write(e.getMessage());
			System.out.println(e.getMessage());		}
	}

}
