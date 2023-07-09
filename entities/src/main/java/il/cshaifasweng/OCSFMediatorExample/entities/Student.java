package il.cshaifasweng.OCSFMediatorExample.entities;;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table (name="students")
public class Student  extends User implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToMany(mappedBy = "listOfStudents", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Exam> exams;

    public Student()
    {
        this.exams = new ArrayList<Exam>();
    }

}
