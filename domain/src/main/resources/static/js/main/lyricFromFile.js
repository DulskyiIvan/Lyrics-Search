const getLyricFromFileButton = document.getElementById('get-lyric-from-file');
const lyricTextArea = document.getElementById('text-area-lyric');
const uploadForm = document.getElementById('upload-form');

getLyricFromFileButton.addEventListener('click', function () {
    const fileInput = document.getElementById('fileInput');
    const file = fileInput.files[0];

    const formData = new FormData();
    formData.append('file', file);

    fetch('/lyric/lyric-from-file', {
        method: 'POST',
        body: formData
    }).then(response => {
        return response.text();
    }).then(lyricText => {
        lyricTextArea.value = lyricText;
    }).catch(error => {
        console.error(error);
    });
});