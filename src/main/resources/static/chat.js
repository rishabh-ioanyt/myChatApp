let stompClient = null;
function connect() {
    const socket = new WebSocket("ws://localhost:8080/ws");
    stompClient = Stomp.client(socket);
    stompClient.connect({}, function (frame) {
    console.log("connected to: " + frame);
});
}
function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}