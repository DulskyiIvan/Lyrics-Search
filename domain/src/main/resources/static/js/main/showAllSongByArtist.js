document.addEventListener('DOMContentLoaded', () => {

    let currentPage = 1;

    const paginationContainer = document.querySelector('#pagination-songs-container');
    paginationContainer.innerHTML = '';
    const songTable = document.querySelector("#song-table tbody");
    songTable.innerHTML = "";
    currentPage = 0;
    getAllSongs(currentPage).then(({songs, totalPages}) => {
        if (songs.length > 0) {
            showSongsTable(songs);
            renderPagination(totalPages, currentPage);
            addPaginationHandlerGetAll();
        }
    });

    function getAllSongs(page) {


        let artistIdInput = document.getElementById("artist-id");
        let artistId = artistIdInput.value;

        const url = `/all-songs-by-artist?page=${page}&artistId=${artistId}`;

        return fetch(url, {
            method: 'GET',
        })
            .then(response => response.json())
            .then(page => {
                const songs = page.content;
                const totalPages = page.totalPages;
                return {songs, totalPages};
            })
            .catch(error => console.log(error));
    }


    function renderPagination(totalPages) {
        const paginationContainer = document.querySelector('#pagination-songs-container');
        paginationContainer.innerHTML = '';
        if (totalPages >= 1) {
            const ul = document.createElement('ul');
            ul.classList.add('pagination');
            paginationContainer.appendChild(ul);
            for (let i = 1; i <= totalPages; i++) {
                const li = document.createElement('li');
                const link = document.createElement('a');
                link.href = '#';
                link.classList.add('page-link');
                link.textContent = i;
                if (i === currentPage) {
                    li.classList.add('active');
                }
                li.appendChild(link);
                ul.appendChild(li);
            }
        }
    }

    function addPaginationHandlerGetAll() {
        const paginationLinks = document.querySelectorAll('.page-link');
        paginationLinks.forEach(link => {
            link.addEventListener('click', function (event) {
                event.preventDefault();
                currentPage = parseInt(event.target.textContent) - 1;
                getAllSongs(currentPage).then(({songs, totalPages}) => {
                    showSongsTable(songs);
                });
            });
        });
    }

    function showSongsTable(songs) {
        const songTable = document.querySelector("#song-table tbody");
        songTable.innerHTML = "";
        if (songs) {
            songs.forEach((song) => {
                const tr = document.createElement("tr");
                tr.setAttribute("style", "height: 55px");
                const titleTd = document.createElement("td");
                titleTd.textContent = song.title;
                tr.appendChild(titleTd);
                const artistTd = document.createElement("td");
                artistTd.textContent = song.artist;
                tr.appendChild(artistTd);
                const showButton = document.createElement("a");
                showButton.classList.add("btn", "btn-primary", "show-song-button");
                showButton.setAttribute("href", "/song?id=" + song.id)
                showButton.textContent = "Show";
                const showColumn = document.createElement("td");
                showColumn.classList.add("show-song-column");
                showColumn.appendChild(showButton);
                tr.appendChild(showColumn);
                songTable.appendChild(tr);
            });
        }
    }

});