package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class GetStudentsGradesForPrincipleEvent {
    private Message message;
    public Message getMessage() {
        return message;
    }

    GetStudentsGradesForPrincipleEvent(Message message){
        this.message = message;
    }
}
