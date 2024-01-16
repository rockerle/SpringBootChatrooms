const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/messageIn/'+document.title
});
stompClient.activate();

stompClient.onConnect = () =>{
    subscribe(document.title);
};
stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};
stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};
function send(room){
    let text = $('#input').val();
    console.log("sending "+text);
    $('#input').val = "";
    stompClient.publish({
        destination: "/messageIn/"+room,
        body: JSON.stringify(text)
    });
}
function createListNode(messageObj){
    console.log("messageObj: " + messageObj);
    return messageObj+"<br>";
}
function showNewMessage(message){
    console.log(message);
    $("#messages").append(message);
}

function subscribe(room){
    stompClient.subscribe('/broadcast/'+room, function(out){
        var json = JSON.parse(out.body);
        showNewMessage(createListNode("["+json.sender+"]: "+json.content))
    });
}