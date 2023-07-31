package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class ViewGradesForTeacherEvent
{
    private Message message;

    public Message getMessage() {
        return message;
    }

    public ViewGradesForTeacherEvent(Message message) {
        this.message = message;
    }
}
