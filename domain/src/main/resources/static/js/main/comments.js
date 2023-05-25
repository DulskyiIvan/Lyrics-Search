document.addEventListener('DOMContentLoaded', function () {
    let username = document.getElementById('username').value;
    let userRole = document.getElementById('user-role').value;
    loadComments();

    document.getElementById('commentForm').addEventListener('submit', function (event) {
        event.preventDefault();
        let commentText = document.getElementById('commentText').value;

        if (commentText.trim() !== '') {
            addComment(commentText);
        }
    });

    function loadComments() {
        let songId = document.getElementById('song-id').value;
        fetch('/comments/get-comments?songId=' + songId)
            .then(response => response.json())
            .then(data => renderComments(data))
            .catch(error => console.error(error));
    }

    function renderComments(comments) {
        let commentList = document.getElementById('commentList');
        commentList.innerHTML = '';

        comments.forEach(function (comment) {
            let commentDiv = document.createElement('div');
            let authorDiv = document.createElement('div');
            authorDiv.innerText = 'Author: ' + comment.username;
            let createdAtDiv = document.createElement('div');
            createdAtDiv.innerText = 'Created At: ' + comment.createdAt;
            let commentTextDiv = document.createElement('div');
            commentTextDiv.innerText = 'Comment: ' + comment.commentText;

            if (comment.username === username || userRole === "ADMIN") {
                let deleteButton = document.createElement('button');
                deleteButton.innerText = 'Delete';
                deleteButton.addEventListener('click', function () {
                    deleteComment(comment.id);
                });
                commentDiv.appendChild(deleteButton);
            }

            commentDiv.appendChild(authorDiv);
            commentDiv.appendChild(createdAtDiv);
            commentDiv.appendChild(commentTextDiv);
            commentList.appendChild(commentDiv);
        });
    }

    function addComment(commentText) {
        let songId = document.getElementById('song-id').value;
        let requestBody = {
            commentText: commentText
        };

        fetch('/comments/add/' + songId, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(requestBody)
        })
            .then(response => response.json())
            .then(data => {
                document.getElementById('commentText').value = '';
                renderComment(data);
            })
            .catch(error => console.error(error));
    }

    function renderComment(comment) {


        let commentDiv = document.createElement('div');
        let authorDiv = document.createElement('div');
        authorDiv.innerText = 'Author: ' + comment.username;
        let createdAtDiv = document.createElement('div');
        createdAtDiv.innerText = 'Created At: ' + comment.createdAt;
        let commentTextDiv = document.createElement('div');
        commentTextDiv.innerText = 'Comment: ' + comment.commentText;

        if (comment.username === username || userRole === "ADMIN") {
            let deleteButton = document.createElement('button');
            deleteButton.innerText = 'Delete';
            deleteButton.addEventListener('click', function () {
                deleteComment(comment.id);
            });
            commentDiv.appendChild(deleteButton);
        }

        commentDiv.appendChild(authorDiv);
        commentDiv.appendChild(createdAtDiv);
        commentDiv.appendChild(commentTextDiv);
        document.getElementById('commentList').appendChild(commentDiv);
    }

    function deleteComment(commentId) {
        let confirmed = confirm("Are you sure you want to delete this comment?");
        if (confirmed) {
            fetch(`/comments/delete?id=` + commentId, {
                method: 'DELETE'
            })
                .then(() => {
                    console.log('Comment deleted successfully!');
                    loadComments();
                })
                .catch(error => console.error(error));
        }
    }
});