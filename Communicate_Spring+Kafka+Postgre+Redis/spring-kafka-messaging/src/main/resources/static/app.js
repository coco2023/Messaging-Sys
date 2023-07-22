// $("#elementId"): select elements by their IDs
// $.ajax({ ... });

function activate() {
  var mobileFormData = JSON.stringify({
    'mobile': $("#mobile").val(),
  }); // -- ActivationRequest

  console.log("here we are")
  $.ajax({
    type: "POST",
    url: "http://localhost:9001/api/auth/getcode",
    data: mobileFormData,   // ActivationRequest
    contentType: "application/json;charset=utf-8",
    success: function(activationResponse){      // ActivationResponse
        $("#activationCode").val(activationResponse.activationCode);
    }
  });
}

function login() {
  var loginFormData = JSON.stringify({
    'mobile': $("#mobile").val(),
    'activationCode': $("#activationCode").val(),
  });  // -- LoginRequest

  $.ajax({
    type: "POST",
    url: "http://localhost:9001/api/auth/login",
    data: loginFormData,    // -- LoginRequest
    contentType: "application/json;charset=utf-8",
    success: function(LoginResponse){  // will exec when server returns a successful response;
        // result: the response data from the server -- LoginResponse
        $("#accessToken").val(LoginResponse.accessToken);
    }
  });
}

// function loadContacts() {
//   var getcontactsFormData = JSON.stringify({
//     'accessToken': $("#accessToken").val(),
//   });  // -- ContactRequest
//
//   $("#contactlist").empty();
//   $.ajax({
//     type: "POST",
//     url: "http://localhost:9001/api/auth/getcontacts",
//     data: getcontactsFormData,   // it is ContactRequest
//     contentType: "application/json;charset=utf-8",
//     success: function(contactResponse){   // response after successful request -- return contactResponse
//         console.log("here the contacts: ", JSON.stringify(contactResponse));
//         contactResponse.contacts.forEach(function(c) {
//             console.log(c.user.mobile);
//             $("#contactlist").append('<tr><td><input type="checkbox" id="sendTo" value="' + c.contactUserId + '">' + c.user.mobile + '</input></td></tr>');
//         });
//     }
//   });
// }

/**
 * connect(): this method establishes a WebSocket connection to the server.
 * WebSockets allow bi-directional communication between the client and server,
 * enabling real-time updates and messaging
 */
function connect() {
    // creates a new WebSocket instance and connects to the server using the specified WebSocket URL
	ws = new WebSocket('ws://localhost:9001/messaging?accessToken=' + $("#accessToken").val());
    ws.addEventListener('error', (error) => {
		console.log("Error: ", error.message);
		setConnected(false);
    });
    ws.addEventListener('close', (event) => {
		console.log("Close: ", event.code + " - " + event.reason);
	    setConnected(false);
    })
    // the event handler for receiving helloWorld messages from the WebSocket server; test
	ws.onmessage = function(event) {
		helloWorld(event.data);
		console.log("Connected to websocket " + event.data);
	}
    // sets the connection status to true
	setConnected(true);
}

// credits: https://www.javainuse.com/spring/boot-websocket
/**
 * represents the status of the WebSocket connection
 * @param connected
 */
// `ws` is used to store the WebSocket instance created when connecting to the server
var ws;
function setConnected(connected) {
    // set the "disabled" property of the element with the ID "connect" ($("#connect") button) to the value of the `connected` parameter
    $("#connect").prop("disabled", connected);      // When `connected` is true, it disables the $("#connect") button
    // set the "disabled" property of the element with the ID "disconnect" ($("#disconnect") button) to the OPPOSITE of the `connected` parameter
    $("#disconnect").prop("disabled", !connected);  // When connected is true, it enables the "Disconnect" button &
                                                          // & allowing the user to click it to disconnect from the WebSocket server
    if(connected) {
        // loadContacts();
        $("#chatbox").show();
    } else {
        $("#chatbox").hide();
    }
}

function disconnect() {
	if (ws != null) {
		ws.close();
	}
	$(".chatbox").hide();
	setConnected(false);
	console.log("Websocket is in disconnected state");
}

function sendData() {
	var data = JSON.stringify({
	  'topic': 'WEATHER',
	  'message': {
	    'accessToken': $("#accessToken").val(),
	    'sendTo': $("#sendTo").val(),
	    'msg': $("#messageArea").val(),
	  }
	})
	ws.send(data);
}

function helloWorld(message) {
	$("#chatmessages").append("<tr><td>" + message + "</td></tr>");
}

/**
 *  event listeners for 5 different buttons with IDs:
 *      "activate", "login", "connect", "disconnect", "send".
 *  When each button is clicked, it calls the corresponding function:
 *      activate(), login(), connect(), disconnect(), sendData().
 */
$(function() {
	$("form").on('submit', function(e) {
		e.preventDefault();
	});
    $("#activate").click(function() {
        activate();
    });
    $("#login").click(function() {
        login();
    });
	$("#connect").click(function() {
		connect();
	});
	$("#disconnect").click(function() {
		disconnect();
	});
	$("#send").click(function() {
		sendData();
	});
});
