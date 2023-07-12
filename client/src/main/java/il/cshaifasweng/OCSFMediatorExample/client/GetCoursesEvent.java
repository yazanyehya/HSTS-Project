package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class GetCoursesEvent
{
    private Message message;

    public Message getMessage() {
        return message;
    }

    public GetCoursesEvent(Message message) {
        this.message = message;
    }
}
