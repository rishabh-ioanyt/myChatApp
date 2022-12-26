let stompClient = null;
let socket;

window.onload = connect;

function connect() {
    if (stompClient == null) {
        socket= new SockJS("/stomp");
        stompClient = Stomp.over(socket);
        let currentUser = document.getElementById("currentUser");
        connectEvent();
        stompClient.connect({}, function (frame) {
            stompClient.subscribe("/topic/messages/" + currentUser.innerText, function (response) {
                let data = JSON.parse(response.body);
                $(".output").append("<span><strong>" + data.fromLogin + "</strong>: " + data.message + "</em></span><br/>");
            });

            stompClient.subscribe("/user",function (response) {
                let data = JSON.parse(response.body);
                console(data.value);
            });
            stompClient.subscribe("/topic/messages", function (response) {
                let data = JSON.parse(response.body);
                $(".output").append("<span><strong>" + data.message + "</strong>: " + data.fromLogin + "</em></span><br/>");
            });
        });
    }
}

function connectEvent(){
    let currentUser = document.getElementById("currentUser");
    var source = new EventSource('/sse');
    console.log(source);
    source.addEventListener(currentUser.innerText,function (event) {
        console.log(event.data);
    });
}
function disconnected() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
}

function connectToChat( username) {
    console.log("connecting to chat...")
    stompClient.connect({}, function (frame) {
        console.log("connected to: " + frame);
        stompClient.subscribe("/topic/messages/" + userName, function (response) {
            console.log(response.body);
        });
    });
}

function sendMsg() {
    // var to = document.getElementsByName("sendto");
    var to = document.querySelector( 'input[name="sendto"]:checked');
    var text = document.getElementById("msg");
    var currentUser = document.getElementById("currentUser");
   /* $(".sendMessage")
        .append("<span><strong>"
            + currentUser.innerText
            + "</strong>: "
            + text.value
            + "</em></span><br/>");*/
    stompClient.send("/app/stomp/" + to.value, {}, JSON.stringify({
        fromLogin: currentUser.innerText,
        message: text.value
    }));
}

function broadCastMsg() {
    var text = document.getElementById("msg");
    var currentUser = document.getElementById("currentUser");
    /* $(".sendMessage")
         .append("<span><strong>"
             + currentUser.innerText
             + "</strong>: "
             + text.value
             + "</em></span><br/>");*/
    stompClient.send("/app/stomp/broadCast", {}, JSON.stringify({
        fromLogin: currentUser.innerText,
        message: text.value
    }));
}