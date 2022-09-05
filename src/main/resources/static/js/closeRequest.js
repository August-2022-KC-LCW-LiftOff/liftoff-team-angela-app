function closeRequest(closeType){
    document.querySelector("#closeOptions").classList.toggle("d-none");
    document.querySelector("#closeType").value = closeType;
    let button = document.querySelector("#submitButton");
    if(button != null){
        button.classList.toggle("d-none");
    }
}
function confirmClose(selection){
    if(selection == "cancel"){
        if(document.querySelector("#status") != null){
            document.querySelector("#status").value="ACTIVE";
        }
        document.querySelector("#closeType").value="cancel";
        document.querySelector(".confirmClose").classList.toggle("d-none");
        if(document.querySelector("#closeRequestForm")){
            document.querySelector("#closeRequestForm").submit();
        }
        return;
    }
    document.querySelector("#closeType").value="";
    document.querySelector(".confirmClose").classList.toggle("d-none");
    document.querySelector("#closeOptions").classList.toggle("d-none");
    return;
}
function statusChanged(status){
    if(status == "INACTIVE"){
        document.querySelector(".confirmClose").classList.toggle("d-none");
    }
}