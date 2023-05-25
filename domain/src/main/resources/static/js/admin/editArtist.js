$(document).ready(function () {
    $('#artist-table').on('click', '.edit-artist-button', function () {
        let artistId = $(this).data('artist-id');
        let description = $(this).data('artist-description')
        let name = $(this).data('artist-name')

        let descriptionArea = $('#edit-description-textarea');
        let nameInput = $('#new-artist-name-input');
        descriptionArea.val(description);
        nameInput.val(name);

        $('#edit-artist-modal').data('artist-id', artistId);
        $('#edit-artist-modal').modal('show');
    });

    $('#update-artist-button').on('click', function () {
        let artistId = $('#edit-artist-modal').data('artist-id');
        let descriptionArea = $('#edit-description-textarea').val();
        let nameInput = $('#new-artist-name-input').val();


        let artist = {
            id: artistId,
            name: nameInput,
            description: descriptionArea
        };

        $.ajax({
            url: '/admin/update-artist',
            type: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify(artist),
            success: function () {
                alert('Artist  successfully updated!');
                $('#edit-artist-modal').modal('hide');
            },
            error: function () {
                alert('Error updating artist!');
            }
        });
    });


});