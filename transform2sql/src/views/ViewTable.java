/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package views;

import controllers.PersistanceSQL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.StatementVisitor;
import net.sf.jsqlparser.statement.create.table.ColDataType;
import net.sf.jsqlparser.statement.create.table.ColumnDefinition;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.create.table.Index;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.drop.Drop;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.replace.Replace;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.truncate.Truncate;
import net.sf.jsqlparser.statement.update.Update;


/**
 *
 * @author javimetal
 */
public class ViewTable implements StatementVisitor,PersistanceSQL{
    private String name;
    private List fields;
    private List indexs;
    private String comments;
    private Map commentsOnField;
   


    public ViewTable() {
        this.fields = new ArrayList();
        this.indexs = new ArrayList();
        this.commentsOnField = new HashMap();
    }

    public void processStatement(Statement statement){
        statement.accept(this);
    }

    public void visit(Select select){}
    public void visit(Delete delete){}
    public void visit(Update update){}
    public void visit(Insert insert){}
    public void visit(Replace rplc){}
    public void visit(Drop drop){}
    public void visit(Truncate trnct){}

    public void visit(CreateTable ct) {
          this.setName(ct.getTable().getName());
          
          if (ct.getColumnDefinitions() != null)
            extractFields(ct);
          if (ct.getIndexes() != null)
            extractKeys(ct);
    }

    public void extractFields(CreateTable ct){

      List columns = ct.getColumnDefinitions();
      Iterator it = columns.iterator();
      ColumnDefinition col = null;
      ViewField viewField;

      while (it.hasNext()){
           col = (ColumnDefinition)it.next();
           viewField = new ViewField();
           viewField.setName(col.getColumnName());

           //tipo de dato
           ColDataType colDataType = col.getColDataType();
           viewField.setType(colDataType.getDataType());

           //argumentos de la lista de tamanios de tipos
           if (colDataType.getArgumentsStringList() != null)
           {
           
               Iterator itArgList = colDataType.getArgumentsStringList().iterator();
               String size = (String) itArgList.next();
               if ( itArgList.hasNext()){
                    size += ".";
                    size += (String) itArgList.next();
              }

               viewField.setSize(Float.valueOf(size).floatValue());
           }
           //primary key, not null , valores por default , etc.
           if (col.getColumnSpecStrings() != null){
                Iterator itSpec = col.getColumnSpecStrings().iterator();
                String arg = (String)itSpec.next();

                if (arg.trim().toLowerCase().equals("not") && itSpec.hasNext()){
                    arg = (String)itSpec.next();
                    if (arg.trim().toLowerCase().equals("null"))
                        viewField.setNotNull(true);
                }
          }
           this.fields.add(viewField);
      }
    }



    public void extractKeys(CreateTable ct){

      Iterator it =  ct.getIndexes().iterator();
      Index index = null;
      ViewIndex viewIndex = null;
      List compousedIndex = null;

        while (it.hasNext()){
           index = (Index)it.next();

           if (index.getType().equals("PRIMARY KEY"))

               if (!index.getColumnsNames().isEmpty()){
                   Iterator itCompousedKeys = index.getColumnsNames().iterator();

                   while (itCompousedKeys.hasNext()){
                        setIsPrimaryKeytoFields((String)itCompousedKeys.next());
                   }                
                 }
           }
     }

    private void setIsPrimaryKeytoFields(String keyName) {
        Iterator itFields = this.fields.iterator();
        ViewField viewField = null;

        while (itFields.hasNext()){
            viewField = (ViewField) itFields.next();
            if (viewField.getName().equals(keyName)){
                viewField.setPrimaryKey(true);
            }
        }
    }
    

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the fields
     */
    public List getFields() {
        return fields;
    }

    /**
     * @param fields the fields to set
     */
    public void setFields(List fields) {
        this.fields = fields;
    }

    /**
     * @return the indexs
     */
    public List getIndexs() {
        return indexs;
    }

    /**
     * @param indexs the indexs to set
     */
    public void setIndexs(List indexs) {
        this.indexs = indexs;
    }

    public boolean equals(ViewTable viewTable){
        return viewTable.getName().equals(this.name);
    }

    public String saveSQL() {
   
        ViewField viewField= null;

        String scriptDDL = "CREATE TABLE "/*+this.dataSource+"."*/;
        scriptDDL +=this.name+" ("+"\n";

        Iterator itFields = this.fields.iterator();
        String subScriptDDL = null;

        //campos
        while (itFields.hasNext()){
            viewField = (ViewField)itFields.next();

            if ((subScriptDDL = viewField.saveSQL()) != null)
                scriptDDL += "\t"+subScriptDDL;

            if (itFields.hasNext() && subScriptDDL != null)
                scriptDDL +=","+"\n";
        }

        //recorro los campos de nuevo pero llamando a que se garben las primary keys
        Iterator itKeys = this.fields.iterator();
        List primaryKeys = new ArrayList();
        //armo una lista de primary key para escibirlas
         while (itKeys.hasNext()){
            viewField = (ViewField)itKeys.next();
            if (viewField.isPrimaryKey()){
                primaryKeys.add(viewField.getName());
            }
        }

        Iterator itPrimaryKeys = primaryKeys.iterator();
        if (!primaryKeys.isEmpty())
            scriptDDL +=","+"\n"+"\t"+" PRIMARY KEY (";
        
        //todas las claves como indices.
        while (itPrimaryKeys.hasNext()){
            scriptDDL += (String)itPrimaryKeys.next();
            if (itPrimaryKeys.hasNext())
                scriptDDL += ",";
        }

        if (!primaryKeys.isEmpty())
            scriptDDL +=")";
        
        scriptDDL +="\n"+");"+"\n";
        Iterator itIndexs = this.indexs.iterator();

        //todas las claves como indices.
        while (itIndexs.hasNext()){
            ViewIndex viewIndex = (ViewIndex)itIndexs.next();

            if ((subScriptDDL = viewIndex.saveSQL()) != null)
                scriptDDL += subScriptDDL;
             scriptDDL +="\n";
        }

        return scriptDDL;
    }

    /**
     * @return the comments
     */
    public String getComments() {
        return comments;
    }

    /**
     * @param comments the comments to set
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

    /**
     * @return the commentsOnField
     */
    public Map getCommentsOnField() {
        return commentsOnField;
    }

    /**
     * @param commentsOnField the commentsOnField to set
     */
    public void setCommentsOnField(Map commentsOnField) {
        this.commentsOnField = commentsOnField;
    }

    public String getNameApp() {
         return name.substring(0,name.indexOf("_"));
    }
    
}
