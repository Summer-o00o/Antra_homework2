package project3.models.pojo;

public class StudentTeacherLinkPojo {
    private Long studentTeacherLinkPojoId;
    private Long studentId;
    private Long teacherId;

    public Long getStudentTeacherLinkPojoId() {
        return studentTeacherLinkPojoId;
    }

    public void setStudentTeacherLinkPojoId(Long studentTeacherLinkPojoId) {
        this.studentTeacherLinkPojoId = studentTeacherLinkPojoId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }
}
