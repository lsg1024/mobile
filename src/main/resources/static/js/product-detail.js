document.addEventListener('DOMContentLoaded', function() {
    const editButton = document.getElementById('edit-button');
    const saveButton = document.getElementById('save-button');
    const form = document.querySelector("form")
    const inputs = document.querySelectorAll('.form-control');


    // 처음에는 모든 필드를 비활성화
    inputs.forEach(function(input) {
        input.disabled = true;
    });

    editButton.addEventListener('click', function() {
        // 필드를 활성화
        inputs.forEach(function(input) {
            input.disabled = false;
        });

        editButton.classList.add('hidden');
        saveButton.classList.remove('hidden');
    });

    // 필드 유효성 검사 함수
    const fieldLabels = {
        'name': '상품명',
        'color': '색상',
        'size': '크기',
        'weight': '무게',
        'other': '기타 정보'
    };

    function validateFields() {
        let emptyFields = [];

        for (let input of inputs) {
            if (!input.value.trim()) {
                emptyFields.push(fieldLabels[input.name] || input.name);
            }
        }

        if (emptyFields.length > 0) {
            alert("다음 필드가 비어 있습니다: " + emptyFields.join(", "));
            return false;
        }
        return true;
    }

    saveButton.addEventListener('click', function(event) {
        event.preventDefault()

        if (!validateFields()) {
            return; // 유효성 검사 실패 시 함수 종료
        }

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
            .then(response => {
                // 성공 메시지 처리 또는 페이지 새로고침 토스트 메시지를 보여준 후 사라지게 함
                if (response.ok) {
                    alert("데이터 수정이 완료되었습니다")

                    // 필드를 다시 비활성화
                    inputs.forEach(function(input) {
                        input.disabled = true;
                    });

                    editButton.classList.remove('hidden');
                    saveButton.classList.add('hidden');

                    return response.json();
                } else {
                    throw new Error("데이터 수정 실패")
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert("업데이트 실패: " + error.message); // 실패 메시지 표시
            });


    });

});
