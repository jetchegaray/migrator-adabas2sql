/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import views.ViewField;
import views.ViewForeignKey;
import views.ViewIndex;
import views.ViewTable;

/**
 *
 * @author javimetal
 */
public class TransformViewTable extends Transform {


    public TransformViewTable(){
        super();
    }

    
    public ViewTable tableAttunity2Oracle(ViewTable table,HandlePrefixesOracle handlePrefix) {

        List fieldsOracle= new ArrayList();
        List indexOracle = new ArrayList();
        ViewField field = null;
        ViewIndex key = null;

        table.setName(patternTableNameOracle(table.getName()));
        if (handlePrefix.getPrefixTableOracle(table.getNameApp()) != null)
            table.setName(handlePrefix.getPrefixTableOracle(table.getNameApp())+"_"+table.getName());

   //     tableOracle.setDataSource(tableAttunity.getDataSource());


        Iterator fieldsIt = table.getFields().iterator();
        Iterator keysIt = null;

        if (table.getIndexs() != null)
          keysIt = table.getIndexs().iterator();


        while (fieldsIt.hasNext()){
            field = (ViewField)fieldsIt.next();
            
            field.setName(patternFieldName((field.getName())));

            fieldsOracle.add(field);
        }

        //indices
        while (keysIt!= null && keysIt.hasNext()){
            key = (ViewIndex)keysIt.next();

            key.setOnTable(patternTableNameOracle(key.getOnTable()));
            if (handlePrefix.getPrefixTableOracle(getNameApp(key.getOnTable())) != null)
                key.setOnTable(handlePrefix.getPrefixTableOracle(getNameApp(key.getOnTable()).trim())+"_"+key.getOnTable().trim());

            String idx = key.getName().substring(0, 3);
            key.setName(idx+"_"+patternFieldName((key.getName().substring(4))));

            Iterator itCompusedKeys = key.getCompousedKey().iterator();
            List compousedAux = new ArrayList();

            while (itCompusedKeys.hasNext()){
                String compKey = (String) itCompusedKeys.next();
                compKey = patternFieldName((compKey));
                compousedAux.add(compKey);
            }
            
            key.setCompousedKey(compousedAux);
            indexOracle.add(key);
        }

        table.setFields(fieldsOracle);
        table.setIndexs(indexOracle);

        return table;
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



    public ViewForeignKey foreignKey2Oracle(ViewForeignKey viewForeignKey,HandlePrefixesOracle handlePrefix) {

            viewForeignKey.setFieldName(patternFieldName(viewForeignKey.getFieldName()));
            viewForeignKey.setFieldReferences(patternFieldName(viewForeignKey.getFieldReferences()));

            String fx = viewForeignKey.getName().substring(0, 2);
            viewForeignKey.setName(fx+"_"+patternFieldName((viewForeignKey.getName().substring(3))));

        //    viewForeignKey.setName(patternFieldName(viewForeignKey.getName()));

            viewForeignKey.setOwnerTable(patternTableNameOracle(viewForeignKey.getOwnerTable()));

            String nameApp =  getNameApp(viewForeignKey.getOwnerTable()).trim();
            if (handlePrefix.getPrefixTableOracle(nameApp) != null)
                viewForeignKey.setOwnerTable(handlePrefix.getPrefixTableOracle(nameApp)+"_"+viewForeignKey.getOwnerTable());

            nameApp =  getNameApp(viewForeignKey.getTableReferences()).trim();
            if (handlePrefix.getPrefixTableOracle(nameApp) != null)
                 viewForeignKey.setTableReferences(handlePrefix.getPrefixTableOracle(nameApp)+"_"+viewForeignKey.getTableReferences());

             viewForeignKey.setTableReferences(patternTableNameOracle(viewForeignKey.getTableReferences()));

            return viewForeignKey;
    }


    
    //como no tngo la tabla la saco dirctamnte del nombre
      public String getNameApp(String name) {
         return name.substring(0,name.indexOf("_"));
    }
//,List fields
    public Map commentFields(Map commentsOnField) {

        Set keys = commentsOnField.keySet();
        Iterator itField = keys.iterator();
        Map newMap = new HashMap();

        while(itField.hasNext()){
            String key = (String) itField.next();
            String newKey = patternFieldName(key);
            String comment = (String) commentsOnField.get(key);
            newMap.put(newKey, comment);
        }
        return newMap;
    }

}
