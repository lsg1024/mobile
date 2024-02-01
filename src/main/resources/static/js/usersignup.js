document.addEventListener('DOMContentLoaded', function() {
    const signup_button = document.getElementById('signup_button');
    const form = document.querySelector("form");

    signup_button.addEventListener('click', function (event) {

        event.preventDefault()

        const formData = new FormData(form);

        // 회원가입 요청 데이터 생성
        let signupData = {
            email: formData.get('email'),
            password: formData.get('password'),
            password_confirm: formData.get('passwordConfirm'),
            name: formData.get('nickname')
        };

        console.log("Request Data:", JSON.stringify(signupData));

        // 회원가입 요청 전송
        fetch('/user/signup', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(signupData)
        })
        .then(response => response.json().then(data => ({ status: response.status, body: data })))
        .then(({ status, body }) => {
            if (status === 200) {
                alert(body.response);
                window.location.href = '/user/login';
            }
            else{
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
