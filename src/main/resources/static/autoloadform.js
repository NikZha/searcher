function autoload(){   
    let query = document.getElementById("findedQuery").innerHTML;     
    let formQuery = document.querySelector("#query");
    if(query !=null)formQuery.value = query;    
}

window.onload = setTimeout(autoload, 2000);
