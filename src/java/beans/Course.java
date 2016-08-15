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
public class Course implements TableItem{
    
    private int id;
    private String name;
    
    
    public Course (int id, String name)
    {
        this.id = id;
        this.name = name;
    }
    
    public Course()
    {
        this.id = 0;
        this.name = "";
    }

    @Override
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
    
    
}
