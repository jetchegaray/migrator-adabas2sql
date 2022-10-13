/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package views;

import controllers.PersistanceSQL;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author javimetal
 */
public class ViewIndex implements PersistanceSQL{
    
    private String name;
    private boolean Unique;
    private boolean Index;
    private List compousedKey;
    private String onTable;

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
     * @return the isUnique
     */
    public boolean isUnique() {
        return Unique;
    }

    /**
     * @param isUnique the isUnique to set
     */
    public void setUnique(boolean isUnique) {
        this.Unique = isUnique;
    }

    /**
     * @return the isIndex
     */
    public boolean isIndex() {
        return Index;
    }

    /**
     * @param isIndex the isIndex to set
     */
    public void setIndex(boolean isIndex) {
        this.Index = isIndex;
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
     * @return the onTable
     */
    public String getOnTable() {
        return onTable;
    }

    /**
     * @param onTable the onTable to set
     */
    public void setOnTable(String onTable) {
        this.onTable = onTable;
    }

    public String saveSQL() {
        String scriptDDL = "";
        // si no es clave primaria la tomo como un indice.
        scriptDDL = " CREATE";
        if (this.Unique){
            scriptDDL += " UNIQUE";
        }
        scriptDDL +=" INDEX "+this.name;
        scriptDDL += " ON "+this.onTable+"(";

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

        return scriptDDL;
    }

}
