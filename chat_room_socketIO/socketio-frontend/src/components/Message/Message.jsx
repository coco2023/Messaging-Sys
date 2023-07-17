import React, { useEffect, useState } from "react";
import { RiSendPlaneLine, RiSendPlaneFill } from "react-icons/ri";

import { timeStampConverter } from "../../util/timeUtils";
import { useSocket } from "../../customerHooks/useSocket";
import { useFetch } from "../../customerHooks/useFetch";
import { MessageList } from "./MessageList";

export const Message = ({ room, username }) => {
    const { isConnected, socketResponse, sendData } = useSocket(room, username);
    const [messageInput, setMessageInput] = useState("");
    const [messageList, setMessageList] = useState([]);
  
    const { responseData, error, loading } = useFetch("/message/" + room);

    const addMessageToList = (val) => {
        if (val.room == "") return;
        // spreading the `messageList` array (`...messageList`) and adding the `val` object to the end
        setMessageList([...messageList, val]);
    };

    /**
     * update the messageList state whenever the responseData changes
     * useEffect() will be triggered when `responseData` changes
     * `responseData` is the dependency of useEffect()
     */
    useEffect(() => {
        if (responseData != undefined) {
            // spreading the responseData array and the existing messageList array
            setMessageList([...responseData, ...messageList]);
        }
    }, [responseData]);

    useEffect(() => {
        console.log("Socket Response: ", socketResponse);
        addMessageToList(socketResponse);
    }, [socketResponse]);

    const sendMessage = (e) => {
        e.preventDefault();
        if (messageInput != "") {
            sendData({
                content: messageInput
            });
            const time = "";
            addMessageToList({
                content: messageInput,
                username: username,
                createdDateTime: new Date(),
                messageType: "CLIENT"
            });
            setMessageInput("");
        }
    };

    return (
        <div className="message_root_div">
        <span className="room_name">Room: {room} </span>
        <span className="user_name">Welcome: {username} </span>
        <div className="message_component">
          <MessageList username={username} messageList={messageList} />
          <form className="chat-input" onSubmit={(e) => sendMessage(e)}>
            <input
              type="text"
              value={messageInput}
              onChange={(e) => setMessageInput(e.target.value)}
              placeholder="Type a message"
            />
            <button type="submit">
              {messageInput == "" ? (
                <RiSendPlaneLine size={25} />
              ) : (
                <RiSendPlaneFill color="#2671ff" size={25} />
              )}
            </button>
          </form>
        </div>
      </div>
  
    );

}

