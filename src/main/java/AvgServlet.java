

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class AvgServlet_
 */
@WebServlet("/AvgServlet")
public class AvgServlet extends HttpServlet {
	
	
	
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		PrintWriter pw = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		Integer hall = new Gson().fromJson(request.getReader(), Integer.class);
		
		Gson gson = new Gson();
		
		// if invalid hall id is given
		if (hall > 3 || hall < 1) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			String error = "invalid hall";
			pw.write(gson.toJson(error));
			pw.flush();
		}
		// otherwise
		else {
			// check the login information against the database
			Double averageScore = JDBCConnector.getAverageScore(hall);
			
			response.setStatus(HttpServletResponse.SC_OK);
			// send the user's ID back to the client
			pw.write(gson.toJson(averageScore));
			pw.flush();
			
		}
		
		
	}

}
