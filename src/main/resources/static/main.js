document.addEventListener("DOMContentLoaded", function () {
    // Fetch JSON data from the server
    fetch('http://localhost:8080/api/cities')
        .then(response => response.json())
        .then(sort => createTable(sort))
        .catch(error => console.error('Error fetching data:', error));
});

function createTable(cityList) {
    console.log("Creating table start")
    const table = document.getElementById("dataTable");

    // Remove existing table rows
    while (table.firstChild) {
        table.firstChild.remove();
    }

    // Create table headers with Russian names
    const headersRussian = {
        id: "id",
        name: "Город",
        region: "Регион",
        district: "Округ",
        population: "Численность",
        foundation: "Основан в"
    };

    // Create header row without the first cell
    const headerRow = table.insertRow();
    Object.keys(headersRussian).forEach((headerText, index) => {
        const th = document.createElement("th");
        th.textContent = headersRussian[headerText];
        // Hide the first cell in the header row
        if (index !== 0) {
            headerRow.appendChild(th);
        }
    });

    // Create table rows and cells with Russian names
    cityList.forEach(rowData => {
        const row = table.insertRow();
        Object.keys(headersRussian).forEach((header, index) => {
            // Hide the first cell in each row
            if (index !== 0) {
                const cell = row.insertCell();
                cell.textContent = rowData[header];
            }
        });
    });
}

function makeTable() {
    let array = [];
    let basicUrl = 'http://localhost:8080/api/cities';

    // Add query selector for checkbox values
    let checkboxes = document.querySelectorAll('input[type=checkbox]:checked');

    for (let i = 0; i < checkboxes.length; i++) {
        array.push(checkboxes[i].value)
    }
    // No value is checked
    if (array.length == 0) {
        fetchUrl(basicUrl)
    } else {
        let parametrisedUrl = basicUrl + '?district=' + array.join(',');
        fetchUrl(parametrisedUrl);

    }
}
function fetchUrl(url){
    fetch(url)
        .then(response => response.json())
        .then(sort => createTable(sort))
        .catch(error => console.error('Error fetching data:', error));
}
