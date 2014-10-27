package Model;

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
    private String alterString;
    private String createString;
    private String addColString;
    private List<String> ColumnNames;
    private StringBuilder AddString = new StringBuilder();

    public SQL() {

    }

    private static final Logger logger = Logger.getLogger(SQL.class);

    public void logCreate(String logInfo) {
        logger.info("Create \\ DDL : " + logInfo);
    }

    public void logAlter(String logInfo) {
        logger.info("Alter  \\ DDL : " + logInfo);
    }
    
    public void logAddCol(String logInfo) {
        logger.info("AddCol \\ DDL : " + logInfo);
    }

    public boolean connect() {

        boolean ConRes = false;

        try {

            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sample_db?user=root&password=123456");
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

    public Boolean createTable(String[] column_name, String table_name) {

        Boolean exist = false;
        createString = createStringSelect(column_name, table_name);
        logCreate(createString);
        connect();
        try {
            int a = st.executeUpdate(createString);
            //exist = true;
        } catch (Exception ex) {
            System.out.println("createTable: " + ex.getMessage());
            exist = true;
        } finally {
            Disconnect();
        }
        return exist;
    }

    public List<TableProperty> getTableProperty(String TableName) {

        connect();
        List<TableProperty> TBList = new ArrayList<TableProperty>();
        ColumnNames = new ArrayList<String>();
        try {

            ResultSet rs = st.executeQuery("describe " + TableName);
            while (rs.next()) {

                if (rs.isFirst()) {
                    continue;
                }
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

    public void AlterTable(String tableName, String colName, String colType) {

        alterString = "alter table " + tableName + " modify " + colName.trim() + " " + colType;

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
     * to create a String SQL query for addCol method 
     */
    public String createAddString(String[] column_name, String table_name) {

        for (String aa : column_name) {

            if (!ColumnNames.contains(aa.trim())) {
                AddString.append(" add column ").append(aa).append(" varchar(100),");
            }
        }
        AddString.replace(AddString.length() - 1, AddString.length(), ";");

        return "alter table " + table_name + AddString.toString();
    }

    /*
     NOTE : might need modification later to add dynamic column type
     if the table already exists adds columns to the current table
     */
    public void addCol(String[] columnName, String tableName) {
        
        addColString = createAddString(columnName, tableName);
        logAddCol(addColString);
        connect();
        try {
            st.executeUpdate(addColString);

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
    public boolean isTabeleExists(String tableName) {
        return !getTableProperty(tableName).isEmpty();
    }
}
