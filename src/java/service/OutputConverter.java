/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import beans.Course;
import beans.Result;
import beans.Student;
import beans.TableItem;
import java.util.List;

/**
 *
 * @author pear
 */
public class OutputConverter {
    
    public String htmlOut(List<TableItem> lst) {
        String out = "";
        if (lst.isEmpty()) {
            System.out.println("Nothing found");
            return "List is empty, please try later.";
        }

        if (lst.get(0).getClass().getSimpleName().equalsIgnoreCase("student")) {
            out = out//+"<!DOCTYPE html><html lang=\"en\"><head><meta charset = \"utf-8\"><title>List</title></head><body>"
                    + "<p>Student List</p>"
                    + "<table>"
                    + "<tr><th>id </th><th>FirstName</th><th>LastName</th>"
                    + "<th>Gender </th><th>StartDate </th></tr>";
            for (TableItem item : lst) {
                Student s = (Student) item;
                out = out + "<tr style=\"text-align: left\">"
                        + "<td>" + s.getId() + "</td><td>" + s.getFirstName() + "</td><td>"
                        + s.getLastName() + "</td><td>" + s.getGender() + "</td><td>"
                        + s.getStartDate() + "</td>"
                        + "</tr>";
            }
            out = out + "</table>"; //</body></html>";
        }
        if (lst.get(0).getClass().getSimpleName().equalsIgnoreCase("Course")) {
            out = out + "<p>Course List</p>"
                    + "<table>"
                    + "<tr><th>Id </th><th>Name</th></tr>";

            for (TableItem item : lst) {
                Course c = (Course) item;
                out = out + "<tr style=\"text-align: left\">"
                        + "<td>" + c.getId() + "</td><td>" + c.getName() + "</td></tr>";
            }
            out = out + "</table>";
        }
        if (lst.get(0).getClass().getSimpleName().equalsIgnoreCase("Result")) {
            out = out + "<p>Result List</p>"
                    + "<table>"
                    + "<tr><th>StudentId</th><th>CourseId</th><th>Mark1</th><th>Mark2</th></tr>";
            for (TableItem item : lst) {
                Result r = (Result) item;
                out = out + "<tr style=\"text-align: left\">"
                        + "<td>" + r.getStudentId() + "</td><td>" + r.getCourseId() + "</td>"
                        + "<td>" + r.getMark1() + "</td><td>" + r.getMark2() + "</td></tr>";
            }
            out = out + "</table>";
        }
        return out;
    }
    
    
    
}
