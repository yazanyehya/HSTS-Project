 package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

 @Entity
@Table(name = "Users")
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String idd;
    @Column(name = "firstname")
    private String firstName;
    @Column(name = "lastname")
    private String lastName;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "isLoggedIn")
    private Boolean isLoggedIn;
    @Column
    private int whoAreYou; // 0 for student, 1 for teacher, 2 for principle

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "notification_id")
    private List<Notification> notificationList;

    public User(String firstName, String lastName, String username, String password, int whoAreYou, Boolean isLoggedIn, String idd)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.whoAreYou = whoAreYou;
        this.isLoggedIn = isLoggedIn;
        this.idd = idd;
        this.notificationList =  new ArrayList<Notification>();
    }

    public User() {
        this.notificationList =  new ArrayList<Notification>();
    }

    public Boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(Boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public int getWhoAreYou() {
        return whoAreYou;
    }

    public void setWhoAreYou(int whoAreYou) {
        this.whoAreYou = whoAreYou;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public int getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getIdd() {
        return idd;
    }

    public void setIdd(String idd) {
        this.idd = idd;
    }

    public List<Notification> getNotificationList() {
        return notificationList;
    }
}
 