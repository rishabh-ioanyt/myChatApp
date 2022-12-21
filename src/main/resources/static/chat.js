let stompClient;


function connect() {
    let socket= new SockJS("/stomp");
    stompClient = Stomp.over(socket);
    let currentUser = document.getElementById("currentUser");
    connectToChat();
    stompClient.connect({}, function (frame) {
        stompClient.subscribe("/topic/messages/" + currentUser.innerText, function (response) {
            let data = JSON.parse(response.body);
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

function connectToChat() {
    console.log("connecting to chat...")
    var userName = document.getElementById("username");
    stompClient.connect({}, function (frame) {
        console.log("connected to: " + frame);
        stompClient.subscribe("/topic/messages/" + userName.innerText, function (response) {
            console.log(response.body);
        });
    });
}

function sendMsg() {
    var to = document.getElementById("username");
    var text = document.getElementById("msg");
    var currentUser = document.getElementById("currentUser");
    stompClient.send("/app/stomp/" + to.innerText, {}, JSON.stringify({
        fromLogin: currentUser.innerText,
        message: text
    }));
    console.log(text.innerText + currentUser.innerText );
}