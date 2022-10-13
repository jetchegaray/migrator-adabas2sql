/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 *
 * @author javimetal
 */
public class HandlePrefixesOracle {

    private String contentFile;
   
    public HandlePrefixesOracle(File inputFile){
        readFile(inputFile);
    }
    
     public HandlePrefixesOracle(){}

    private void readFile(File inputFile) {

        BufferedReader br = null;
        char bufferContent[] = null;
        int index = inputFile.getAbsolutePath().lastIndexOf("\\");
        String path = inputFile.getAbsolutePath().substring(0,index+1);
        
        File fileTxt = new File(path+"prefijos_tablas.txt");
        inputFile.getPath();

        try {
             BufferedReader bufferReader=new  BufferedReader(new FileReader(fileTxt));
             bufferContent = new char[(int)fileTxt.length()];
             bufferReader.read(bufferContent,0, (int) fileTxt.length());
        }
        catch(Exception e){
         e.printStackTrace();
       }
       this.contentFile =  String.valueOf(bufferContent, 0, (int) fileTxt.length());
    }



    //a partir de un prefijo de adabas me da el prefijo correspondiente
    // a la tabla oracle, desde el archivo.
    //se llama en el transform.
    public String getPrefixTableOracle(String prefixXml){

        String prefixAttunity = null;

        if (this.contentFile.contains(prefixXml)){
            int index = this.contentFile.indexOf(prefixXml);
            int indexEndLine = this.contentFile.indexOf("\n",index);

            prefixAttunity = this.contentFile.substring(index+prefixXml.length(),indexEndLine).trim();
        }
        return prefixAttunity;
    }


    //a partir de un cnombre de tabla lo convierte con guiones
    //y busca e prefijo que deberian tener los campos que forman
    //parte de la misma
    //sino lo encuentra debe retornar NULL ¡¡¡¡
    public String getPrefixFieldFromTableName(String tableName) {
        String prefixName = null;

        tableName = tableName.replaceAll("_U_", "-").replaceAll("_","-");

        int index = this.contentFile.indexOf(tableName);

        if (index != -1){
            prefixName = this.contentFile.substring(index-11, index-11+3);
        }

        return prefixName;
    }

    public String getTableNameFromPrefixFieldName(String appName, String prefixField) throws RuntimeException {

        String result = null;
        String toSearh = prefixField+" "+appName+"-"+prefixField+" ";
        int index = this.contentFile.indexOf(toSearh);
        if (index != -1){
            result = this.contentFile.substring(index+toSearh.length(),this.contentFile.indexOf("\n",index)-1);
        }
        if (result != null)
            result.replaceAll("-","_");
        
        return result;
    }

   
}
