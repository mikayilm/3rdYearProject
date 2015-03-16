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
    private StringBuilder AddString = null;
    private List<String> undoString;
    
    private List<String> ColumnNames;
    private List<String> tableNames;
    private String DBname;


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
    
    public void logUndo(String logInfo) {
        logger.info("Undo \\ DDL : " + logInfo);
    }

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
            String url = "jdbc:h2:tcp://localhost/~/" + DBname;

            conn = DriverManager.getConnection(url, "sa", "");
            st = conn.createStatement();

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

    public void generateSchema(String[] colName, String tabName, String dbName) {

        setDBname(dbName);

        if (isTabeleExists(tabName, dbName)) {
            if (!colName[0].equals("ALL")) {
                addCol(colName, tabName, dbName);
                setUndoString(colName, tabName, 1);
            }
        } else {
            if (colName[0].equals("ALL")) {
                createTable(tabName, dbName);
            } else {
                createTable(colName, tabName, dbName);
                setUndoString(colName, tabName, 0);
            }
        }
    }

    // Every time when the input enterd new object created to record all the lines of the entered query
    public void createUndoString(){
        undoString = new ArrayList<>();
    }
    
    public void setUndoString(String[] col_name, String tab_name, int change){
       
        //undoString = new ArrayList<>();
        if (change == 0){
            undoString.add("DROP TABLE " + tab_name.trim());
        }else{
            for (String column: col_name){
                undoString.add("ALTER TABLE " + tab_name.trim() + " DROP COLUMN " + column.trim());
            }
        }    
    }
       
    public void undo(String dbName){
        
        setDBname(dbName);
        
        for (String undo: undoString){
        
            logUndo(undo);
            connect();
            
            try {
                st.executeUpdate(undo);
            } catch (Exception ex) {
                System.out.println("undo: " + ex.getMessage());
            } finally {
                Disconnect();
            }
        }
    }
    
    
    //  ADD columns to EXISTING table        
    //  note : might need modification later to add dynamic column type        
    public void addCol(String[] columnName, String tableName, String dbName) {

        setDBname(dbName);
        // Creating Add String
        AddString = new StringBuilder();
        for (String aa : columnName) {
            if (!ColumnNames.contains(aa.trim())) {
                AddString.append(aa.trim()).append(" varchar(100),");
            }
        }
        if (AddString.length() == 0) {
            addColString = "";
        } else {
            AddString.replace(AddString.length() - 1, AddString.length(), ");");
            addColString = "alter table " + tableName.trim() + " add column (" + AddString.toString();
        }
        // END Creating Add String       
        logAddCol(addColString);
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

    public Boolean createTable(String[] column_name, String table_name, String dbName) {

        Boolean exist = false;
        // Create String
        StringBuilder columnNames = new StringBuilder();
        for (String column_name1 : column_name) {
            columnNames.append(column_name1.trim()).append(" varchar(100),");
        }

        createString = "create table " + table_name.trim()
                + "(" + table_name.trim() + "_id int, "
                + columnNames
                + "primary key(" + table_name.trim() + "_id)) ";
        // END Create String

        logCreate(createString);
        setDBname(dbName);
        connect();
        try {
            st.executeUpdate(createString);
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
        createString = "create table " + table_name.trim();
        logCreate(createString);
        setDBname(dbName);
        connect();
        try {
            st.executeUpdate(createString);
        } catch (Exception ex) {
            System.out.println("createTable: " + ex.getMessage());
            exist = true;
        } finally {
            Disconnect();
        }
        return exist;
    }

    public void AlterTable(String tableName, String colName, String colType, String dbName) {

        setDBname(dbName);
        alterString = "alter table " + tableName.trim() + " modify " + colName.trim() + " " + colType;
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

    /*         
     getTableProperty() returns the list of table properities
     if it return null it meaans table does not exists
     */
    public boolean isTabeleExists(String tableName, String dbName) {
        return !getTableProperty(tableName, dbName).isEmpty();
    }

    public List<TableProperty> getTableProperty(String TableName, String dbName) {

        setDBname(dbName);
        connect();
        List<TableProperty> TBList = new ArrayList<TableProperty>();
        ColumnNames = new ArrayList<String>();
        try {

            ResultSet rs = st.executeQuery("show columns from " + TableName.trim());
            while (rs.next()) {

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

    public List<TableProperty> sendSelectStament(String[] colNames, String tbName, String dbName) {

        List<String> columns = new ArrayList<String>();
        for (String col : colNames) {
            columns.add(col.trim());
        }

        setDBname(dbName);
        connect();

        List<TableProperty> TBList = new ArrayList<TableProperty>();
        try {
            ResultSet rs = st.executeQuery("show columns from " + tbName.trim());
            int i = 0;
            while (rs.next()) {
                if (columns.contains(rs.getString("field").trim())) {
                    TableProperty TB = new TableProperty();
                    TB.setFIELD(rs.getString("field"));
                    TB.setTYPE(rs.getString("type"));
                    TBList.add(TB);
                }
            }
        } catch (Exception ex) {
            System.out.println("DropTables: " + ex.getMessage());
        } finally {
            Disconnect();
        }

        return TBList;
    }

    public List<String> getTableNames(String dbName) {
        setDBname(dbName);
        connect();
        tableNames = new ArrayList<String>();

        try {
            ResultSet rs = st.executeQuery("show tables");
            while (rs.next()) {
                tableNames.add(rs.getString("TABLE_NAME").trim());
            }
        } catch (Exception ex) {
            System.out.println("getTableNames: " + ex.getMessage());
        }
        return tableNames;
    }

    public void resetDB(String dbname) {
        getTableNames(dbname);
        setDBname(dbname);

        for (String tb : tableNames) {
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
