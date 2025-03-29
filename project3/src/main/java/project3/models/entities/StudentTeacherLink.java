package project3.models.entities;

import javax.persistence.*;

@Entity
public class StudentTeacherLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_teacher_link_id")
    private Long studentTeacherLinkId;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Long getStudentTeacherLinkId() {
        return studentTeacherLinkId;
    }

    public void setStudentTeacherLinkId(Long studentTeacherLinkId) {
        this.studentTeacherLinkId = studentTeacherLinkId;
    }
}
