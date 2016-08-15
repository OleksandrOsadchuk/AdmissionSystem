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
public class Student implements TableItem {
    // Ivars

    private String firstName;
    private String lastName;
    private String startDate;
    private String gender;
    private int id;

    public Student() {
        firstName = "";
        lastName = "";
        startDate = "";
        gender = "";
        id = 0;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getGender() {
        return gender;
    }

    @Override
    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

//    public void setMark1(double mark1) {
//        this.mark1 = mark1;
//    }
//
//    public void setMark2(double mark2) {
//        this.mark2 = mark2;
//    }
//
//    public double getMark1() {
//        return mark1;
//    }
//
//    public double getMark2() {
//        return mark2;
//    }

   
    
    public Student(int id, String firstName, String lastName, String gender, String startDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.startDate = startDate;
        this.id = id;
    }

//    private void calculateAverage() {
//        avg = (mark1 + mark2) / 2;
//    }
//
//    public void calculateGrade() {
//        if (avg < 60) {
//            grade = 'e';
//        } else if (avg < 70) {
//            grade = 'd';
//        } else if (avg < 80) {
//            grade = 'c';
//        } else if (avg < 90) {
//            grade = 'b';
//        } else {
//            grade = 'a';
//        }
//    }
//
//    public void displayGrade() {
//        this.calculateAverage();
//        this.calculateGrade();
//        System.out.println(id + " " + firstName + " " + lastName + " : " + "grade = " + grade + "\n");
//    }
}
