let sampleReviews = [];

document.addEventListener('DOMContentLoaded', () => {
	updateNavigation();
    const userId = localStorage.getItem('user_id'); // Retrieve user ID from local storage
    if (userId) {
        fetchReviewsForUser(userId);
    } else {
        console.log("User ID not found, unable to fetch reviews.");
    }
  

    // createCalendar(currentMonth, currentYear, reviews); // Initialize the calendar with reviews data
	// renderJournalEntries(reviews); // Render all journal entries initially

});

// Function to fetch reviews for a specific user
function fetchReviewsForUser(userId) {
    fetch('http://localhost:8081/CSCI_201_Final_Project_redo/UserReviewServlet', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(userId)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok ' + response.statusText);
        }
        return response.json();
    })
    .then(reviews => {
		sampleReviews = reviews;
				
		
		console.log(reviews);
        if (reviews.length) {
            renderJournalEntries(reviews);
            
            // Create a new Date object for the current month and year
			const currentMonth = new Date().getMonth();
			const currentYear = new Date().getFullYear();
            // createCalendar(currentMonth, currentYear, reviews);
        } else {
            console.log('No reviews found or empty list returned.');
        }
    })
    .catch(error => console.error('Error fetching reviews:', error));
}

// Function to create and render a journal entry
function createJournalEntry(entry) {
    const entryContainer = document.createElement('div');
    entryContainer.classList.add('journal-entry');
    
    let diningHallName = "";
    
    if (entry.diningHallID === 1) {
			diningHallName = "Village";
	}
	else if(entry.diningHallID === 2){
		diningHallName = "Parkside";
	}
	else if(entry.diningHallID === 3){
		diningHallName = "EVK";
	}

    const entryHeader = document.createElement('div');
    const diningHallElement = document.createElement('h3');
    diningHallElement.textContent = diningHallName;
    const dateElement = document.createElement('p');
    dateElement.textContent = `Date: ${entry.date}`;
    //console.log(entry.date);
    const ratingElement = document.createElement('p');
    ratingElement.textContent = `Rating: ${entry.score}/5`;

    entryHeader.appendChild(diningHallElement);
    entryHeader.appendChild(dateElement);
    entryHeader.appendChild(ratingElement);

    const entryDetails = document.createElement('div');
    entryDetails.classList.add('journal-entry-details');
    const reviewElement = document.createElement('p');
    reviewElement.textContent = `Review: ${entry.comment}`;
    entryDetails.appendChild(reviewElement);

    entryContainer.appendChild(entryHeader);
    entryContainer.appendChild(entryDetails);

    entryContainer.addEventListener('click', () => {
        entryContainer.classList.toggle('show-details');
    });

    return entryContainer;
}

// Function to render all journal entries
function renderJournalEntries(reviews) {
    const entriesContainer = document.getElementById('entries-container');
    entriesContainer.innerHTML = ''; // Clear previous entries

    reviews.forEach(entry => {
        const entryElement = createJournalEntry(entry);
        entriesContainer.appendChild(entryElement);
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


const sortingSelect = document.getElementById('sorting-select');
sortingSelect.addEventListener('change', (e) => {
    sortReviews(e.target.value);
});


function sortReviews(sortOption) {
    let sortedReviews = [...sampleReviews];
    if (sortOption === 'date') {
        sortedReviews.sort((a, b) => new Date(b.date) - new Date(a.date));
    } else if (sortOption === 'rating') {
        sortedReviews.sort((a, b) => b.score - a.score);
    } else if (sortOption === 'diningHall') {
        sortedReviews.sort((a, b) => getDiningHallName(a.diningHallID).localeCompare(getDiningHallName(b.diningHallID)));
    }
    renderJournalEntries(sortedReviews);
}


// Create a new Date object for the current month and year
const currentMonth = new Date().getMonth();
const currentYear = new Date().getFullYear();


// Function to create the calendar
// Function to create the calendar
function createCalendar(month, year) {
   const calendarElement = document.getElementById('calendar');
   calendarElement.innerHTML = '';


   // Create the calendar header
   const calendarHeader = document.createElement('div');
   calendarHeader.id = 'calendar-header';
   calendarHeader.textContent = `${new Date(year, month).toLocaleString('default', { month: 'long' })} ${year}`;
   calendarElement.appendChild(calendarHeader);


   // Create the calendar days container
   const calendarDays = document.createElement('div');
   calendarDays.id = 'calendar-days';
   calendarElement.appendChild(calendarDays);


   // Add day names header
   ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'].forEach(day => {
       const dayElement = document.createElement('div');
       dayElement.classList.add('calendar-day-name');
       dayElement.textContent = day;
       calendarDays.appendChild(dayElement);
   });


   // Calculate the number of days and the first day of the month
   const firstDayOfMonth = new Date(year, month, 1).getDay();
   const daysInMonth = new Date(year, month + 1, 0).getDate();


   // Add empty slots for days before the first day of the month
   for (let i = 0; i < firstDayOfMonth; i++) {
       calendarDays.appendChild(document.createElement('div'));
   }


   // Add actual day numbers
   for (let day = 1; day <= daysInMonth; day++) {
       const dayElement = document.createElement('div');
       dayElement.classList.add('calendar-day');
       dayElement.textContent = day;
       dayElement.addEventListener('click', () => showReviewsForDay(year, month, day));
       calendarDays.appendChild(dayElement);
   }
}



// Function to show reviews for the selected day
function showReviewsForDay(year, month, day) {
   const selectedDayReviews = document.getElementById('selected-day-reviews');
   selectedDayReviews.innerHTML = '';


   //const selectedDate = new Date(year, month, day).toISOString().split('T')[0];
   const selectedDate = new Date(year, month, day);
   const formattedDate = selectedDate.toLocaleDateString('en-US', { month: 'long', day: 'numeric', year: 'numeric' });
   console.log(formattedDate);
   
   //console.log(selectedDate);
   const reviewsForDay = sampleReviews.filter(review => review.date === formattedDate);
   //const reviewsForDay = sampleReviews.filter(review => review.date);
   
   
   console.log(reviewsForDay);


   if (reviewsForDay.length === 0) {
       const noReviewsMessage = document.createElement('p');
       noReviewsMessage.textContent = 'No reviews for this day.';
       selectedDayReviews.appendChild(noReviewsMessage);
   } else {
	   reviewsForDay.forEach(review => {
	   const reviewElement = document.createElement('div');
	   reviewElement.textContent = `${getDiningHallName(review.diningHallID)} - ${review.comment}`;
	   selectedDayReviews.appendChild(reviewElement);
	   
	   const reviewRating = document.createElement('div');
	   reviewRating.textContent =  `(Rating: ${review.score}/5)`;
	   selectedDayReviews.appendChild(reviewRating);
	   
	   const spacer = document.createElement('br'); // Adds a horizontal line as a spacer
	   selectedDayReviews.appendChild(spacer);
	   });
	   // delete the last child in selected day reviews
	   selectedDayReviews.removeChild(selectedDayReviews.lastChild);
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



// Initial rendering of the calendar
createCalendar(currentMonth, currentYear);




