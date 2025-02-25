let stompClient = null;

function connect() {
    let socket = new SockJS('/chat');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function (frame) {
        console.log("Connected: " + frame);
        stompClient.subscribe('/topic/messages', function (message) {
            showMessage(JSON.parse(message.body));
        });
    });
}

function sendMessage() {
    let input = document.getElementById("message-input");
    let messageContent = input.value.trim();

    if (messageContent && stompClient) {
        let chatMessage = {
            sender: "User",
            content: messageContent
        };

        stompClient.send("/app/sendMessage", {}, JSON.stringify(chatMessage));
        input.value = "";
    }
}

function showMessage(message) {
    let messagesDiv = document.getElementById("messages");
    let newMessage = document.createElement("div");
    newMessage.textContent = message.sender + ": " + message.content;
    messagesDiv.appendChild(newMessage);
}

window.onload = connect;
