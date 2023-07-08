/**
 * stompClient: store the reference to the Stomp client,
 *              which is responsible for handling the WebSocket communication
  */
var stompClient = null;

/**
 * called to update the UI based on the connection status.
 * It enables/disables buttons and shows/hides the conversation section
 * @param connected: true/ false (Boolean)
 * -------
 * $("#connect"): selects the HTML element with the id "connect".
 */
function setConnected(connected) {
    // prop(): manipulate properties of HTML elements
    // sets the "disabled" property to the value of the connected variable
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");  // sets the HTML content of the element with the id "greetings" to an empty string
}

/**
 * will be invoked when "Connect" button is clicked.
 * It establishes a WebSocket connection to the server using SockJS and Stomp.
 * Upon successful connection:
 *      it sets the connection status to true.
 *      subscribes to the "/topic/greetings" destination,
 *      displays the received greetings
 */
function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
        });
    });
}

/**
 * is called when the "Disconnect" button is clicked
 * It disconnects the WebSocket connection，
 * sets the connection status to false,
 * updates the UI accordingly
 */
function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

/**
 * sends a WebSocket message to the server
 * by invoking stompClient.send() with the "/app/hello" destination
 * and the name in the input field.
 */
function sendName() {
    stompClient.send("/app/hello", {}, JSON.stringify({'name': $("#name").val()}));
}

/**
 * show the message when greeting message is received from the server
 * @param message
 */
function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

/**
 * jQuery
 * sets up event handlers for the buttons and prevents the default form submission behavior
 * $("form"): a submit event handler to all <form> elements in the document. When a form is submitted, the provided function is executed.
 *
 */
$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault(); // trigger a page reload
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
});