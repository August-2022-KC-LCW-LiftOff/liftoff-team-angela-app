let geocoder;

function getGeocodeFromAddress(){
    geocoder = new google.maps.Geocoder();
    let address = document.querySelector("#addressLine1").value;
    if(address != ""){
        if (document.querySelector("#addressLine2").value != ""){
                address += ", " + document.querySelector("#addressLine2").value;
            }
            address += ", " + document.querySelector("#city").value;
            address += ", " + document.querySelector("#state").value;
            address += "  " + document.querySelector("#zipcode").value;
                geocoder.geocode({'address':address},function(results, status){
                    if(status == 'OK'){
                        document.querySelector("#location").value = results[0].geometry.location.toString().replace("(","").replace(")","");
                        toggleSubmitAddressButton(false);
                    } else {
                        console.log('Geocode not successful: ' + status);
                    }
                });
    }

    document.querySelector("#submitAddressButton").addEventListener('click',getGeocodeFromAddress);

}
function toggleSubmitAddressButton(show){
    let btn = document.querySelector("#submitAddressButton");
    if(show){
        btn.classList.remove("hidden");
        return;
    }
    btn.classList.add("hidden");
}


