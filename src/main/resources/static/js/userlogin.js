document.addEventListener('DOMContentLoaded', function() {
    const login_button = document.getElementById("login_button");
    const login_form = document.querySelector("form");

    login_button.addEventListener('click', function (e) {

        e.preventDefault();

        const formData = new FormData(login_form);

        let loginData = {
            email: formData.get('login_email'),
            password: formData.get('login_password')
        }

        console.log("Request Data:", JSON.stringify(loginData));

        // 로그인 요청
        fetch('/user/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(loginData)
        })
        .then(response => response.json()
        .then(data => ({status: response.status, header: response.headers ,body: data})))
        .then(({ status, header, body }) => {
            if (status === 200) {
                alert(body.response);
                window.location.href = '/home';
            } else {
                // 오류 메시지 표시
                let errorMessage = body.message + ": ";
                for (const [field, message] of Object.entries(body.errors)) {
                    errorMessage += `${message} `;
                }
                alert(errorMessage);
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert(error);
        });
    });
})
