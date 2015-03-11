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
import java.util.regex.*;

@ManagedBean(name = "query_proc")
@SessionScoped
public class QueryProc implements Serializable {

    private final String REGEX1 = "SELECT(.*)FROM";
    private final String REGEX2 = "FROM(.*)";
    private int ColumnCount;
    private List TableName;
    private String inputVal;
    private List<String> lines;
    private String[] column_names_proces;
    private String table_name_process;
    private String DBname;


    private ArrayList<String> DBnames = new ArrayList<String>();

    SQL sql = new SQL();

    public void Input() {
        for (String line1 : lineSeparator(inputVal)) {

            SelectOptions(procesSelect(line1));
            extractTableName(procesFrom(line1));

            sql.generateSchema(column_names_proces, table_name_process, DBname);
        }
    }

    
    /*
         to chose which method to execute after,
         according to the command that follows Select command 
    */
    public String[] SelectOptions(List<String> stringsAfterSelect) {

        String str = "";
        for (String strL : stringsAfterSelect) {
            str += strL;
        }
        // split using spaces:if string count > 2 => there is extra command
        String[] query = str.split("\\s+");
        if (query.length > 2) {
            str = query[2];
        }

        // don't put space among columns in input query
        String[] columns = str.split(",");

        if (query[1].equals("ALL") || query[1].equals("*")) {
            column_names_proces = new String[1];
            column_names_proces[0] = "ALL";
        } else {
            column_names_proces = columns;
        }

        return null;
    }
    
    // REGEX ***************************************************
    // "SELECT(.*)FROM"
    public List procesSelect(String line) {

        System.out.println("line in regex " + line);
        Pattern p = Pattern.compile(REGEX1);
        Matcher m = p.matcher(line); // get a matcher object
        List<String> matchS = new ArrayList<>();
        
        while (m.find()) {
            for (int i = 1; i <= m.groupCount(); i++) {
                matchS.add(m.group(i));
            }
        }
        for (String a : matchS) {
            System.out.println("Select: " + a);
        }
        ColumnCount = matchS.size();
        return matchS;
    }

    // "FROM(.*)"
    public List procesFrom(String line) {
        Pattern p = Pattern.compile(REGEX2);
        Matcher m = p.matcher(line); // get a matcher object
        List<String> matchF = new ArrayList<>();

        while (m.find()) {
            for (int i = 1; i <= m.groupCount(); i++) {
                matchF.add(m.group(i));
            }
        }
        for (String a : matchF) {
            System.out.println("From: " + a);
        }
        TableName = matchF;
        return matchF;
    }
    // END REGEX ******************************************
    
    public void extractTableName(List<String> Tname) {
        table_name_process = Tname.get(0);
    }
    
    // SEPERATE LINES of query line by line
    private List<String> lineSeparator(String input) {
        lines = new ArrayList<String>();
        Scanner scanner = new Scanner(input);
        for (int i = 0; scanner.hasNextLine(); i++) {
            lines.add(scanner.nextLine());
        }
        scanner.close();
        return lines;
    }
    
    // FILL in tableDisplay.jp
    public List<TableProperty> TBList(String Table_Name, String dbName) {
        SQL query = new SQL();
        List<TableProperty> TBList = new ArrayList<>();
        
        if (column_names_proces[0].equals("ALL")){    
            TBList = query.getTableProperty(Table_Name, dbName);                    
        }else{
            TBList = query.sendSelectStament(column_names_proces, Table_Name, dbName);
        }
        
        return TBList;        
    }

    
    // FILL in dbToTableDisplay.jp
    public List<TableProperty> tableList(String Table_Name, String dbName) {
        SQL query = new SQL();
        List<TableProperty> TBList = new ArrayList<>();
         
        TBList = query.getTableProperty(Table_Name, dbName);                    
        
        return TBList;        
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
        return column_names_proces;
    }

    public void setColumnName(String[] ColumnName) {
        this.column_names_proces = ColumnName;
    }

    public void setDBname(String Dname) {
        // used in SQL class
        this.DBname = Dname;

        if (!DBnames.contains(Dname)) {
            DBnames.add(Dname);
        }
    }

    public ArrayList<String> getDBnames() {
        
//        if (DBnames.isEmpty()) {
//            DBnames.add("chose database name");        
//        }                
        return DBnames;
    }

    public String getDBname() {
        return DBname;
    }
    
    
    public void resetDB(String dbname, String action) {
        if (action.equals("reset")) {
//            SQL sql = new SQL();
            sql.resetDB(dbname);
        }
    }

    // to use in TableMap and DaatabaseDisplay.jsp
    public List<String> getTableNames(String dbname) {
        List<String> tableNames = sql.getTableNames(dbname);
        return tableNames;
    }
    

    public String getTable_name_process() {
        return table_name_process;
    }

    public void setTable_name_process(String table_name_process) {
        this.table_name_process = table_name_process;
    }
    
    public List getColumnNames(String DBName, String TableName){
                
        return null;
    }   
    
    
    // MODIFIY TYPEs of columns in tabel - TableDisplay.jsp
    public void alterDDL(String db_name, String tableName, Map<String, String> colType)
    {       
        for( Map.Entry<String, String> map : colType.entrySet() ){
            sql.AlterTable(tableName, map.getKey(), map.getValue(), db_name);            
        }        
    } 
    
    
    // FROM TO in TableMap.jsp
    public Map<String, String> getRelation(Map<String, String> relation)
    {
        Iterator<Map.Entry<String, String>> it = relation.entrySet().iterator();

        while (it.hasNext())
        {
          Map.Entry<String, String> entry = it.next();
          if (entry.getKey().equals("save"))
            it.remove();
        }
        
        return relation;       
    } 
}
