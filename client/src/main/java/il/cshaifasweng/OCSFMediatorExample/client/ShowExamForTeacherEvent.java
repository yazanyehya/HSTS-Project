package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class ShowExamForTeacherEvent
{
    private Message message;

    public Message getMessage() {
        return message;
    }
    ShowExamForTeacherEvent(Message message){this.message = message;}
}
