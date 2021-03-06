package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Manager;


@WebServlet("/Buy")
public class Buy extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public Buy() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("WEB-INF/view/index.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String productList = request.getParameter("products");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		int userID = Manager.getUser(email,password).getId();
		
		Manager.processPurchase(productList,userID);
		
		request.getRequestDispatcher("WEB-INF/view/Success.jsp").forward(request, response);
	}

}
