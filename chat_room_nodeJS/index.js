const express = require('express');
const app = express();
const http = require('http');
const server = http.createServer(app);

// initialize a new instance of socket.io object
const { Server } = require("socket.io");
const io = new Server(server);

app.get('/', (req, res) => {
    res.sendFile(__dirname + '/index.html');
  });

// // listen on the connection event for incoming sockets and log it to the console
// io.on('connection', (socket) => {
//     console.log('a user connected');
//     // disconnect event; refresh page/ send a new mesg
//     socket.on('disconnect', () => {
//         console.log('user disconnected');
//     });
// });

// // sending message
// io.on('connection', (socket) => {
//     socket.on('chat message', (msg) => {
//       console.log('message: ' + msg);
//     });
//   });

// Broadcasting
io.on('connection', (socket) => {
    socket.on('chat message', (msg) => {
      io.emit('chat message', msg);
    });
  });  
  
server.listen(3000, () => {
  console.log('listening on *:3000');
});