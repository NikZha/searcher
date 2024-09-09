function autourl(td) {
    let txt = td.innerHTML;
    let tmp;
    if (!(txt.includes("_blank"))) {
        txt = txt.replace(/&amp;quot/g, "");
        tmp = "<a href=\"" + txt + "\" target=\"_blank\">" + txt + "</a>";
        td.innerHTML = tmp;
    }
}
