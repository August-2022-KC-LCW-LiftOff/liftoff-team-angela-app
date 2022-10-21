function getGeocodeFromAddress(){
    let geocoder = new google.maps.Geocoder();

    return new Promise(function(resolve,reject) {
        let address = "";
        address = document.querySelector("#addressLine1, #userDetails\\.addressLine1").value;
        if (document.querySelector("#addressLine2, #userDetails\\.addressLine2").value != ""){
            address += ", " + document.querySelector("#addressLine2, #userDetails\\.addressLine2").value;
        }
        address += ", " + document.querySelector("#city, #userDetails\\.city").value;
        address += ", " + document.querySelector("#state, #userDetails\\.state").value;
        address += "  " + document.querySelector("#zipcode, #userDetails\\.zipcode").value;

       if(address == ""){
            reject(new Error('Address field is empty.'));
       } else {
            try{
                geocoder.geocode({'address':address},function(results, status){
                    if(status == 'OK'){
                        resolve(results[0].geometry.location.toString().replace("(","").replace(")",""))
                    } else {
                        reject(new Error('Location invalid.'));
                        console.log('Geocode not successful: ' + status);
                    }
                });
            } catch (error){
                console.log(error.getMessage());
            }
       }
    });
}

function submitForm(event){
    event.preventDefault();
    let locationPromise = getGeocodeFromAddress();

    locationPromise.then(function(returnValue){
        if(returnValue.includes("Error")){
            console.log(returnValue);
        } else {
            if(document.querySelector("#location") !== null){
                document.querySelector("#location").value = returnValue;
            } else {
                document.querySelector("#userDetails.location").value = returnValue;
            }

            event.target.submit();
        }
    });
}


