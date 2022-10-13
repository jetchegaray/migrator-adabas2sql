/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controllers;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.TableComments;
import net.sf.jsqlparser.JSQLParserException;
import views.DialogOnlyAccept;
import views.JPanelFieldsTable;
import views.ViewForeignKey;
import views.ViewTable;

/**
 *
 * @author javimetal
 */
public class HandleViewTablesInFile {

    private File fileSqlStream;
    private List viewTables;
    private List otherCases;
    private List ForeignKeys;


    public HandleViewTablesInFile(File sqlReader){
        this.fileSqlStream = sqlReader;
    }


     public void processSQLAndGetViewTables() {

        String scriptDDL="";
        String line = null;
        BufferedReader streamReader = readFile();

        try {
            do{
                if ((line = streamReader.readLine()) != null)
                    scriptDDL += line;
            }while (line != null);
        } catch (IOException ex) {
            Logger.getLogger(JPanelFieldsTable.class.getName()).log(Level.SEVERE, null, ex);
        }

        SQLParser parser = new SQLParser();

        try {
            parser.parse(scriptDDL);
        } catch (JSQLParserException ex) {
            DialogOnlyAccept dialog = new DialogOnlyAccept(null, true,"El Script SQL no es Válido");
            dialog.setVisible(true);
            dialog.dispose();
        }
        this.setViewTables(parser.getViewTables());
        this.setOtherCases(parser.getViewCases());
        this.setForeignKeys(parser.getViewForeignKeys());
    }


     

    private BufferedReader readFile(){

        BufferedReader  buffReader = null;

        try {
            buffReader = new BufferedReader(new FileReader(this.fileSqlStream));
        } catch (FileNotFoundException ex) {
       //     Logger.getLogger(JPanelInit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return buffReader;
    }



    
    public void SaveViewTablesAnOtherCasesToFile(List viewTables,List foreignKeys,List otherCases){

        this.fileSqlStream.delete();
        try {
            this.fileSqlStream.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(HandleViewTablesInFile.class.getName()).log(Level.SEVERE, null, ex);
        }
       

        Iterator itTables = viewTables.iterator();
        String newScript = "";

        while (itTables.hasNext()){
            ViewTable viewTable = (ViewTable)itTables.next();
            newScript += viewTable.saveSQL();

            TableComments tableComments = new TableComments();

            //tengo que crearlo asi porque se crea a partir de un table no de un view table
            tableComments.setTableName(viewTable.getName());
            tableComments.setCommentOnTable(viewTable.getComments());
            tableComments.setCommentsOnFields(viewTable.getCommentsOnField());
            newScript += tableComments.saveSQL();
        }


        Iterator itFkeys = foreignKeys.iterator();
     
        while (itFkeys.hasNext()){
            ViewForeignKey viewForeignKey = (ViewForeignKey)itFkeys.next();
             newScript += viewForeignKey.saveSQL();
        }

        newScript += SaveViewOtherCasesToFile(otherCases);

        if (newScript != null){
             BufferedOutputStream SQLbufferStream = null;
            try {
                SQLbufferStream = new BufferedOutputStream(new FileOutputStream(this.fileSqlStream.getAbsolutePath()));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(HandleViewTablesInFile.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            try {
                SQLbufferStream.write(newScript.getBytes());
                SQLbufferStream.flush();
                SQLbufferStream.close();
            } catch (IOException ex) {
                 Logger.getLogger(HandleViewTablesInFile.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * @return the viewTables
     */
    public List getViewTables() {
        return viewTables;
    }

    /**
     * @param viewTables the viewTables to set
     */
    public void setViewTables(List viewTables) {
        this.viewTables = viewTables;
    }

    /**
     * @return the otherCases
     */
    public List getOtherCases() {
        return otherCases;
    }

    /**
     * @param otherCases the otherCases to set
     */
    public void setOtherCases(List otherCases) {
        this.otherCases = otherCases;
    }

    /**
     * @return the ForeignKeys
     */
    public List getForeignKeys() {
        return ForeignKeys;
    }

    /**
     * @param ForeignKeys the ForeignKeys to set
     */
    public void setForeignKeys(List ForeignKeys) {
        this.ForeignKeys = ForeignKeys;
    }


   private String SaveViewOtherCasesToFile(List otherCases) {
        Iterator it = otherCases.iterator();
        String newSql = "";

        while(it.hasNext()){
           newSql += (String)it.next();
           newSql += ";"+"\n";
        }
        return newSql;
    }

}
