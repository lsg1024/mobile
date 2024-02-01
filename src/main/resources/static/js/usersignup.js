document.addEventListener('DOMContentLoaded', function() {
    const signup_button = document.getElementById('join_button');
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
                alert("회원가입이 완료되었습니다");
                window.location.href = '/user/login';
            }
            else if (status === 409) {
                alert(body.response)
            }
            else  {
                let errorMessage = body.message + ": ";
                for (const [field, message] of Object.entries(body.errors)) {
                    errorMessage += `${message}, `;
                }
                // 오류 메시지 표시
                alert(errorMessage);
            }
        })
        .catch(error => {
        console.error('Error:', error);
        alert(error);
        });
    });
})
