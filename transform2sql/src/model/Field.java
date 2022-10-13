/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import controllers.PersistanceSQL;
import java.util.Iterator;
import java.util.List;


/**
 *
 * @author javimetal
 */
public class Field implements PersistanceSQL{


    private String dataType;
    private String name;
    private boolean isNullSupppressed;
    private float size;
   
    //opcionales
    private boolean autoincrement;
    private boolean nonUpdateable;
    private boolean rowind;
    
    //para campos dentro de grupos
    private int scale;
    private int dimension1;
    private String CounterName;
    private boolean counter;
    private boolean foreignKey;

    public Field(){
        scale = -1;
    }

    /**
     * @return the dataType
     */
    public String getDataType() {
        return dataType;
    }

    /**
     * @param dataType the dataType to set
     */
    public void setDataType(String dataType) {
        this.dataType = dataType;
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
     * @return the isNullSupppressed
     */
    public boolean isIsNullSupppressed() {
        return isNullSupppressed;
    }

    /**
     * @param isNullSupppressed the isNullSupppressed to set
     */
    public void setIsNullSupppressed(boolean isNullSupppressed) {
        this.isNullSupppressed = isNullSupppressed;
    }

  
    /**
     * @return the autoincrement
     */
    public boolean isAutoincrement() {
        return autoincrement;
    }

    /**
     * @param autoincrement the autoincrement to set
     */
    public void setAutoincrement(boolean autoincrement) {
        this.autoincrement = autoincrement;
    }

    /**
     * @return the nonUpdateable
     */
    public boolean isNonUpdateable() {
        return nonUpdateable;
    }

    /**
     * @param nonUpdateable the nonUpdateable to set
     */
    public void setNonUpdateable(boolean nonUpdateable) {
        this.nonUpdateable = nonUpdateable;
    }

    /**
     * @return the rowind
     */
    public boolean isRowind() {
        return rowind;
    }

    /**
     * @param rowind the rowind to set
     */
    public void setRowind(boolean rowind) {
        this.rowind = rowind;
    }

    /**
     * @return the scale
     */
    public int getScale() {
        return scale;
    }

    /**
     * @param scale the scale to set
     */
    public void setScale(int scale) {
        this.scale = scale;
    }

    /**
     * @return the dimension
     */
    public int getDimension1() {
        return dimension1;
    }

    /**
     * @param dimension the dimension to set
     */
    public void setDimension1(int dimension1) {
        this.dimension1 = dimension1;
    }

    /**
     * @return the CounterName
     */
    public String getCounterName() {
        return CounterName;
    }

    /**
     * @param CounterName the CounterName to set
     */
    public void setCounterName(String CounterName) {
        this.CounterName = CounterName;
    }



    public String saveSQL(){

        String scriptDDL = null;

        scriptDDL = this.name+" ";
        scriptDDL += this.dataType.toUpperCase();

       if (!this.dataType.toLowerCase().contains("date"))
       {
            scriptDDL +="(";
        
            if (this.dataType.toLowerCase().contains("decimal") || scale != -1){
                scriptDDL +=  String.valueOf(this.size).replace(".", ",");
            }else
                scriptDDL +=  Float.valueOf(this.size).intValue();

            scriptDDL += ")";
       }

        return scriptDDL;
    }


    
    public boolean isPrimaryKey(List keys){

        Iterator itKeys = keys.iterator();
        Key key = null;

        while (itKeys.hasNext()){
            key = (Key)itKeys.next();

            if (key.getName().equals(this.name))
                return true;
        }
        return false;
    }

    /**
     * @return the sizeOracle
     */
    public float getSize() {
        return size;
    }

    /**
     * @param sizeOracle the sizeOracle to set
     */
    public void setSize(float size) {
        this.size = size;
    }

    public boolean isCounter() {
        return this.counter;
    }

    public void setCounter(boolean booleanValue) {
        this.counter = counter;
    }

    public void setForeignKey(boolean b) {
        this.foreignKey = b;
    }

    public boolean isForeignKey() {
        return this.foreignKey;
    }

    

    


}





