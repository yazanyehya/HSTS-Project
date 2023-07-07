package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;

public class Message implements Serializable {
    private String title;
    private Object body;

    public Message(String title, Object body) {
        this.title = title;
        this.body = body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public Object getBody() {
        return body;
    }
}
