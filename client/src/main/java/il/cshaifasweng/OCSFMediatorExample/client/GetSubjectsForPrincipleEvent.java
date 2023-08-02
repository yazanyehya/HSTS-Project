package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class GetSubjectsForPrincipleEvent {
    private Message message;
    public Message getMessage() {
        return message;
    }

    GetSubjectsForPrincipleEvent(Message message){
        this.message = message;
    }
}
