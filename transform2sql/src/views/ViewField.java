/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package views;

import controllers.PersistanceSQL;

/**
 *
 * @author javimetal
 */
public class ViewField  implements PersistanceSQL{

    private String name;
    private String type;
    private float size;
    private boolean notNull;
    private boolean primaryKey;
    private boolean foreignKey;


    public ViewField(){}

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
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the size
     */
    public float getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(float size) {
        this.size = size;
    }

    /**
     * @return the notNull
     */
    public boolean isNotNull() {
        return notNull;
    }

    /**
     * @param notNull the notNull to set
     */
    public void setNotNull(boolean notNull) {
        this.notNull = notNull;
    }

    /**
     * @return the primaryKey
     */
    public boolean isPrimaryKey() {
        return primaryKey;
    }

    /**
     * @param primaryKey the primaryKey to set
     */
    public void setPrimaryKey(boolean primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String saveSQL() {
        String scriptDDL = null;

        scriptDDL = this.name+" ";
        scriptDDL += this.type.toUpperCase();

        if (!this.type.toLowerCase().contains("date"))
        {
            scriptDDL += "(";

            if (this.type.equals("DECIMAL")){
                scriptDDL +=  String.valueOf(this.size).replace(".", ",");
            }else
                scriptDDL += Float.valueOf(this.size).intValue();

            scriptDDL += ")";
        }
        
        
        if (this.notNull)
            scriptDDL +=" NOT NULL";

        return scriptDDL;
    }

    /**
     * @return the foreignKey
     */
    public boolean isForeignKey() {
        return foreignKey;
    }

    /**
     * @param foreignKey the foreignKey to set
     */
    public void setForeignKey(boolean foreignKey) {
        this.foreignKey = foreignKey;
    }

    


}
