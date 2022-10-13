/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import controllers.PersistanceSQL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.text.StyledEditorKit.ItalicAction;

/**
 *
 * @author javimetal
 */
public abstract class Table implements PersistanceSQL {
    protected String name;
    protected List fields;
    protected List keys;
    private String dataSource;
    private Key primaryKey;
    private List primaryKeyField = new ArrayList();

    // el primary key field identifica los campos que van en una primary key, esos campos se deben copiar
   // a las tablas auxiliares para que esas tablas tengan una primary Key.



    public String getNameApp() {
        return name.substring(0,name.indexOf("_"));
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
     * @return the keys
     */
    public List getKeys() {
        return keys;
    }

    /**
     * @param keys the keys to set
     */
    public void setKeys(List keys) {
        this.keys = keys;
    }


    public String saveSQL(){

        //al hacer la transformacion de nombres pueden quedar nombres iguales
        //por lo tanto se numeran.
        eliminateDuplicateNames();
        Field field= null;
        
        String scriptDDL = "CREATE TABLE "/*+this.dataSource+"."*/;
        scriptDDL +=this.name+" ("+"\n";

        Iterator itFields = this.fields.iterator();
        String subScriptDDL = null;

        //campos
        while (itFields.hasNext()){
            field = (Field)itFields.next();
            
            if ((subScriptDDL = field.saveSQL()) != null)
                scriptDDL += "\t"+subScriptDDL;

            if (itFields.hasNext() && subScriptDDL != null)
                scriptDDL +=","+"\n";
        }

       

        Iterator itKeys = this.keys.iterator();
        Key key = null;
        boolean  existsPrimaryKey = false;
        boolean  existsSuperDescriptor = false;

        //existe alguna clave primaria
         while (itKeys.hasNext()){
            key = (Key)itKeys.next();
            if (!key.isBestUnique()){
                existsPrimaryKey = true;
            }
            if (key.isSuperdescriptor()){
                existsSuperDescriptor = true;
                break;
            }
        }

        if (existsSuperDescriptor) //queda en el itarador.
        {
            scriptDDL +=","+"\n";
            scriptDDL +=key.saveSQLPrimaryKeys();
        }else if (existsPrimaryKey){

        //cuando se escribe desde el xml al sql se tiene una sola clave primaria, no es asi cuando se
        //levanta sql en los viewModel
            scriptDDL +=","+"\n";
            key = (Key)this.keys.get(0);
            //si la 0 es el ISN lo salteo 
            if (key.isBestUnique()){
                key = (Key)this.keys.get(1);
            }
             scriptDDL += key.saveSQLPrimaryKeys();      
        }
        
        scriptDDL +="\n"+");"+"\n";

       
        Iterator itKeys3 = this.keys.iterator();

        //todas las claves como indices.
        while (itKeys3.hasNext()){
            key = (Key)itKeys3.next();

            if ((subScriptDDL = key.saveSQLIndex(this.name)) != null)
                scriptDDL += subScriptDDL;
             scriptDDL +="\n";
        }
       
        return scriptDDL;
    }

    /**
     * @return the dataSource
     */
    public String getDataSource() {
        return dataSource;
    }

    /**
     * @param dataSource the dataSource to set
     */
    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
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



    public void defineUniquePrimaryKeyAndFieldCompoused() {

        if (keys != null){
               Iterator itKeys = this.keys.iterator();
               Key key = null;

               while (itKeys.hasNext()){
                    key = (Key)itKeys.next();
                    if (key.isSuperdescriptor()){
                        this.setPrimaryKey(key);
                        break;
                    }
               }

               if (this.getPrimaryKey() == null)
               {
                   Iterator it = this.keys.iterator();
                   while (it.hasNext()){
                       Key key2 =  (Key) it.next();
                       //si no es el ISN y es un unique, se coloca el primer unique encontrado
                       //siemre y cuando no haya un superdescriptor
                       if (!key2.isBestUnique() && key2.isUnique()){
                            this.setPrimaryKey(key2);
                            break;
                       }
                   }
                }
                if (this.getPrimaryKey() == null)
                    return;

                Iterator itCompoused = this.getPrimaryKey().getCompousedKey().iterator();

                while (itCompoused.hasNext()){
                   String comp = (String)itCompoused.next();
                   Field newField = new Field();
                   newField.setName(comp);

                   Iterator itFields = this.fields.iterator();
                   while (itFields.hasNext()){
                       Field field = (Field)itFields.next();
                       if ( newField.getName().equals(field.getName())){
                            this.primaryKeyField.add(field);
                            break;
                       }
                   }

                }

            }
    }

    /**
     * @return the primaryKey
     */
    public Key getPrimaryKey() {
        return primaryKey;
    }

    /**
     * @return the primaryKeyField
     */
    public List getPrimaryKeyField() {
        return primaryKeyField;
    }

    /**
     * @param primaryKey the primaryKey to set
     */
    public void setPrimaryKey(Key primaryKey) {
        this.primaryKey = primaryKey;
    }

    /**
     * @param primaryKeyField the primaryKeyField to set
     */
    public void setPrimaryKeyField(List primaryKeyField) {
        this.primaryKeyField = primaryKeyField;
    }


     
}
