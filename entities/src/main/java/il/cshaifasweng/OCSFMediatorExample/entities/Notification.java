package il.cshaifasweng.OCSFMediatorExample.entities;

import java.time.LocalDateTime;

public class Notification {
    private String message;
    private LocalDateTime timestamp;
    private boolean isRead;

   public Notification(String message,LocalDateTime timestamp, boolean isRead)
   {
       this.message = message;
       this.timestamp = timestamp;
       this.isRead = isRead;
   }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isRead() {
        return isRead;
    }
    // Constructor, getters, setters
}
