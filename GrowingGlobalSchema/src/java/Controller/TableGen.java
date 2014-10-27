package Controller;

import Model.SQL;
import Model.TableProperty;
import java.util.List;

/**
 *
 * @author Mikayil
 */
public class TableGen {

    private String table_name;
    private String[] column_name;

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

    /*
        to check if table exist already to add columns
        or if not exists to create table
    */
    public void generateSchema()
    {        
        SQL sql = new SQL();
        if (sql.isTabeleExists(table_name))
        {
            sql.addCol(column_name, table_name);
        }
        else
        {
            sql.createTable(column_name, table_name);
        }
    }
    
    public String[] getColumn_name() {
        return column_name;
    }

    public void setColumn_name(String[] column_name) {
        this.column_name = column_name;
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
