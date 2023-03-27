console.log("사인업 제이에스");


const checkObj = {
    "memberEmail" : false, // 1) 정규 표현식 이메일 형식 맞는지 체크 -> 2) 맞으면 AJAX 통신 중복검사 -> true
    "memberPw" : false,  // 1) 정규 표현식 체크
    "mwmeberPwConfirm" : false, // 비밀번호랑 맞는지 체크
    "memberNickName" : false, // 1) 정규 표현식(영어/숫자/한글 2~10글자) 이메일 형식 맞는지 체크 -> 2) 맞으면 AJAX 통신 중복검사 -> true
    "memberTel" : false, // 1) 정규 표현식 체크
    "sendEmail" : false
};






const memberEmail = document.getElementById("memberEmail");

const sendBtn = document.getElementById("sendBtn");
const cMessage = document.getElementById("cMessage");
const cNumber = document.getElementById("cNumber");
const emailMessage = document.getElementById("emailMessage"); // 이메일 밑에 메세지



let checkInterval;
let min = 4;
let sec = 59;


memberEmail.addEventListener("keyup", e =>{
    
    //이메일 형식 정규표현식 
    let regExp =  /^[0-9A-Z]([-_\.]?[0-9A-Z])*@[0-9A-Z]([-_\.]?[0-9A-Z])*\.[A-Z]{2,6}$/i;
    
    if(regExp.test(e.target.value)){
        checkObj.memberEmail = true;
        emailMessage.innerText = "사용 가능한 이메일 형식입니다.";
        emailMessage.classList.add("confirm");
        emailMessage.classList.remove("error");
    }else{
        emailMessage.innerText = "사용 불가능한 이메일 형식입니다.";
        emailMessage.classList.add("error");
        emailMessage.classList.remove("confirm");
    }
});



sendBtn.addEventListener("click", () =>{


	console.log("?????????: ", !checkObj.memberEmail)
    //멤버 이메일이 값이 있을 때, 인증번호가 날라와야함.
    if(checkObj.memberEmail){
        console.log("tet!!!")
        $.ajax({
            url : "sendEmail",
            data : {"inputEmail" : memberEmail.value},
            type : "GET",
            success : function(result){
                
                console.log("이메일 발송 성공");
                checkObj.memberEmail = true;
                console.log(checkObj.memberEmail);
            },
            error: function(){
                console.log("이메일 발송 실패");
            }
        }); 
    }

    cMessage.innerText = "5:00"; // 초 나오는거
    
    min = 4; // 분 설정 
    sec = 59; // 초 설정
    // 4:59 초 부터 시작할거기때문에 셋인터벌은 1초 지연되게 설정

    cMessage.classList.remove("confirm");
    cMessage.classList.remove("error");

    checkInterval = setInterval(function(){
        
        if(sec < 10) sec = "0" + sec;
        cMessage.innerText = min + ":" + sec +"초 남았습니다.";
        
        if(Number(sec) == 0){
            min--;
            sec = 59;

        }else{
            sec--;
        }

        if( min == -1){
            cMessage.innerText = "인증 시간이 만료되었습니다.";
            cMessage.classList.add("error");
            clearInterval(checkInterval);
        }
        
    },1000);

    alert("인증번호가 발송되었습니다.");
    
});



// 인증번호 받는 버튼
const cBtn = document.getElementById("cBtn");

// 인증번호를 입력하고 클릭했을 때, 값이 DB값과 같아야함.
cBtn.addEventListener("click", function() {

    $.ajax({
        url : "sendCheckNumber",
        data : {"memberEmail" : memberEmail.value},
        type : "GET",
        success : function(result){
            console.log("인증 성공");
            console.log(result);
            
            if(result.equals(cNumber.value) && checkInterval != 0){
                alert("인증 완료");
                clearInterval(checkInterval);
                
            }else{
                alert("인증 실패 다시 인증 시도해주세요.");
                clearInterval(checkInterval);
            }
            
        },
        error: function(){
            console.log("인증 실패");
        }
    })
});



const memberPw = document.getElementById("memberPw"); // 비번 인풋 요소
const memberPwConfirm = document.getElementById("memberPwConfirm"); // 비번 확인 인풋 요소
const pwMessage = document.getElementById("pwMessage"); // 비번이 맞으면 밑에 텍스트 출력 요소


// 1차 비밀번호 확인
memberPw.addEventListener("keyup", e =>{
    
    const pwRegExp = /^[a-zA-Z0-9!@#-_]{6,30}/; // 비밀번호 정규 표현식

    if(pwRegExp.test(e.target.value)){
        checkObj.memberPw = true;
        console.log(pwRegExp.test(e.target.value));
        pwMessage.innerText = "사용 가능한 비밀번호 입니다.";
        pwMessage.classList.add("confirm");
        pwMessage.classList.remove("error");
    }else{
        pwMessage.innerText = "사용 불가능한 비밀번호 입니다.";
        console.log(pwRegExp.test(e.target.value));
        pwMessage.classList.add("error");
        pwMessage.classList.remove("confirm");
    }
});


// 2차 비밀번호 확인
// 1차 비밀번호와 값이 같아야한다.
memberPwConfirm.addEventListener("keyup", e =>{

    if(memberPw.value == e.target.value){
        pwMessage.innerText = "비밀번호가 일치합니다.";
        checkObj.memberPwConfirm = true;
    }else{
        pwMessage.innerText = "비밀번호가 일치하지 않습니다. 다시 입력해주세요.";
    }
});


// 닉네임
// 닉네임은 디비에 있기에 에이젝스 통신으로 디비에 전달해주자

const nickName = document.getElementById("memberNickName");
const nickMessage = document.getElementById("nicknameMessage");

nickName.addEventListener("keyup", e => {
    
    // 닉네임 정규 표현식을 먼저 작성
    const nickRegExp = /^[a-zA-Z가-힣]{2,10}]$/;
    
    // 닉네임 형식과 맞으면 에이젝스 통신을 통해서 디비로 닉네임 값을 넘겨야한다.

    if(nickRegExp.test(e.target.value)){
        console.log("성공");
        $.ajax({
            url : "nickName",
            data : {"nickName" : nickName.value},
            type : "GET",
            success : function(result){
               
                if(result == 1){
                    nickMessage.innerText = "사용 불가능한 닉네임입니다.";
                    nickMessage.classList.add("error");
                    nickMessage.classList.remove("confirm");
                    
                }else{
                    nickMessage.innerText = "사용 가능한 닉네임";
                    nickMessage.classList.remove("error");
                    nickMessage.classList.add("confirm");
                    checkObj.memberNickName = true;
                }
            },
            error : function(){
                console.log("실패");
            }
        });
    }else{
        nickMessage.innerText = "영어/숫자/한글 2~10글자 사이로 작성해주세요.";
    }
});


const memberTel = document.getElementById("memberTel");
const telMessage = document.getElementById("telMessage");

memberTel.addEventListener("keyup", e =>{

    const telRegExp = /^[0-9]{10,11}$/;

    if(telRegExp.test(e.target.value)){
        checkObj.memberTel = true;
        telMessage.innerText = "유효한 전화번호입니다.";
        telMessage.classList.add("confirm");
        telMessage.classList.remove("error");
    }else{
        telMessage.innerText = "유효하지 않은 전화번호입니다.";
        telMessage.classList.remove("confirm");
        telMessage.classList.add("error");
    }
    
});


function signUpVD(){
    if(checkObj.memberEmail == true && checkObj.memberNickName == true && checkObj.memberPw ==true
        && checkObj.mwmeberPwConfirm == true && checkObj.memberTel == true){
            alert("회원가입완료");
        }else{
            alert("회원가입 미완료");
        }
}


