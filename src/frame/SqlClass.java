/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frame;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Ross
 */
public class SqlClass {
    
    Connection conn;
    ResultSet rs = null;
    String myDriver = "org.sqlite.JDBC";
    String myConnect = "jdbc:sqlite:albumStorage.sqlite";
    
    //this method will create the connection
    public Connection getConnection() {
        try {
        Class.forName(myDriver);
        conn = DriverManager.getConnection(myConnect);
        }
        catch(ClassNotFoundException | SQLException e) {JOptionPane.showMessageDialog(null, e);}
        
    return conn;    
    }
    //this method will use the included connection and gather a resultset
    public ResultSet getResult(Connection con, String mySql){
        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(mySql);
        }
        catch (SQLException e) {JOptionPane.showMessageDialog(null, e);
    }
        return rs;
    
    }
    //this method will fill the inserted JTable with the results
    //of the inserted ResultSet
    public void fillTable(ResultSet rs, JTable jt) {
        DefaultTableModel model = (DefaultTableModel) jt.getModel();
        model.setNumRows(0);
        Object[] myData = null;
        try {
            int columnCount = rs.getMetaData().getColumnCount();
            myData = new Object[columnCount];
            while (rs.next()) {
                for (int x = 0; x < columnCount; x++){
                    myData[x] = rs.getObject(x+1);
                }
                model.addRow(myData);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(SqlClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        jt.setModel(model);
    }
}
