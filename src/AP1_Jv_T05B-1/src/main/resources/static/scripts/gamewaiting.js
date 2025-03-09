const searchURL = localStorage.getItem("serverURL") + '/game/multiplayer/checkInFoundedList';
const gameURI = '/game/multiplayer/';

let searchTimerId = setInterval(sendSearchRequest, 1000); 
var gameUUID = null;

async function sendSearchRequest() {
    const accessToken = localStorage.getItem("accessToken");

    let response = await fetch(searchURL, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${accessToken}`,
            'Content-Type': 'application/json'
        },
    });
    gameUUID = await response.text();
    
    if(gameUUID != '') {
        console.log(gameUUID);

        clearInterval(searchTimerId);
        localStorage.setItem("loadPage", gameURI + gameUUID);
    }
}
