let stompClient;


function connect() {
    let socket= new SockJS("/stomp");
    stompClient = Stomp.over(socket);
    let currentUser = document.getElementById("currentUser");
    connectToChat();
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
});
}
function disconnected() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
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
    console.log(to.value);
    console.log(text.value);
    console.log(currentUser.innerText);
    stompClient.send("/app/stomp/" + to.value, {}, JSON.stringify({
        fromLogin: currentUser.innerText,
        message: text.value
    }));
}