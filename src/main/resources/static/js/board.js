let index = {
    init: function(){
        $("#btn-save").on("click", ()=>{
            this.save();
        });
        $("#btn-delete").on("click", ()=>{
            this.delete();
        });
        $("#btn-update").on("click", ()=>{
            this.update();
        });
        $("#btn-reply-save").on("click", ()=>{
        this.replySave();
        });

    },

    save: function(){
        let data = {
            title: $("#title").val(),
            content: $("#content").val()
        };
    
        $.ajax({
            type: "POST",   
            url: "",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done((res) => { 
            alert("게시글 작성이 완료었습니다. ");
            location.href = "/";
        }).fail((err) => { 
            alert(JSON.stringify(err));
        });
    },

    delete: function(){
        let id = $("#id").text();
    
        $.ajax({
            type: "DELETE",
            url: "",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "JSON"
        }).done((res) => { 
            alert("게시글 삭제가 완료되었습니다. ");
            location.href = "/";
        }).fail((err) => { 
            alert(err);
            console.log(err);
        });
    },

    update: function(){
        let id = $("#id").val();

        let data = {
            title: $("#title").val(),
            content: $("#content").val()
        };
    
        $.ajax({
            type: "POST",
            url: "/api/board/" + id,
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
        }).done((res) => { 
            alert("게시글이 수정되었습니다. ");
            location.href = "/";
        }).fail((err) => { 
            alert(JSON.stringify(err));
        });
    },

    replySave: function(){
        let data = {
            userId: $("#userId").val(),
            boardId: $("#boardId").val(),
            content: $("#reply-content").val()
        };

        console.log(data);

        $.ajax({
            type: "POST",   
            url: `/api/board/${data.boardId}/reply`,
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
        }).done((res) => { 
            alert("댓글 작성이 완료었습니다. ");
            location.href = `/board/${data.boardId}`;
        }).fail((err) => { 
            alert(JSON.stringify(err));
        });
    },

    replyDelete: function(boardId, replyId){
        $.ajax({
            type: "DELETE",   
            url: `/api/board/${boardId}/reply/${replyId}`,
        }).done((res) => { 
            alert("댓글 삭제가 완료었습니다. ");
            location.href = `/board/${boardId}`;
        }).fail((err) => { 
            alert(JSON.stringify(err));
        });
    },
}

function toggleLove(boardId) {
    let loveIcon = $(`#boardLoveIcon-${boardId}`);

    if (loveIcon.hasClass("far")) {
        loveIcon.addClass("fas");
        loveIcon.addClass("active");
        loveIcon.removeClass("far");
    } else {
        loveIcon.removeClass("fas");
        loveIcon.removeClass("active");
        loveIcon.addClass("far");
    }
}

index.init();