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
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		User user = Manager.getUser(email, password);
		Product product = Manager.getProduct(Integer.parseInt(productID));

		try {
			if(product.getSupplier_id() == user.getId())
				Manager.updateProduct(productName,productDescription,productPrice,productID);
			else
				throw new RuntimeException("Trying to hack me, eh?");	
		}
		catch(RuntimeException e) {
			response.getWriter().write(e.getMessage());
		}
	}

}
