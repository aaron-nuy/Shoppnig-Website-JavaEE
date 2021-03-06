package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Manager;
import model.dataTypes.Product;
import model.dataTypes.User;

@WebServlet("/DeleteProduct")
public class DeleteProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DeleteProduct() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("WEB-INF/view/index.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			int productID = Integer.parseInt(request.getParameter("productID"));
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			User user = Manager.getUser(email, password);
			Product product = Manager.getProduct(productID);
			
			if(product.getSupplier_id() == user.getId())
				Manager.deleteProduct(productID);
			else
				throw new RuntimeException("Trying to hack me, huh?");
		}
		catch(RuntimeException e) {
			response.getWriter().write(e.getMessage());
		}
	}

}
