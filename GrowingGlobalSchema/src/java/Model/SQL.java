package Model;

import Controller.QueryProc;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 *
 * @author Mikayil
 */
public class SQL {

    private Connection conn;
    private Statement st;
    private String dropString;
    private String alterString;
    private String createString;
    private String addColString;
    private List<String> ColumnNames;
    private StringBuilder AddString = null;
    private String DBname;

    private String[] column_names_proces ;
    private String table_name_process;
    //private ArrayList<String> DBnames;
    private List<String> tableNames;
    
    public SQL() {

    }

    private static final Logger logger = Logger.getLogger(SQL.class);

    public void logCreate(String logInfo) {
        logger.info("Create \\ DDL : " + logInfo);
    }

    public void logAlter(String logInfo) {
        logger.info("Alter  \\ DDL : " + logInfo);
    }
    
    public void logDrop(String logInfo) {
        logger.info("Drop  \\ DDL : " + logInfo);
    }

    public void logAddCol(String logInfo) {
        logger.info("AddCol \\ DDL : " + logInfo);
    }

//    public ArrayList<String> getDBnames() {
//        return DBnames;
//    }

    public void setDBname(String DBname) {
        this.DBname = DBname;
    }
    
    public String getDBname() {
        return DBname;
    }
    
    public boolean connect() {

        boolean ConRes = false;

        try {
            
            Class.forName("org.h2.Driver");
            System.out.println("DBname from connect: " + DBname);
            
            String url = "jdbc:h2:tcp://localhost/~/" + DBname;
            System.out.println("database " + url);
            
            conn = DriverManager.getConnection(url, "sa", "");
            st = conn.createStatement();
//            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sample_db?user=root&password=123456");
//            st = conn.createStatement();
            ConRes = true;
        } catch (Exception ex) {
            System.out.println("connect: " + ex.getMessage());
        }

        return ConRes;

    }

    public boolean Disconnect() {

        boolean DisConnRes = false;
        try {

            st.close();
            conn.close();
            DisConnRes = true;
        } catch (Exception ex) {
            System.out.println("Disconnect: " + ex.getMessage());
        }
        return DisConnRes;
    }

    
    public void generateSchema(String[] colName, String tabName, String dbName)
    {   
        System.out.println("ddddd " + dbName);
        table_name_process = tabName;
        column_names_proces = colName;
        setDBname(dbName);     

        System.out.println("gen sch");
        SQL sql = new SQL();
        if (sql.isTabeleExists(table_name_process, dbName))
        {
            if (!column_names_proces [0].equals("ALL"))
                sql.addCol(column_names_proces , table_name_process, dbName);
        }
        else
        {
            System.out.println("gen sch else");
            if (column_names_proces [0].equals("ALL"))
            {
                System.out.println("gen sch all");
                sql.createTable(table_name_process, dbName);
            }
            else
            {
                System.out.println("generate tab");
                sql.createTable(column_names_proces , table_name_process, dbName);
            }
        }
    }
    
    public String createStringSelect(String[] column_name, String table_name) {

        StringBuilder columnNames = new StringBuilder();
        for (String column_name1 : column_name) {
            columnNames.append(column_name1.trim()).append(" varchar(100),");
        }

        return createString = "create table " + table_name
                + "(id int, "
                + columnNames
                + "primary key(id)) ";
    }

    public Boolean createTable(String[] column_name, String table_name, String dbName) {

        Boolean exist = false;
        createString = createStringSelect(column_name, table_name);
        logCreate(createString);
        setDBname(dbName);
        connect();
        try {
            int a = st.executeUpdate(createString);
        } catch (Exception ex) {
            System.out.println("createTable: " + ex.getMessage());
            exist = true;
        } finally {
            Disconnect();
        }
        return exist;
    }
    
    public Boolean createTable(String table_name, String dbName) {

        Boolean exist = false;
        createString = "create table " + table_name;
        logCreate(createString);
        setDBname(dbName);
        connect();
        try {
            int a = st.executeUpdate(createString);
        } catch (Exception ex) {
            System.out.println("createTable: " + ex.getMessage());
            exist = true;
        } finally {
            Disconnect();
        }
        return exist;
    }

    public List<TableProperty> getTableProperty(String TableName, String dbName) {

        setDBname(dbName);
        connect();
        List<TableProperty> TBList = new ArrayList<TableProperty>();
        ColumnNames = new ArrayList<String>();
        try {

            ResultSet rs = st.executeQuery("show columns from " + TableName);
            while (rs.next()) {
                if (rs.isFirst())
                    continue;

                ColumnNames.add(rs.getString("field"));
                TableProperty TB = new TableProperty();
                TB.setFIELD(rs.getString("field"));
                TB.setTYPE(rs.getString("type"));

                TBList.add(TB);
            }

        } catch (Exception ex) {
            System.out.println("getTableProperty: " + ex.getMessage());
        }

        return TBList;
    }
    
    public List<String> getTableNames (String dbName)
    {
        setDBname(dbName);
        connect();
        tableNames = new ArrayList<String>();

        try {
            ResultSet rs = st.executeQuery("show tables");
            while (rs.next())
                tableNames.add(rs.getString("TABLE_NAME"));                                             
        } catch (Exception ex) {
            System.out.println("getTableProperty: " + ex.getMessage());
        }
        return tableNames;
    }

    public void AlterTable(String tableName, String colName, String colType, String dbName) {
        
        setDBname(dbName);
        alterString = "alter table " + getDBname() + "." + tableName + " modify " + colName.trim() + " " + colType;
        logAlter(alterString);
        connect();

        try {
            st.executeUpdate(alterString);
        } catch (Exception ex) {
            System.out.println("AlterTable: " + ex.getMessage());
        } finally {
            Disconnect();
        }
    }

    /**
     *  to create a String SQL query for addCol method
     */
    public String createAddString(String[] column_name, String table_name, String dbName) {

        setDBname(dbName);
        AddString = new StringBuilder();
        String result;
        for (String aa : column_name) {

            if (!ColumnNames.contains(aa.trim())) {
                AddString.append(aa.trim()).append(" varchar(100),");
            }
        }

        if (AddString.length() == 0) {

            result = "";

        } else {
            AddString.replace(AddString.length() - 1, AddString.length(), ");");
            result = "alter table " + getDBname() + "." + table_name.trim() + " add column (" + AddString.toString();
        }

        return result;
    }

    /*
       NOTE : might need modification later to add dynamic column type
       if the table already exists adds columns to the current table
     */
    public void addCol(String[] columnName, String tableName, String dbName) {

        addColString = createAddString(columnName, tableName, dbName);
        logAddCol(addColString);
        setDBname(dbName);
        connect();
        
        try {
            if (!addColString.equals("")) {
                st.executeUpdate(addColString);
            }

        } catch (Exception ex) {
            System.out.println("addCol: " + ex.getMessage());
        } finally {
            
            Disconnect();
        }
    }

    /*
     to check if table exists
     the called method returns the list of table properities
     if it return nothing it meaans that table does not exists in DB
     */
    public boolean isTabeleExists(String tableName, String dbName) {
        return !getTableProperty(tableName, dbName).isEmpty();
    }
    
    public void resetDB(String dbname)
    {
        getTableNames(dbname);        
        setDBname(dbname);        
        
        for (String tb : tableNames){        
            dropString = "DROP TABLE " + tb;
            logDrop(dropString);
            connect();

            try {
                st.executeUpdate(dropString);
            } catch (Exception ex) {
                System.out.println("DropTables: " + ex.getMessage());
            } finally {
                Disconnect();
            }
        }
    }
}
