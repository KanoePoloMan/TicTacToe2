const uuidElement = document.getElementById('UUID');
const gameUUID = uuidElement.dataset.myVariable;
console.log(gameUUID);

const gameBoard = [
  ['', '', ''],
  ['', '', ''],
  ['', '', '']
];

let currentPlayer = 'X';
let gameOver = false;
let winningLine = [];

const gameContainer = document.getElementById('game-container');
const gameResult = document.getElementById('game-result'); // Получаем элемент для отображения результата
const gameInfo = document.getElementById('game-info'); // Получаем элемент для отображения информации об игре

let backendGameAI = {
  uuid: null,
  player: null,
  x: false,
  field: {
    gameField: [
      [0, 0, 0],
      [0, 0, 0],
      [0, 0, 0]
    ]
  },
  state: null,
  error: null
};

const getGameURL = localStorage.getItem("serverURL") + '/game/ai/';
let updateGameURL = localStorage.getItem("serverURL")  + '/game/ai/';

function createGameBoard() {
  console.log("Create game board");
  for (let i = 0; i < 3; i++) {
    const row = document.createElement('div');
    row.classList.add('row');
    for (let j = 0; j < 3; j++) {
      const cell = document.createElement('div');
      cell.classList.add('cell');
      cell.dataset.row = i;
      cell.dataset.col = j;
      cell.addEventListener('click', handleCellClick);
      row.appendChild(cell);
    }
    gameContainer.appendChild(row);
  }
  getGame();
  if(backendGameAI.error != null) alert(backendGameAI.error);
}

async function handleCellClick(event) {
  if (gameOver) return;

  const row = parseInt(event.target.dataset.row);
  const col = parseInt(event.target.dataset.col);

  if (gameBoard[row][col] !== '') return;

  gameBoard[row][col] = currentPlayer;
  event.target.textContent = currentPlayer;
  backendGameAI.field.gameField[row][col] = currentPlayer === 'X' ? 2 : 1;
  await updateGame();
  renderGameBoard();

  if (checkWin() != '') {
    gameOver = true;
    highlightWinningLine(); // Подсветка победной линии
    gameResult.innerHTML = `${checkWin()} победил!`; // Выводим результат в gameResult
  } else if (checkDraw()) {
    gameOver = true;
    gameResult.innerHTML = 'Ничья!';
  } else {
    // switchPlayer();
  }
}

function checkWin() {
  // Проверка строк
  for (let i = 0; i < 3; i++) {
    if (gameBoard[i][0] !== '' && gameBoard[i][0] === gameBoard[i][1] && gameBoard[i][0] === gameBoard[i][2]) {
      winningLine = [[i, 0], [i, 1], [i, 2]]; // Запись координат победной линии
      if(gameBoard[i][0] == 'X') return 'X';
      else return 'O';
    }
  }

  // Проверка столбцов
  for (let i = 0; i < 3; i++) {
    if (gameBoard[0][i] !== '' && gameBoard[0][i] === gameBoard[1][i] && gameBoard[0][i] === gameBoard[2][i]) {
      winningLine = [[0, i], [1, i], [2, i]];
      if(gameBoard[0][i] == 'X') return 'X';
      else return 'O';
    }
  }

  // Проверка диагоналей
  if (gameBoard[0][0] !== '' && gameBoard[0][0] === gameBoard[1][1] && gameBoard[0][0] === gameBoard[2][2]) {
    winningLine = [[0, 0], [1, 1], [2, 2]];
    if(gameBoard[0][0] == 'X') return 'X';
      else return 'O';
  }
  if (gameBoard[0][2] !== '' && gameBoard[0][2] === gameBoard[1][1] && gameBoard[0][2] === gameBoard[2][0]) {
    winningLine = [[0, 2], [1, 1], [2, 0]];
    if(gameBoard[0][2] == 'X') return 'X';
      else return 'O';
  }

  return '';
}

function checkDraw() {
  for (let i = 0; i < 3; i++) {
    for (let j = 0; j < 3; j++) {
      if (gameBoard[i][j] === '') {
        return false;
      }
    }
  }
  return true;
}

function switchPlayer() {
  currentPlayer = currentPlayer === 'X' ? 'O' : 'X';
}

function highlightWinningLine() {
  winningLine.forEach(([row, col]) => {
    const cell = gameContainer.querySelectorAll('.cell')[row * 3 + col];
    cell.style.backgroundColor = 'red'; // Закрашиваем ячейку победной линии красным
  });
}

createGameBoard();

const themeSwitch = document.getElementById('theme-switch');

themeSwitch.addEventListener('click', () => {
  document.body.classList.toggle('dark');
  document.querySelector('.container').classList.toggle('dark');
  document.querySelectorAll('.cell').forEach(cell => cell.classList.toggle('dark'));
  document.getElementById('game-result').classList.toggle('dark');
});

async function getGame() {
    console.log("Get game");
    const accessToken = localStorage.getItem("accessToken");

    let response = await fetch(getGameURL + gameUUID, {
      method: 'GET',
            headers: {
                'Authorization': `Bearer ${accessToken}`,
                'Content-Type': 'application/json'
            }
    });
    backendGameAI = await response.json();
    console.log(backendGameAI);

    currentPlayer = backendGameAI.x ? 'X' : 'O';
    renderGameBoard();
}
async function updateGame() {
  if(backendGameAI.error == null) {
    const accessToken = localStorage.getItem("accessToken");
    
    let response = await fetch(updateGameURL + backendGameAI.uuid, {
      method: 'POST',
      headers: {
        'Authorization': `Bearer ${accessToken}`,
        'Content-Type': 'application/json;charset=utf-8'
      },
      body: JSON.stringify(backendGameAI)
    });
    console.log("Sended: " + JSON.stringify(backendGameAI.field));
    backendGameAI = await response.json();
    console.log(backendGameAI);
  } else {
    alert(backendGameAI.error);
  }
}
function renderGameBoard() {
  for(let row = 0; row < 3; row++) {
    for(let col = 0; col < 3; col++) {
      const cell = gameContainer.querySelectorAll('.cell')[row * 3 + col];
      if(backendGameAI.field.gameField[row][col] == 1) {
        gameBoard[row][col] = 'O';
        cell.textContent = 'O';
      } else if(backendGameAI.field.gameField[row][col] == 2) {
        gameBoard[row][col] = 'X';
        cell.textContent = 'X';
      }
    }
  }
  gameInfo.innerHTML = 'Вы играете с ИИ за ' + currentPlayer;
}