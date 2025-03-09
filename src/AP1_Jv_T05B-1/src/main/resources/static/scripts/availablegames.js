const getAvailableGamesURL = localStorage.getItem("serverURL") + '/game/multiplayer/getAvailableGames';
const getNameURL = localStorage.getItem("serverURL") +  '/game/multiplayer/getNameByUUID';
const multiplayerURI = '/game/multiplayer/';

// Данные для списка (можно заменить на ваши данные)
let listData = [];
let gamesList;

getAvailableList();

console.log("Games List");
console.log(gamesList);

  // Количество элементов списка (можно изменить)
let numberOfItems;

  // Получаем элемент списка по его ID
const scrollableList = document.getElementById("scrollableList");

  // Получаем кнопку "Вернуться в Меню" по ее ID
const backButton = document.getElementById("backButton");

// Функция для создания кнопки
function createListItemButton(text, index) {
    const button = document.createElement("button");
    button.classList.add("listItemButton"); // Добавляем класс для стилизации
    button.textContent = text;
    button.setAttribute("data-index", index); // Добавляем data-index атрибут
    button.addEventListener("click", onButtonClick);
    return button;
}

function onButtonClick() {
    const buttonIndex = this.getAttribute("data-index"); // Получаем data-index

    localStorage.setItem("loadPage", multiplayerURI + gamesList[buttonIndex].uuid + '/connectToGame');
    // location.href = multiplayerURL + gamesList[buttonIndex].uuid + '/connectToGame';
}

// Обработчик события для кнопки "Вернуться в Меню"
backButton.addEventListener("click", function() {
    // Здесь добавьте код для возврата в меню.
    // Например, можно перенаправить пользователя на другую страницу:
    localStorage.setItem("loadPage", "/menu");
    // location.href = "http://localhost:8081/menu";
});

async function getAvailableList() {
    console.log("Get available games");
    const accessToken = localStorage.getItem("accessToken");

    let response = await fetch(getAvailableGamesURL, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${accessToken}`,
            'Content-Type': 'application/json'
        }
    });
    gamesList = await response.json();
    
    numberOfItems = gamesList.length;

    fillDiv();
}

async function fillDiv() {
    // Создаем и добавляем кнопки в список
    for (let i = 0; i < numberOfItems; i++) {
        const accessToken = localStorage.getItem("accessToken");
        let response = await fetch(getNameURL, {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${accessToken}`,
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify(gamesList[i].x)
        });
        console.log("Sended: " + JSON.stringify(gamesList[i].x));
        
        let nick = await response.text()

        console.log(nick);

        listData[i] = nick;
        const button = createListItemButton(listData[i], i);
        scrollableList.appendChild(button);
    }
}
