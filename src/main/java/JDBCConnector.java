import java.sql.*;
import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class JDBCConnector {
    // ========== public methods ==========

    /**
     * returns an array list containing all the reviews for a given user ID
     */
    public synchronized static ArrayList<Review> getAllReviews(int userID) {
    	
    	try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    	
        // init array list to return
        ArrayList<Review> reviews = new ArrayList<>();

        // init objects to query db
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        // try getting all the user's reviews
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/csci_201_final_project?user=root&password=root");
            preparedStatement = connection.prepareStatement("SELECT reviews.review_id  reviewID, users.username as username, reviews.dining_hall_id as diningHallID, reviews.date, reviews.score, reviews.comment FROM reviews LEFT JOIN users ON reviews.user_id = users.user_id LEFT JOIN dining_halls ON reviews.dining_hall_id = dining_halls.dining_hall_id WHERE reviews.user_id = ?");
            preparedStatement.setInt(1, userID);
            resultSet = preparedStatement.executeQuery();

            // iterate through each row
            while (resultSet.next()) {
                // save each value
                int reviewID = resultSet.getInt("reviewID");
                String username = resultSet.getString("username");
                int diningHallID = resultSet.getInt("diningHallID");
                int score = resultSet.getInt("score");
                String comment = resultSet.getString("comment");
                Date date = resultSet.getDate("date");
                // init new review
                Review review = new Review(reviewID, userID, username, diningHallID, score, comment, date);

                // add to reviews array list
                reviews.add(review);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        closeEverything(connection, preparedStatement, resultSet);

        return reviews;
    }

    /**
     * inserts a review into db
     * - returns true if review is successfully inserted into db
     * - returns false if review cannot be inserted into db
     */
    public synchronized static boolean insertReview(int userID, int diningHallID, int score, String comment, Date date) {
    	
    	try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    	
        // init value to return
        boolean success = true;

        // init objects to query db
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int rowsUpdated = 0;

        // try inserting new review into db
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/csci_201_final_project?user=root&password=root");
            preparedStatement = connection.prepareStatement("INSERT INTO reviews (user_id, dining_hall_id, date, score, comment) VALUES(?, ?, ?, ?, ?)");
            preparedStatement.setInt(1, userID);
            preparedStatement.setInt(2, diningHallID);
            preparedStatement.setDate(3, date);
            preparedStatement.setInt(4, score);
            preparedStatement.setString(5, comment);
            rowsUpdated = preparedStatement.executeUpdate();

            // if number of rows affected by insert statement doesn't equal 1, return false
            if (rowsUpdated != 1) {
                return !success;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        closeEverything(connection, preparedStatement, null);

        return success;
    }

    /**
     * queries database for a user with a given username and password
     * - returns a User object if the given username and password are associated with a user in the database
     * - returns null if the given username and password aren't associated with a user in the database
     */
    public synchronized static User loginUser(String username, String password) {
    	
    	try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    	
        // init object to return
        User user = null;

        // init objects to query db
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        // try querying db for the given username and password
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/csci_201_final_project?user=root&password=root");
            preparedStatement = connection.prepareStatement("SELECT users.user_id AS userID, users.email, users.restriction_id as restrictionID, users.goal_id as goalID FROM users WHERE users.username = ? AND users.password = ?");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();

            // if there's no user with the given username and password, return null
            if (!resultSet.next()) {
                return null;
            }

            // save the result set's info
            int userID = resultSet.getInt("userID");
            String email = resultSet.getString("email");
            int restrictionID = resultSet.getInt("restrictionID");
            int goalID = resultSet.getInt("goalID");

            // init user
            user = new User(userID, username, email, password, restrictionID, goalID);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        closeEverything(connection, preparedStatement, resultSet);

        return user;
    }

    /**
     * adds a new user to database
     * - returns a User object if the given username and email aren't taken
     * - returns null if the given username or email are taken
     */
    public synchronized static User registerUser(String username, String email, String password, int restrictionID, int goalID) {
    	
    	System.out.println("IN REGISTER JDBC");
    	
    	try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    	
        // init object to return
        User user = null;

        // init objects to query db
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        // try querying db for a row with the given username
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/csci_201_final_project?user=root&password=root");
            preparedStatement = connection.prepareStatement("SELECT users.user_id as userID, users.username FROM users WHERE users.username = ? OR users.email = ?");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, email);
            resultSet = preparedStatement.executeQuery();

            // if there's a user with the given username or email, return null
            if (resultSet.next()) {
                return null;
            }
            
            // print out received information
            System.out.println("username: " + username);
            System.out.println("email: " + email);
            System.out.println("password: " + password);
            System.out.println("restriciton ID: " + restrictionID);
            System.out.println("goal ID: " + goalID);
            
            // insert a new row into users table
            preparedStatement = connection.prepareStatement("INSERT INTO users (username, email, password, restriction_id, goal_id) VALUES (?, ?, ?, ?, ?)");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);
            preparedStatement.setInt(4, restrictionID);
            preparedStatement.setInt(5, goalID);
            int rowsUpdated = preparedStatement.executeUpdate();

            // if number of rows affected by insert statement doesn't equal 1, return null
            if (rowsUpdated != 1) {
                return null;
            }

            // get most recently inserted user's ID
            preparedStatement = connection.prepareStatement("SELECT last_insert_id() AS userID");
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int userID = resultSet.getInt("userID");

            // init user
            user = new User(userID, username, email, password, restrictionID, goalID);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        closeEverything(connection, preparedStatement, resultSet);

        return user;
    }

    /**
     * returns an array list containing the two most recent reviews for the village
     * - 0th element is most recent review
     * - 1st element is second most recent review
     */
    public synchronized static ArrayList<Review> getTwoMostRecentReviewsVillage() {
        return getTwoMostRecentReviews(1);
    }

    /**
     * returns an array list containing the two most recent reviews for EVK
     * - 0th element is most recent review
     * - 1st element is second most recent review
     */
    public synchronized static ArrayList<Review> getTwoMostRecentReviewsEVK() {
        return getTwoMostRecentReviews(2);
    }

    /**
     * returns an array list containing the two most recent reviews for Parkside
     * - 0th element is most recent review
     * - 1st element is second most recent review
     */
    public synchronized static ArrayList<Review> getTwoMostRecentReviewsParkside() {
        return getTwoMostRecentReviews(3);
    }

    /**
     * returns a double containing the average of all scores for village reviews
     */
    public synchronized static double getAverageScoreVillage() {
        return getAverageScore(1);
    }

    /**
     * returns a double containing the average of all scores for EVK reviews
     */
    public synchronized static double getAverageScoreEVK() {
        return getAverageScore(2);
    }

    /**
     * returns a double containing the average of all scores for Parkside reviews
     */
    public synchronized static double getAverageScoreParkside() {
        return getAverageScore(3);
    }

    // ========== private methods ==========

    /**
     * helper method to calculate the average score for a given dining hall
     */
    public synchronized static double getAverageScore(int diningHallID) {
    	
    	try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    	
        // init value to return
        double averageScore = -1.0;

        // init objects to query db
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        // try querying db for average score of a given dining hall
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/csci_201_final_project?user=root&password=root");
            preparedStatement = connection.prepareStatement("SELECT AVG (score) as averageScore FROM reviews WHERE dining_hall_id = ?");
            preparedStatement.setInt(1, diningHallID);
            resultSet = preparedStatement.executeQuery();

            // get average score from
            while (resultSet.next()) {
                averageScore = resultSet.getDouble("averageScore");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        closeEverything(connection, preparedStatement, resultSet);

        return averageScore;
    }

    /**
     * helper method to get most recent two reviews for a given dining hall
     */
    public synchronized static ArrayList<Review> getTwoMostRecentReviews(int diningHallID) {
    	
    	try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    	
        // init array list to return
        ArrayList<Review> reviews = new ArrayList<>(2);

        // init objects to query db
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        // try querying db for the two most recent reviews for a given dining hall
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/csci_201_final_project?user=root&password=root");
            preparedStatement = connection.prepareStatement("SELECT reviews.review_id as reviewID, reviews.user_id as userID, reviews.date as date, users.username as username, reviews.score as score, reviews.comment as comment FROM reviews LEFT JOIN users ON reviews.user_id = users.user_id WHERE reviews.dining_hall_id = ? ORDER BY date DESC LIMIT 2;");
            preparedStatement.setInt(1, diningHallID);
            resultSet = preparedStatement.executeQuery();

            // save two most recent reviews
            while (resultSet.next()) {
                // save all values from result set
                int reviewID = resultSet.getInt("reviewID");
                int userID = resultSet.getInt("userID");
                String username = resultSet.getString("username");
                int score = resultSet.getInt("score");
                String comment = resultSet.getString("comment");

                // init new review
                Review review = new Review(reviewID, userID, username, diningHallID, score, comment);

                // add review to reviews array list
                reviews.add(review);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        closeEverything(connection, preparedStatement, resultSet);

        return reviews;
    }

    /**
     * helper method to close all objects when querying db
     */
    private synchronized static void closeEverything(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
    	
    	try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    	
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
	
    
    // KENNY's JDBC FOR REVIEW ARRAY
     
	// things that are too long and messy pause
	private static final String DATABSE_STRING = "jdbc:mysql://localhost/csci_201_final_project?user=root&password=root";
	private static final String STATEMENT_STRING = "SELECT review_id, user_id, dining_hall_id, score, comment, date "
            + "FROM reviews "
            + "WHERE dining_hall_id =? ORDER BY date DESC";
	
	/*
	 * Takes in a dining_hall_id indicating which dining hall to return all reviews from
	 * 1 : village
	 * 2 : parkside
	 * 3 : evk 
	 */
	public synchronized static JsonArray sendAllReviews(Integer dining_hall_id) {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	
		// instantiates conn, ps, rs, reviewArray
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        JsonArray reviewArray = new JsonArray();
        
        // try connecting to db
        try 
        {
        	// connects to database
        	conn = DriverManager.getConnection(DATABSE_STRING);
        	
            // exectues statement to get all reviews from a given dining hall
            ps = conn.prepareStatement(STATEMENT_STRING);
            
            // set question mark to dinnig_hall_id parameter and executes statement
            ps.setInt(1, dining_hall_id);  
            rs = ps.executeQuery();
            
            // while there are still elements...
            while (rs.next()) {
                int reviewId = rs.getInt("review_id");
                int userId = rs.getInt("user_id");
                int diningHallId = rs.getInt("dining_hall_id");
                int score = rs.getInt("score");
                String comment = rs.getString("comment");
                
                JsonObject rv = new JsonObject();
                
                rv.addProperty("reviewID", reviewId);
                rv.addProperty("userId", userId);
                rv.addProperty("diningHallId", diningHallId);
                rv.addProperty("score", score);
                rv.addProperty("comment", comment);
                
                reviewArray.add(rv);
            }
        }
        catch (SQLException e) {
            e.printStackTrace(); 
            System.out.println("SQUException in portfolioServelet.");
        } 
        
        // close rs, ps, and conn
        finally {
        	closeEverything2(conn, ps, rs);
        }
        
        // return json array
        return reviewArray;
	    
	}
	
	// helper function to clsoe everything 
	private synchronized static void closeEverything2(Connection c, PreparedStatement p, ResultSet r) {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
            if (r != null) r.close();
            if (p != null) p.close();
            if (c != null) c.close();
        } catch (SQLException e) {
            System.out.println("sqle: " + e.getMessage());
        }
	}
    
}