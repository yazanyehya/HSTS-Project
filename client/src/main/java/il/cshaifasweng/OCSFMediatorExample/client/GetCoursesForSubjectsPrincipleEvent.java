package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class GetCoursesForSubjectsPrincipleEvent {
    private Message message;
    public Message getMessage() {
        return message;
    }

    GetCoursesForSubjectsPrincipleEvent(Message message){
        this.message = message;
    }
}
