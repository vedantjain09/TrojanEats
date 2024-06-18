document.addEventListener('DOMContentLoaded', () => { 
	updateNavigation();
	getDininghallReviews(3);
	displayAverageScore(3, "EVK");
	} );

function getDininghallReviews(dininghallID) {
	
	// clear the potential reviews from the other dining halls
	const oldReviews = document.getElementsByClassName("reviews-list")[0];
	
    while (oldReviews.firstChild) {
        oldReviews.removeChild(oldReviews.firstChild);
    }
    
    console.log("INSIDE getDininghallReveiws");
	
	// create variable to store reviews
	let reviews = [];
	
	// fetch the list of reveiws
	
	
	// send a POST request
    fetch("http://localhost:8081/CSCI_201_Final_Project_redo/ReviewServlet", {
		method: 'POST',
		body: JSON.stringify(dininghallID),
		headers: {
			'Content-Type': 'application/json'
		}
	})
	.then(response => response.json())
	.then(data => {
		// take the recieved data
		reviews = data;
		
		console.log("INSIDE fetch");
		
		// make sure all review entries are received
		reviews.forEach(entry => {
        	console.log(entry);
        })
        
        // create new <li> in the the "reviews-list" class list for each review
        reviews.forEach(entry => {
        	let score = entry.score;
        	let comment = entry.comment;
        	
        	const review = document.createElement("li");
        	
        	review.textContent = `${comment} - ${score} Stars`
        	
        	document.getElementsByClassName("reviews-list")[0].appendChild(review);
        })
		
		
		
	}) 
	.catch(error => {
        console.error('Error:', error);
    });
	
	
	
}


/**
 * 
 */

function submitPost(event){
	event.preventDefault();
    var form = event.target; // get the form element
    
    console.log("IN SUBMIT POST");
    
    // check to make sure logged in
    console.log('isLoggedIn:', localStorage.getItem('isLoggedIn'));
	if (localStorage.getItem('isLoggedIn') === 'false' || localStorage.getItem('isLoggedIn') === null) {
        alert('Please log in to leave a review');
        return;
    }
     
    // get the values from the form elements
    var userID = localStorage.getItem('user_id');
    var hall = form.querySelector('input[name="hall"]:checked').value;
    var rating = form.querySelector('#score').value;
    var review = form.querySelector('#review').value;
    
    console.log(userID);
    console.log(hall);
    console.log(rating);
    console.log(review);
    
    // validate
    if (!hall || !rating || !review) {
        alert('Please fill in all fields');
        return;
    }
    
    // Create a JSON object with the user information
    const requestBody = {
        userID: userID,
        diningHallID: hall,
        score: rating,
        comment: review
    };
    
    // send a POST request
    fetch("http://localhost:8081/CSCI_201_Final_Project_redo/PostServlet", {
		method: 'POST',
		body: JSON.stringify(requestBody),
		headers: {
			'Content-Type': 'application/json'
		}
	})
	.then(response => response.json())
	.then(data => {
		// if unsuccessful
		if(data.userID == -1){
			// alert error message
			alert('Review submission unsuccessful');
		}
		// else if successful
		else{
			// just alert that it is successful
			alert('Review submission successful');
		}
	}) 
	.catch(error => {
        console.error('Error:', error);
    });	
}

function displayAverageScore(diningHallId, diningHallName) {
	
  // converts dining hall id to json string
  const requestBody = JSON.stringify(diningHallId);
  
  console.log("IN DISPLAY AVG SERVLET");

  // fetch request to servlet
  fetch('http://localhost:8081/CSCI_201_Final_Project_redo/AvgServlet', {
    mode: 'no-cors', // COMMENT THIS OUT AND CORS ERROR WILL APPEAR BUT IF YOU LEAVE THIS LINE IN THEN NO CORS ERROR BUT STILL DOESNT WORK
    method: 'POST', // specify post methof
    headers: {
      'Content-Type': 'application/json'
    },
    body: requestBody
  })
  
    // error checking
    .then(response => {
      if (response.ok) {
        return response.json();
      } else {
        throw new Error('Network response was not ok.');
      }
    })
    
    // creates new div and inserts average score right after dining hall name 
    .then(averageScore => {
		console.log("Test");
		console.log("AVERAGE SCORE FOR " + diningHallName + ": " + averageScore);
		
		document.getElementById('rating').innerHTML =averageScore.toFixed(2) + "/5 Stars";
		
    })
    .catch(error => {
      console.error('Error:', error);
    });
    
}

function updateNavigation() {
    const isLoggedIn = localStorage.getItem('isLoggedIn') === 'true';
    console.log(isLoggedIn);
    const loginLinks = document.querySelectorAll('a[href="../login/login.html"]');

    const journalLink = document.querySelector('a[href="../journal/journal.html"]');


    if (isLoggedIn) {
        // Change 'LOGIN' to 'LOGOUT' and update the links
        loginLinks.forEach(link => {
            link.textContent = "LOGOUT";
            link.href = '../login/login.html'; // Update this href to point to your actual logout page or logout function
        });

        // Show the Journal link if the user is logged in
        if (journalLink) {
            journalLink.style.display = 'inline';
        }
    } else {
        // Hide the Journal link if the user is not logged in
        if (journalLink) {
            journalLink.style.display = 'none';
        }
    }
}