import { useCallback, useEffect, useState } from "react";
import * as io from 'socket.io-client';
import { SOCKET_BASE_URL } from "../constants/apiConstants";

export const useSocket = (room, username) => {
    const [socket, setSocket] = useState();
    const [isConnected, setConnected] = useState(false);
    const [socketResponse, setSocketResponse] = useState({
        room: "",
        content: "",
        username: "",
        messageType: "",
        createdDateTime: "",
    });

    const sendData = useCallback(
        (playload) => {
            socket.emit("send_message", {
                room: room,
                content: playload.content,
                username: username,
                messageType: "CLIENT"
            });
        },
        [socket, room]
    );

    /**
     * establish a socket connection
     * listen for events
     * @return: socketResponse, isConnected,  sendData
     * 
     */
    useEffect(() => {
        // io: associated with socket.io-client
        const socketObj = io(SOCKET_BASE_URL, {
            reconnection: false,
            query: `username=${username}&room=${room}`, //"room=" + room+",username="+username,
        }) ;
        setSocket(socketObj);
        // sets up a listener for the "connect" event
        socketObj.on("connect", () => setConnected(true));
        // sets up a listener for the "read_message" event
        socketObj.on("read_message", (res) => {
            console.log(res);
            setSocketResponse({
                room: res.room,
                content: res.content,
                username: res.username,
                messageType: res.messageType,
                createdDateTime: res.createdDateTime,
            });
        });
        return () => {
            socketObj.disconnect();
        };
    }, 
    [room]);

    return { socketResponse, isConnected, sendData };

};

