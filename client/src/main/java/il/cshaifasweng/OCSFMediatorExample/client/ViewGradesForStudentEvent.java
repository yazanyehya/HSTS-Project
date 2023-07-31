package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class ViewGradesForStudentEvent
{
    private Message message;

    public Message getMessage() {
        return message;
    }

    public ViewGradesForStudentEvent(Message message) {
        this.message = message;
    }
}
