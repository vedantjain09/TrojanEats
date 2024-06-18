/**
 * dietary restriction ID to value mapping
 * 1 : Vegan
 * 2 : Vegetarian
 * 3 : None
 * <p>
 * dietary goal ID to value mapping
 * 1 : Lose weight
 * 2 : Maintain weight
 * 3 : Gain weight
 */
public class User {
    // ========== attributes ==========
    private int userID;
    private String username;
    private String email;
    private String password;
    private int restrictionID;
    private int goalID;

    // ========== methods ==========
    public User(int userID, String username, String email, String password, int restrictionID, int goalID) {
        this.userID = userID;
        this.username = username;
        this.email = email;
        this.password = password;
        this.restrictionID = restrictionID;
        this.goalID = goalID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getRestrictionID() {
        return restrictionID;
    }

    public void setRestrictionID(int restrictionID) {
        this.restrictionID = restrictionID;
    }

    public int getGoalID() {
        return goalID;
    }

    public void setGoalID(int goalID) {
        this.goalID = goalID;
    }

    @Override
    public String toString() {
        return "User: " +
                "user ID = " + userID +
                ", username = " + username +
                ", password = " + password +
                ", restrictionID = " + restrictionID +
                ", goalID = " + goalID;
    }
}