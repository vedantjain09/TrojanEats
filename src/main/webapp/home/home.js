

// Sample reviews data

console.log("IN HOME.JS");


// Function to render reviews
function renderReviews() {
	
		const reviews = [
	    {
	        diningHall: 'EVK',
	        review: 'Great food and atmosphere!',
	        rating: 5
	    },
	    {
	        diningHall: 'Village',
	        review: 'Not a lot of options, but still decent.',
	        rating: 3
	    },
	    {
	        diningHall: 'Parkside',
	        review: 'Excellent vegetarian choices!',
	        rating: 4
	    },
	    // Add more reviews as needed
	];
	
    const reviewElements = document.querySelectorAll('.reviews');

    reviews.forEach(review => {
        const diningHallReview = reviewElements[['EVK', 'Village', 'Parkside'].indexOf(review.diningHall)];

        if (diningHallReview) {
            const reviewElement = document.createElement('div');
            reviewElement.textContent = `${review.review} - ${review.rating}/5`;
            diningHallReview.appendChild(reviewElement);
        }
    });
}


// function takes in dining hall id and dining hall name
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
		
		if (diningHallId === 1) {
			document.getElementById('Village').innerHTML = averageScore.toFixed(2) + "/5 Stars";
		}
		else if(diningHallId === 2){
			document.getElementById('Parkside').innerHTML = averageScore.toFixed(2) + "/5 Stars";
		}
		else if(diningHallId === 3){
			document.getElementById('EVK').innerHTML = averageScore.toFixed(2) + "/5 Stars";
		}
		
    })
    .catch(error => {
      console.error('Error:', error);
    });
}



function getDininghallReviews(dininghallID) {
	
	id = "";
	
	if (dininghallID === 1) {
		id = "Villagereviews";
	}
	if (dininghallID === 2) {
		id = "Parksidereviews";
	}
	if (dininghallID === 3) {
		id = "EVKreviews";
	}
	
	// clear any old reveiws from this dining hall's div
	const reviewdiv = document.getElementById(id);
	
    while (reviewdiv.firstChild) {
        reviewdiv.removeChild(reviewdiv.firstChild);
    }
    
    console.log("INSIDE getDininghallReveiws");
	
	// create variable to store reviews
	let reviews = [];
	
	// fetch the list of reveiws
	
	
	// send a POST request
    fetch("http://localhost:8081/CSCI_201_Final_Project_redo/FirstTwoReviewServlet", {
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
		console.log(reviews);
		
		// make sure all review entries are received
		reviews.forEach(entry => {
        	console.log(entry);
        })
        
        let reviewli;
        
        // create new <li> in the the "reviews-list" class list for each review
        reviews.forEach(entry => {
    	let score = entry.score;
    	let comment = entry.comment;
    	
    	reviewli = document.createElement("div");
    	
    	// format the review entries on the page
    	
    	// review.textContent = `${comment} - ${score} Stars`
    	
    	const reviewElement = document.createElement('div');
	   reviewElement.textContent = `${entry.comment}`;
	   reviewli.appendChild(reviewElement);
	   
	   const reviewRating = document.createElement('div');
	   reviewRating.textContent =  `(Rating: ${entry.score}/5)`;
	   reviewli.appendChild(reviewRating);
	   
	   const spacer = document.createElement('br'); // Adds a horizontal line as a spacer
	   reviewli.appendChild(spacer);
	   
	   reviewdiv.appendChild(reviewli);
        	
        })
        
        // delete the last child in selected day reviews
	    reviewli.removeChild(reviewli.lastChild);
		
	}) 
	.catch(error => {
        console.error('Error:', error);
    });
	
	
	
}


function getDiningHallName(id) {
    switch (id) {
        case 1: return "Village";
        case 2: return "Parkside";
        case 3: return "EVK";
        default: return "Unknown";
    }
}




// Function to check login status and update navigation
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


// Call the updateNavigation function on page load
document.addEventListener('DOMContentLoaded', function() {
    updateNavigation();
    renderReviews();
    displayAverageScore(1, "Village");
    displayAverageScore(2, "Parkside");
    displayAverageScore(3, "EVK");
    getDininghallReviews(1);
    getDininghallReviews(2);
    getDininghallReviews(3);
});

