package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Principles")
public class Principle extends User implements Serializable {


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "principle_id")
    private List<Teacher> teachers;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "principle_id")
    private List<Course> courses;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "principle_id")
    private List<Student> students;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "principle_id")
    private List<Subject> subjects;
    public Principle(String firstName, String lastName, String username, String password, int whoAreYou, String idd)
    {
        super(firstName, lastName, username, password, whoAreYou, false, idd);
        this.students = new ArrayList<Student>();
        this.teachers = new ArrayList<Teacher>();
        this.courses = new ArrayList<Course>();
        this.subjects = new ArrayList<Subject>();
    }
    public Principle(){
        this.students = new ArrayList<Student>();
        this.teachers = new ArrayList<Teacher>();
        this.courses = new ArrayList<Course>();
        this.subjects = new ArrayList<Subject>();
    }
    public List<Course> getCourses() {
        return courses;
    }
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
    public List<Student> getStudents() {
        return students;
    }
    public void setStudents(List<Student> students) {
        this.students = students;
    }
    public List<Teacher> getTeachers() {
        return teachers;
    }
    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }
    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }
}