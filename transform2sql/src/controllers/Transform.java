package controllers;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import model.ListForeignKeys;
import model.TableOracle;
import model.Table;
import model.Key;
import model.Group;
import model.Field;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.*;
import model.ForeignKey;
import model.TableComments;



/**
 *
 * @author javier
 */
public class Transform {

    Properties properties;

    public Transform(){
        
        this.properties=new Properties();
        try {
            this.properties.load(this.getClass().getResource("TransformConfig.properties").openStream());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    

    public Table tableAttunity2Oracle(Table tableAttunity,HandlePrefixesOracle handlePrefix) {

        List fieldsOracle= new ArrayList();
        List keysOracle = new ArrayList();
        Field field = null;
        Key key = null;

        TableOracle tableOracle = new TableOracle();

        tableOracle.setName(patternTableNameAttunity(tableAttunity.getName()));
  //      tableOracle.setName(patternTableNameOracle(tableAttunity.getName()));
 //       if (handlePrefix.getPrefixTableOracle(tableAttunity.getNameApp()) != null)
  //          tableOracle.setName(handlePrefix.getPrefixTableOracle(tableAttunity.getNameApp())+"_"+tableOracle.getName());

   //     tableOracle.setDataSource(tableAttunity.getDataSource());

        
        Iterator fieldsIt = tableAttunity.getFields().iterator();
        Iterator keysIt = null;

        if (tableAttunity.getKeys() != null)
          keysIt = tableAttunity.getKeys().iterator();


        while (fieldsIt.hasNext()){
            field = (Field)fieldsIt.next();

            //para grupos periodicos
            if (field.isCounter() && fieldsIt.hasNext()){
                Field fieldPeriodic = (Field)fieldsIt.next();
                if (fieldPeriodic.getCounterName().equals(field.getName())){
                    fieldPeriodic.setSize(fieldPeriodic.getScale()*fieldPeriodic.getDimension1());
                    //tengo que borrar el field anterior de la lista
                    //ListIterator itAux = group.getFields().listIterator()
                }
            }
            //antes de convertir los nombres lo hago porque sino el conunter name no es igual al
            //nombre convertido.
            patternFieldType(field);
  //          field.setName(patternFieldName((field.getName())));
          
            fieldsOracle.add(field);
        }

        //indices
        while (keysIt!= null && keysIt.hasNext()){
            key = (Key)keysIt.next();
            //la clave debe tener el nombre transformado al igual que el campo para buscar cual es key.
          
            if (key.getName().contains("_SP_") || key.getName().contains("SP_"))
                key.setSuperdescriptor(true);

  //          key.setName(patternFieldName((key.getName())));
            Iterator itCompusedKeys = key.getCompousedKey().iterator();
            List compousedAux = new ArrayList();

            while (itCompusedKeys.hasNext()){
                String compKey = (String) itCompusedKeys.next();
    //            compKey = patternFieldName((compKey));
                compousedAux.add(compKey);
            }
            key.setCompousedKey(compousedAux);
            keysOracle.add(key);
        }

        tableOracle.setFields(fieldsOracle);
        tableOracle.setKeys(keysOracle);

        return tableOracle;
    }



     public List groupsAttunity2Oracle(List groups,Table table) {

        List  fieldsOracle= new ArrayList();
        Group group = null;

        Iterator itGroups = groups.iterator();
        Iterator itFields = null;

        while (itGroups.hasNext()){
            group = (Group)itGroups.next();

 //           group.setName(patternGroupName(group.getName()));
            itFields = group.getFields().iterator();
            
            while (itFields.hasNext()){
                
                Field field = (Field)itFields.next();
                 patternFieldType(field);
 //               field.setName(patternFieldName(field.getName()));
                fieldsOracle.add(field);
            }
            
            group.setFields((List) ((ArrayList)fieldsOracle).clone());
            fieldsOracle.clear();
        }
        
        return groups;
    }



     
    public Table tableAttunity2Attunity(Table tableAttunity) {

        Field field = null;
        Iterator fieldsIt = tableAttunity.getFields().iterator();

        tableAttunity.setName(patternTableNameAttunity(tableAttunity.getName()));
        while (fieldsIt.hasNext()){
            field = (Field)fieldsIt.next();
            patternFieldTypeAtt(field);
        }

        return tableAttunity;
    }




    public List groupsAttunity2Attunity(List groups,Table table) {

        Group group = null;
        Iterator itGroups = groups.iterator();
        Iterator itFields = null;

        while (itGroups.hasNext()){
            group = (Group)itGroups.next();

            group.setDataSource(table.getDataSource());
            itFields = group.getFields().iterator();

            while (itFields.hasNext()){
                Field field = (Field)itFields.next();
                patternFieldTypeAtt(field);
            }
        }
        return groups;
    }



    

    public TableComments commentsToOracle(TableComments comments,TableOracle tableOracle){

        comments.setTableName(tableOracle.getName());

        Iterator itFields = comments.getCommentsOnFields().keySet().iterator();
        Map newCommentsOnField = new HashMap();
        Map oldCommentsOnField = comments.getCommentsOnFields();
        
        while(itFields.hasNext()){
            String nameKeyField = (String) itFields.next();
            String comment = (String) oldCommentsOnField.get(nameKeyField);
//            newCommentsOnField.put(patternFieldName(nameKeyField),comment);
             newCommentsOnField.put(nameKeyField,comment);
        }

        comments.setCommentsOnFields(newCommentsOnField);
        return comments;
    }



    
      public TableComments commentsToAttunity(TableComments comments){

        comments.setTableName(patternTableNameAttunity(comments.getTableName()));

        comments.setCommentsOnFields(comments.getCommentsOnFields());
        return comments;
    }



    
    private String patternTableNameOracle(String nameTable){

       String patternNameTableAttu;
       String patternNameTableOrac;
       boolean stop = false;
       String result = nameTable;

        for (int i = 1;stop != true;i++)
        {
          patternNameTableAttu = this.properties.getProperty("nameTable_attu_"+Integer.toString(i));
          patternNameTableOrac = this.properties.getProperty("nameTable_orac_"+Integer.toString(i));

          stop = (patternNameTableAttu == null) && (patternNameTableOrac == null);

          if (!stop)
          {
            Pattern pattern = Pattern.compile(patternNameTableAttu);
             //busco el patron de attunity a remplazar en el nombre que me pasan
            Matcher matcher = pattern.matcher(result);
            result = matcher.replaceFirst(patternNameTableOrac); //reemplazo por el de oracle
          }
        }
        return result;
    }



     private String patternTableNameAttunity(String nameTable){


         if (nameTable.contains("_U_")){
             return nameTable.replace("_U_", "_");
         }
         return nameTable;
    }
    
    
    
    
    private String patternFieldName(String nameField){

        int index= 0;
        String result = null;
        if ( nameField.contains("_CO_")){
           nameField = nameField.replace("_CO_", "_CD_");
        }
        if ((index = nameField.indexOf("_")) != -1){
            boolean isCod = ((nameField.indexOf("CO") != -1) && (nameField.indexOf("CO") < index));
            
            String prefix = nameField.substring(0,index);
            result = nameField.substring(index,nameField.length());

            if (prefix.length() == 5){
                String carac2 = String.valueOf(prefix.charAt(2)).concat(String.valueOf(prefix.charAt(3)));
                result =  carac2.concat(result);
                if (isCod){
                    result = result.replaceFirst("CO","CD");
                }
            }
            //pongo el co solo que le refijo de 5 caracteres, lo de tres caracateres se ignora todo
        }
        if (result != null && result.charAt(0) == '_')
            result = result.substring(1,result.length());

        return (result == null) ? nameField : result;
    }




    private void patternFieldType(Field field){
        
       String result = null;

      //  if (field.getName().contains(this.properties.getProperty("type_att_date")))//date
        //    result = this.properties.getProperty("type_ora_date");
        if (field.getName().contains("_FX_"))
            result = "date";
        else if (this.properties.getProperty(field.getDataType()) != null)
            result  = this.properties.getProperty(field.getDataType());

       if (field.getDataType().equals("unsigned_decimal") && field.getScale() != -1){
            field.setSize(Float.valueOf(String.valueOf(Float.valueOf(field.getSize()).intValue()-field.getScale())+"."+String.valueOf(field.getScale())).floatValue());
        }
       field.setDataType(result);
    }


    

     private void patternFieldTypeAtt(Field field){

       String result = null;
       //esta hardcodeado por falta de tiempo

       if (field.getName().contains("_FX_"))
            result = "varchar";
       else if (this.properties.getProperty("att."+field.getDataType()) != null)
            result  = this.properties.getProperty("att."+field.getDataType());

       if (field.getDataType().equals("unsigned_decimal") && field.getScale() != -1){
            field.setSize(Float.valueOf(String.valueOf(Float.valueOf(field.getSize()).intValue()-field.getScale())+"."+String.valueOf(field.getScale())).floatValue());
        }
      
       field.setDataType(result);
    }


    
    private String patternGroupName(String nameGroup) {

        String patternNameTableAttu;
        String patternNameTableOrac;
        boolean stop = false;
        String result = nameGroup;

        for (int i = 1;stop != true;i++)
        {
          patternNameTableAttu = this.properties.getProperty("nameGroup_attu_"+Integer.toString(i));
          patternNameTableOrac = this.properties.getProperty("nameGroup_orac_"+Integer.toString(i));

          stop = (patternNameTableAttu == null) && (patternNameTableOrac == null);

          if (!stop)
          {
            Pattern pattern = Pattern.compile(patternNameTableAttu);
             //busco el patron de attunity a remplazar en el nombre que me pasan
            Matcher matcher = pattern.matcher(result);
            result = matcher.replaceFirst(patternNameTableOrac); //reemplazo por el de oracle
          }
        }
        return result;
    }




    public ListForeignKeys ListForeignKeyToOracle(ListForeignKeys listForeignKeys,String prefixTableTransform) {
        Iterator itFkey = listForeignKeys.getForeignsKeys().iterator();

        //campos
        while (itFkey.hasNext()){
            ForeignKey foreignKey = (ForeignKey)itFkey.next();
        //    foreignKey.getField().setName(patternFieldName(foreignKey.getField().getName()));
            foreignKey.setOwnerTable(/*patternTableNameOracle(*/foreignKey.getOwnerTable()/*)*/);
            if (prefixTableTransform != null)
                foreignKey.setOwnerTable(prefixTableTransform+"_"+foreignKey.getOwnerTable());

            foreignKey.setTableReferences(patternGroupName(foreignKey.getTableReferences()));
        }

        return listForeignKeys;
    }

   


    public ListForeignKeys ListForeignKeyToAttunity(ListForeignKeys listForeignKeys,String prefixTableTransform) {
        Iterator itFkey = listForeignKeys.getForeignsKeys().iterator();

        //campos
        while (itFkey.hasNext()){
            ForeignKey foreignKey = (ForeignKey)itFkey.next();
            foreignKey.setOwnerTable(patternTableNameAttunity(foreignKey.getOwnerTable()));
        }
        return listForeignKeys;
    }


      
}
