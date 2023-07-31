package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class ViewGradesForTeacherIIBoundryEvent
{
    private Message message;

    public Message getMessage() {
        return message;
    }
    ViewGradesForTeacherIIBoundryEvent(Message message){this.message = message;}
}
