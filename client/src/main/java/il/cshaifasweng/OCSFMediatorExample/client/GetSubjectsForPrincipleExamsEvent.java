package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class GetSubjectsForPrincipleExamsEvent {
    private Message message;
    public Message getMessage() {
        return message;
    }

    GetSubjectsForPrincipleExamsEvent(Message message){
        this.message = message;
    }
}
