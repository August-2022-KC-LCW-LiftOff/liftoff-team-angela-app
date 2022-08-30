let map;
let geocoder;
const locations = requestLocations;
let contents = [];
function centerOnUserLocation(){
    map.setCenter(userLocation);
    map.setZoom(6);
}
function initMap() {
    geocoder = new google.maps.Geocoder();
    getContent();

    let mapOptions = {
        center: new google.maps.LatLng(38.943868, -95.294001),
        zoom: 4,
        styles: styles["hide"],
    };
    map = new google.maps.Map(document.getElementById("map"), mapOptions);
    centerOnUserLocation();
    const icon = {
        url: '../images/arkheartsmall.png',
        size: new google.maps.Size(32,30),
        anchor: new google.maps.Point(16,16),
    };

  const infoWindow = new google.maps.InfoWindow({
    content: "",
    disableAutoPan: false,
  });
  // Add some markers to the map.
  const markers = locations.map((position, i) => {
//    const label = labels[i % labels.length];
    const content = contents[i % contents.length];
    const marker = new google.maps.Marker({
      position,
      icon: icon,
    });


    // markers can only be keyboard focusable when they have click listeners
    // open info window when marker is clicked
    marker.addListener("click", () => {

      infoWindow.setContent(content);
      infoWindow.open(map, marker);
    }, {passive: true});
    return marker;
  });

//   Add a marker clusterer to manage the markers.
  new markerClusterer.MarkerClusterer({ markers, map });
  document.querySelector("#centerButton").addEventListener('click',centerOnUserLocation);
}

const styles = {
      default: [],
      hide: [
          {
              featureType: "poi.business",
              stylers: [{ visibility: "off" }],
          },
          {
              featureType: "transit",
              elementType: "labels.icon",
              stylers: [{ visibility: "off" }],
          },
      ],
};
function getContent(){
    let contentElements = document.querySelectorAll(".content");
    let contentString = "";
    for (let element in contentElements){
        let contentItemElements = contentElements[element].children;
        for (let itemElement in contentItemElements){
            if(contentItemElements[itemElement].innerHTML != undefined){
                if(contentItemElements[itemElement].classList != "exclude"){
                    contentString += "<p>" + (contentItemElements[itemElement].getAttribute('label')!=null?contentItemElements[itemElement].getAttribute('label'):'') + contentItemElements[itemElement].innerHTML + "</p>";
                    if(contentItemElements[itemElement].classList == "title"){
                        contentString += "<hr/>";

                    }

                }
            }

        }
        contents.push(contentString);
        contentString = "";
    }
}
window.initMap = initMap;




