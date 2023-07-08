refer: 

[1] https://spring.io/guides/gs/messaging-stomp-websocket/

[2] https://www.devglan.com/spring-boot/spring-boot-websocket-example

[3] https://www.javainuse.com/spring/boot-websocket

<hr>

1. $(function () {}
- jQuery
- sets up event handlers for the buttons and prevents the default form submission behavior
- $("form"): a submit event handler to all <form> elements in the document. When a form is submitted, the provided function is executed.

2. function setConnected(connected) { }
- called to update the UI based on the connection status.
- It enables/disables buttons and shows/hides the conversation section
- @param connected: true/ false (Boolean)
- $("#connect"): selects the HTML element with the id "connect".
   
3. function connect() { }
- will be invoked when "Connect" button is clicked.
- It establishes a WebSocket connection to the server using SockJS and Stomp.
- Upon successful connection:
  - it sets the connection status to true.
  - subscribes to the "/topic/greetings" destination,
  - displays the received greetings

4. function disconnect() { }
* is called when the "Disconnect" button is clicked
* It disconnects the WebSocket connection，
* sets the connection status to false,
* updates the UI accordingly

5. function sendName() { }
- sends a WebSocket message to the server
- by invoking stompClient.send() with the "/app/hello" destination
- and the name in the input field.

6. function showGreeting(message) { }
- show the message when greeting message is received from the server

