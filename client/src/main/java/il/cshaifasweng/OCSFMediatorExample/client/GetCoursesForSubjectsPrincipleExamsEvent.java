package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class GetCoursesForSubjectsPrincipleExamsEvent {
    private Message message;
    public Message getMessage() {
        return message;
    }

    GetCoursesForSubjectsPrincipleExamsEvent(Message message){
        this.message = message;
    }
}
