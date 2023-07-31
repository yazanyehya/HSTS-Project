package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class GetCoursesAPPEvent
{
    private Message message;

    public Message getMessage() {
        return message;
    }

    public GetCoursesAPPEvent(Message message) {
        this.message = message;
    }
}
