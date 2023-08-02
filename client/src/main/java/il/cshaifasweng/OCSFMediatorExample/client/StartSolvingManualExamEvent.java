package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class StartSolvingManualExamEvent
{
    private Message message;

    public Message getMessage() {
        return message;
    }
    StartSolvingManualExamEvent(Message message){this.message = message;}
}
