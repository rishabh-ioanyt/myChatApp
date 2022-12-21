'use strict'
let stompClient;
let currentUser= null;
const socket = new SockJS("/stomp");


function connect() {
    currentUser = document.querySelector('input').value;
    stompClient = Stomp.over(socket);
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
            console.log(response);
        });
    });
}

function sendMsg() {
    let to = document.querySelector('p').value;
    let text = document.querySelector('input').value;
    stompClient.send("/app/stomp/" + to, {}, JSON.stringify({
        fromLogin: currentUser,
        message: text
    }));
}