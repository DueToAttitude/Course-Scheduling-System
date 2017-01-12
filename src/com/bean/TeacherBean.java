package com.bean;

/**
 * Created by recycle on 12/15 0015.
 */
public class TeacherBean {
    private int idTeacher;
    private String teacherName;
    private int idCourse;
    private int teacherScheduled;

    public int getTeacherScheduled() {
        return teacherScheduled;
    }

    public void setTeacherScheduled(int teacherScheduled) {
        this.teacherScheduled = teacherScheduled;
    }


    public int getIdTeacher() {
        return idTeacher;
    }

    public void setIdTeacher(int idTeacher) {
        this.idTeacher = idTeacher;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public int getIdCourse() {
        return idCourse;
    }

    public void setIdCourse(int idCourse) {
        this.idCourse = idCourse;
    }
}
