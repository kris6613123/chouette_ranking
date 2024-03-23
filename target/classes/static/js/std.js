// const maxSize = 5 * 1024 * 1024;

function addWinningAgingRow(std, rowCount) {
    let newRow = document.createElement('tr');

    let cell1 = document.createElement('td');
    let input1 = document.createElement('input');
    input1.setAttribute('class', 'io');
    input1.setAttribute('required', '');
    if (std === 'buyin' || std === 'aging') {
        input1.setAttribute('name', 'grade');
        cell1.appendChild(input1);
        if(std === 'buyin') {
            cell1.appendChild(document.createTextNode('만'))
        }
        else {
            cell1.appendChild(document.createTextNode('일'))
        }
    }
    else if(std === 'reward') {
        input1.setAttribute('name', 'rewardWeight');
        cell1.appendChild(input1);
    }

    let cell3 = document.createElement('td');
    let input3 = document.createElement('input');
    input3.setAttribute('class', 'io');
    input3.setAttribute('name', 'weight');
    input3.setAttribute('required', '');
    cell3.appendChild(input3);

    let cell4 = document.createElement('td');
    let btn = document.createElement('button');
    btn.setAttribute('type', 'button');
    btn.setAttribute('class', 'row-del');
    btn.setAttribute('value', 'row-del-' + rowCount);
    btn.textContent = 'X';
    btn.addEventListener('click', delRow);
    cell4.appendChild(btn);

    newRow.appendChild(cell1);
    newRow.appendChild(cell3);
    newRow.appendChild(cell4);

    document.querySelector('.tbl > tbody').appendChild(newRow);
}

function addTierRow(rowCount) {
    let newRow = document.createElement('tr');

    let cell1 = document.createElement('td');
    let input1 = document.createElement('input');
    input1.setAttribute('class', 'io');
    input1.setAttribute('name', 'tier');
    input1.setAttribute('required', '');
    cell1.appendChild(input1);

    let cell2 = document.createElement('td');

    let input2 = document.createElement('input');
    input2.setAttribute('class', 'io');
    input2.setAttribute('name', 'weight');
    input2.setAttribute('required', '');
    cell2.appendChild(input2);

    let input3 = document.createElement('input');
    input3.setAttribute('type', 'hidden');
    input3.setAttribute('name', 'type');
    input3.setAttribute('value', "R");
    cell2.appendChild(input3);

    let cell3 = document.createElement('td');
    cell3.setAttribute('class', 'td-img');

    let label = document.createElement('label');
    label.setAttribute('for', 'R' + rowCount);
    label.setAttribute('style', 'display: flex; align-items: center; justify-content: center; width: 100%; height: 100%; border: 1px solid #ccc; box-sizing: border-box;');

    let input4 = document.createElement('input');
    input4.setAttribute('type', 'hidden');
    input4.setAttribute('name', 'file');
    input4.setAttribute('value', "");
    input4.setAttribute('id', 'preFile-R' + rowCount);
    label.appendChild(input4);

    let input5 = document.createElement('input');
    input5.setAttribute('type', 'file');
    input5.setAttribute('accept', 'image/*');
    input5.setAttribute('class', 'io file-add');
    input5.setAttribute('name', 'formFile')
    input5.setAttribute('id', 'R' + rowCount );
    input5.setAttribute('style', 'display: none');
    input5.addEventListener('change', previewImg );
    label.appendChild(input5);

    let img = document.createElement('img');
    img.setAttribute('id', 'preview-R' + rowCount);
    img.setAttribute('style', 'max-width:100%; max-height: 100%; object-fit: contain');
    img.setAttribute('src', '');
    img.setAttribute('alt', ' ');
    label.appendChild(img);

    let btn = document.createElement('button');
    btn.setAttribute('type', 'button');
    btn.setAttribute('class', 'file-del');
    btn.setAttribute('id', 'use-baseimg-R' + rowCount);
    btn.setAttribute('value', 'R' + rowCount);
    btn.textContent = '휘장 미사용';
    btn.addEventListener('click', function (evt) {
        baseImg( evt.target.previousElementSibling.querySelector('.file-add'), "" );
    });

    cell3.appendChild(label);
    cell3.appendChild(btn);

    let cell4 = document.createElement('td');
    let btn2 = document.createElement('button');
    btn2.setAttribute('type', 'button');
    btn2.setAttribute('class', 'btn-del');
    btn2.setAttribute('value', 'row-del-' + rowCount);
    btn2.textContent = 'X';
    btn2.addEventListener('click', delRow);
    cell4.appendChild(btn2);

    newRow.appendChild(cell1);
    newRow.appendChild(cell2);
    newRow.appendChild(cell3);
    newRow.appendChild(cell4);

    document.querySelector('.tbl > tbody').appendChild(newRow);
}

function delRow(evt) {
    let row = this.closest('tr');
    row.remove();
}