package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class GetStudentsForPrincipleEvent {
    private Message message;
    public Message getMessage() {
        return message;
    }

    GetStudentsForPrincipleEvent(Message message){
        this.message = message;
    }
}
