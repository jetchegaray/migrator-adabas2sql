/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import controllers.HandlePrefixesOracle;
import controllers.PersistanceSQL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author javimetal
 */
public class ListForeignKeys implements PersistanceSQL{

    private List foreignsKeys;
    private HandlePrefixesOracle handlePrefijos;

    public ListForeignKeys(HandlePrefixesOracle handlePrefix){
        this.handlePrefijos = handlePrefix;
        this.foreignsKeys = new ArrayList();
    }

    public ListForeignKeys(){}


    
    //es foreign key si pertenece su prefijo a otra tabla.
 
    public boolean isForeignKey(String fieldName,String tableName,String appName){

        String fieldPrefix = fieldName.substring(0,fieldName.indexOf("_"));
        String prefixNameField = null;
        
        if (fieldPrefix.length() == 3){
            prefixNameField = handlePrefijos.getPrefixFieldFromTableName(tableName);
            if (prefixNameField != null && !prefixNameField.equals(fieldPrefix)){
                //si existe el prefijo en el archivo y no igual al que me dio el campo
                //es una foreign key
                return true;
            }
        }else if (fieldPrefix.length() == 5){
            String appNameInField = fieldPrefix.substring(0,2);
            if (!appName.equals(appNameInField) && fieldName.charAt(4) == 'B'){
                return false;
                //el dia que se sepa de donde sacar la tabla pasarloa true
                //e implemtar por afuera lo que sea necesario en el listForeigmKey
                //es una foreign key pero no se de que tabla es.
            }
        }

        return false;
    }



    public String saveSQL() {
        Iterator itFKey = this.getForeignsKeys().iterator();
        String scriptDLL="";

        while (itFKey.hasNext()){
            ForeignKey Fkey = (ForeignKey)itFKey.next();
            scriptDLL += Fkey.saveSQL();
        }
        return scriptDLL;
    }


    //todas las foerign key las meto en la lista, ademas de seguir estando en la lista de campos de la tabla

    public void LoadForeignKey(Table table) {
        Iterator itFields = table.getFields().iterator();
 
        //campos
        while (itFields.hasNext()){
            Field field = (Field)itFields.next();
            if (isForeignKey(field.getName(), table.getName(),table.getNameApp()))
                this.getForeignsKeys().add(createForeignKey(field,table));
        }
    }


    //siempre crea claves foraneas a partir de prefijos de tres caracteres
    //el field references queda en null y el nombre de a tabala no esta completo como deberia ser
    //si no se seteanbien seguran en null y no se mostrara
    private ForeignKey createForeignKey(Field field,Table table){
        
        ForeignKey Fkey = new ForeignKey();

        Fkey.setField(field);
        Fkey.setOwnerTable(table.getName());
        Fkey.setTableReferences(handlePrefijos.getTableNameFromPrefixFieldName(table.getNameApp(),field.getName().substring(0,3)));
        Fkey.setOwnerTable(table.getName());

        return Fkey;
    }

    /**
     * @return the foreignsKeys
     */
    public List getForeignsKeys() {
        return foreignsKeys;
    }

    /**
     * @param foreignsKeys the foreignsKeys to set
     */
    public void setForeignsKeys(List foreignsKeys) {
        this.foreignsKeys = foreignsKeys;
    }

    public void setReferencesFieldsForAllFK(List groups) {

        Iterator itFkey = this.foreignsKeys.iterator();

        while (itFkey.hasNext()){
            ForeignKey Fkey = (ForeignKey)itFkey.next();
            List groupsOfTableReferences = setCompleteNameForTablereferences(Fkey,groups);
            if (groupsOfTableReferences.size() == 1){

                int cantReferencesField = 0;
                String nameTosearch = Fkey.getConvertedFieldNameReferenced();
                
                Iterator itFields = ((Group)groupsOfTableReferences.get(0)).getFields().iterator();
                while (itFields.hasNext()){
                    Field field = (Field)itFields.next();
                    
                    if (field.getName().contains(nameTosearch)){
                        cantReferencesField++;
                        Fkey.setFieldReferences(field.getName());
                    }
                }

                //le seteo null asi no lo graba en el .sql
                if (cantReferencesField > 1){
                    Fkey.setFieldReferences(null);
                }
            }
        }
    }


    //en la table referncs tenia el nombre de latabal que paso por el transfomr y e hio
    //la tansformacion de nombre de grupo , pero no le puso el nameApp como prefijo
    //por las dudas quyeno sea de la misma app la tabla referenciada
    //en este metodo es donde se busca bien el nombre de dica tabla
    private List setCompleteNameForTablereferences(ForeignKey foreignKey,List groups) {

        List groupMeets = new ArrayList();
        Iterator itGroup = groups.iterator();
        while (itGroup.hasNext()){
            Group group = (Group)itGroup.next();
                if (foreignKey.getTableReferences()!= null && group.getName().contains(foreignKey.getTableReferences()))
                    groupMeets.add(group);
        }
        
        if (groupMeets.size() == 1){
            foreignKey.setTableReferences(((Group)groupMeets.get(0)).getName());
        }

        return groupMeets;
    }


}
