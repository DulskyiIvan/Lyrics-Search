const deleteSelectedLyricBtn = document.getElementById('deleteSelectedLyric');
deleteSelectedLyricBtn.addEventListener('click', function (event) {
    event.preventDefault();
    const select = document.getElementById('select-lyric');
    const selectedOption = select.selectedOptions[0];
    const lyricId = selectedOption.getAttribute('data-lyric-id');
    let songId = document.getElementById("song-id").value;
    fetch(`/lyric/delete?songId=${songId}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            id: lyricId
        })
    })
        .then(function (response) {
            if (response.ok) {
                alert("Lyric successfully deleted!")
                selectedOption.remove();
                select.dispatchEvent(new Event('change'));
            } else {
                throw new Error('Network response was not ok');
            }
        })
        .catch(function (error) {
            console.error('There was a problem with deleting lyric:', error);
            alert("Something went wrong!")
        });
});