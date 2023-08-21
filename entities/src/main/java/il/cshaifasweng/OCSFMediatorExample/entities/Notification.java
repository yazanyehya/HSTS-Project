package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Entity
@Table(name = "Notifications")
public class Notification implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String message;
    private LocalDateTime timestamp;
    private boolean isRead;
    private String readOrNot;
    private String date;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "notification_id")
    private User user;

   public Notification(String message,LocalDateTime timestamp, boolean isRead)
   {
       this.message = message;
       this.timestamp = timestamp;
       this.isRead = isRead;
       this.readOrNot = isRead ? "Read" : "Unread";

       // Format the date and time as desired (change the pattern as needed)
       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd\n " +
               "HH:mm:ss");
       String dateTimeString = timestamp.format(formatter);
       this.date = dateTimeString;
   }
    public Notification(){};
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
        this.readOrNot = read ? "Read" : "Unread";
    }

    public String getDate() {
        return date;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isRead() {
        return isRead;
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getReadOrNot() {
        return readOrNot;
    }
    // Constructor, getters, setters
}
