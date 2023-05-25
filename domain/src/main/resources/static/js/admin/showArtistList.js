$(document).ready(function () {


    let currentPage = 1;

    const searchArtistForm = document.getElementById('search-artists-form');
    searchArtistForm.addEventListener('submit', function (event) {
        event.preventDefault();
        const artistTable = document.querySelector('#artist-table tbody');
        artistTable.innerHTML = '';
        const paginationContainer = document.querySelector('#pagination-artists-container');
        paginationContainer.innerHTML = '';
        currentPage = 0;
        const artistNameInput = document.querySelector('input[name="artist-name"]');
        const name = artistNameInput.value;

        searchArtistsByQuery(currentPage, name)
            .then(({artists, totalPages}) => {
                if (artists.length > 0) {
                    showArtistList(artists);
                    renderPagination(totalPages);
                    addPaginationHandlerSearchByQuery(currentPage);
                }
            });

    });

    const getAllArtistsButton = document.getElementById('all-artists-button');
    getAllArtistsButton.addEventListener('click', function () {
        const artistTable = document.querySelector('#artist-table tbody');
        artistTable.innerHTML = '';
        const paginationContainer = document.querySelector('#pagination-artists-container');
        paginationContainer.innerHTML = '';
        currentPage = 0;
        getAllArtists(currentPage)
            .then(({artists, totalPages}) => {
                if (artists.length > 0) {
                    showArtistList(artists);
                    renderPagination(totalPages);
                    addPaginationHandlerGetAllArtist();
                }
            });

    });


    function searchArtistsByQuery(page, query) {
        const url = `/artist/artist-page?query=${query}&page=${page}`;
        return fetch(url)
            .then(response => response.json())
            .then(page => {
                const artists = page.content;
                const totalPages = page.totalPages;
                return {artists, totalPages};
            })
            .catch(error => console.log(error));
    }

    function getAllArtists(page) {
        const url = `/artist/all?page=${page}`;
        return fetch(url)
            .then(response => response.json())
            .then(page => {
                const artists = page.content;
                const totalPages = page.totalPages;
                return {artists, totalPages};
            })
            .catch(error => console.log(error));
    }


    function showArtistList(artists) {
        const artistTable = document.querySelector('#artist-table tbody');
        artistTable.innerHTML = '';
        if (artists) {
            artists.forEach(artist => {
                const tr = document.createElement('tr');
                tr.setAttribute("id", artist.id)
                const idTd = document.createElement('td');
                idTd.textContent = artist.id;
                tr.appendChild(idTd);
                const nameTd = document.createElement('td');
                nameTd.textContent = artist.name;
                tr.appendChild(nameTd);
                const descriptionTd = document.createElement('td');
                descriptionTd.textContent = artist.description;
                tr.appendChild(descriptionTd);
                const editButton = document.createElement('button');
                editButton.classList.add('btn', 'btn-primary', 'edit-artist-button');
                editButton.setAttribute('data-artist-id', artist.id);
                editButton.setAttribute('data-artist-description', artist.description);
                editButton.setAttribute('data-artist-name', artist.name);
                editButton.textContent = 'Edit';
                const editColumn = document.createElement('td');
                editColumn.classList.add('edit-artist-column');
                editColumn.appendChild(editButton);
                const deleteButton = document.createElement('button');
                deleteButton.classList.add('btn', 'btn-primary', 'delete-artist-button');
                deleteButton.setAttribute('data-artist-id', artist.id);
                deleteButton.setAttribute('data-artist-description', artist.description);
                deleteButton.setAttribute('data-artist-name', artist.name);
                deleteButton.addEventListener("click", deleteArtist);
                deleteButton.textContent = 'Delete';
                const deleteColumn = document.createElement('td');
                deleteColumn.classList.add('edit-artist-column');
                deleteColumn.appendChild(deleteButton);
                tr.appendChild(deleteColumn);
                tr.appendChild(editColumn);
                artistTable.appendChild(tr);
            });
        }
    }

    function deleteArtist() {
        const artistDataId = this.getAttribute('data-artist-id');
        const artistDataName = this.getAttribute('data-artist-name');
        const artistDataDescription = this.getAttribute('data-artist-description');
        const artistId = artistDataId.valueOf();
        const artistName = artistDataName.valueOf();
        const artistDescription = artistDataDescription.valueOf();

        if (confirm('Are you sure you want to delete this artist?')) {
            const artist = {
                id: artistId,
                name: artistName,
                description: artistDescription
            };

            fetch(`/admin/delete-artist`, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(artist)
            })
                .then(function (response) {
                    if (response.status === 204) {
                        alert('Artist successfully deleted!');
                        const tr = document.querySelector(`tr[id="${artistId}"]`);
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
        const paginationContainer = document.querySelector('#pagination-artists-container');
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

    function addPaginationHandlerGetAllArtist() {
        const paginationLinks = document.querySelectorAll('.page-link');
        paginationLinks.forEach(link => {
            link.addEventListener('click', function (event) {
                event.preventDefault();
                currentPage = parseInt(event.target.textContent) - 1;
                getAllArtists(currentPage).then(({artists, totalPages}) => {
                    showArtistList(artists);
                });
            });
        });
    }

    function addPaginationHandlerSearchByQuery(query) {
        const paginationLinks = document.querySelectorAll('.page-link');
        paginationLinks.forEach(link => {
            link.addEventListener('click', function (event) {
                event.preventDefault();
                currentPage = parseInt(event.target.textContent) - 1;
                searchArtistsByQuery(currentPage, query).then(({artists, totalPages}) => {
                    showArtistList(artists);
                });
            });
        });
    }

});