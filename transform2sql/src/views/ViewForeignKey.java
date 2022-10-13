/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package views;

import controllers.PersistanceSQL;
import java.util.StringTokenizer;

/**
 *
 * @author javier
 */
public class ViewForeignKey implements PersistanceSQL{

    private String name;
    private String fieldName;
    private String tableReferences;
    private String fieldReferences;
    private String ownerTable;
    
    //se crea a partir de un string proveniente del scriptSQL
    public ViewForeignKey(String sql){

        //agrego espacios para la interpretacion de los parentessis
        sql = sql.replace("("," ( ").replace(")"," ) ");

        StringTokenizer tokenizer = new StringTokenizer(sql);

        while (tokenizer.hasMoreTokens()){
            String token = tokenizer.nextToken().trim();
            if (token.equals("TABLE")){
                this.ownerTable = tokenizer.nextToken().trim();
            }else if (token.equals("CONSTRAINT")){
                 this.name = tokenizer.nextToken().trim();
            }else if (token.equals("KEY")){
                if (tokenizer.nextToken().trim().equals("("))
                    this.fieldName = tokenizer.nextToken();
           // }else if (token.equals("(")){
             //   this.fieldName = tokenizer.nextToken().replace(")", "").trim();
            }else if (token.equals("REFERENCES")){
                this.tableReferences = tokenizer.nextToken().trim();
                if (tokenizer.nextToken().trim().equals("(")){
                    this.fieldReferences = tokenizer.nextToken().trim();
                }
            }
        }
    }



     public ViewForeignKey(){}
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
     * @return the fieldName
     */
    public String getFieldName() {
        return fieldName.trim();
    }

    /**
     * @param fieldName the fieldName to set
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    /**
     * @return the tableReferences
     */
    public String getTableReferences() {
        return tableReferences;
    }

    /**
     * @param tableReferences the tableReferences to set
     */
    public void setTableReferences(String tableReferences) {
        this.tableReferences = tableReferences;
    }

    /**
     * @return the fieldReferences
     */
    public String getFieldReferences() {
        return fieldReferences.trim();
    }

    /**
     * @param fieldReferences the fieldReferences to set
     */
    public void setFieldReferences(String fieldReferences) {
        this.fieldReferences = fieldReferences;
    }

    /**
     * @return the ownerTable
     */
    public String getOwnerTable() {
        return ownerTable.trim();
    }

    /**
     * @param ownerTable the ownerTable to set
     */
    public void setOwnerTable(String ownerTable) {
        this.ownerTable = ownerTable;
    }


     public String saveSQL() {
       String scriptDLL="";

       if (this.fieldReferences != null && this.tableReferences != null){
            scriptDLL += "ALTER TABLE "+this.getOwnerTable()+" ADD (";
            scriptDLL += "CONSTRAINT "+this.name+" )";
            scriptDLL += " FOREIGN KEY ("+this.fieldName+") ";
            scriptDLL += "REFERENCES "+this.tableReferences+"("+this.fieldReferences+")";
        }
       return scriptDLL;
    }

    @Override
     public boolean equals(Object object){
       ViewForeignKey viewForeignKey = (ViewForeignKey)object;
       return viewForeignKey.getName().equals(this.getName());
     }
}
