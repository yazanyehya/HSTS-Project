package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class GetSubjectsForTeacherAPPEvent
{
    private Message message;
    public Message getMessage() {
        return message;
    }

    GetSubjectsForTeacherAPPEvent(Message message){
        this.message = message;
    }
}
