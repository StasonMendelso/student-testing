class Timer {
    constructor({ element, seconds , input}) {
        this.input = input;
        this.element = element;
        this.seconds = seconds;
        this.targetTime = null;
        this.timer = undefined;
        this.flag=true;
        this.init();
    }
    init(){
        const targetTime = new Date();
        targetTime.setSeconds(targetTime.getSeconds() + this.seconds + 1);
        this.targetTime = targetTime;

        this.timer = setInterval(this.handleTimer, 10);
    };

    handleTimer = () => {
        const date = new Date();
        const diff = this.targetTime.getTime() - date.getTime();

        if (diff > 0) {
            const timeRemain = new Date(diff);
            const TZOffset = timeRemain.getTimezoneOffset();
            timeRemain.setMinutes(timeRemain.getMinutes() + TZOffset);
            if(diff<181000){
                this.element.style.color = "#ffb700";
            }
            if(diff<60000){
                this.element.style.color = "#f00";
            }
            input.setAttribute('value',timeRemain.getSeconds()+timeRemain.getMinutes()*60+timeRemain.getHours()*3600);
            this.element.innerHTML = timeRemain.toLocaleString("RU-ru",{
                hour: "2-digit",
                minute: "2-digit",
                second: "2-digit"
            });
        }
        if(diff<=0 && this.flag===true){
            clearInterval(this.interval);
            let form = document.getElementById("UserAnswers");
            form.action="";
            form.submit();
            this.flag=false;
        }
    };

}

function startTimer2(timeSeconds){
    // element = document.querySelector(".timer");
    element = document.getElementById("timer");
    input = document.getElementById("leftTime");

    const timer = new Timer({ element , seconds:timeSeconds, input });

}

