$(document).ready(function () {
    $('#propose-table').on('click', '.edit-propose-button', function () {
        let songId = $(this).data('song-id');
        let proposeId = $(this).data('propose-id');
        let lyricText = $(this).data('propose-text')


        let lyricTextArea = $('#edit-lyric-textarea');
        lyricTextArea.val(lyricText);

        $('#edit-propose-modal').data('song-id', songId);
        $('#edit-propose-modal').data('propose-id', proposeId);
        $('#edit-propose-modal').modal('show');
    });

    $('#add-lyric-button').on('click', function () {

        let songId = $('#edit-propose-modal').data('song-id');
        let proposeId = $('#edit-propose-modal').data('propose-id');
        let lyricTextArea = $('#edit-lyric-textarea').val();


        let propose = {
            id: proposeId,
            lyricText: lyricTextArea
        };

        $.ajax({
            url: '/admin/add-lyric?songId=' + songId,
            type: 'POST',
            async: false,
            contentType: 'application/json',
            data: JSON.stringify(propose),
            success: function () {
                alert('Lyric  successfully added!');
                $('#edit-propose-modal').modal('hide');
                const $tr = $(`tr[id="${proposeId}"]`);
                $tr.remove();
            },
            error: function () {
                alert('Error add lyric!');
            }
        });
    });


});