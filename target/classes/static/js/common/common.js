const maxSize = 5 * 1024 * 1024;

// 페이지, 검색 관련 js //
function paging( evt ){
    let pagenum = evt.target.value;
    let searchParams = new URLSearchParams( location.search );
    searchParams.set('pagenum', pagenum);
    location.href = location.pathname + "?" + searchParams;
}

function searching( element ){
    let searchParams = new URLSearchParams( location.search );
    if( searchParams.get('pagenum') ) {
        searchParams.set('pagenum', 1);
    }
    searchParams.set( element.id, element.value );
    location.href = location.pathname + "?" + searchParams;
}

function searchingByCustomUrl( url, element ){
    let searchParams = new URLSearchParams( "" );
    searchParams.set( element.id, element.value );
    location.href = url + "?" + searchParams;
}
// 페이지, 검색 관련 js //



// 투명도룰 포함한 color 처리 관련 js //
function setColoring( element, color, opacity ){
    let r = parseInt(color.slice(1, 3), 16),
        g = parseInt(color.slice(3, 5), 16),
        b = parseInt(color.slice(5, 7), 16);
    element.style.backgroundColor = "rgba(" + r + ", " + g + ", " + b + ", " + opacity + ")";
}
// 투명도룰 포함한 color 처리 관련 js //


// 이미지 파일 관련 처리 js //
function previewImg( evt ) {
    if( evt.target.files[0].size > maxSize ) {
        alert("파일의 크기가" + (maxSize / 1048576) + "MB를 초과합니다.");
        return;
    }
    let reader = new FileReader();
    reader.readAsDataURL(evt.target.files[0]);
    reader.onload = function(e) {
        evt.target.nextElementSibling.src = e.target.result;
        if( evt.target.previousElementSibling.id.startsWith('preFile') ) {           //기존 파일 값 없애기
            evt.target.previousElementSibling.value = "";
        }
    };
}
function baseImg( element, src ) {
    element.nextElementSibling.src = src;
    if(element.previousElementSibling.id.startsWith('preFile')) {
        element.previousElementSibling.value = "";
    }
}
// 이미지 파일 관련 처리 js //

// 일반적인 json 데이터 ajax 호출 js //
function defaultApply( evt, form, reUrl ) {
    evt.preventDefault();
    let formData = new FormData( form );
    let xhr = new XMLHttpRequest();

    xhr.onreadystatechange = function() {
        if ( xhr.readyState === XMLHttpRequest.DONE ) {
            if ( xhr.status === 200 ) {
                alert( xhr.responseText );
                if( reUrl !== "" ) {
                    location.href = reUrl;
                }
                else {
                    location.reload();
                }
            }
            else if( xhr.status === 400 ) {
                alert( xhr.responseText );
            }
        }
    };

    let obj= {};
    for (let [ key, value ] of formData.entries()) {
        obj[key] = value;
    }
    xhr.open('POST', evt.target.action, true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.send( JSON.stringify(obj) );
}
// 일반적인 json 데이터 ajax 호출 js //

// 관리자 페이지에서 아이쳄 삭제 js //
function delItem( evt, type ) {
    if( confirm("정말 삭제하시겠습니까?") ){
        let xhr = new XMLHttpRequest();

        xhr.onreadystatechange = function() {
            if ( xhr.readyState === XMLHttpRequest.DONE ) {
                if ( xhr.status === 200 ) {
                    alert( xhr.responseText );
                    location.reload();
                }
                else if( xhr.status === 400 ) {
                    alert( xhr.responseText );
                }
            }
        };
        xhr.open('POST', "/" + type + "/" + evt.target.value + "/del", true);
        xhr.send();
    }
}

// 관리자 페이지에서 아이쳄 삭제 js //


//숫자 0 세개마다 , 붙이기 //
function setNumWithComma() {
    let bigNum = document.querySelectorAll('.big-number');
    bigNum.forEach(function (num) {
        let parts = num.textContent.split('.');
        let integerPart = parts[0].toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
        if (parts.length > 1) {
            num.textContent = integerPart + '.' + parts[1];
        } else {
            num.textContent = integerPart;
        }
    });
}
//숫자 0 세개마다 , 붙이기 //

// tel format //
function telFormat(str) {
    str = str.replace(/[^0-9]/g, '');
    let tmp = '';
    if( str.length < 4){
        return str;
    }else if(str.length < 7){
        tmp += str.substr(0, 3);
        tmp += '-';
        tmp += str.substr(3);
        return tmp;
    }else if(str.length < 11){
        tmp += str.substr(0, 3);
        tmp += '-';
        tmp += str.substr(3, 3);
        tmp += '-';
        tmp += str.substr(6);
        return tmp;
    }else{
        tmp += str.substr(0, 3);
        tmp += '-';
        tmp += str.substr(3, 4);
        tmp += '-';
        tmp += str.substr(7);
        return tmp;
    }
}
// tel format //


