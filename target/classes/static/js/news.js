
window.onload = function () {
    let oEditors = [];
    nhn.husky.EZCreator.createInIFrame({
        oAppRef: oEditors,
        elPlaceHolder: "ir1",
        sSkinURI: "./smarteditor2/SmartEditor2Skin.html",
        fCreator: "createSEditor2"
    });
    // const { Editor } = toastui;
    // const { colorSyntax } = Editor.plugin;
    // const editor = new Editor( {
    //     el : document.querySelector( '#editor'),
    //     // height : '500px',
    //     initialEditType : 'wysiwyg',
    //     initialValue : (document.querySelector( '#existingCon').value),
    //     placeholder : '게시글 내용을 입력하세요',
    //     plugins : [ colorSyntax ],
    //     hideModeSwitch : true,
    //     theme: 'dark',
    //     toolbarItems : [
    //         [ 'heading', 'bold', 'italic', 'strike' ],
    //         [ 'quote' ],
    //         [ 'ul', 'ol' ],
    //         [ 'table', 'image', 'link' ]
    //     ]
    // } );
    // editor.removeHook( 'addImageBlobHook' );
    // console.log(editor.getHTML());

    let btn_file = document.querySelector('.file-add');
    let form = document.getElementById('f-news');
    form.addEventListener('submit', function (evt, content) {
        apply(evt, editor.getHTML());
    });
    btn_file.addEventListener( 'change', preview);

    document.getElementById('use-baseimg').addEventListener('click', function() {
        document.getElementById('formFile').value = "";
        document.getElementById('preview').src = '';
        document.getElementById('file').value = "";
    });

}

function apply(evt, content) {
    evt.preventDefault();
    let data = new FormData( document.getElementById( 'f-news' ) );
    let xhr = new XMLHttpRequest();

    xhr.onreadystatechange = function() {
        if ( xhr.readyState === XMLHttpRequest.DONE ) {
            if ( xhr.status === 200 ) {
                alert( xhr.responseText );
                location.href = "/news";
            }
            else if( xhr.status === 400 ) {
                alert( xhr.responseText );
            }
        }
    };

    xhr.open('POST', '/news/mod/p', true);

    let obj = {};
    for (let [key, value] of data.entries()) {
        if(key !== 'formFile') {
            obj[key] = value;
        }
    }
    obj['content'] = content;
    let formData = new FormData();
    let file = document.querySelector('.file-add');

    if(!!file.files[0]) {
        formData.append( 'formFile', file.files[0] );
    }
    formData.append( 'vo', new Blob( [ JSON.stringify( obj ) ], { type: 'application/json' } ) );
    xhr.send( formData );

}

//사진 미리보기
function preview(evt) {
    let maxSize = 5 * 1024 * 1024;
    if(evt.target.files[0].size > maxSize) {
        alert("파일의 크기가" + (maxSize / 1048576) + "MB를 초과합니다.");
        return;
    }
    let reader = new FileReader();
    reader.readAsDataURL(evt.target.files[0]);
    reader.onload = function(e) {
        document.getElementById('preview').src = e.target.result;
        document.getElementById('file').value = "";

    };

}


