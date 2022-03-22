document.getElementById("pagination1").onchange= function (pageNumber2) {
    let form = document.getElementById("pagination_form1");
    let input = document.createElement("input");
    input.setAttribute("type","hidden");
    input.setAttribute("name","pageNumber2");
    input.setAttribute("value",pageNumber2);
    form.appendChild(input);
    form.submit();
}