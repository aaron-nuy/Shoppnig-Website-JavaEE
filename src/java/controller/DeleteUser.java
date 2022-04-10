package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Manager;
import model.dataTypes.User;

@WebServlet("/DeleteUser")
public class DeleteUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DeleteUser() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("WEB-INF/view/index.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			int userID = Integer.parseInt(request.getParameter("userID"));
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			User user = Manager.getUser(email, password);
			
			if(user.getType().equals("admin"))
				Manager.deleteUser(userID);
			else
				throw new RuntimeException("Trying to hack me, eh?");
		}
		catch(RuntimeException e) {
			response.getWriter().write(e.getMessage());
		}
	}

}
