document.getElementById("downloadSongText").addEventListener("click", function () {
    let songId = document.getElementById("song-id").value;
    let lyric = document.getElementById("lyricsContainer").textContent;
    let artistName = document.getElementById('artist').textContent;
    let titleSong = document.getElementById('title').textContent;

    let formData = new FormData();
    formData.append("songId", songId);
    formData.append("lyric", lyric);

    let xhr = new XMLHttpRequest();
    xhr.open("POST", "/lyric/download-lyric");
    xhr.responseType = "blob";
    xhr.onload = function () {
        let url = window.URL.createObjectURL(xhr.response);
        let a = document.createElement("a");
        a.href = url;
        a.download = artistName + "_" + titleSong + ".docx";
        document.body.appendChild(a);
        a.click();
        document.body.removeChild(a);
    };
    xhr.send(formData);
});