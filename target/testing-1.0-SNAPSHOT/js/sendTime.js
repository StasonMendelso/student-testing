function sendClientTime(id) {
    var currentdate = new Date();
    // var datetime = currentdate.getDate() + "/"
    //     + (currentdate.getMonth() + 1) + "/"
    //     + currentdate.getFullYear() + "@"
    //     + currentdate.getHours() + ":"
    //     + currentdate.getMinutes() + ":"
    //     + currentdate.getSeconds();


    let form = document.createElement('form');
    form.action = '/web-application/testing/student/test';
    form.method = 'GET';

    let input1 = document.createElement('input');
    input1.name = 'currentClientTime';
    input1.value = currentdate.valueOf();
    input1.hidden=true;
    let input2 = document.createElement('input');
    input2.name = 'id';
    input2.value = id;
    input2.hidden=true;
    form.appendChild(input1);
    form.appendChild(input2);
// перед отправкой формы, её нужно вставить в документ
    document.body.append(form);

    form.submit();
    return currentdate.valueOf();
}