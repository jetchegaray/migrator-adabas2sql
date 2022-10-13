/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import controllers.PersistanceSQL;
import java.util.StringTokenizer;
import javax.swing.SwingConstants;

/**
 *
 * @author javimetal
 */
public class ForeignKey implements PersistanceSQL{

    private Field field;
    private String tableReferences;
    private String fieldReferences;
    private String ownerTable;

    /**
     * @return the field
     */
    public Field getField() {
        return field;
    }

    /**
     * @param field the field to set
     */
    public void setField(Field field) {
        this.field = field;
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
        return fieldReferences;
    }

    /**
     * @param fieldReferences the fieldReferences to set
     */
    public void setFieldReferences(String fieldReferences) {
        this.fieldReferences = fieldReferences;
    }

    public String saveSQL() {
       String scriptDLL="";


       if (field !=  null && this.fieldReferences != null && this.tableReferences != null){
            scriptDLL += "ALTER TABLE "+this.getOwnerTable()+" ADD (CONSTRAINT FK_"+this.field.getName()+" ) ";
            scriptDLL += "FOREIGN KEY ("+this.field.getName()+") ";
            scriptDLL += "REFERENCES "+this.tableReferences+"("+this.fieldReferences+");";
        }
       return scriptDLL;
    }

    /**
     * @return the ownerTable
     */
    public String getOwnerTable() {
        return ownerTable;
    }

    /**
     * @param ownerTable the ownerTable to set
     */
    public void setOwnerTable(String ownerTable) {
        this.ownerTable = ownerTable;
    }

    String getConvertedFieldNameReferenced() {
        String name = "";
        StringTokenizer tokenizer = new StringTokenizer(this.field.getName().concat("_"),"_");
        int k = 0;

        while(tokenizer.hasMoreTokens()){
            String token = tokenizer.nextToken();

            if (k != 0 && tokenizer.hasMoreTokens())
                name += token+"_";
            k++;
        }
        name = name.substring(0,name.lastIndexOf("_")-1);
        if (name.trim().equals(""))
            return null;
        
        return name;
    }

}