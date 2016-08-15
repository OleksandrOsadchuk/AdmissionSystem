/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

/**
 *
 * @author Administrator
 */
public class Result implements TableItem {
    private int studentId;
    private int courseId;
    private int mark1;
    private int mark2;
    private int id;
    
    public Result (int studId, int courseId, int mark1, int mark2)
    {
        this.courseId = courseId;
        this.studentId = studId;
        this.mark1 = mark1;
        this.mark2 = mark2;
    }
    
    public Result()
    {
        this.courseId = 0;
        this.studentId = 0;
        this.mark1 = 0;
        this.mark2 = 0;  
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }
    
    public int getStudentId() {
        return studentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getMark1() {
        return mark1;
    }

    public void setMark1(int mark1) {
        this.mark1 = mark1;
    }

    public int getMark2() {
        return mark2;
    }

    public void setMark2(int mark2) {
        this.mark2 = mark2;
    }

   
    
    
    
}
