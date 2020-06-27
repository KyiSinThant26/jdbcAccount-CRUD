/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbc.account;

import java.awt.Container;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Mike Chang
 */
public class Controller {

    public static Connection con = null;
    public static String usernamefromDb = null;
    public static String namefromDb = null;
    public static String emailfromDb = null;
    public static String dobfromDb = null;
    public static String IDfromDB=null;
     
public static boolean visible=true;
    public Controller() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        String url = "jdbc:mysql://localhost";
        con = DriverManager.getConnection(url, "root", "");
        Statement s = con.createStatement();
        String sql = "Create database if not exists account";
        s.execute(sql);
        String sqls = "Use account";
        s.execute(sqls);
        String sql1 = "CREATE TABLE if not exists `account` (ID varchar(10) PRIMARY KEY not null,"
                + "username varchar(100) Unique NOT NULL,"
                + "  name varchar(150) NOT NULL,"
                + "  email varchar(100) UNIQUE NOT NULL,"
                + "  dob date DEFAULT NULL,"
                + "  verified tinyint(1) NOT NULL DEFAULT 0"
                + ")";
        s.executeUpdate(sql1);

        String sql2 = "Create table if not exists `user`"
                + "(`Name` varchar(20),`Password` varchar(50),`Validation` tinyint(1) not null Default 0)";
        s.executeUpdate(sql2);

      /*  String sql3 = "Insert into user values (?,?,?)";
        PreparedStatement p = con.prepareStatement(sql3);
        p.setString(1, "kyikyi");
        p.setString(2, "kyisinthant");
        p.setInt(3, 1);
        p.execute();*/
    }

    public void loginCheck(String username, char[] pssw) throws SQLException {
        String pssw2 = new String(pssw);

        Statement stm = null;
        String userCheck, psswCheck;
        String sql = "Select Name,Password from user";
        stm = con.createStatement();
        ResultSet rs = stm.executeQuery(sql);

        while (rs.next()) {
            userCheck = rs.getString("Name");
            psswCheck = rs.getString("Password");
            if (userCheck.equals(username) && psswCheck.equals(pssw2)) {
               
               visible=false;
                AccountPage accountpage = new AccountPage();
                   accountpage.setVisible(true);

            }
            else{ 
                
                      
                LoginPage.fullwarning.setText("Please Check Your Username or Password again!");
            }
        }

    }

    public void createAccount(String id, String username, String name, String email, Date dob) throws SQLException  {

        String sql = "Insert into account values (?,?,?,?,?,?)";
    
       
            PreparedStatement   pstm = con.prepareStatement(sql);
             pstm.setString(1, id);
        pstm.setString(2, username);
        pstm.setString(3, name);
        pstm.setString(4, email);
        pstm.setDate(5, dob);
        pstm.setInt(6, 1);
        pstm.execute();

        
       
    }

    public void getDatafromDb() throws SQLException {
        // DefaultTableModel dmodel= new DefaultTableModel();

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

        }

    }

    public void deleteDatafromDB(String id) throws SQLException {

        String sql4 = "Delete from account where ID=?";
        PreparedStatement p = con.prepareStatement(sql4);
        p.setString(1, id);

        p.executeUpdate();

    }

    public void updateDatatoDB(String username, String name, String email, String dob,String id) throws SQLException {

        String s = "Update account SET username=?,name=?,email=?,dob=? where ID=?";
        PreparedStatement pstm = con.prepareStatement(s);
        pstm.setString(1, username);
        pstm.setString(2, name);
        pstm.setString(3, email);
        pstm.setString(4, dob);
        pstm.setString(5, id);
        pstm.executeUpdate();

    }
      

    /*public static void main(String[] args) throws SQLException {
        Controller c= new Controller();
        c.getDatafromDb();
        System.out.println(Controller.usernamefromDb);
       
    }*/

}
