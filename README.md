# TrojanEats

TrojanEats is a web application developed to help students at the University of Southern California (USC) rate and review the three dining halls on campus. Inspired by Beli, TrojanEats enables users to provide daily reviews and ratings for the dining hall meals, assisting the USC community in making informed dining choices. The application also features a journal to chronicle personal dining experiences, offering insights into past meals and helping users track their dining habits over time.

## Team Members

- Vedant Jain - [vedantj@usc.edu](mailto:vedantj@usc.edu)
- Jaedon Wong - [jaedonwo@usc.edu](mailto:jaedonwo@usc.edu)
- Tristan Ma - [trma@usc.edu](mailto:trma@usc.edu)
- Ian Gravallese - [gravalle@usc.edu](mailto:gravalle@usc.edu)
- Mike Gee - [mpgee@usc.edu](mailto:mpgee@usc.edu)
- Kenny Nguyen - [kennyang@usc.edu](mailto:kennyang@usc.edu)
- Abdullah Rafique - [azrafiqu@usc.edu](mailto:azrafiqu@usc.edu)

## Project Proposal

TrojanEats is designed to be a user-friendly platform for USC students to rate, review, and explore dining options across the three main dining halls: EVK, Village Dining, and Parkside. By providing a comprehensive review system, TrojanEats aims to improve the dining experience for students by offering real-time feedback and aggregate scores for each dining hall.

## High-Level Requirements

### Core Features

- **Login Page:** Users can log in using their USC credentials or continue as a guest.
- **Home Page:** Toggle between the “Dining Hall” and “Journal” pages. The “Dining Hall” page displays top-rated dishes and overall scores for the dining halls. The “Journal” page allows users to view their dining history.
- **Dining Hall Page:** Users can choose a dining hall to view its current overall score and top-rated dish. Users can also submit their own reviews and ratings.
- **Journal:** A calendar-like feature that records daily dining experiences, allowing users to view past reviews and ratings.

### Optional Features

- Aggregate scores for each dining hall.
- Daily scores calculated from an algorithm considering all ratings for the day.

## Technical Specifications

### Technologies Used

- **Frontend:** React (HTML/CSS, JavaScript)
- **Backend:** Java, Node.js
- **Database:** MySQL
- **Server:** Apache Tomcat
- **Additional Libraries:** Gson, MySQL Connector

### Database Schema

- **User Login Table:** Stores userID, username, password, dietary preference.
- **Dining Halls Table:** Stores diningHallID, diningHallName.
- **Meal Table:** Stores mealID, mealName.
- **Rating Categories Table:** Stores ratingCategoryID, diningHallID, ratingName.
- **Dining Hall Reviews Table:** Stores reviewID, userID, ratingCategoryID, mealID, diningHallID, dateAdded.

## Detailed Design

### User Login

- Users can sign up with a username, password, dietary preference, and dietary goals.
- Login verification checks user credentials against the database.

### Home Page

- Navbar with links to login, dining hall pages, and the journal page.
- Display sections for each dining hall with average ratings and recent reviews.

### Dining Hall Page

- Options to choose a dining hall.
- Forms for submitting reviews with scores for meal balance, taste, satisfaction, busyness, and vegan/vegetarian friendliness.

### Journal Page

- Calendar view displaying past reviews and ratings.
- List view for sorting and displaying journal entries.

## Testing Plan

### Black Box Testing

- Verify scoring updates correctly.
- Ensure reviews are added to the journal.
- Test account creation and login functionalities.

### Regression Testing

- Ensure stability with multiple concurrent users.

### Stress Testing

- Test application performance with numerous simultaneous logins and reviews.

### White Box Testing

- Validate database updates for new accounts and reviews.
- Check login and sign-up error handling.

### Unit Testing

- Verify account creation and review submission functionalities.
- Test concurrency and real-time updates.

## Deployment Document

### Prerequisites

- Java 17
- Eclipse IDE
- MySQL
- Apache Tomcat 9

### Steps

1. Import the project into Eclipse.
2. Configure the SQL database.
3. Add necessary JAR files to the build path.
4. Deploy the application on Tomcat server.

Visit [http://localhost:8080/login/login.html](http://localhost:8080/login/login.html) to access the application.

## Conclusion

TrojanEats aims to enhance the dining experience at USC by providing a platform for students to share their reviews and make informed dining choices. By leveraging user feedback, TrojanEats creates a more engaged and discerning dining hall culture within the USC community.
