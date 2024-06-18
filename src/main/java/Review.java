import java.util.Date;

public class Review {
    // ========== attributes ==========
    int reviewID;
    int userID;
    String username;
    int diningHallID;
    int score;
    String comment;
    Date date;

    // ========== methods ==========
    public Review(int reviewID, int userID, String username, int diningHallID, int score, String comment) {
        this.reviewID = reviewID;
        this.userID = userID;
        this.username = username;
        this.diningHallID = diningHallID;
        this.score = score;
        this.comment = comment;
    }
    
 // ========== methods ==========
    public Review(int reviewID, int userID, String username, int diningHallID, int score, String comment, Date date) {
        this.reviewID = reviewID;
        this.userID = userID;
        this.username = username;
        this.diningHallID = diningHallID;
        this.score = score;
        this.comment = comment;
        this.date = date;
    }

    public int getReviewID() {
        return reviewID;
    }

    public void setReviewID(int reviewID) {
        this.reviewID = reviewID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getDiningHallID() {
        return diningHallID;
    }

    public void setDiningHallID(int diningHallID) {
        this.diningHallID = diningHallID;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Review: " +
                "review ID = " + reviewID +
                ", user ID = " + userID +
                ", username = " + username +
                ", dining hall ID = " + diningHallID +
                ", score = " + score +
                ", comment = " + comment;
    }
}