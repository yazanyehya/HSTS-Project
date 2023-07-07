package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class AlreadyLoggedIn
{
    private Message message;
    public Message getMessage() {
        return message;
    }

    AlreadyLoggedIn(Message message){
        this.message = message;
    }
}
