package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class GetCoursesForSubjectsExamEvent
{
    private Message message;
    public Message getMessage() {
        return message;
    }

    GetCoursesForSubjectsExamEvent(Message message){
        this.message = message;
    }
}
