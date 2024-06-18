/**
 * 
 */

function verifyLogin(event){
	// prevent form submission
	event.preventDefault();
	
	// get form inputs
	var email = document.querySelector('input[type="email"]').value;
    var username = document.querySelector('input[type="text"]').value;
    var password = document.querySelector('input[type="password"]').value;
    
    // validate
    if(email === '' | username === '' | password === ''){
		// alert the user
		alert('Please fill in all fields');
		return;
	}
	
	// create a JSON object with the user information
	const requestBody = {
		username: username,
		password: password,
		email: email
	};
	
	// send a POST request
	fetch("http://localhost:8081/CSCI_201_Final_Project_redo/LoginServlet", {
		method: 'POST',
		body: JSON.stringify(requestBody),
		headers: {
			'Content-Type': 'application/json'
		}
	})
	.then(response => response.json())
	.then(data => {
		// if user isn't found
		if(data === null){
			// alert error message
			alert('Login failed');
			//window.location.href = "../login/login.html";
		}
		// else if user is found
		else{
			// log the user in and jump to home window
			window.location.href = "../home/home.html";
			localStorage.setItem('isLoggedIn', true );
			localStorage.setItem('user_id', data.userID);
			
			console.log("Signed in user id: " + data.userID);
			// alert(data.response);
			
		}
		
	})
	.catch(error => {
        console.error('Error:', error);
    });
}
