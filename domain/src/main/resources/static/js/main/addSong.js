$(document).ready(function () {
    $('#add-song-form').submit(function (event) {
        event.preventDefault();
        let artistName = $('input[name="artist"]').val();
        let title = $('input[name="title"]').val();
        let releaseDateInput = $('#date-input').val();
        let genreId = $('#dropDownList').val();
        let youtubeLink = $('input[name="youtube-link"]').val();
        let lyric = $('textarea[name="lyric"]').val();

        let song = {
            title: title,
            artist: artistName,
            lyric: lyric,
            releaseDate: releaseDateInput,
            genre: genreId,
            youtubeLink: youtubeLink
        }

        $.ajax({
            url: '/save-song',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(song),
            success: function (response) {
                alert('Song successfully saved!');
                window.location.replace("/song?id=" + response.id)
            },
            error: function (response) {
                let errors = JSON.parse(response.responseText);
                let errorMessage = "Something went wrong! Error messages:\n" + errors;
                alert(errorMessage);
            }
        });

    });
});