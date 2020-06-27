/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbc.account;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import static jdbc.account.AccountPage.jTable1;
import static jdbc.account.Controller.IDfromDB;
import static jdbc.account.Controller.con;
import static jdbc.account.Controller.dobfromDb;
import static jdbc.account.Controller.emailfromDb;
import static jdbc.account.Controller.namefromDb;
import static jdbc.account.Controller.usernamefromDb;

/**
 *
 * @author Mike Chang
 */
public class tablemodeling extends Controller{

    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

    public tablemodeling() throws SQLException {
    }

    

         
       
    
    
    public void datafromDB() throws SQLException {

        model.addRow(new Object[]{Controller.IDfromDB, usernamefromDb, namefromDb, emailfromDb, dobfromDb});//add rows

    }

    public void refresh() {
        model.fireTableDataChanged();

    }

    public void deleteRow() throws SQLException {
        int getrow = jTable1.getSelectedRow();
        String idfromtable = (String) jTable1.getValueAt(jTable1.getSelectedRow(), 0);
        System.out.println(idfromtable);
        Controller c = new Controller();
        c.deleteDatafromDB(idfromtable);

        if (jTable1.getSelectedRow() != -1) {
            // remove selected row from the model
            model.removeRow(getrow);
        }

    }

    
public void existedData() throws SQLException {
        String s = "Select *from account";

        Statement stmm = null;
        stmm = con.createStatement();
        ResultSet resultset = stmm.executeQuery(s);
  
        while (resultset.next()) {
           
            IDfromDB = resultset.getString(1);
            usernamefromDb = resultset.getString(2);
            namefromDb = resultset.getString(3);
            emailfromDb = resultset.getString(4);
            dobfromDb = resultset.getString(5);
              model.addRow(new Object[]{IDfromDB,usernamefromDb,namefromDb,emailfromDb,dobfromDb});

            

        }
}
    public void updatedata() {
       
        String idfromtable = (String) jTable1.getValueAt(jTable1.getSelectedRow(), 0);
        String usernamefromtable = (String) jTable1.getValueAt(jTable1.getSelectedRow(), 1);
         String  namefromtable = (String) jTable1.getValueAt(jTable1.getSelectedRow(), 2);
        String emailfromtable = (String) jTable1.getValueAt(jTable1.getSelectedRow(), 3);
        String dobfromtable = (String) jTable1.getValueAt(jTable1.getSelectedRow(), 4);
         
        Date date=Date.valueOf(dobfromtable);//converting string to sql Date
       
       
      
        try {
           Controller c = new Controller();
            c.updateDatatoDB(usernamefromtable, namefromtable, emailfromtable, dobfromtable,idfromtable);
        } catch (SQLException ex) {
            
        }
      
    }

}
