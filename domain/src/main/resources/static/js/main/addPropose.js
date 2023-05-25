$(document).ready(function () {

    $('#add-lyric-button').click(function () {
        $('#add-propose-modal').modal('show');
    });

    $('#add-propose-button').on('click', function () {
        let songId = $('#song-id').val();
        let lyricText = $('#add-propose-textarea').val();

        let propose = {
            lyricText: lyricText
        };

        $.ajax({
            url: '/propose-edit/add?songId=' + songId,
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(propose),
            success: function () {
                alert('Propose edit lyric successfully added!');
                $('#add-propose-modal').modal('hide');
            },
            error: function () {
                alert('Error added propose edit lyric!');
            }
        });
    });
});