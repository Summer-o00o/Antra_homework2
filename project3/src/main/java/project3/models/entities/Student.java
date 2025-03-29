package project3.models.entities;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long studentId;

    @Column(length = 100, name = "first_name")
    private String firstName;

    @Column(length = 100, name = "last_name")
    private String lastName;

    @OneToMany(mappedBy = "student")
    private List<StudentTeacherLink> studentTeacherLinks = new ArrayList<>();

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<StudentTeacherLink> getStudentTeacherLinks() {
        return studentTeacherLinks;
    }

    public void setStudentTeacherLinks(List<StudentTeacherLink> studentTeacherLinks) {
        this.studentTeacherLinks = studentTeacherLinks;
    }

}
