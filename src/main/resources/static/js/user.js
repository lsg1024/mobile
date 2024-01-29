// 문서 로드 완료 시 이벤트 리스너 등록
document.addEventListener('DOMContentLoaded', function() {
    const join_button = document.getElementById('join_button');
    const form = document.querySelector("form");
    const nickname = document.getElementById("nickname");
    const password = document.getElementById("password");
    const password_Confirm = document.getElementById("passwordConfirm");

    join_button.addEventListener('click', function (event) {

        event.preventDefault()

        const fieldNames = {
            email: "이메일",
            name: "이름",
            password: "비밀번호"
        };

        const formData = new FormData(form);

        // 회원가입 요청 데이터 생성
        let signupData = {
            email: formData.get('email'),
            password: formData.get('password'),
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
            } else {
                let errorMessage = body.message + ": ";
                for (const [field, message] of Object.entries(body.errors)) {
                    errorMessage += `${fieldNames[field] || field}: ${message}, `;
                }
                // 오류 메시지 표시
                alert(errorMessage);
            }
        })
        .catch(error => {
        console.error('Error:', error);
        alert("업데이트 실패: " + error.message); // 실패 메시지 표시
        });
    });
});
