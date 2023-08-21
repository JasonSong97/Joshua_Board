<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="../layout/header.jsp" %>


<div class="container my-3">
    <c:if test="${sessionUser.id == board.user.id}">
        <div class="mb-3 d-flex">
            <a href="/s/board/${board.id}/updateForm" class="btn btn-warning">수정</a>
            <form action="/s/board/${board.id}/delete" method="post">
                <button class="btn btn-danger">삭제</button>
            </form>
        </div>
    </c:if>


    <div class="mb-2 d-flex justify-content-end">
        글 번호 :
        <span id="id" class="me-3">
            <i>${board.id}</i>
        </span>
        작성자 :
        <span class="me-3">
            <i>${board.user.username}</i>
        </span>
    </div>

    <div>
        <h1><b>${board.title}</b></h1>
    </div>
    <hr/>
    <div>
        <div>${board.content}</div>
    </div>

    <hr/>
    <!-- 러브 -->
    <i class="fas fa-heart active" id="storyLikeIcon-${board.id}" onclick="toggleLike(${board.id})"></i>


    <!-- 등록 -->
    <div class="card mt-3">
        <form>
            <input type="hidden" id="boardId" value="${board.id}"/>
            <div class="card-body">
                <textarea id="reply-content" class="form-control" rows="1"></textarea>
            </div>
            <div class="card-footer">
                <button type="button" id="btn-reply-save" class="btn btn-primary">등록</button>
            </div>
        </form>
    </div>

    <br/>
    
    <!-- 댓글 -->
    <div class="card">
        <div class="card-header">댓글 리스트</div>
        <ul id="reply-box" class="list-group">
            <c:forEach var="reply" items="${board.replys}">
                <li id="reply-${reply.id}" class="list-group-item d-flex justify-content-between">
                    <div>${reply.content}</div>
                        <div class="d-flex">
                            <div class="font-italic"><strong>작성자</strong> : ${reply.user.username} &nbsp;</div>
                            <div class="font-italic"><strong>작성날짜</strong> : ${reply.createdAt} &nbsp;</div> <!--날짜 check-->
                            <c:if test="${sessionUser.id == reply.user.id}">
                                <button onClick="index.replyDelete(${board.id}, ${reply.id})" class="badge bg-secondary">삭제</button>
                            </c:if>
                        </div>
                </li>
            </c:forEach>
        </ul>
    </div>

</div>
<script src="/js/board.js"></script>
<%@ include file="../layout/footer.jsp" %>