var intervalId;
function startTimerForNavbar(outDateMilliseconds, id) {
    this.id=-1;
    this.id = id;
    let outDate = new Date(outDateMilliseconds);

//   запуск таймера
    intervalId = setInterval(timerForNavbar,100,outDate,this.id);

}
function timerForNavbar(outDate,id){
    let elementTimer = document.getElementById("timer");
    this.id=-1;
    this.id=id;
    // let elementNowDate = document.getElementById("nowDate");
    // let elementOutDate = document.getElementById("date");
    // elementOutDate.innerHTML=outDate;
    let nowDate = new Date();
    // elementNowDate.innerHTML = nowDate;

    let deltaTime = outDate-nowDate +1000;
    let hours = Math.floor((deltaTime/ (1000 * 60 * 60)));
    let minutes = Math.floor((deltaTime / (1000 * 60)) % 60);
    let seconds = Math.floor((deltaTime / 1000) % 60);
    let result = "";
    if(hours<10){
        result+=0;
    }
    result+=hours +":";
    if(minutes<10){
        result+=0;
    }
    result+=minutes +":";
    if(seconds<10){
        result+=0 ;
    }
    result+=seconds;
    if(deltaTime<181000){
        elementTimer.style.color = "#ffb700";
    }
    if(deltaTime<61000){
        elementTimer.style.color = "#f00";
    }
    // elementDeltaDate.innerHTML= hours + ":" + minutes + ":" + seconds;
    elementTimer.innerHTML= result;
    console.log(this.id);
    if(deltaTime<=1000) {
        clearInterval(intervalId);
        let result="/web-application/testing/student/test?id=";
        result+=this.id;
        window.location.href = result;
    }
}