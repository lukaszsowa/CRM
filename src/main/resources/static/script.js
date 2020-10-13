function showTime(){
    var date = new Date();
    var h = date.getHours();
    var m = date.getMinutes();
    var s = date.getSeconds();

    h = (h < 10) ? "0" + h : h;
    m = (m < 10) ? "0" + m : m;
    s = (s < 10) ? "0" + s : s;

    var time = h + ":" + m + ":" + s;
    document.getElementById("clock2").innerText = time;
    document.getElementById("clock2").textContent = time;

    setTimeout(showTime, 1000);
}



function myPopup(myURL, title, myWidth, myHeight) {
            var left = (screen.width/2) - (myWidth/2);
            var top = (screen.height/2) - (myHeight/4);
            var myWindow = window.open(myURL, title, 'toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=no, copyhistory=no, width=' + myWidth + ', height=' + myHeight + ', top=' + top + ', left=' + left);
         }


function post_value(id){
    opener.document.getElementById("companyChoice").value = document.getElementById("companyId" + id).innerHTML;
    opener.document.getElementById("companyNameChoice").value = document.getElementById("companyName").innerText;
    self.close();
    }



