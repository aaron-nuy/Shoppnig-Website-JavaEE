package controller;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import model.dataTypes.User;
import model.dbManager.Manager;


@WebServlet("/Login")
public class LoginUser extends HttpServlet {
	private static final long serialVersionUID = 1L;


    public LoginUser() {
        super();

    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("WEB-INF/view/index.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");

			
		try {
			User user = Manager.login(email,password);
			if(user.getType().equals("user")) {
				request.setAttribute("userID", String.valueOf(user.getId()));
				request.getRequestDispatcher("UserHomeServlet").forward(request, response);
			}
			else if(user.getType().equals("supplier")) {
				request.setAttribute("userID", String.valueOf(user.getId()));
				request.getRequestDispatcher("SupplierHomeServlet").forward(request, response);

			}
			else if(user.getType().equals("admin")) {
				request.getRequestDispatcher("AdminHomeServlet").forward(request, response);
			}
		}
		catch(Exception e) {
			response.getWriter().write(e.getMessage());
		}

		
	}

}
