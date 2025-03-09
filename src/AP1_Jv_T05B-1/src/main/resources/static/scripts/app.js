localStorage.setItem("serverURL", "http://localhost:8081");

const authURI = "/auth";



function authorization() {
    fetch(localStorage.getItem("serverURL") + authURI)
    .then(response => {
        if(!response.ok) {
            throw new Error('Ошибка загрузки данных');
        }
        return response.text(); // Получаем HTML как текст
    })
    .then(html => {
        localStorage.setItem("updated", false);
        localStorage.setItem("html", html);
    })
    .catch(error => {
        // Обработка ошибок
        console.error('Ошибка:', error);
        document.getElementById('content').innerHTML = '<p style="color: red;">Не удалось загрузить контент.</p>';
    });
}
function checkUpdate() {
    if(localStorage.getItem('updated') == 'false') {
        const container = document.getElementById('content');
        const html = localStorage.getItem("html");

        container.innerHTML = html;

        const scripts = container.querySelectorAll('script');
        scripts.forEach(script => {
            const newScript = document.createElement('script');

            // Если скрипт внешний (имеет src)
            if (script.src) {
                newScript.src = script.src;
            }
            // Если скрипт встроенный (имеет текст)
            else {
                newScript.textContent = script.textContent;
            }

            // Добавляем скрипт в документ
            document.body.appendChild(newScript);
        });

        localStorage.setItem("updated", true);
    }
}
function loadPage() {
    if(localStorage.getItem("loadPage") != "none") {
        const URI = localStorage.getItem("loadPage");
        const accessToken = localStorage.getItem("accessToken");

        fetch(localStorage.getItem("serverURL") + URI, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${accessToken}`,
                'Content-Type': 'application/json'
            }
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Ошибка загрузки данных');
            }
            return response.text(); // Обрабатываем ответ как JSON
        })
        .then(html => {
            localStorage.setItem('updated', false);
            localStorage.setItem('html', html);
        })
        .catch(error => {
            // Обработка ошибок
            console.error('Ошибка:', error);
            document.getElementById('content').innerHTML = '<p style="color: red;">Не удалось загрузить контент.</p>';
        });

        localStorage.setItem("loadPage", "none");
    }
}

authorization();
setInterval(loadPage, 200);
setInterval(checkUpdate, 200);
// Вставить провурку токенов на истечение