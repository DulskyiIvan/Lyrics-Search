document.addEventListener('DOMContentLoaded', () => {
    showGenreTab();
    showGenreList();

    const navTabs = document.querySelectorAll('ul.nav-tabs a');

    navTabs.forEach(tab => {
        tab.addEventListener('click', e => {
            e.preventDefault();

            const target = e.target.getAttribute('href');
            const activeTab = document.querySelector('.tab-pane.active');
            activeTab.classList.remove('active');
            document.querySelector(target).classList.add('active');

            navTabs.forEach(tab => tab.classList.remove('active'));
            tab.classList.add('active');

            switch (target) {
                case '#genres':
                    showGenreTab();
                    showGenreList();
                    break;
                case '#users':
                    showUserTab();
                    break;
                case '#artists':
                    showArtistTab();
                    break;
                case '#songs':
                    showSongTab();
                    break;
                case '#proposes':
                    showProposeTab();
                    break;
            }
        });
    });

    function showGenreTab() {
        document.querySelector('ul.nav-tabs a[href="#genres"]').click();
    }

    function showArtistTab() {
        document.querySelector('ul.nav-tabs a[href="#artists"]').click();
    }

    function showUserTab() {
        document.querySelector('ul.nav-tabs a[href="#users"]').click();
    }

    function showSongTab() {
        document.querySelector('ul.nav-tabs a[href="#songs"]').click();
    }

    function showProposeTab() {
        document.querySelector('ul.nav-tabs a[href="#proposes"]').click();
    }

    document.querySelector('#add-genre-form').addEventListener('submit', function (event) {
        event.preventDefault();
        let name = document.querySelector('input[name="name"]').value;
        fetch('/admin/add-genre', {
            method: 'POST',
            body: JSON.stringify({title: name}),
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(response => response.json())
            .then(data => {
                alert("Genre " + data.title + " successfully added")
                showGenreList();
                document.querySelector('input[name="name"]').value = '';
            })
            .catch(error => {
                alert("Genre has not saved!")
                console.log(error);
            });
    });

    document.getElementById('genre-list').addEventListener('click', function (event) {
        if (event.target.classList.contains('delete-genre-button')) {
            const genreId = event.target.dataset.genreId;
            const genreTitle = event.target.dataset.genreTitle;
            if (confirm(`Are you sure you want to delete the genre '${genreTitle}'?`)) {
                const genre = {
                    id: genreId,
                    title: genreTitle
                };
                fetch('/admin/delete-genre', {
                    method: 'DELETE',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(genre)
                })
                    .then(response => {
                        if (response.status === 204) {
                            alert('Genre successfully deleted');
                            showGenreList();
                        } else {
                            throw new Error('Something went wrong!');
                        }
                    })
                    .catch(error => {
                        console.error(error);
                        alert(error.message);
                    });
            }
        }
    });

    function showGenreList() {
        fetch('/genre/all')
            .then(response => response.json())
            .then(genres => {
                const genreList = document.querySelector('#genre-list');
                genreList.innerHTML = '';
                genres.forEach(genre => {
                    const tr = document.createElement('tr');
                    const idTd = document.createElement('td');
                    idTd.textContent = genre.id;
                    tr.appendChild(idTd);
                    const titleTd = document.createElement('td');
                    titleTd.textContent = genre.title;
                    tr.appendChild(titleTd);
                    const deleteButton = document.createElement('button');
                    deleteButton.classList.add('btn', 'btn-primary', 'delete-genre-button');
                    deleteButton.setAttribute('data-genre-id', genre.id);
                    deleteButton.setAttribute('data-genre-title', genre.title);
                    deleteButton.textContent = 'Delete';
                    const deleteColumn = document.createElement('td');
                    deleteColumn.appendChild(deleteButton);
                    tr.appendChild(deleteColumn);
                    genreList.appendChild(tr);
                });
            })
            .catch(error => console.log(error));
    }
});