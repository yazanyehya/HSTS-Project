package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Courses")
public class Course implements Serializable
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "subject_id")
    private List<Question> listOfQuestions;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "course_question",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "teacher_id")
    )
    private List<Teacher> listOfTeachers;

    public Course(String name)
    {
        this.name = name;
        this.listOfQuestions = new ArrayList<Question>();
        this.listOfTeachers = new ArrayList<Teacher>();
    }
    public Course()
    {
        this.listOfQuestions = new ArrayList<Question>();
        this.listOfTeachers = new ArrayList<Teacher>();
    }
    public List<Question> getListOfQuestions() {
        return listOfQuestions;
    }

    public String getName() {
        return name;
    }

    public List<Teacher> getListOfTeachers() {
        return listOfTeachers;
    }

    public int getId() {
        return id;
    }

    public void setListOfQuestions(List<Question> listOfQuestions) {
        this.listOfQuestions = listOfQuestions;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setListOfTeachers(List<Teacher> listOfTeachers) {
        this.listOfTeachers = listOfTeachers;
    }

    public void setId(int id) {
        this.id = id;
    }
}
