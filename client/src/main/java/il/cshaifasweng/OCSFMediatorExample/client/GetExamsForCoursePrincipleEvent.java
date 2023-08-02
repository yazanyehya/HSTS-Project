package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class GetExamsForCoursePrincipleEvent {
    private Message message;
    public Message getMessage() {
        return message;
    }

    GetExamsForCoursePrincipleEvent(Message message){
        this.message = message;
    }
}
