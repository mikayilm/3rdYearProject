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
    private String[] column_name;
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
        String str = "";
        for (String strL : Cname)
          str += strL;
        String[] parts = str.split(",");
        column_name = parts;
    }

    public String createStringSelect()
    {
        String columnNames = "";
        if (column_name.length > 1)
        {
            for (String column_name1 : column_name) {
                columnNames += column_name1 + " varchar(100), ";
            }
            System.out.println("column names " + columnNames);
        }
        else
            columnNames = column_name[0] + " varchar(100), ";
        
        return createString = "create table " + table_name
                    + "(id int, "
                    + columnNames
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

    public String[] getColumn_name() {
        return column_name;
    }

    public void setColumn_name(String[] column_name) {
        this.column_name = column_name;
    }
}
