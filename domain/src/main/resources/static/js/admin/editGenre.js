$(document).ready(function () {

    $('#edit-genre-button').click(function () {
        $('#edit-genre-modal').modal('show');
    });

    $('#save-genre-button').on('click', function () {
        let songId = $('#song-id').val();
        let genreId = $('#select-genre option:selected').val();

        let song = {
            id: songId,
            genre: {
                id: genreId
            }
        };

        $.ajax({
            url: '/admin/song/update-genre',
            type: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify(song),
            success: function () {
                alert('Song genre successfully changed!');
                $('#edit-genre-modal').modal('hide');
            },
            error: function () {
                alert('Error changing song genre!');
            }
        });
    });
});