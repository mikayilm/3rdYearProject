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

    private  final String REGEX1 = "SELECT(.*)FROM";
    private  final String REGEX2 = "FROM(.*)";
    private int ColumnCount;
    private String[] ColumnName;
    private String[] distinct_columns;
    private List TableName;
    private String inputVal;
    private List<String> lines;
    private String[] column_names_proces ;
    private String table_name_process;
    private String DBname;
    private ArrayList<String> DBnames = new ArrayList<String>();

    
    public void Input()
    {
        for(String line1 : lineSeparator(inputVal))
        {
            SQL sql = new SQL();             
            SelectOptions(procesSelect(line1));
            extractTableName(procesFrom(line1));
            sql.generateSchema(column_names_proces, table_name_process, DBname);
        }
    }
   
    // separates the multiple lines of query line by line
    private List<String> lineSeparator(String input)
    {   
        lines = new ArrayList<String>();
        Scanner scanner = new Scanner(input);
        for(int i=0; scanner.hasNextLine(); i++)   
            lines.add(scanner.nextLine());

        scanner.close();
        
        return lines;
    }
 
    // processes the part of the input between the Strings SELECT and FROM
    public  List procesSelect(String line)
    {
        distinct_columns = null;
        // REGEX START
        System.out.println("line in regex " + line);
        Pattern p = Pattern.compile(REGEX1);
        Matcher m = p.matcher(line); // get a matcher object
        
        List<String> matchS = new ArrayList<>();
        while (m.find()){
            for (int i = 1; i <= m.groupCount(); i++)
            { matchS.add(m.group(i)); }
        }       
        for(String a : matchS)
        { System.out.println("S: "+a); }    
        ColumnCount = matchS.size();
        return matchS;
        // REGEX END
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
            System.out.println("in distinct");
            column_names_proces  = part2;
            distinct_columns = column_names_proces ;
//            return column_names_proces ;
        }
        else if(part1[1].equals("ALL") || part1[1].equals("*"))
        {
            column_names_proces  = new String[1];
            column_names_proces [0] = "ALL";
        }
        else
            column_names_proces  = part2;
        
        return null;
    }
    
    /*
        processes the part of the input that starts after the Strind FROM
    */
    public  List procesFrom(String line)
    {
        Pattern p = Pattern.compile(REGEX2);
        Matcher m = p.matcher(line); // get a matcher object

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

    public void extractTableName(List<String> Tname) {
        table_name_process = Tname.get(0);
    }
    
    public List<TableProperty> TBList(String Table_Name, String dbName)
    {    
        System.out.println("TBList");
        SQL query = new SQL();
        List<TableProperty> TBList = new ArrayList<>();
        TBList = query.getTableProperty(Table_Name, dbName);
        
        
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
    
//    public void setInput(String inputValue){
//        
//        System.out.println("input value: "+inputValue);
//        inputVal = inputValue;
//        return "./TableDisplay.jsp";
//    }
    
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
      
    public void setDBname(String Dname){
        // used in SQL class
        this.DBname = Dname;

        if (!DBnames.contains(Dname))
            DBnames.add(Dname); 
        
        int i=0;
        for(String db : DBnames)
        {
           System.out.println("DBnames.get("+i+") " + db);
           i++;
        }        
    }
        
    public ArrayList<String> getDBnames() {
        if (DBnames.isEmpty())
            DBnames.add("chose database name");
        return DBnames;
    }
    
    public void resetDB(String dbname, String action)
    {   
        if (action.equals("Reset"))
        {
            SQL sql = new SQL();
            sql.resetDB(dbname);
        }
    }    
}
