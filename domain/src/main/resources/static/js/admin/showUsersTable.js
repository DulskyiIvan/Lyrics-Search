$(document).ready(function () {

    let currentPage = 1;

    const getAllUsersButton = document.getElementById('all-users-button');
    getAllUsersButton.addEventListener('click', function () {
        const paginationContainer = document.querySelector('#pagination-users-container');
        paginationContainer.innerHTML = '';
        const userTable = document.querySelector("#user-table tbody");
        userTable.innerHTML = "";
        currentPage = 0;
        getAllUsers(currentPage).then(({users, totalPages}) => {
            if (users.length > 0) {
                showUserTable(users);
                renderPagination(totalPages, currentPage);
                addPaginationHandlerGetAll();
            }
        });
    });

    const searchUserForm = document.getElementById('search-users-form');
    searchUserForm.addEventListener('submit', function (event) {
        event.preventDefault();
        const userTable = document.querySelector("#user-table tbody");
        userTable.innerHTML = "";
        const paginationContainer = document.querySelector('#pagination-users-container');
        paginationContainer.innerHTML = '';
        const usernameInput = document.querySelector('input[name="username"]');
        const username = usernameInput.value;

        currentPage = 0;
        getUsersByUsername(currentPage, username)
            .then(({users, totalPages}) => {
                    if (users.length > 0) {
                        showUserTable(users);
                        renderPagination(totalPages, currentPage);
                        addPaginationHandlerSearchByUsername(username);
                    }
                }
            )
    });

    function getUsersByUsername(page, username) {
        const url = `/admin/users?username=${username}&page=${page}`;
        return fetch(url)
            .then(response => response.json())
            .then(page => {
                const users = page.content;
                const totalPages = page.totalPages;
                return {users, totalPages};
            })
            .catch(error => console.log(error));
    }

    function getAllUsers(page) {
        const url = `/admin/all-users?page=${page}`;
        return fetch(url)
            .then(response => response.json())
            .then(page => {
                const users = page.content;
                const totalPages = page.totalPages;
                return {users, totalPages};
            })
            .catch(error => console.log(error));
    }

    function renderPagination(totalPages) {
        const paginationContainer = document.querySelector('#pagination-users-container');
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
                getAllUsers(currentPage).then(({users, totalPages}) => {
                    showUserTable(users);
                });
            });
        });
    }

    function addPaginationHandlerSearchByUsername(name) {
        const paginationLinks = document.querySelectorAll('.page-link');
        paginationLinks.forEach(link => {
            link.addEventListener('click', function (event) {
                event.preventDefault();
                currentPage = parseInt(event.target.textContent) - 1;
                getUsersByUsername(currentPage, name).then(({users, totalPages}) => {
                    showUserTable(users);
                });
            });
        });
    }

    function showUserTable(users) {
        const userTable = document.querySelector("#user-table tbody");
        userTable.innerHTML = "";
        if (users) {
            users.forEach((user) => {
                const tr = document.createElement("tr");
                const idTd = document.createElement("td");
                idTd.textContent = user.id;
                tr.appendChild(idTd);
                const firstNameTd = document.createElement("td");
                firstNameTd.textContent = user.firstName;
                tr.appendChild(firstNameTd);
                const lastNameTd = document.createElement("td");
                lastNameTd.textContent = user.lastName;
                tr.appendChild(lastNameTd);
                const usernameTd = document.createElement("td");
                usernameTd.textContent = user.username;
                tr.appendChild(usernameTd);
                const roleTd = document.createElement("td");
                roleTd.textContent = user.role;
                tr.appendChild(roleTd);
                const editButton = document.createElement("button");
                editButton.classList.add("btn", "btn-primary", "edit-user-button");
                editButton.setAttribute("data-user-id", user.id);
                editButton.textContent = "Edit";
                const editColumn = document.createElement("td");
                editColumn.classList.add("edit-user-column");
                editColumn.appendChild(editButton);
                tr.appendChild(editColumn);
                userTable.appendChild(tr);
            });
        }
    }
});