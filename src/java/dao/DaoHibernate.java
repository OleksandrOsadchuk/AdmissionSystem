/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import beans.Course;
import beans.Result;
import beans.Student;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import beans.TableItem;
import java.util.ArrayList;
import org.hibernate.Query;

/**
 *
 * @author pear
 */
public class DaoHibernate {

    private final SessionFactory ssnFact;
    private Session ssn;
    private List rList;
    

    public DaoHibernate() {

        Configuration conf = new Configuration().configure("cfg/hibernate.cfg.xml");
        StandardServiceRegistryBuilder ssrb
                = new StandardServiceRegistryBuilder().applySettings(conf.getProperties());
        ssnFact = conf.buildSessionFactory(ssrb.build());
        

    }

    public List<TableItem> selectAll(String tabName) {
        try {
            ssn = ssnFact.openSession();
            ssn.getTransaction().begin();
            Query query=ssn.createQuery("from "+tabName+" ORDER BY "+tabName+"id");
            if(tabName.equalsIgnoreCase("Result")) 
                query=ssn.createQuery("from "+tabName+" ORDER BY studentid, courseid");
            rList = query.list();
            ssn.getTransaction().commit();
            return rList;

        } catch (Exception e) {
            System.out.println("problem:" + e);
        } finally {
            if (ssn.isOpen()) {
                ssn.close();
            }
        }
        return null;
    }

    public List<TableItem> selectId(TableItem itm) {
        try {
            ssn = ssnFact.openSession();
            ssn.getTransaction().begin();
            rList=new ArrayList();
            
            if(itm.getClass().getSimpleName().equalsIgnoreCase("Result")){
                Result re=(Result)itm;
                Query query=ssn.createQuery("FROM Result WHERE studentid = " +
                        re.getStudentId()+" AND courseid = "+re.getCourseId());
                rList = query.list();
            }else{
                Query query=ssn.createQuery("from "+itm.getClass().getSimpleName()+
                        " WHERE "+itm.getClass().getSimpleName()+"id = "+itm.getId());
                rList = query.list();
            }
            ssn.getTransaction().commit();
            return rList;
        } catch (Exception e) {
            System.out.println("" + e);
        } finally {
            ssn.close();
        }
        return null;
    }

    public int insert(TableItem item) {
        if(!selectId(item).isEmpty()) return 0; //already exists
        try {
            ssn = ssnFact.openSession();
            ssn.getTransaction().begin();
            if (item.getClass().getSimpleName().equalsIgnoreCase("Student")) {
                item = (Student) item;
            } else if (item.getClass().getSimpleName().equalsIgnoreCase("Course")) {
                item = (Course) item;
            } else if (item.getClass().getSimpleName().equalsIgnoreCase("Result")) {
                item = (Result) item;
            }
            ssn.save(item);
            ssn.getTransaction().commit();

        } catch (HibernateException he) {
            if (ssn.getTransaction() != null) {
                ssn.getTransaction().rollback();
            }
            System.out.println("hibernate exc:" + he);
            return 0;
        } finally {
            if (ssn.isOpen()) {
                ssn.close();
            }
        }
        return 1;
    }
 
    public int delete(TableItem item) {
        
        List ls = selectId(item);
        if(ls.isEmpty()) return 0; // nothing to delete
        
        try {
            ssn = ssnFact.openSession();
            ssn.getTransaction().begin();
            ssn.delete(ls.get(0));
            ssn.getTransaction().commit();
            return 1;

        } catch (HibernateException he) {
            if (ssn.getTransaction() != null) {
                ssn.getTransaction().rollback();
            }
            System.out.println("hibernate exc:" + he);
            return 0;
        } finally {
            if (ssn.isOpen()) {
                ssn.close();
            }
        }
    }

    public int update(TableItem item) {
        
        List ls = selectId(item);
        if(ls.isEmpty()) return 0; // nothing to update
            
        try {
            ssn = ssnFact.openSession();
            ssn.getTransaction().begin();
            if (item.getClass().getSimpleName().equalsIgnoreCase("Student")) {
                item = (Student) item;
            } else if (item.getClass().getSimpleName().equalsIgnoreCase("Course")) {
                item = (Course) item;
            } else if (item.getClass().getSimpleName().equalsIgnoreCase("Result")) {
                item = (Result) item;
                Result oldRes = (Result)ls.get(0); //to get hidden 'rID' for the result:
                item.setId(oldRes.getId());
            }
            ssn.update(item);
            ssn.getTransaction().commit();
            return 1;

        } catch (HibernateException he) {
            if (ssn.getTransaction() != null) {
                ssn.getTransaction().rollback();
            }
            System.out.println("hibernate exc:" + he);
            return 0;
        } finally {
            if (ssn.isOpen()) {
                ssn.close();
            }
        }
    }

}
