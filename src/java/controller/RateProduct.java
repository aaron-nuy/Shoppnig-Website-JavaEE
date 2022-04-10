package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Manager;

@WebServlet("/RateProduct")
public class RateProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;


    public RateProduct() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("WEB-INF/view/index.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String rating = request.getParameter("rating");
		String product = request.getParameter("productID");
		
		int productID = Integer.parseInt(product);
		
		try {
			Manager.login(email, password);
			float rate = Float.parseFloat(rating) - 2.5f;
			Manager.updateUserReputation(Manager.getProduct(productID).getSupplier_id(), rate);
		}
		catch(Exception e) {
			response.getWriter().write("Trying to hack me, eh?");
		}
		
	}

}
