$(document).ready(function(){
    $('#searchInput').keyup(function(e){
        if(this.value.length >2){
            getAddr();
        }
    })
})

// 도로명 주소 api 호출
function getAddr() {
    var param = {
        resultType : $("#form").find('input[name=resultType]').val(),
        confmKey : $("#form").find('input[name=confmKey]').val(),
        hstryYn : $("#form").find('input[name=hstryYn]').val(),
        firstSort : $("#form").find('input[name=firstSort]').val(),
        addInfoYn : $("#form").find('input[name=addInfoYn]').val(),
        keyword : $("#form").find('input[name=keyword]').val(),
    };
    param.keyword = $('#region_code1 option:selected').text() +' '+param.keyword;
    console.log(param)
    $.ajax({
        url: "https://www.juso.go.kr/addrlink/addrLinkApiJsonp.do",
        type: "post",
        data: serialize(param),
        dataType: "jsonp",
        crossDomain: true,
        success: function(jsonStr) {
            $("#searchList").html("").show();
            var errCode = jsonStr.results.common.errorCode;
            var errDesc = jsonStr.results.common.errorMessage;
            if(errCode != "0"){
                if(errCode == "E0001") {alert("승인되지 않은 KEY입니다.");}
                else if(errCode == "E0005") {alert("검색어를 입력해주세요");}
                else{
                    console.log(jsonStr.results.common);
                    // alert("에러가 발생했습니다. 잠시후 다시 시도해주세요.");
                }
            }else{
                if(jsonStr != null) {
                    makeListJson(jsonStr);
                }
            }
        },
        error: function(xhr,status,error) {
            alert("에러발생");
        }
    })
}


function serialize(obj) {
    var str = [];
    for (var p in obj)
        if (obj.hasOwnProperty(p)) {
            str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
        }
    return str.join("&");
}

// 건물명 검색시 해당 주소들 보여주기
function makeListJson(jsonStr){
    var htmlStr ="";
    $(jsonStr.results.juso).each(function(){
        htmlStr += "<tr>";
        htmlStr += "<td><a id='roadAddr' href='#' onclick='setAddr(this);'>" + this.roadAddr + "</a></td>";
        htmlStr += "</tr>";
    });
    $("#searchList").html(htmlStr);
}

// 특수문자, 특정문자열 제거
function checkSearchWord(obj){
    if(obj.value.length > 0) {
        // 특수문자 제거
        var expText = /[%=><]/;
        if(expText.test(obj.value) == true) {
            alert("특수문자를 입력할 수 없습니다.");
            obj.value = obj.value.spilt(expText).join("");
            return false;
        }
        //특정문자열 제거
        var sqlArray = new Array(
            "OR", "SELECT", "INSERT", "DELETE", "UPDATE", "CREATE", "DROP", "EXEC", "UNION", "FETCH", "DECLARE", "TRUNCATE"
        );
        var regex;
        var regex_plus;
        for(var i=0; i<sqlArray.length; i++){
            regex = new RegExp("\\s" + sqlArray[i] + "\\s", "gi" );
            if(regex.test(obj.value)) {
                alert("\"" + sqlArray[i] + "\"와(과) ㅣ같은 특정 문자열로 검색할 수 없습니다.");
                obj.value = obj.value.replace(regex, "");
                return false;
            }
            regex_plus = new RegExp("\\s" + sqlArray[i] + "\\s", "gi" );
            if (regex_plus.test(obj.value)) {
                alert("\"" + sqlArray[i] + "\"와(과) ㅣ같은 특정 문자열로 검색할 수 없습니다.");
                obj.value = obj.value.replace(regex_plus, "");
                return false;
            }
        }
    }
    return true;
}

// 검색어 체크
function searchJuso() {
    if(!checkSearchWord(document.form.keyword)) {
        return ;
    }
}
// 불러온 도로주소 값 세팅
function setAddr(obj) {
    var road = obj.innerText;
    if($("#searchInput").length > 0) {
        $('#searchInput').val(road);
        $('#searchList').empty().hide()
    }
}
// 포커싱 효과 주기, 셀렉트 박스 선택값 보내기
function getSearch() {
    // 포커싱 효과 주기
    document.getElementById('searchInput').focus(function () {
    document.getElementById('searchInput').css({border: 'green'});
    })
    //셀렉트 박스 선택값 보내기
    var selectedRegionCode = $("#region_code1 option:selected").val();


}
// 검색 주소 좌표 변환 및 이동
function jusoCallBack() {
    var roadAddr = $('#searchInput').val();
    console.log(roadAddr);
    naver.maps.Service.geocode({address: roadAddr}, function(status, response){
        if(status !== naver.maps.Service.Status.OK) {
            return alert('주소를 확인 후 다시 시도 바랍니다.');
        }
            var items = response.result.items[1];
            console.log(items);
            var point = new naver.maps.LatLng(items.point.y, items.point.x);
            console.log(point);
            navMap.map.setCenter(point);
    });
}

function regionData() {
    $.ajax({
        url: 'http://api.vworld.kr/req/data?service=data&request=GetFeature&data=LT_C_ADSIGG_INFO&key=3D05FBFD4F7D3E06A42ADBED77EF1E77&domain=http://localhost:8888/board/mapApi',
        type: 'get',
        data : {},
        dataType: 'json',
        success(result) {
            console.log(result);
        }
    })
}