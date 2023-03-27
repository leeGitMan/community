console.log("메인 로드");

document.getElementById("btn1").addEventListener("click", function(){

    const input = document.getElementById("input1");
    const div = document.getElementById("result1");

    
    $.ajax({

        url : "member/selectOne",
        data : {"memberEmail" : input.value},
        type : "POST",
        dataType : "JSON",
        success : function(member){
            console.log("test");
            console.log(member);
            
            if(member != null){ // 회원 정보 존재 O

                // 2) ul 요소 생성
                const ul = document.createElement("ul");

                // 3) li 요소 생성 * 5 + 내용 추가
                const li1 = document.createElement("li");
                li1.innerText = "이메일 : " + member.memberEmail;

                const li2 = document.createElement("li");
                li2.innerText = "닉네임 : " + member.memberNickname;

                const li3 = document.createElement("li");
                li3.innerText = "전화번호 : " + member.memberTel;

                const li4 = document.createElement("li");
                li4.innerText = "주소 : " + member.memberAddress;

                const li5 = document.createElement("li");
                li5.innerText = "가입일 : " + member.enrollDate;

                // 4) ul에 li를 순서대로 추가
                ul.append(li1, li2, li3, li4, li5);

                // 5) div에 ul 추가
                div.append(ul);

            } else { // 회원 정보 존재 X

                // 1) h4 요소 생성
                const h4 = document.createElement("h4");

                // 2) 내용 추가
                h4.innerText = "일치하는 회원이 없습니다";

                // 3) 색 추가
                h4.style.color = "red";

                // 4) div에 추가
                div.append(h4);
            }



        },

        error : function(request, status, error){
            console.log("ajax 에러 발생");
            console.log("상태 코드 : " + request.status); // 404, 500
        }
    })

});



function createTable(){
    const div = document.getElementById("result2");

    $.ajax({
        url : "member/selectAll",
        type : "POST",
        dataType : "JSON",
        success : function(memberList) {
            

            // div 내부에 HTML 태그가 없어야함.
            div.innerHTML = "";
            
            // 테이블 헤드
            const table = document.createElement("table");
            const tr = document.createElement("tr");
            const th1 = document.createElement("th");
            const th2 = document.createElement("th");
            const th3 = document.createElement("th");

           

            

            th1.innerText="회원번호";
            th2.innerText="이메일";
            th3.innerText="닉네임";

            if(memberList != null){
                tr.append(th1);
                tr.append(th2);
                tr.append(th3);

                for(let member of memberList){

                     // 테이블 데이터
                    const tr = document.createElement("tr");
                    const td1 = document.createElement("td");
                    const td2 = document.createElement("td");
                    const td3 = document.createElement("td");

                    td1.innerText = member.memberNo;
                    td2.innerText = member.memberEmail;
                    td3.innerText = member.memberNickname;

                    tr.append(td1,td2,td3);

                    table.append(tr);

                }

                div.append(table);

            }else{
                div.innerHTML = "";
                const h3 = document.createElement("h3");

                h3.innerText = "회원 목록이 없습니다.";
                
                div.append(h3);
            }


        },
        error : function(request, status, error){
            console.log("ajax 에러 발생");
        }
    });
}

window.onlaoad = createTable();

window.setInterval(createTable, 10000);
    

