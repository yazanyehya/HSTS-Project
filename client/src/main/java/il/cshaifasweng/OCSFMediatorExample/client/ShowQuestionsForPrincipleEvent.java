package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class ShowQuestionsForPrincipleEvent
{
    private Message message;

    public Message getMessage() {
        return message;
    }
    ShowQuestionsForPrincipleEvent(Message message){this.message = message;}
}
