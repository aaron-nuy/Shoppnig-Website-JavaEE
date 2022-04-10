package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Manager;
import model.dataTypes.User;

@WebServlet("/AdminHomeServlet")
public class AdminHomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AdminHomeServlet() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("WEB-INF/view/index.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			ArrayList<User> userList = Manager.getAllSuppliers();
			String email = (String)request.getAttribute("email");
			String password = (String)request.getAttribute("password");
			request.setAttribute("email", email);
			request.setAttribute("password", password);
			
			request.setAttribute("userList", userList);
			request.getRequestDispatcher("WEB-INF/view/adminhome.jsp").forward(request,response);
		}
		catch(RuntimeException e) {
			response.getWriter().write(e.getMessage());
		}
	}

}
