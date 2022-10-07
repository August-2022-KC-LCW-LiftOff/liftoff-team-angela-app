function closeAll(event){
    event.preventDefault();
    let detailElements = document.querySelectorAll("details");
    for(let a=0;a<detailElements.length;a++){
        detailElements[a].removeAttribute("open");
    }
    event.currentTarget.setAttribute("open","");
}

function showPanel(panelIds){
    let panels = document.querySelectorAll(".infoPanel");
    panelIds = panelIds.split(",");
    for(let a=0;a<panels.length;a++){
        panels[a].classList.add("hidden");
    }
    for(let a=0;a<panelIds.length;a++){
        document.querySelector("#"+panelIds[a]).classList.toggle("hidden");
    }
}