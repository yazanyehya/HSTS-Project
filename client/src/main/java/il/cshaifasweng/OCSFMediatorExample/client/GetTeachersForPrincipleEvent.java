package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class GetTeachersForPrincipleEvent {
    private Message message;
    public Message getMessage() {
        return message;
    }

    GetTeachersForPrincipleEvent(Message message){
        this.message = message;
    }
}
