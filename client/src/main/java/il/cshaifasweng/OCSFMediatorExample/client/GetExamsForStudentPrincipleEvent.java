package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class GetExamsForStudentPrincipleEvent {
    private Message message;
    public Message getMessage() {
        return message;
    }

    GetExamsForStudentPrincipleEvent(Message message){
        this.message = message;
    }
}
