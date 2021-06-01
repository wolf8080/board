const navMap = new Vue({
    el: "#localConstruction",
    data: {
        isShowInfo: true,
        map: {},
        markerClustering: {},
        locations: [],
        type: 1, // 1- 구단위 default 2- 동단위  3 - 기본마커
        markers: [],

    },
    created: function() {
        this.initWatcher();
    },
    mounted: function() {
        var $that = this;
        this.initMap().then(()=>{
            naver.maps.Event.addListener($that.map, 'zoom_changed', function(zoom) {
                //console.log('zoom 값이' + zoom + '으로 변경되었습니다.' );
                if(zoom <= 13) {
                    $that.type = 1;
                }else if(zoom >=14 && zoom <= 15) {
                    $that.type = 2;
                }else if(zoom >= 16) {
                    $that.type = 3;
                }
                console.log($that.type);
                $that.getData();
                return true;
            });
            $that.getData();
        });
    },
    updated: function() {

    },
    methods: {
        // 네이버 맵 생성
        initMap() {
            var $that = this;
            let myPromise = new Promise((resolve, reject) => {
                $that.map = new naver.maps.Map('map', {
                    center: new naver.maps.LatLng(37.5172, 127.0473),
                    zoom: 6,
                    maxZoom: 16, //
                    minZoom: 12, //  Zoom-Level 최대값 제한 설정
                    zoomControl: true,
                    zoomControlOptions: {
                        style: naver.maps.ZoomControlStyle.SMALL,
                        position: naver.maps.Position.TOP_LEFT
                    },
                    scaleControl: true,
                    scaleControlOptions: {
                        position: naver.maps.Position.BOTTOM_LEFT
                    }
                });
                resolve();
            });
            return myPromise;
        },

        // 데이터가 변할때 감지하는 역할
        initWatcher() {
            var $this = this;
            this.$watch('locations', (newVal, oldVal) => {
                $this.initMarker();
            });
        },

        // 서버로부터 데이터가져오기
        getData() {
            var $that = this;
            var typeValue = $that.type
            console.log(typeValue);
            axios.get('/api/board/mapApi'
                , {params: {type: typeValue}}).then((result) => {
                this.locations = result.data.list
            })
        },

        // 다수의 기본마커 생성
        initMarker() {
            var $that = this
            $that.clearMarkers();
            this.locations.forEach(location => {
                console.log(location);
                $that.addMarker(location)
            });
        },

        // 기본마커 생성
        addMarker(location) {
            var $that = this;
            var orginalIcon = {
                content: $that.getContent(location),
                size: new naver.maps.Size(38, 58),
                anchor: new naver.maps.Point(19, 58),
            };
            var marker = new naver.maps.Marker({
                position: new naver.maps.LatLng(location.lng, location.lat),
                map: $that.map,
                icon: orginalIcon,
                draggable: false,
                zIndex: 15,
            });

            // 인포 윈도우 창
            const popContentHtml = '<ul class="mod_prd_list06" style="padding: 20px">' +
                '<li style="border: none">' +
                '<div class="prd_info_wrap" style="width: 100%;">' +
                '<div class="prd_img">' +
                '<img src=""  style="width: 30px;height: 30px; left;">' +
                '<span><h7>몽땅뚝딱시공파트너스</h7></span></div>' +
                '<div class="prd_info_box" style="width: 100%;">' +
                '<p class="prd_brand">'+location.companyNm+'</p>' +
                '<div class="price_box">' +
                '<p><span class="price_current">시공파트너스</span></p>' +
                '</div>' +
                '</div>' +
                '</div>' +
                '<div class="prd_info_wrap" style="width: 100%">' +
                '<img src="" style="height: 130px; width: 100%">' +
                '</div>' +
                '<div class="prd_info_wrap" style="width: 100%">' +
                '<p class="prd_brand">'+location.address+'</p>' +
                '<p class="prd_brand">'+location.interiorDong+'</p>' +
                '</div>' +
                '</li>';

            let infoWindow = new naver.maps.InfoWindow({
                content: popContentHtml,
                maxWidth: 250,
                backgroundColor: "#eee",
                anchorSize: new naver.maps.Size(0, 0),
                anchorSkew: true,
                pixelOffset: new naver.maps.Point(180, 250),
                zIndex:5
            });

            var markerZIndex = marker.getZIndex();
            var infoWindowZIndex = infoWindow.getZIndex();

            // 마커 이벤트 설정
            if($that.type === 3) {

                //naver.maps.Event.addListener(marker, 'mouseover', () => this.markerOverEvent(this.map,marker,infoWindow));
                //naver.maps.Event.addListener(marker, 'mouseout', () => this.markerOutEvent(infoWindow));
                naver.maps.Event.addListener(marker, 'click', () => this.markerClickEvent(this.map, infoWindow, marker, location));
                infoWindow.open(map, marker);
                marker.setZIndex(markerZIndex);
                infoWindow.setZIndex(infoWindowZIndex);

            }
            marker.setIcon(orginalIcon);
            naver.maps.Event.addListener(marker, 'click', () => this.markerClickMoveEvent(marker, location));
            this.markers.push(marker);
        },

        // 시공장소 주소를 좌표로 전환
        setLatLng(location) {
            naver.maps.Service.geocode({query: roadAddr}, function(status, response) {
                if(status !== naver.maps.Service.Status.OK) {
                    return alert("주소를 확인 후 다시 시도해주시기 바랍니다.");
                }
                var items = response.result.items[0];
                return items;

            });
        },

        // type값에 따른 마커모양
        getContent(location){
            let html = ''
            //var items = this.setLatLng(location);
            switch(this.type){
                case 1:
                    html = '<div style="width: 80px;height: 40px;border: 1px solid black; padding: 10px; margin: 10px; background-color:white;opacity:70%;box-shadow: 0px 0px 5px #000;text-align: center;"><strong>'+ location.interiorGu +'</br>'+ location.interiorCount+'건'+'</strong></div>'
                    break;
                case 2:
                    html = html = '<div style="width: 80px;height: 40px;border: 1px solid black; padding: 10px; margin: 10px; background-color:white;opacity:70%;box-shadow: 0px 0px 5px #000;text-align: center;"><strong>'+ location.interiorDong +'</br>'+ location.interiorCount+'건'+'</strong></div>'
                    break;
                case 3:
                    html = '<div style="width:40px;height:40px;border:0;border-radius:50px;filter:alpha(opacity=30);opacity:70%;background:#000;' +
                        'line-height:42px;font-size:13px;color:white;text-align:center;box-shadow: 0px 0px 5px #000;">'+ location.interiorCount +'건'+'</div>'
                    break;
            }
            return html;
        },

        // type값에 따른 마커 위치
        getPosition(location) {
            let position ='';
            let items = this.setLatLng(location);
            let sigugun = items.addrdetail.sigugun;
            let dongmyun = items.addrdetail.dongmyun;
            switch(this.type) {
                case 1:
                    let sigugunItems = this.setLatLng(sigugun);
                    position = new naver.maps.LatLng(sigugunItems.point.y, sigugunItems.point.x);
                    break;
                case 2:
                    let dongmyunItems = this.setLatLng(dongmyun);
                    position = new naver.maps.LatLng(dongmyunItems.point.y, dongmyunItems.point.x);
                    break;
                case 3:
                    position = new naver.maps.LatLng(location.lng, location.lat);
                    break;
            }
            return position;
        },

        // 기본마커 삭제
        clearMarkers() {
            for (var marker in this.markers) {
                this.markers[marker].setMap(null);
            }
            this.markers = [];
        },

        // 마커 클릭 이벤트
        markerClickEvent(map, infoWindow, marker, location ) {
            var $that = this;

            // 클릭시 정보창 나타내기
            if(infoWindow.getMap()) {
                var markerIcon = {
                    content: $that.getContent(location),
                    size: new naver.maps.Size(38, 58),
                    anchor: new naver.maps.Point(19, 58),
                };
                var markerZIndex = 15;
                var infoWindowZIndex = 5;
                infoWindow.close();
            }else {
                var markerIcon = {
                    content: '<div style="width:40px;height:40px;border:0;border-radius:50px;filter:alpha(opacity=30);opacity:100%;background:#33ff33;' +
                        'line-height:42px;font-size:13px;color:white;text-align:center;box-shadow: 0px 0px 5px #000;">'+ location.interiorCount +'건' + '</div>',
                    size: new naver.maps.Size(38, 58),
                    anchor: new naver.maps.Point(19, 58),
                };
                var markerZIndex = 999;
                var infoWindowZIndex = 990;
                infoWindow.open(map, marker);
            }
            // 클릭시 아이콘 변경
          marker.setIcon(markerIcon);

            // 클릭시 마커, 정보창 zindex 변경
          marker.setZIndex(markerZIndex);
          infoWindow.setZIndex(infoWindowZIndex);
        },

        // 클릭 시 마커위치로 이동
        markerClickMoveEvent(marker, location) {
            this.map.setCenter(new naver.maps.LatLng(location.lng, location.lat));
        },

        // 마커 정보창 마우스 오버 이벤트
        markerOverEvent(map, marker, infoWindow) {
            infoWindow.open(map, marker);
        },
        // 마커 정보창 마우스 아웃 이벤트
        markerOutEvent(infoWindow) {
            infoWindow.close();
        },
    }
});






