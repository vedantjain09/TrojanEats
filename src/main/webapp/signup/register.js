/**
 * 
 */

function verifyRegister(event){
	// prevent default form submission
	event.preventDefault();
	
	// get data inputs
	var email = document.querySelector('input[type="email"]').value;
    var username = document.querySelector('input[type="text"]').value;
    var password = document.querySelector('input[type="password"]').value;
    var restrictionSelect = document.querySelector('#dietaryRestrictions').value;
    var goalsSelect = document.querySelector('#dietaryGoals').value;
    var restriction = parseInt(restrictionSelect);
    var goals = parseInt(goalsSelect);
    
    console.log("Restriction value: " + restrictionSelect);
    console.log("Goal value:" + goalsSelect);
    
    console.log("Restriction int: " + restriction);
    console.log("Goal int:" + goals);
    
    
    // validate
    if (email === '' || username === '' || password === '' || restrictionSelect === '0' || goalsSelect === '0') {
        // alert error if some fields are empty
        alert('Please fill in all fields');
        return;
    }
    
    // Create a JSON object with the user information
    const requestBody = {
        username: username, 
        password: password,
        email: email,
        restrictionID: restriction,
        goalID: goals
    };
    
    // send a POST request
    fetch("http://localhost:8081/CSCI_201_Final_Project_redo/RegisterServlet", {
		method: 'POST',
		body: JSON.stringify(requestBody),
		headers: {
			'Content-Type': 'application/json'
		}
	})
	.then(response => response.json())
	.then(data => {
		// if the user isn't found
		if(data.userID == -1){
			// alert error message
			alert(data.response);
		}
		// else if user is found
		else{
			// automatically sign the user in and jump to home window
			window.location.href = "../home/home.html";
			localStorage.setItem('isLoggedIn', true);
			localStorage.setItem('user_id', data.userID);
			
			console.log("Signed in user id: " + data.userID);
			// alert(data.response);
		}
	}) 
	.catch(error => {
        console.error('Error:', error);
    });
}
