

public class reviewObject {
	// constructor
	public reviewObject(Integer user_id, Integer hall, Integer score, String review) {
	        this.user_id = user_id;
	        this.hall = hall;
	        this.score = score;
	        this.review = review;
    	}
	
	/*
	 * keeps track of user
	 */
	private Integer user_id;
	
	/*
	 * 1 : village
	 * 2 : parkside
	 * 3 : evk
	 */
	private Integer hall;
	
	/*
	 * rating the dining hall 1-5
	 */
	private Integer score;
	
	/*
	 * the actual review user will write
	 */
	private String review;


	/*
	 * getters
	 */
	public Integer getUserID () {
		return user_id;
	}
	
	public Integer getDiningHall() {
		return hall;
	}
	
	public Integer getScore() {
		return score;
	}
	
	public String getReview() {
		return review;
	}

		
	/*
	 *  setters
	 */
	public void setUserID (Integer new_userID) {
		this.user_id = new_userID;
	}
	
	public void setDiningHall(Integer new_diningHall) {
		this.hall = new_diningHall;
	}
	
	public void setScore(Integer new_score) {
		this.score = new_score;
	}
	
	public void setReview(String new_review) {
		this.review = new_review;
	}

	// to string method
	@Override
    	public String toString() {
	return "reviewObject {" +
		"\n\tuser_id : " + user_id +
		"\n\thall : " + hall +
		"\n\tscore : " + score +
		"\n\treview : '" + review + '\'' +
		"\n}";
    	}
}