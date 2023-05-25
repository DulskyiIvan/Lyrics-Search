document.addEventListener('DOMContentLoaded', () => {
    let currentPage = 1;

    const getShowProposeListButton = document.getElementById('show-propose-button');
    getShowProposeListButton.addEventListener('click', function () {

        currentPage = 0;
        getAllProposes(currentPage)
            .then(({proposes, total}) => {
                renderProposes(proposes);
                renderPagination(total);
                addPaginationHandlerGetAllProposes();
            })

    });

    const getShowByDateButton = document.getElementById('show-propose-by-date-button');
    getShowByDateButton.addEventListener('click', function () {

        currentPage = 0;
        getProposesByDate(currentPage)
            .then(({proposes, total}) => {
                renderProposes(proposes);
                renderPagination(total);
                addPaginationHandlerGetByDate();
            })

    });

    function getAllProposes(currentPage) {
        const url = `/admin/propose-edit/all-proposes?page=${currentPage}`;
        return fetch(url)
            .then(response => response.json())
            .then(page => {
                const proposes = page.content;
                const total = page.totalPages;
                return {proposes, total};
            })
            .catch(error => console.error(error));
    }

    function getProposesByDate(page) {
        const url = `/admin/propose-edit/all-by-date?page=${page}`;
        return fetch(url)
            .then(response => response.json())
            .then(page => {
                const proposes = page.content;
                const total = page.totalPages;
                return {proposes, total};
            })
            .catch(error => console.log(error));
    }


    function renderProposes(proposes) {
        const proposeTable = document.querySelector("#propose-table tbody");
        proposeTable.innerHTML = "";
        if (proposes) {
            proposes.forEach((propose) => {
                const tr = document.createElement("tr");
                tr.setAttribute("id", propose.id)
                const idTd = document.createElement("td");
                idTd.textContent = propose.id;
                tr.appendChild(idTd);
                const titleTd = document.createElement("td");
                titleTd.textContent = propose.songTitle;
                tr.appendChild(titleTd);
                const artistTd = document.createElement("td");
                artistTd.textContent = propose.artistName;
                tr.appendChild(artistTd);
                const deleteButton = document.createElement("button");
                deleteButton.classList.add("btn", "btn-primary", "delete-propose-button");
                deleteButton.setAttribute("data-propose-id", propose.id);
                deleteButton.addEventListener("click", deletePropose);
                deleteButton.textContent = "Delete propose";
                const deleteColumn = document.createElement("td");
                deleteColumn.classList.add("delete-song-column");
                deleteColumn.setAttribute("id", "delete-song-column");
                deleteColumn.appendChild(deleteButton);
                tr.appendChild(deleteColumn);
                const editButton = document.createElement("button");
                editButton.classList.add("btn", "btn-primary", "edit-propose-button");
                editButton.setAttribute("data-propose-id", propose.id);
                editButton.setAttribute("data-song-id", propose.songId);
                editButton.setAttribute("data-propose-text", propose.lyricText);
                editButton.textContent = "Edit propose";
                const editColumn = document.createElement("td");
                editColumn.classList.add("edit-propose-column");
                editColumn.setAttribute("id", "edit-propose-column");
                editColumn.appendChild(editButton);
                tr.appendChild(editColumn);
                proposeTable.appendChild(tr);
            });
        }
    }

    function renderPagination(totalPages) {
        const paginationContainer = document.querySelector('#pagination-proposes-container');
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

    function addPaginationHandlerGetAllProposes() {
        const paginationLinks = document.querySelectorAll('.page-link');
        paginationLinks.forEach(link => {
            link.addEventListener('click', function (event) {
                event.preventDefault();
                currentPage = parseInt(event.target.textContent) - 1;
                getAllProposes(currentPage).then(({proposes, totalPages}) => {
                    renderProposes(proposes);
                });
            });
        });
    }

    function addPaginationHandlerGetByDate() {
        const paginationLinks = document.querySelectorAll('.page-link');
        paginationLinks.forEach(link => {
            link.addEventListener('click', function (event) {
                event.preventDefault();
                currentPage = parseInt(event.target.textContent) - 1;
                getProposesByDate(currentPage).then(({proposes, totalPages}) => {
                    renderProposes(proposes);
                });
            });
        });
    }

    function deletePropose() {
        const proposeData = this.getAttribute('data-propose-id');
        const proposeId = proposeData.valueOf();

        const propose = {
            id: proposeId
        }

        fetch(`/admin/propose-edit/delete`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(propose)
        })
            .then(function (response) {
                if (response.status === 204) {
                    alert('Propose successfully deleted!');
                    const tr = document.querySelector(`tr[id="${proposeId}"]`);
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

});
