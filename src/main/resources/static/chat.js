let stompClient = null;
let currentUser= null;

function connect(user) {
    currentUser = user;
    const socket = new WebSocket("ws://localhost:8080/ws");
    stompClient = Stomp.client(socket);
    stompClient.connect({}, function (frame) {
        stompClient.subscribe("/topic/" + currentUser, function (response) {
            console.log(response);
        });
        console.log(frame);
});
}
function disconnected() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}

function connectToChat(userName) {
    console.log("connecting to chat...")
    stompClient = Stomp.over("/stomp");
    stompClient.connect({}, function (frame) {
        console.log("connected to: " + frame);
        stompClient.subscribe("/topic/" + userName, function (response) {
            console.log(userName);
        });
    });
}