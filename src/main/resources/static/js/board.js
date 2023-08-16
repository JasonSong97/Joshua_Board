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
     },

     save: function(){
          let data = {
              "title": $("#title").val(),
              "content": $("#content").val()
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
              alert(JSON.stringify(error));
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
              "title": $("#title").val(),
              "content": $("#content").val()
          };
     
          $.ajax({
              type: "POST",
              url: "/api/board/" + id,
              data: JSON.stringify(data),
              contentType: "application/json; charset=utf-8",
              dataType: "json"
          }).done((res) => { 
              alert("게시글이 수정되었습니다. ");
              location.href = "/";
          }).fail((err) => { 
               alert(JSON.stringify(error));
          });
     },
}

index.init();