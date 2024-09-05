
function autoload(){   
    let query = document.getElementById("findedQuery").innerHTML;     
    let formQuery = document.querySelector("#query");
    formQuery.value = query;    
}

window.onload = setTimeout(autoload, 2000);
