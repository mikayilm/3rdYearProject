/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.SQL;
import java.util.*;
import java.util.Map.*;
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
    {
        
    }
    
    public void alterDDL(String tableName, String[] colName, Map<String, String> colType)
    {
        SQL sql = new SQL();

        for( Map.Entry<String, String> ee : colType.entrySet() ){
            System.out.println(ee.getKey() + "   " + ee.getValue());
            sql.AlterTable(tableName, ee.getKey(), ee.getValue());
            
        }
        
    }
    
}
