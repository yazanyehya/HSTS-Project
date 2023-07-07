package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Subjects")
public class Subject implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "subject_id")
    private List<Question> listOfQuestions;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "subject_id")
    private List<Teacher> listOfTeachers;

    public Subject(String name)
    {
        this.name = name;
        listOfQuestions = new ArrayList<Question>();
        listOfTeachers = new ArrayList<Teacher>();
    }
    public Subject()
    {
        listOfQuestions = new ArrayList<Question>();
        listOfTeachers = new ArrayList<Teacher>();
    }

    public int getId() {
        return id;
    }

    public List<Question> getListOfQuestions() {
        return listOfQuestions;
    }

    public List<Teacher> getListOfTeachers() {
        return listOfTeachers;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setListOfQuestions(List<Question> listOfQuestions) {
        this.listOfQuestions = listOfQuestions;
    }

    public void setListOfTeachers(List<Teacher> listOfTeachers) {
        this.listOfTeachers = listOfTeachers;
    }

    public void setName(String name) {
        this.name = name;
    }

}
