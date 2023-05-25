document.addEventListener('DOMContentLoaded', () => {

    let currentPage = 1;

    const getAllSongsButton = document.getElementById('all-songs-button');
    getAllSongsButton.addEventListener('click', function () {
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
    });

    const searchSongsForm = document.getElementById('search-songs-form');
    searchSongsForm.addEventListener('submit', function (event) {
        event.preventDefault();
        const songTable = document.querySelector("#song-table tbody");
        songTable.innerHTML = "";
        const paginationContainer = document.querySelector('#pagination-songs-container');
        paginationContainer.innerHTML = '';
        const titleInput = document.querySelector('input[name="title"]');
        const title = titleInput.value;

        currentPage = 0;
        getSongsByTitle(currentPage, title)
            .then(({songs, totalPages}) => {
                    if (songs.length > 0) {
                        showSongsTable(songs);
                        renderPagination(totalPages, currentPage);
                        addPaginationHandlerGetByTitle(title);
                    }
                }
            )
    });

    function getSongsByTitle(page, title) {
        const url = `/search-by-title?title=${title}&page=${page}`;
        return fetch(url)
            .then(response => response.json())
            .then(page => {
                const songs = page.content;
                const totalPages = page.totalPages;
                return {songs, totalPages};
            })
            .catch(error => console.log(error));
    }


    function getAllSongs(page) {
        const url = `/get-all-song?page=${page}`;
        return fetch(url)
            .then(response => response.json())
            .then(page => {
                const songs = page.content;
                const totalPages = page.totalPages;
                return {songs, totalPages};
            })
            .catch(error => console.log(error));
    }


    function showSongsTable(songs) {
        const songTable = document.querySelector("#song-table tbody");
        songTable.innerHTML = "";
        if (songs) {
            songs.forEach((song) => {
                const tr = document.createElement("tr");
                tr.setAttribute("id", song.id)
                const idTd = document.createElement("td");
                idTd.textContent = song.id;
                tr.appendChild(idTd);
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
                const deleteButton = document.createElement("button");
                deleteButton.classList.add("btn", "btn-primary", "delete-song-button");
                deleteButton.setAttribute("data-song-id", song.id);
                deleteButton.addEventListener("click", deleteSong);
                deleteButton.setAttribute("sec:authorize", "hasAnyAuthority('SUPERADMIN','ADMIN')");
                deleteButton.textContent = "Delete";
                const deleteColumn = document.createElement("td");
                deleteColumn.classList.add("delete-song-column");
                deleteColumn.setAttribute("id", "delete-song-column");
                deleteColumn.appendChild(deleteButton);
                tr.appendChild(deleteColumn);
                songTable.appendChild(tr);
            });
        }
    }


    function deleteSong() {
        const songData = this.getAttribute('data-song-id');
        const songId = songData.valueOf();

        if (confirm('Are you sure you want to delete this song?')) {
            const song = {
                id: songId
            };

            fetch(`/admin/delete-song`, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(song)
            })
                .then(function (response) {
                    if (response.status === 204) {
                        alert('Song successfully deleted!');
                        const tr = document.querySelector(`tr[id="${songId}"]`);
                        tr.remove();
                    } else {
                        throw new Error('Something went wrong!');
                    }
                })
                .catch(function (error) {
                    console.error(error);
                    alert(error.message);
                });
        }
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

    function addPaginationHandlerGetByTitle(title) {
        const paginationLinks = document.querySelectorAll('.page-link');
        paginationLinks.forEach(link => {
            link.addEventListener('click', function (event) {
                event.preventDefault();
                currentPage = parseInt(event.target.textContent) - 1;
                getSongsByTitle(currentPage, title).then(({songs, totalPages}) => {
                    showSongsTable(songs);
                });
            });
        });
    }
});
