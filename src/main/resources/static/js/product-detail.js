document.addEventListener('DOMContentLoaded', function() {
    const editButton = document.getElementById('edit-button');
    const saveButton = document.getElementById('save-button');
    const form = document.querySelector("form")
    const inputs = document.querySelectorAll('.form-control');


    // 처음에는 모든 필드를 비활성화
    inputs.forEach(function(input) {
        input.disabled = true;
    });

    // 데이터 수정 버튼
    editButton.addEventListener('click', function() {
        // 필드를 활성화
        inputs.forEach(function(input) {
            input.disabled = false;
        });

        editButton.classList.add('hidden');
        saveButton.classList.remove('hidden');
    });

    // 데이터 수정 완료 버튼
    saveButton.addEventListener('click', function(event) {
        event.preventDefault()

        const formData = new FormData(form);
        for (let [key, value] of formData.entries()) {
            console.log(key, value);
        }
        let productDto = {
            color: formData.get('color'),
            name: formData.get('name'),
            other : formData.get('other'),
            size: formData.get('size'),
            weight: formData.get('weight')
        };

        const url = new URL(window.location.href);
        const product_id = url.pathname.split('/').pop();

        console.log("Request Data:", JSON.stringify(productDto));

        fetch('/product/update', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'user_id': '1', // 예시 user_id
                'factory_id' : '1',
                'product_id': product_id
            },
            body: JSON.stringify(productDto)
        })
        .then(response => response.json().then(data => ({ status: response.status, body: data })))
        .then(({ status, body }) => {
            if (status === 200) {
                // 성공 메시지 처리
                alert("데이터 수정이 완료되었습니다");

                // 필드를 다시 비활성화
                inputs.forEach(function(input) {
                    input.disabled = true;
                });

                editButton.classList.remove('hidden');
                saveButton.classList.add('hidden');
            } else {
                // 오류 메시지 생성
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
            alert("업데이트 실패: " + error.message); // 실패 메시지 표시
        });
    });
});
