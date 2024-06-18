

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class PostServlet
 */
@WebServlet("/PostServlet")
public class PostServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("IN POST SERVLET");
		
		PrintWriter pw = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		Review review = new Gson().fromJson(request.getReader(), Review.class);
		
		Integer userID = review.getUserID();
		Integer diningHallID = review.getDiningHallID();
		Integer score = review.getScore();
		String comment = review.getComment();
		
		// get current time, convert to java.util date, then convert that to SQL date
        long time = System.currentTimeMillis();
        java.util.Date juDate = new java.util.Date(time);
		Date date = new Date(juDate.getTime());
		
		
		Gson gson = new Gson();
		
		// if user data is null or blank
		if (userID < 1) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			String error = "User info missing";
			pw.write(gson.toJson(error));
			pw.flush();
		}
		// otherwise
		else {
		
			// attempt to register the user to the database
			Boolean worked = JDBCConnector.insertReview(userID, diningHallID, score, comment, date);
			
			response.setStatus(HttpServletResponse.SC_OK);
			// send the new user's ID back to the client
			
			if (worked) {
				pw.write(gson.toJson(userID));
				pw.flush();
			}
			else {
				pw.write(gson.toJson(-1));
				pw.flush();
			}
		
		}
		
	}

}
