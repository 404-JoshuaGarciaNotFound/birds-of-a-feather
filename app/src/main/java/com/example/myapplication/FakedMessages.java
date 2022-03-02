package com.example.myapplication;

import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;

import java.nio.charset.StandardCharsets;

// This class is used to fake messages to test Nearby Message API
public class FakedMessages {
    public static void fakedMessages(MessageListener searchingClassmate) {
        // Fake message to test nearby messages API
        String fakedMessageStr = "Andy\n" +
                "https://i.ibb.co/N7MGG27/download.png\n" +
                "2022,SP,CSE,127 " +
                "2022,WI,CSE,110 ";
        Message fakedMessage = new Message(fakedMessageStr.getBytes(StandardCharsets.UTF_8));
        searchingClassmate.onFound(fakedMessage);

        String fakedMessageStr2 = "Ariana\n" +
                "https://i.ibb.co/N7MGG27/download.png\n" +
                "2022,WI,CSE,110 " +
                "2022,WI,CSE,123 " +
                "2022,SP,CSE,123";
        Message fakedMessage2 = new Message(fakedMessageStr2.getBytes(StandardCharsets.UTF_8));
        searchingClassmate.onFound(fakedMessage2);
    }
}
