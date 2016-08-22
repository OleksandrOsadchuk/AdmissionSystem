/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import beans.Course;
import beans.Result;
import beans.Student;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import beans.TableItem;
import dao.DaoHibernate;
import dao.DaoJDBC;

/**
 *
 * @author pear
 */
@WebServlet(name = "AppServlet", urlPatterns = {"/AppServlet"})
public class ControllerServlet extends HttpServlet {

    TableItem inputItem;
    //DaoJDBC dao;
    DaoHibernate dao;
    String msg, tabName, op, url;
    List<TableItem> itemList;
    List l;
    int lastIdSt, lastIdCs, lastIdRe, lastId, editId, r;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {

        if (dao == null) {
            dao = new DaoHibernate(); //DaoJDBC(); //use hibernet this time 
        }

        try {
            op = request.getParameter("operation");
            tabName = request.getParameter("tab");

            switch (op) {

                default:
                    msg = "Welcome to Student Admission";
                    url = "/index.jsp";
                    request.setAttribute("message", msg);
                    break;

                case "list":
                    itemList = dao.selectAll(tabName);
                    if (itemList.isEmpty()) {
                        msg = "no" + tabName + "found";
                    } else {
                        l = this.processItemList(itemList);
                    }

                    msg = "List of  " + tabName;
                    if (tabName.equalsIgnoreCase("result")) {
                        msg = "Examination results, students have 2 exams per course.";
                    }
                    url = "/index.jsp";
                    request.setAttribute("list", l);
                    request.setAttribute("message", msg);
                    request.setAttribute("tab", tabName);
                    break;

                case "findId":
                    inputItem = this.setItemIdTabname(request);
                    if ((itemList = dao.selectId(inputItem)).isEmpty()) {
                        msg = "nothing found";
                        l = null;
                    } else {
                        msg = "found " + tabName + " entries: " + itemList.size();
                        l = this.processItemList(itemList);
                    }
                    url = "/index.jsp";
                    request.setAttribute("list", l);
                    request.setAttribute("message", msg);
                    request.setAttribute("tab", tabName);
                    break;

                case "delete":
                    inputItem = this.setItemIdTabname(request);
                    r = dao.delete(inputItem);
                    if (r < 1) {
                        msg = "wasn't deleted...";
                    } else {
                        msg = "deleted " + r + " position(s)";
                    }

                    itemList = dao.selectAll(tabName);
                    l = this.processItemList(itemList);
                    request.setAttribute("list", l);

                    url = "/index.jsp";
                    request.setAttribute("message", msg);
                    request.setAttribute("tab", tabName);
                    break;

                case "add":
                    inputItem = validateInputItem(request);
                    if (inputItem != null) {
                        r = dao.insert(inputItem);
                        if (r < 1) {
                            msg = "Can't add this postion: already exist or incorrect id.";
                        } else {
                            msg = "Added positions: " + r;
                        }

                        l = this.processItemList(dao.selectAll(tabName));
                        request.setAttribute("list", l);
                        url = "/index.jsp";
                    } else {
                        request.setAttribute("submitLabel", "Add");
                        request.setAttribute("operationValue", "add");
                        request.setAttribute("idValue", (lastId + 1));
                        url = "/form.jsp";
                    }
                    request.setAttribute("message", msg);
                    request.setAttribute("tab", tabName);
                    break;

                case "update":
                    inputItem = validateInputItem(request);
                    if (inputItem != null) {
                        r = dao.update(inputItem);
                        if (r < 1) {
                            msg = "Can't update this postion: not exist or incorrect id..";
                        } else {
                            msg = "Updated positions: " + r;
                        }

                        l = this.processItemList(dao.selectAll(tabName));
                        request.setAttribute("list", l);
                        url = "/index.jsp";
                    } else {
                        inputItem = this.setItemIdTabname(request);
                        List updList = l;

                        request.setAttribute("list", updList);
                        request.setAttribute("tab", tabName);
                        request.setAttribute("submitLabel", "Save changes");
                        request.setAttribute("operationValue", "update");
                        request.setAttribute("idValue", editId);
                        url = "/form.jsp";
                    }

                    request.setAttribute("message", msg);
                    request.setAttribute("tab", tabName);
                    break;

                case "addForm":
                    request.setAttribute("tab", tabName);
                    msg = "Please, fill the form and click 'add'.";
                    if (tabName.equals("Result")) {
                        msg = "Fill the form and click 'add'."
                                + "<br>You can add a record only for a Student and Course IDs which already exist";
                    }
                    request.setAttribute("message", msg);
                    request.setAttribute("submitLabel", "Add");
                    request.setAttribute("operationValue", "add");
                    request.setAttribute("idValue", (lastId + 1));
                    url = "/form.jsp";
                    break;

                case "editForm":
                    inputItem = this.setItemIdTabname(request);
                    l = this.processItemList(dao.selectId(inputItem));
                    request.setAttribute("list", l);

                    request.setAttribute("tab", tabName);
                    request.setAttribute("message", "Please, edit values and click 'save' . ");
                    request.setAttribute("submitLabel", "Save changes");
                    request.setAttribute("operationValue", "update");
                    request.setAttribute("idValue", editId);
                    url = "/form.jsp";
                    break;

                case "email":
                    String mailAddr = request.getParameter("mailAddr");
                    if (mailAddr.matches("[a-z0-9](\\.?[a-z0-9_-]){0,}@[a-z0-9-]+\\.([a-z]{1,6}\\.)?[a-z]{2,6}")) {
                        EmailSender sm = new EmailSender();
                        OutputConverter outConv = new OutputConverter();
                        String content = outConv.htmlOut(l);
                        msg = sm.sendMail(mailAddr, content);
                        outConv=null;
                    } else {
                        msg = "incorrect email address!";
                    }
                    url = "/index.jsp";
                    request.setAttribute("list", l);
                    request.setAttribute("message", msg);
                    request.setAttribute("tab", tabName);
                    break;
            }

        } catch (Exception ee) {
            System.out.println("exc: " + ee);
            msg = ee + "";
            url = "/404.jsp";
            request.setAttribute("message", msg);
        } finally {
            ;
        }
        this.getServletContext().getRequestDispatcher(url).forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private List processItemList(List<TableItem> itemList) {
        List lst = new ArrayList();
        if (tabName.equalsIgnoreCase("Student")) {

            for (TableItem i : itemList) {
                Student st = (Student) i;
                lst.add(st);
                if (lastIdSt < st.getId()) {
                    lastIdSt = st.getId();
                }
                lastId = lastIdSt;
                editId = st.getId();
            }

        } else if (tabName.equalsIgnoreCase("Course")) {

            for (TableItem i : itemList) {
                Course cs = (Course) i;
                lst.add(cs);
                if (lastIdCs < cs.getId()) {
                    lastIdCs = cs.getId();
                }
                lastId = lastIdCs;
                editId = cs.getId();
            }

        } else if (tabName.equalsIgnoreCase("Result")) {

            for (TableItem i : itemList) {
                Result rr = (Result) i;
                lst.add(rr);
                if (lastIdRe < rr.getStudentId()) {
                    lastIdRe = rr.getStudentId();
                }
                lastId = lastIdRe;
                editId = rr.getStudentId();
            }

        }
        return lst;
    }

    private TableItem setItemIdTabname(HttpServletRequest request) {
        tabName = request.getParameter("tab");

        if (tabName.equalsIgnoreCase("Student")) {
            inputItem = new Student();
            if(request.getParameter("id")!="")inputItem.setId(Integer.parseInt(request.getParameter("id")));
            else inputItem.setId(0);
        } else if (tabName.equalsIgnoreCase("Course")) {
            inputItem = new Course();
            if(request.getParameter("id")!="")inputItem.setId(Integer.parseInt(request.getParameter("id")));
            else inputItem.setId(0);
        } else if (tabName.equalsIgnoreCase("Result")) {
            Result re = new Result();
            if(request.getParameter("sid")!="")
            re.setStudentId(Integer.parseInt(request.getParameter("sid")));
            else re.setStudentId(0);
            if(request.getParameter("cid")!="")
            re.setCourseId(Integer.parseInt(request.getParameter("cid")));
            else re.setCourseId(0);
            inputItem = re;
        }
        return inputItem;
    }

    private TableItem validateInputItem(HttpServletRequest request) {

        tabName = request.getParameter("tab");

        if (tabName.equalsIgnoreCase("Student")) {
            if (!request.getParameter("id").matches("\\d+")) {
                msg = "id must be a number!";
            } else if (request.getParameter("fn").length() < 3) {
                msg = "first name too short!";
            } else if (request.getParameter("ln").length() < 3) {
                msg = "last name too short!";
            } else if (!request.getParameter("gender").equalsIgnoreCase("M")
                    && !request.getParameter("gender").equalsIgnoreCase("F")) {
                msg = "gender must be M or F !";
            } else if (!request.getParameter("sdate").matches("(199|200|201)[1-9]-(0[1-9]|1[0-2])-(0[1-9]|1[0-9]|2[0-9]|30)")) {
                msg = "date must be valid and in format YYYY-MM-DD !";
            } else {
                Student s = new Student();
                s.setId(Integer.parseInt(request.getParameter("id")));
                s.setFirstName(request.getParameter("fn"));
                s.setLastName(request.getParameter("ln"));
                s.setGender(request.getParameter("gender"));
                s.setStartDate(request.getParameter("sdate"));
                inputItem = s;
                msg = "ok";
                return inputItem;
            }

        } else if (tabName.equalsIgnoreCase("Course")) {
            if (!request.getParameter("id").matches("\\d+")) {
                msg = "id must be a number!";
            } else if (request.getParameter("cn").length() < 3) {
                msg = "course name too short!";
            } else {
                Course cs = new Course();
                cs.setId(Integer.parseInt(request.getParameter("id")));
                cs.setName(request.getParameter("cn"));
                inputItem = cs;
                msg = "ok";
                return inputItem;
            }

        } else if (tabName.equalsIgnoreCase("Result")) {
            if (!(request.getParameter("sid").matches("\\d+")
                    && request.getParameter("cid").matches("\\d+"))) {
                msg = "id must be a number!";
            } else if (!(request.getParameter("m1").matches("\\d{2}")
                    && request.getParameter("m2").matches("\\d{2}"))) {
                msg = "mark must be between 01..99 !";
            } else {
                Result re = new Result();
                re.setStudentId(Integer.parseInt(request.getParameter("sid")));
                re.setCourseId(Integer.parseInt(request.getParameter("cid")));
                re.setMark1(Integer.parseInt(request.getParameter("m1")));
                re.setMark2(Integer.parseInt(request.getParameter("m2")));
                inputItem = re;
                msg = "";
                return inputItem;
            }
        }
        return null;
    }

}
