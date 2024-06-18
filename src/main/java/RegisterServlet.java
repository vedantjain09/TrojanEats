

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class RegisterServlet
 */

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("IN REGISTER SERVLET");
		
		PrintWriter pw = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		User user = new Gson().fromJson(request.getReader(), User.class);
		
		String username = user.getUsername();
		String password = user.getPassword();
		String email = user.getEmail();
		Integer restriction = user.getRestrictionID();
		Integer goals = user.getGoalID();
		
		
		Gson gson = new Gson();
		
		// if user data is null or blank
		if (username == null || username.isBlank() 
				|| password == null || password.isBlank()
				|| email == null || email.isBlank()
		) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			String error = "User info missing";
			pw.write(gson.toJson(error));
			pw.flush();
		}
		// otherwise
		else {
		
			// attempt to register the user to the database
			User retUser = JDBCConnector.registerUser(username, email, password, restriction, goals);
			
			response.setStatus(HttpServletResponse.SC_OK);
			// send the new user's ID back to the client
			pw.write(gson.toJson(retUser));
			pw.flush();
		
		}
		
	}

}