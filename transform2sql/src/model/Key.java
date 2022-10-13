/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 *
 * @author javimetal
 */
public class Key {

    private boolean bestUnique;
    private String name;
    private int size;
    private List compousedKey;
    private boolean superdescriptor;
    private boolean unique;


    
    public Key() {
        this.compousedKey = new ArrayList();
    }


    /**
     * @return the bestUnique
     */
    public boolean isBestUnique() {
        return bestUnique;
    }

    /**
     * @param bestUnique the bestUnique to set
     */
    public void setBestUnique(boolean bestUnique) {
        this.bestUnique = bestUnique;
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
     * @return the size
     */
    public int getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * @return the unique
     */
    public boolean isUnique() {
        return unique;
    }

    /**
     * @param unique the unique to set
     */
    public void setUnique(boolean unique) {
        this.unique = unique;
    }


    public String saveSQLIndex(String tableName){

        String scriptDDL = "";
        // si no es clave primaria la tomo como un indice.
        if (!this.bestUnique){
            scriptDDL = " CREATE";
            if (this.unique){
                scriptDDL += " UNIQUE";
            }
            scriptDDL +=" INDEX IDX_"+this.name;
            scriptDDL += " ON "+tableName+"(";
        

            if (this.compousedKey != null){
                Iterator it = this.compousedKey.iterator();
                String nameCompousedKey = null;

                while (it.hasNext()){
                    nameCompousedKey = (String) it.next();
                    scriptDDL += nameCompousedKey;
                    if (it.hasNext())
                        scriptDDL += ",";
                }
            }
            if (!scriptDDL.equals(""))
                scriptDDL +=");"+"\n";
        }
        return scriptDDL;
    }


    
    public String saveSQLPrimaryKeys(){

        String scriptDDL = "";
           
        if (this.compousedKey != null){
            scriptDDL +="\t"+"PRIMARY KEY(";
            Iterator it = this.compousedKey.iterator();
            String nameCompousedKey = null;

            while (it.hasNext()){
                nameCompousedKey = (String) it.next();
                scriptDDL += nameCompousedKey;
                if (it.hasNext())
                    scriptDDL += ",";
            }
            scriptDDL += ")"+"\n";
        }

        return scriptDDL;
    }

    /**
     * @return the compousedKey
     */
    public List getCompousedKey() {
        return compousedKey;
    }

    /**
     * @param compousedKey the compousedKey to set
     */
    public void setCompousedKey(List compousedKey) {
        this.compousedKey = compousedKey;
    }

    /**
     * @return the superdescriptor
     */
    public boolean isSuperdescriptor() {
        return superdescriptor;
    }

    /**
     * @param superdescriptor the superdescriptor to set
     */
    public void setSuperdescriptor(boolean superdescriptor) {
        this.superdescriptor = superdescriptor;
    }




    
}
