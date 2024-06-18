

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class AvgServlet_
 */
@WebServlet("/FirstTwoReviewServlet")
public class FirstTwoReviewServlet extends HttpServlet {
	
	
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("In FirstTwoReview Servlet");
		
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
			// get an array of users from the database
			ArrayList<Review> reveiws = JDBCConnector.getTwoMostRecentReviews(hall);
			
			response.setStatus(HttpServletResponse.SC_OK);
			// send the array of reviews to the client
			pw.write(gson.toJson(reveiws));
			pw.flush();
			
		}
		
		
	}

}
