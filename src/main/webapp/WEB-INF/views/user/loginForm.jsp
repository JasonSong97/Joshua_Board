<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="../layout/header.jsp" %>

<div class="container my-3">

    <div class="container">
        <form action="/login" method="post">
            <div class="form-group mb-2">
                <input type="text" name="username" class="form-control" placeholder="Enter username" value="park">
            </div>

            <div class="form-group mb-2">
                <input type="password" name="password" class="form-control" placeholder="Enter password" value="1234">
            </div>

            <button class="btn btn-primary">로그인</button>
            <a href="https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=536973069c3a0abd183851a68226f823&redirect_uri=http://localhost:8080/auth/kakao/callback"><img height="47px" src="/images/kakao_login_button.png"/></a>
        </form>

    </div>
</div>

<%@ include file="../layout/footer.jsp" %>