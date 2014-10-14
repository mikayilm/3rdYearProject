/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;

/**
 *
 * @author Mikayil
 */
public class TableGen {

    private Connection conn = null;
    private String table_name;
    private String column_name;
    private String createString;

    public TableGen() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sample_db?user=root&password=123456");
        } catch (Exception ex) {
            System.out.println("conn exception: " + ex.getMessage());
        } finally {
        }
    }

    public void setTableName(List<String> Tname) {
        table_name = Tname.get(0);
    }

    public void setColName(List<String> Cname) {
        column_name = Cname.get(0);
    }

    public String createStringSelect()
    {
        return createString = "create table " + table_name
                    + "(id int, "
                    + column_name + " varchar(100), "
                    + "primary key(id)) " ;
    }
    
    public void createTable() {
        
        createString = createStringSelect();

        try {
            Statement st = conn.createStatement();

            int a = st.executeUpdate(createString);
        } catch (Exception ex) {
            System.out.println("createTable: "+ex.getMessage());
          
        }

    }

    /*   public static void main(String[] aa) {
     try {
     //            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sample_db?user=root&password=123456");
     Statement st = conn.createStatement();

     int a = st.executeUpdate("create table " + table_name
     + "(id int, "
     + column_name + " varchar(100), "
     + "primary key(id)) "
     );

     System.out.println("result: " + a);
     } catch (Exception ex) {
     System.out.println("conn exception: " + ex.getMessage());
     } finally {
     }
     }*/
}
