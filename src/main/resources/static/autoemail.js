function autoemail(td) {
    let txt = td.innerHTML;
    let array = new Array();
    let email = "";
    if (!txt.includes("mailto:")) {
        for (let i = 0; i < txt.length; i++) {
            if (txt[i] != '[' && txt[i] != ']' && txt[i] != ',' && txt[i] != ' ') {
                email += txt[i];
            } else {
                if (email != "") array.push(email);
                email = "";
            }
        }
        email = "";
        for (let i = 0; i < array.length; i++) {
            array[i] = "<p><a href=\"mailto:" + array[i] + "?subject=Заявка" + "\">" + array[i] + "<\/a><\/p>";
            email += (array[i] + ' ');
        }

        td.innerHTML = email;
    }
}