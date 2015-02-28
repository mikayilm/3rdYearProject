package Controller;

import Model.SQL;
import java.util.*;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Mikayil
 */
@ManagedBean(name = "alter_table")
@SessionScoped
public class AlterTable {
    
    public AlterTable()
    {  }
    
    public void alterDDL(String DBname, String tableName, String[] colName, Map<String, String> colType)
    {
        SQL sql = new SQL();

        for( Map.Entry<String, String> ee : colType.entrySet() ){
            sql.AlterTable(tableName, ee.getKey(), ee.getValue(), DBname);            
        }        
    }    
}
