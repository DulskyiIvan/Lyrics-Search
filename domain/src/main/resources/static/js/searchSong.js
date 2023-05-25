document.addEventListener('DOMContentLoaded', () => {
    const queryInput = document.getElementById('query');
    const searchResults = document.getElementById('searchResults');

    queryInput.addEventListener('input', () => {
        const query = queryInput.value;

        if (query.length >= 3) {
            fetch(`/search-result?query=${query}`)
                .then(response => response.json())
                .then(data => {
                    searchResults.innerHTML = '';

                    if (data.length > 0) {
                        const resultList = document.createElement('ul');
                        resultList.className = 'search-results-list';

                        data.forEach(song => {
                            const listItem = document.createElement('li');
                            const link = document.createElement("a");
                            link.href = `/song?id=${song.id}`;
                            link.textContent = `${song.title} - ${song.artist}`;
                            listItem.appendChild(link);
                            resultList.appendChild(listItem);
                        });

                        searchResults.appendChild(resultList);
                        searchResults.style.display = 'block';
                        searchResults.style.width = `${queryInput.offsetWidth}px`;
                    } else {
                        searchResults.textContent = 'No results found';
                        searchResults.style.display = 'block';
                        searchResults.style.width = `${queryInput.offsetWidth}px`;
                    }
                })
                .catch(error => {
                    searchResults.textContent = 'Error occurred';
                    searchResults.style.display = 'block';
                    searchResults.style.width = `${queryInput.offsetWidth}px`;
                });
        } else {
            searchResults.innerHTML = '';
        }
    });
});