// NEED TO UPDATE IMPORTS FOR FINAL PROJECT

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class LoginServlet
 */

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter pw = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		User user = new Gson().fromJson(request.getReader(), User.class);
		
		String username = user.getUsername();
		String password = user.getPassword();
		
		Gson gson = new Gson();
		
		// if user data is null or blank
		if (username == null || username.isBlank() 
			|| password == null || password.isBlank()
		) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			String error = "User info missing";
			pw.write(gson.toJson(error));
			pw.flush();
		}
		// otherwise
		else {
			// check the login information against the database
			User retUser = JDBCConnector.loginUser(username, password);
			
			response.setStatus(HttpServletResponse.SC_OK);
			// send the user's ID back to the client
			pw.write(gson.toJson(retUser));
			pw.flush();
			
		}
		
	}

}