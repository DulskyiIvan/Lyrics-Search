let btn = document.getElementById("modal-btn");
let modal = document.getElementById("modal-artist-details");

let artistNameElem = document.getElementById("artist-name");
let artistDescriptionElem = document.getElementById("artist-description");

function displayModal() {
    let artistName = document.getElementById('artist').textContent;
    let artistDescription = document.getElementById('song-artist-description').value;

    artistNameElem.innerHTML = artistName;
    if (artistDescription.length === 0) {
        artistDescriptionElem.innerHTML = "~NO INFORMATION~";
    } else {
        artistDescriptionElem.innerHTML = artistDescription;
    }


    modal.style.display = "block";
}

modal.getElementsByClassName("close")[0].onclick = function () {
    modal.style.display = "none";
}

window.onclick = function (event) {
    if (event.target === modal) {
        modal.style.display = "none";
    }
}

btn.onclick = function () {
    displayModal();
}