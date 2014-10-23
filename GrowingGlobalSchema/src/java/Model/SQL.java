/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mikayil
 */
public class SQL {

    private Connection conn;
    private Statement st;
    private String alterString;

    public SQL() {

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

    public List<TableProperty> getTableProperty(String TableName) {

        connect();
        List<TableProperty> TBList = new ArrayList<TableProperty>();
        try {

            ResultSet rs = st.executeQuery("describe " + TableName);
            while (rs.next()) {

                if (rs.isFirst()) {
                    continue;
                }

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
        
        alterString = "alter table " + tableName + " modify " + colName + " " + colType;
        connect();

        try {
            st.executeUpdate(alterString);
            
        } catch (Exception ex) {
            System.out.println("AlterTable: " + ex.getMessage());
        } finally {
            Disconnect();
        }
    }
}
