/**
 *
 * @author Mikayil
 */
package Controller;

import Model.SQL;
import Model.TableProperty;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.*;

@ManagedBean(name = "query_proc")
@SessionScoped
public class QueryProc implements Serializable {

    private  final String REGEX1 = "SELECT(.*)FROM";
    private  final String REGEX2 = "FROM(.*)";
    private int ColumnCount;
    private String[] ColumnName;
    private String[] distinct_columns;
    private List TableName;
    private String inputVal;

    
    
    public void Input()
    {
        TableGen tb = new TableGen();        
//        tb.setColName(procesSelect());      
        tb.SelectOptions(procesSelect());
//        ColumnName = tb.getColumn_name();
//        TableName = procesFrom();
        tb.setTableName(procesFrom());    
        tb.generateSchema();
    }
   
    /*
        this method processes the part of the input between the Strings SELECT and FROM
    */
    public  List procesSelect()
    {
        Pattern p = Pattern.compile(REGEX1);
        Matcher m = p.matcher(inputVal); // get a matcher object

        List<String> matchS = new ArrayList<>();

        while (m.find()){
            for (int i = 1; i <= m.groupCount(); i++)
            { matchS.add(m.group(i)); }
        }
        
        for(String a : matchS)
        { System.out.println("S: "+a); }
        
        ColumnCount = matchS.size();
        return matchS;
    }
    
    /*
        processes the part of the input that starts after the Strind FROM
    */
    public  List procesFrom()
    {
        Pattern p = Pattern.compile(REGEX2);
        Matcher m = p.matcher(inputVal); // get a matcher object

        List<String> matchF = new ArrayList<>();

        while (m.find()) {
            for (int i = 1; i <= m.groupCount(); i++) 
            { matchF.add(m.group(i)); }
        }
        
        for(String a : matchF)
        { System.out.println("F: "+a); }

        TableName = matchF;
        return matchF;
    }

    
    public List<TableProperty> TBList(String Table_Name)
    {    
        System.out.println("how many times");
        SQL query = new SQL();
        List<TableProperty> TBList = new ArrayList<>();
        TBList = query.getTableProperty(Table_Name);
        
        
        TableGen tb = new TableGen();
        distinct_columns = tb.SelectOptions(procesSelect());
        if(distinct_columns != null)
        {        
            Iterator<TableProperty> iter = TBList.iterator();
            int i = 0;
            while(iter.hasNext())
            {
                TableProperty TB = iter.next();
                if(!distinct_columns[i].trim().equals(TB.getFIELD().trim()))
                {
                    iter.remove();
                    System.out.println("TBList.size() " + TBList.size());
                }
                if (i < distinct_columns.length - 1)
                    i++;
            }
        }
        return TBList;
        
    }
    
    public String getURL(String inputValue){
        
        System.out.println("input value: "+inputValue);
        inputVal = inputValue;
        return "./TableDisplay.jsp";
        
    }
    
    public String getInputVal() {
        return inputVal;
    }

    public void setInputVal(String inputVal) {
        this.inputVal = inputVal;
    }

    public int getColumnCount() {
        return ColumnCount;
    }

    public void setColumnCount(int ColumnCount) {
        this.ColumnCount = ColumnCount;
    }

    public List getTableName() {
        return TableName;
    }

    public void setTableName(List TableName) {
        this.TableName = TableName;
    }
    public String[] getColumnName() {
        return ColumnName;
    }

    public void setColumnName(String[] ColumnName) {
        this.ColumnName = ColumnName;
    }
    
    

    public static void main(String[] args) {
    }    
}
