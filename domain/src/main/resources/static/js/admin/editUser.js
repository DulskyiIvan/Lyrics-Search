$(document).ready(function () {
    $('#user-table').on('click', '.edit-user-button', function () {
        let userId = $(this).data('user-id');
        $('#edit-password-modal').data('user-id', userId);
        $('#edit-password-modal').modal('show');
    });

    $('#generate-password-button').on('click', function () {
        let passwordInput = $('#new-password-input');
        let newPassword = Math.floor(Math.random() * 900000 + 100000);
        passwordInput.val(newPassword);
    });

    $('#save-password-button').on('click', function () {
        let userId = $('#edit-password-modal').data('user-id');
        let newPassword = $('#new-password-input').val();

        let user = {
            id: userId,
            password: newPassword
        };

        $.ajax({
            url: '/admin/update-user-password',
            type: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify(user),
            success: function () {
                alert('User password successfully changed!');
                $('#edit-password-modal').modal('hide');
            },
            error: function () {
                alert('Error changing user password!');
            }
        });
    });

    $('#edit-password-modal').on('hidden.bs.modal', function () {
        $('#new-password-input').val('');
        $(this).removeData('user-id');
    });

    $('#save-role-button').on('click', function () {
        let userId = $('#edit-password-modal').data('user-id');
        let roleId = $('#select-role option:selected').val();
        let roleTitle = $('#select-role option:selected').text();

        let role = {
            id: roleId,
            title: roleTitle
        }

        let user = {
            id: userId,
            role: role
        };

        $.ajax({
            url: '/admin/update-user-role',
            type: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify(user),
            success: function () {
                alert('User role successfully changed!');
                $('#edit-password-modal').modal('hide');
            },
            error: function () {
                alert('Error changing user role!');
            }
        });
    });
});