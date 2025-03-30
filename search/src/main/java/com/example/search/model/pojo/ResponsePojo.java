package com.example.search.model.pojo;

import java.util.List;

public class ResponsePojo {
    private Object details;
    private List<StudentPojo> studentPojos;

    public Object getDetails() {
        return details;
    }

    public void setDetails(Object details) {
        this.details = details;
    }

    public List<StudentPojo> getStudentPojos() {
        return studentPojos;
    }

    public void setStudentPojos(List<StudentPojo> studentPojos) {
        this.studentPojos = studentPojos;
    }
}
