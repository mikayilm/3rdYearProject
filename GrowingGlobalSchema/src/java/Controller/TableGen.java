package Controller;

import Model.SQL;
import java.util.List;
import java.util.regex.*;
import java.util.*;

/**
 *
 * @author Mikayil
 */
public class TableGen {

    private String table_name;
    private String[] column_names ;
    private String column_name;
    private  final String REGEX2 = "FROM(.*)";
    
    public void setTableName(List<String> Tname) {
        table_name = Tname.get(0);
    }


    /*
      to chose which method to execute after,
      according to the command that follows Select command 
    */
    public String[] SelectOptions(List<String> stringsAfterSelect)
    {
        String str = "";
        for (String strL : stringsAfterSelect)
          str += strL;
        System.out.println("str" + str);
           
        // split using spaces:if string count > 2 => there is extra command
        String[] part1 = str.split("\\s+");
        if (part1.length > 2)
            str = part1[2];
                
        // don't put space among columns in input query
        String[] part2 = str.split(",");
                
        // statement following SELECT [DIS or ALL]
        if(part1[1].equals("DISTINCT"))
        {    
            column_names = part2;
            return column_names;
        }
        else if(part1[1].equals("ALL") || part1[1].equals("*"))
        {
            column_names = new String[1];
            column_names[0] = "ALL";
        }
        else
            column_names = part2;
        
        return null;
    }
    
    
    //>>>>>>>>>>>>>> might add this to SQL class  after debug
    /*
        if table already exists, add columns,
        if not create table
    */
    public void generateSchema()
    {        
        SQL sql = new SQL();
        if (sql.isTabeleExists(table_name))
            if (!column_names[0].equals("ALL"))
                sql.addCol(column_names, table_name);
        else
        {
            if (column_names[0].equals("ALL"))
                sql.createTable(table_name);
            else
                sql.createTable(column_names, table_name);
        }
    }
    
//    public void setColumn_name(String[] column_name) {
//        this.column_names = column_name;
//    }
//    
//    public String[] getColumn_name() {
//        return column_names;
//    }    
//    public void setColName(List<String> Cname) {
//        String str = "";
//        for (String strL : Cname)
//          str += strL;
//        String[] parts = str.split(",");
//        column_names = parts;
//    }

    /*   public static void main(String[] aa) {
     }*/
}
