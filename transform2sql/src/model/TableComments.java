/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import controllers.PersistanceSQL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;


/**
 *
 * @author javier
 */
public class TableComments implements PersistanceSQL{

    private String tableName;
    private String commentOnTable;
    private Map commentsOnFields;

    //uso interno
    private List fields;
    private boolean generateTableComments;
    private Map<String,Integer> appearNameFields = new HashMap();


    public TableComments(Table table){
       this.tableName = table.getName();
       this.fields = table.getFields();
       this.commentsOnFields = new HashMap();
       this.commentOnTable = null;
       this.generateTableComments = true;
    }

    public TableComments(Group group){
       this.tableName = group.getName();
       this.fields = group.getFields();
       this.commentsOnFields = new HashMap();
       this.commentOnTable = null;
    }

    public TableComments(){
       this.commentsOnFields = new HashMap();
    }

    public void setNewGroupForGenerateComments(Group group){
       this.setTableName(group.getName());
       this.fields = group.getFields();
       this.generateTableComments = false; //solo es para la tabla principal
    }

    public void setNewTableForGenerateComments(Table table){
       this.setTableName(table.getName());
       this.fields = table.getFields();
       this.generateTableComments = true;
    }


    public void generateComments(String text){

         if (text != null){
            generateCommentForTable(text);
         }
         if (text != null)
         {
             Iterator itFields = this.fields.iterator();
             Field field = null;
             String comment = null;
             
             itFields = this.fields.iterator();
             while(itFields.hasNext()){
                comment = null;
                field = (Field) itFields.next();
                comment = generateCommentForField(text, field.getName());
                this.commentsOnFields.put(field.getName(),String.valueOf(comment));
             }
        }
    }




    public String saveSQL() {
        
        
        String scriptDLL = "";

        if (this.getCommentOnTable() != null){
            scriptDLL += "COMMENT ON TABLE "+this.getTableName();
            scriptDLL += " IS '"+this.getCommentOnTable()+"'"+";"+"\n";
        }

        Iterator itFields = this.commentsOnFields.keySet().iterator();
        
         while(itFields.hasNext()){
            String nameKey = (String) itFields.next();
            String key = (String) this.commentsOnFields.get(nameKey);
            if (this.commentsOnFields.get(nameKey) != null && !this.commentsOnFields.get(nameKey).equals("null")){
                scriptDLL += "COMMENT ON COLUMN "+this.getTableName();
                scriptDLL += "."+nameKey;
                String comment = (String) this.getCommentsOnFields().get(nameKey);
                scriptDLL += " IS '"+comment.replace("'", " ")+"'"+";"+"\n";
             }
        }
        return scriptDLL;
    }




    
    private void generateCommentForTable(String text) {

        if (!this.generateTableComments)
            return;//para grupos no.

        String beginAbstract = "Abstract";
        String endAbstract = "Description";
        String beginLong = "1  Definicion larga";
        String endLong = "2  Ciclo de vida";

        int indexBeginTable = text.indexOf("Entity: "+getTableNameInComment(this.getTableName()),0);
        
        if (indexBeginTable  != -1){

             //busco primero la descripcion larga
            int indexB = text.indexOf(beginLong,indexBeginTable);
            int indexE = text.indexOf(endLong,indexB);

            if ( indexB !=-1 && indexE !=-1 ){

                String description = text.substring(indexB+beginLong.length(), indexE);
                description = description.replaceAll("0", "").trim();
                this.commentOnTable = description.trim().replace("\r\n","").replaceAll("'","");
                this.commentOnTable = eliminateSpaces(this.commentOnTable).replaceAll("-", "");

                if (!this.commentOnTable.isEmpty())
                    return;
             }
            //descripcion corta
          if ((indexB = text.indexOf(beginAbstract,indexBeginTable))!= -1 || this.commentOnTable.isEmpty()){

                if ((indexE = text.indexOf(endAbstract,indexB)) != -1){

                    this.commentOnTable = text.substring(indexB + beginAbstract.length(), indexE).replaceAll("-","").replaceAll("0","");
                    this.commentOnTable = this.commentOnTable.trim().replace("\r\n","").replaceAll("'","");
                    this.commentOnTable = eliminateSpaces(this.commentOnTable);
                }
             }
        }
    }

    

   private String generateCommentForField(String text,String fieldName) {

        if (!this.generateTableComments)
            return null;

        String beginAbstract = "Abstract";
        String endAbstract = "Description";
        String beginLong = "1  Definicion larga";
        String endLong = "2  Ciclo de vida";
        String dataElement = "Data Element: ";
        String nameField = fieldName.replace("_","-");
        int indexBeginField = text.indexOf(dataElement+nameField);
        int nextIndexField = text.indexOf(dataElement,indexBeginField+dataElement.length()+1);
        String comment = null;
        String description = null;

        if (indexBeginField  != -1){

             //busco primero la descripcion larga
            int indexBL = text.indexOf(beginLong,indexBeginField);
            int indexEL = text.indexOf(endLong,indexBL);
            int indexBC = text.indexOf(beginAbstract,indexBeginField);

            if ( indexBL !=-1 && indexEL !=-1 && nextIndexField != -1 && indexBL < nextIndexField){
                description = text.substring(indexBL+beginLong.length(), indexEL);
   
                description = description.replaceAll("0", "").trim().replaceAll("2", "");
                comment = description.trim().replace("\r\n","").replaceAll("  ", " ").replaceAll("'","");
                return comment;
             }
            //descripcion corta
            if (indexBC != -1 && description == null  && nextIndexField != -1 && indexBC < nextIndexField ){
                int indexEC = text.indexOf(endAbstract,indexBC);
                if (indexEC != -1){

                    comment = text.substring(indexBC + beginAbstract.length(), indexEC).replaceAll("-","").replaceAll("0","");
                    comment = comment.trim().replace("\r\n","").replaceAll("  ", " ").replaceAll("'","");
                    return comment;
                }
             }
        }
        return comment;
    }






    /**
     * @return the tableName
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * @param tableName the tableName to set
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * @return the commentOnTable
     */
    public String getCommentOnTable() {
        return commentOnTable;
    }

    /**
     * @param commentOnTable the commentOnTable to set
     */
    public void setCommentOnTable(String commentOnTable) {
        this.commentOnTable = commentOnTable;
    }

    /**
     * @return the commentsOnFields
     */
    public Map getCommentsOnFields() {
        return commentsOnFields;
    }

    /**
     * @param commentsOnFields the commentsOnFields to set
     */
    public void setCommentsOnFields(Map commentsOnFields) {
        this.commentsOnFields = commentsOnFields;
    }

    private String eliminateSpaces(String comment) {

        StringTokenizer tokens = new StringTokenizer(comment," ");
        comment = "";
        while (tokens.hasMoreTokens()){
            String token = tokens.nextToken();
            comment += token.trim()+" ";
        }
        return comment;
    }


    private String getTableNameInComment(String tableName){
         
        StringTokenizer tokens = new StringTokenizer(tableName,"_");
        String modifiedName = "";
        while (tokens.hasMoreTokens()){
            String token = tokens.nextToken();
            if (!token.equals("U"))
            {
                modifiedName += token.trim();
                if (tokens.hasMoreTokens())
                    modifiedName += "-";
            }
        }
        return modifiedName;
    }


}
