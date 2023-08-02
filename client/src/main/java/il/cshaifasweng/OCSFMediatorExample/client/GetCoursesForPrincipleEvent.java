package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class GetCoursesForPrincipleEvent {
    private Message message;

    public Message getMessage() {
        return message;
    }

    public GetCoursesForPrincipleEvent(Message message) {
        this.message = message;
    }
}
