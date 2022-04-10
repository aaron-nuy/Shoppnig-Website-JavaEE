package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Manager;


@WebServlet("/Create")
public class CreateUser extends HttpServlet {
	private static final long serialVersionUID = 1L;


    public CreateUser() {
        super();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("WEB-INF/view/index.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String firstName = request.getParameter("fName");
		String lastName = request.getParameter("lName");
		String eMail = request.getParameter("cEmail").toLowerCase();
		String regNum = request.getParameter("suppID");
		String password = request.getParameter("cPassword");
		String birthdate = request.getParameter("date");
		String gender = request.getParameter("sex");
		
		try{
			Manager.createAccount(firstName,lastName,eMail,regNum,password,gender,birthdate);
			request.getRequestDispatcher("WEB-INF/view/index.jsp").forward(request, response);
		}
		catch(RuntimeException e) {
			response.getWriter().write(e.getMessage());
		}
	}

}
