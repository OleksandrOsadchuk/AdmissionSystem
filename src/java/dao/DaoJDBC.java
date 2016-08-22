/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import beans.Course;
import beans.Result;
import beans.Student;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import beans.TableItem;

/**
 *
 * @author pear
 */
public class DaoJDBC {

    private Connection con = null;
    private Statement st = null;

    private List<TableItem> retList; //return list has to handle different types
    String url = "jdbc:mysql://localhost/admission";
    String scheme = "root";
    String passw = "1234";
    String query;

    public Connection openConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection(url, scheme, passw);
        return con;
    }

    public void closeConnection() throws SQLException, ClassNotFoundException {
        if (con != null) {
            con.close();
        }
    }

    public List selectAll(String tabName) throws SQLException, ClassNotFoundException {

        retList = new ArrayList();
        st = openConnection().createStatement();

        try {
            query = "SELECT * FROM " + tabName;
            if (tabName.equalsIgnoreCase("Result")) {
                query += " ORDER BY studentid, courseid";
            } else {
                query += " ORDER BY " + tabName + "id";
            }
            ResultSet rSet = st.executeQuery(query);

            if (tabName.equalsIgnoreCase("student")) {
                while (rSet.next()) {
                    Student s = new Student();
                    s.setId(rSet.getInt("studentid"));
                    s.setFirstName(rSet.getString("firstname"));
                    s.setLastName(rSet.getString("lastname"));
                    s.setGender(rSet.getString("gender"));
                    s.setStartDate(rSet.getString("startdate"));
                    retList.add(s);
                }
            }
            if (tabName.equalsIgnoreCase("course")) {
                while (rSet.next()) {
                    Course cs = new Course();
                    cs.setId(rSet.getInt("courseid"));
                    cs.setName(rSet.getString("coursename"));
                    retList.add(cs);
                }
            }
            if (tabName.equalsIgnoreCase("result")) {
                while (rSet.next()) {
                    Result r = new Result();
                    r.setCourseId(rSet.getInt("courseid"));
                    r.setStudentId(rSet.getInt("studentid"));
                    r.setMark1(rSet.getInt("marks1"));
                    r.setMark2(rSet.getInt("marks2"));
                    retList.add(r);
                }
            }
        } finally {
            // st.close();
            closeConnection();
        }
        return retList;

    }

    public List selectId(TableItem itm) throws SQLException, ClassNotFoundException {
        retList = new ArrayList();
        st = openConnection().createStatement();

        try {
            query = "SELECT * FROM " + itm.getClass().getSimpleName()
                    + " WHERE " + itm.getClass().getSimpleName() + "id"
                    + " = " + itm.getId();
            if (itm.getClass().getSimpleName().equalsIgnoreCase("result")) {
                Result re = (Result) itm;
                query = "SELECT * FROM result WHERE studentid = " + re.getStudentId()
                        + " AND courseid = " + re.getCourseId();
            }

            ResultSet rSet = st.executeQuery(query);

            if (itm.getClass().getSimpleName().equalsIgnoreCase("student")) {
                while (rSet.next()) {
                    Student s = new Student();
                    s.setId(rSet.getInt("studentid"));
                    s.setFirstName(rSet.getString("firstname"));
                    s.setLastName(rSet.getString("lastname"));
                    s.setGender(rSet.getString("gender"));
                    s.setStartDate(rSet.getString("startdate"));
                    retList.add(s);
                }
            }
            if (itm.getClass().getSimpleName().equalsIgnoreCase("course")) {
                while (rSet.next()) {
                    Course cs = new Course();
                    cs.setId(rSet.getInt("courseid"));
                    cs.setName(rSet.getString("coursename"));
                    retList.add(cs);
                }
            }
            if (itm.getClass().getSimpleName().equalsIgnoreCase("result")) {
                while (rSet.next()) {
                    Result r = new Result();
                    r.setCourseId(rSet.getInt("courseid"));
                    r.setStudentId(rSet.getInt("studentid"));
                    r.setMark1(rSet.getInt("marks1"));
                    r.setMark2(rSet.getInt("marks2"));
                    retList.add(r);
                }
            }
        } finally {
            // st.close();
            closeConnection();
        }
        return retList;

    }

    public int insert(TableItem itm) throws SQLException, ClassNotFoundException {
        st = openConnection().createStatement();
        String query = "";
        
        if(selectId(itm).isEmpty()) return 0; //already exists, do not add

        try {
            if (itm.getClass().getSimpleName().equalsIgnoreCase("student")) {
                Student stud = (Student) itm;
                query = "INSERT INTO student"
                        + " VALUES(" + stud.getId() + ",'" + stud.getFirstName() + "','" + stud.getLastName()
                        + "','" + stud.getGender() + "','" + stud.getStartDate() + "')";
            }
            if (itm.getClass().getSimpleName().equalsIgnoreCase("course")) {
                Course cs = (Course) itm;
                query = "INSERT INTO course"
                        + " VALUES(" + cs.getId() + ",'" + cs.getName() + "')";
            }
            if (itm.getClass().getSimpleName().equalsIgnoreCase("result")) {
                Result r = (Result) itm;
                query = "INSERT INTO result"
                        + " VALUES(NULL, " + r.getStudentId() + "," + r.getCourseId() + "," + r.getMark1()
                        + "," + r.getMark2() + ")";
            }
            return st.executeUpdate(query);

        } finally {
            closeConnection();
        }

    }

    public int update(TableItem itm) throws SQLException, ClassNotFoundException {
        String query = "";
        st = openConnection().createStatement();
        try {
            if (itm.getClass().getSimpleName().equalsIgnoreCase("student")) {
                Student stud = (Student) itm;
                query = "UPDATE student"
                        + " SET firstname='" + stud.getFirstName() + "',lastname='" + stud.getLastName()
                        + "',gender='" + stud.getGender() + "',startdate='" + stud.getStartDate() + "' "
                        + "WHERE studentid=" + stud.getId();
            }
            if (itm.getClass().getSimpleName().equalsIgnoreCase("course")) {
                Course cs = (Course) itm;
                query = "UPDATE course"
                        + " SET coursename = '" + cs.getName() + "' WHERE courseid = " + cs.getId();
            }
            if (itm.getClass().getSimpleName().equalsIgnoreCase("result")) {
                Result r = (Result) itm;
                query = "UPDATE result"
                        + " SET marks1=" + r.getMark1() + ", marks2=" + r.getMark2()
                        + " WHERE studentid=" + r.getStudentId() + " AND courseid="
                        + r.getCourseId();
            }
            return st.executeUpdate(query);
        } finally {
            closeConnection();
        }
    }

    public int delete(TableItem itm) throws SQLException, ClassNotFoundException {
        st = openConnection().createStatement();
        try {
            query = "DELETE FROM " + itm.getClass().getSimpleName()
                    + " WHERE " + itm.getClass().getSimpleName() + "id"
                    + " = " + itm.getId();
            if (itm.getClass().getSimpleName().equalsIgnoreCase("result")) {
                Result re = (Result) itm;
                query = "DELETE FROM result WHERE studentid = " + re.getStudentId()
                        + " AND courseid = " + re.getCourseId();
            }

            return st.executeUpdate(query);

        } finally {
            closeConnection();
        }

    }

}
