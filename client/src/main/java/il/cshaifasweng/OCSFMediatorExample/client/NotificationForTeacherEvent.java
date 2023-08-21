package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class NotificationForTeacherEvent
{
    private Message message;

    public Message getMessage() {
        return message;
    }

    public NotificationForTeacherEvent(Message message) {
        this.message = message;
    }
}
