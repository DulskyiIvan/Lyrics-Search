function showLyrics(selectElement) {
    let selectedLyricText = selectElement.value;
    let lyricsContainer = document.getElementById("lyricsContainer");

    lyricsContainer.innerHTML = "";

    let paragraphs = selectedLyricText.split('\n');
    for (let i = 0; i < paragraphs.length; i++) {
        let paragraph = document.createElement("p");
        paragraph.textContent = paragraphs[i];
        lyricsContainer.appendChild(paragraph);
    }
}