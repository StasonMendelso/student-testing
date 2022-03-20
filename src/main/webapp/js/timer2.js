var intervalId;
function startTimer(outDateMilliseconds) {

   let outDate = new Date(outDateMilliseconds);

//   запуск таймера
   intervalId = setInterval(timer,100,outDate)

}
function timer(outDate){
   let elementTimer = document.getElementById("timer");

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
   if(deltaTime<=1000) {
      clearInterval(intervalId);
      let form = document.getElementById("UserAnswers");
      form.action="";
      let finishInput = document.createElement("input");
      finishInput.setAttribute("name","finish");
      finishInput.setAttribute("value","finish");
      form.appendChild(finishInput);
      form.submit();
   }
}