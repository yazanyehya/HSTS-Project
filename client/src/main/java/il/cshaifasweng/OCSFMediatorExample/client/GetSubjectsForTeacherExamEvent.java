package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class GetSubjectsForTeacherExamEvent
{
    private Message message;
    public Message getMessage() {
        return message;
    }

    GetSubjectsForTeacherExamEvent(Message message){
        this.message = message;
    }
}
