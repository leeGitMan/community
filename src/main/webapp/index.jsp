<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>



<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>community</title>

    <link rel="stylesheet" href="resources/css/main-style.css">

    <!--  fontawesome 사이트 아이콘 이용   -->
    <script src="https://kit.fontawesome.com/f7459b8054.js" crossorigin="anonymous"></script>
</head>
<body>
    <!-- 
        기존 영역 분할에 사용한 div, span 태그는
        태그의 이름만 봤을 때 나눈다는 것 이외의 의미를 파악할 수 없다.
        -> id, 또는 class 속성을 필수적으로 추가해야하는 번거로움이 있음

        이러한 문제점을 해결하고자
        태그의 이름만으로 어느정도 어떤 역할을 하는지 알 수 있고
        추가적으로 웹 접근성 향상에 도움이되는
        시맨틱 태그(Semantic Tag, 의미있는 태그)가 HTML5에서 추가됨.

        * 시맨틱 태그는 div 태그의 이름만 바뀐 것으로 생각하는게 좋다!
         
        [시맨틱 태그 종류]
        <main> : 현재 문서의 주된 콘텐츠를 작성하는 영역

        <header> : 문서의 제목, 머리말 영역

        <footer> : 문선의 하단 부분, 꼬리말, 정보 작성 영역

        <nav> : 나침반 역할(다른 페이지, 사이트 이동)의 링크 작성 영역

        <aside> : 사이드바, 광고 등을 표시하는 양쪽 영역
        
        <section> : 구역을 구분하기 위한 영역

        <article> : 본문과 독립된 콘텐츠를 작성하는 영역
    -->

    <main>
       
	  <!-- 내부 접근 절대 경로 -->
		<jsp:include page="/WEB-INF/views/common/header.jsp"/>

        <section class="content">
            <section class="content-1">

                <h3>이메일 검색(AJAX)</h3>
                <p>회원 정보 조회</p>

                이메일 검색 : <input type="text" name="inputEmail" id="input1">
                <div id="result1" style="height : 150px"></div>
                <button id="btn1">검색하기(이메일)</button>

                <h3>회원 목록 조회</h3>
                <div id="result2" style="height: 150px"></div>

            </section>

            <section class="content-2">

                <form action="#" name="login-frm">
        
                    <!-- 아이디, 비밀번호, 로그인 버튼 -->
                    <fieldset id="id-pw-area">
                        <section>
                            <input type="text" name="inputId"
                             placeholder="아이디" autocomplete="off">
                            <!-- autocomplete="off" : 자동완성 사용 X -->
        
                            <input type="password" name="inputPw" placeholder="비밀번호">
                        </section>
        
                        <section>
                            <!-- type="submit"이 기본값 -->
                            <button>로그인</button>
                        </section>
                    </fieldset>
        
        
                    <!-- label 태그 내부에 input태그를 작성하면 자동 연결됨 -->
                    <label>
                        <input type="checkbox" name="saveId"> 아이디 저장
                    </label>
        
        
                    <!-- 회원가입 / ID/PW 찾기 -->
                    <article id="signUp-find-area">
                        <a href="${contextPath}/member/signUp">회원가입</a>
                        <span>|</span>
                        <a href="#">ID/PW찾기</a>
                    </article>
                </form>
            </section>
        </section>

    </main>
    
     <!-- footer include -->
    
    <jsp:include page="/WEB-INF/views/common/footer.jsp"/>

    
	<!-- jQuery 라이브러리 추가 -->
 	<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>

    <!--  main.js 연결 -->
	<script src="${contextPath}/resources/js/main.js"></script>


</body>
</html>