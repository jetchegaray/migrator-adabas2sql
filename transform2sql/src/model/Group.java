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
public class Group implements PersistanceSQL {

    private boolean groupEntry;
    private List fields;
    private String name;
    private String DataSource;
    private String nameApp;// no se usa
    private Key primaryKey;
    protected List primaryKeyFields;


    /**
     * @return the groupEntry
     */
    public boolean isGroupEntry() {
        return groupEntry;
    }

    /**
     * @param groupEntry the groupEntry to set
     */
    public void setGroupEntry(boolean groupEntry) {
        this.groupEntry = groupEntry;
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

    public void setNameApp(String nameApp ){
        this.name = nameApp+"_"+this.name;
    }


  public String saveSQL(){

        eliminateDuplicateNames();
        Field field= null;

        String scriptDDL = "CREATE TABLE "/*+this.DataSource+"."*/;
        scriptDDL += this.name+" ("+"\n";

        Iterator itFields = this.fields.iterator();
        String subScriptDDL = null;

    
        //campos
        while (itFields.hasNext()){
            field = (Field)itFields.next();

            if ((subScriptDDL = field.saveSQL()) != null)
                scriptDDL +="\t"+subScriptDDL;

            if (itFields.hasNext() && subScriptDDL != null)
                scriptDDL +=","+"\n";
        }

        if (!this.primaryKeyFields.isEmpty()){
            scriptDDL += ","+"\n";
        }

        //campos que son claves añadidas desde afuera, o se de la tabal principal
        Iterator itFieldsKeys = this.primaryKeyFields.iterator();
        while (itFieldsKeys.hasNext()){
            field = (Field)itFieldsKeys.next();
       //     scriptDDL +=","+"\n";
            if ((subScriptDDL = field.saveSQL()) != null)
                scriptDDL +="\t"+subScriptDDL;

            if (itFieldsKeys.hasNext() && subScriptDDL != null)
                scriptDDL +=","+"\n";
        }
        if (primaryKey != null){
            scriptDDL += ","+"\n";
            scriptDDL += this.primaryKey.saveSQLPrimaryKeys();
        }
        //cuando se escribe desde el xml al sql se tiene una sola clave primaria, no es asi cuando se
        //levanta sql en los viewModel

        scriptDDL +="\n"+");"+"\n";


        scriptDDL += savePrimaryKeyAsIndex();
        scriptDDL += "\n";
        
        return scriptDDL;
    }

    /**
     * @return the DataSource
     */
    public String getDataSource() {
        return DataSource;
    }

    /**
     * @param DataSource the DataSource to set
     */
    public void setDataSource(String DataSource) {
        this.DataSource = DataSource;
    }


    
    private void eliminateDuplicateNames() {
        Field field1,field2;

        for (int i = 0;i <= this.fields.size()-1;i++){
            field1 = (Field) this.fields.get(i);
            int k=1;
            for (int j = i+1;j <= this.fields.size()-1;j++){
                 field2 = (Field) this.fields.get(j);
                 if (field1.getName().equals(field2.getName())){
                    field2.setName(field2.getName().concat("_"+String.valueOf(k)));
                    k++;
                    this.fields.set(j, field2);
                }
            }
        }
    }


    
    public void setToChoosePrimaryKey(Key key,List fields){
        this.primaryKey = key;
        this.primaryKeyFields = fields;
    }


    
      private String savePrimaryKeyAsIndex(){

          if (this.primaryKey != null){
            return this.primaryKey.saveSQLIndex(this.name);
          }
          return "";
    }

      
}
