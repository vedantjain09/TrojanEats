document.addEventListener('DOMContentLoaded', function() {
    const apiKey = '7fe9db4f18bd4f229e530336240405'; 
    const location = 'Los Angeles'; 
    const url = `https://api.weatherapi.com/v1/current.json?key=${apiKey}&q=${location}&aqi=no`;

    fetch(url)
    .then(response => response.json())
    .then(data => {
        console.log(data); // For debugging
        const weatherData = data.current;
        const weatherElement = document.getElementById('weather');
        weatherElement.innerHTML = `
            <p>Temperature: ${weatherData.temp_f} °F</p>
            <p>Condition: ${weatherData.condition.text}</p>
            <img src="${weatherData.condition.icon}" alt="Weather Icon" />
        `;
    })
    .catch(error => console.error('Error fetching weather:', error));
    
});