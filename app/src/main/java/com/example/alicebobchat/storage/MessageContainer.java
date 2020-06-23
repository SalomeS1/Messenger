package com.example.alicebobchat.storage;

import android.content.Context;

import java.util.ArrayList;

public class MessageContainer {

    public static String STORAGE_KEY = "Chat_Contrainer";

    private ArrayList<Messages> messages = new ArrayList<Messages>();

    public ArrayList<Messages> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Messages> messages) {
        this.messages = messages;
    }

    public static void addMessage(Context context, String text, String receiver)
    {
        Messages message = new Messages();
        message.setText(text);
        message.setReceiver(receiver);
        Storage storage = new StorageImpl();
        Object storageAsObject = storage
                .getObject(context, MessageContainer.STORAGE_KEY, MessageContainer.class);

        MessageContainer messageContainerStorage;
        if (storageAsObject != null) {
            messageContainerStorage = (MessageContainer) storageAsObject;
        } else {
            messageContainerStorage = new MessageContainer();
        }
        messageContainerStorage.getMessages().add(message);
        storage.add(context, MessageContainer.STORAGE_KEY, messageContainerStorage);
    }
}
