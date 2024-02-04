document.addEventListener('DOMContentLoaded', function() {
    const editButtons = document.querySelectorAll('.edit_btn');
    // const deleteButtons = document.querySelectorAll('.delete_btn');

    editButtons.forEach(button => {
        button.addEventListener('click', function () {
            const storeId = this.getAttribute('data-store-id');
            const storeName = prompt("상점 이름을 수정하세요:", "");
            if (storeName != null && storeName !== "") {
                fetch(`stores/update?storeId=${storeId}`, {
                    method: 'POST',
                    headers: {
                        'Content-Type' : 'application/json'
                    },
                    body: JSON.stringify({store_name: storeName})
                }).then(response => {
                    if (response.ok) {
                        window.location.reload();
                    } else {
                        alert("상점 이름 수정에 실패했습니다.");
                    }
                })
            }
        })
    });

    // deleteButtons.forEach(button => {
    //     button.addEventListener('click', function() {
    //         if (confirm("이 상점을 삭제하시겠습니까?")) {
    //             const storeId = this.getAttribute('data-store-id');
    //             // 상점 삭제 API 호출
    //             fetch(`/store/delete/${storeId}`, {
    //                 method: 'DELETE'
    //             }).then(response => {
    //                 if (response.ok) {
    //                     window.location.reload();
    //                 } else {
    //                     alert("상점 삭제에 실패했습니다.");
    //                 }
    //             });
    //         }
    //     });
    // });

})