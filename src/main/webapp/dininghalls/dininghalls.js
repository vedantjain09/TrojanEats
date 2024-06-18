document.addEventListener('DOMContentLoaded', dgetDininghallReviews() );

function getDininghallReviews(dininghallID) {
	
	// clear the potential reviews from the other dining halls
    while (document.getElementsByClassName(reviews-list).firstChild) {
        document.getElementsByClassName(reviews-list).removeChild(reviewsList.firstChild);
    }
	
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
		
		// make sure all review entries are received
		reviews.forEach(entry => {
        	console.log(entry);
        })
        
        // create new <li> in the the "reviews-list" class list for each review
        reviews.forEach(entry => {
        	let score = entry.score;
        	let comment = entry.comment;
        	
        	const review = document.createElement("li");
        	
        	review.textContent = "${comment} - ${score} Stars"
        	
        	document.getElementsByClassName(reviews-list).appendChild(review);
        })
		
		
		
	}) 
	.catch(error => {
        console.error('Error:', error);
    });
	
	
	
}