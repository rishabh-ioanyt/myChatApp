let stompClient = null;
let socket;


function connect() {
    if (stompClient == null) {
        socket= new SockJS("/stomp");
        stompClient = Stomp.over(socket);
        let currentUser = document.getElementById("currentUser");
        stompClient.connect({}, function (frame) {
            stompClient.subscribe("/topic/messages/" + currentUser.innerText, function (response) {
                let data = JSON.parse(response.body);
                $(".output")
                    .append("<span><strong>"
                        + data.message
                        + "</strong>: <em> send by "
                        + data.fromLogin
                        + "</em></span><br/>");
                console.log(data.message + " " + currentUser);
            });

            stompClient.subscribe("/user/topic",function (response) {
                let data = JSON.parse(response.body);
                console(data.value);
            })
        });
    }
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
    var to = document.getElementById("sendto");
    var text = document.getElementById("msg");
    var currentUser = document.getElementById("currentUser");
    console.log(to.innerText);
    console.log(text.value);
    console.log(currentUser.innerText);
    stompClient.send("/app/stomp/" + to.innerText, {}, JSON.stringify({
        fromLogin: currentUser.innerText,
        message: text.value
    }));
}