/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/*
package controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;


public class HandleForeignKeys {

  private String contentFile;

    public HandleForeignKeys(File inputFile){
        readFile(inputFile);
    }

    


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
       this.contentFile = this.contentFile.replaceAll("\t", " ");
    }




    public boolean belongsTheTable(String appName, String tableName) {
        int index = this.contentFile.indexOf(appName);
        int index2 = this.contentFile.indexOf(tableName.replaceAll("_", "-"));

        if (index != -1 && index2 != -1 && index2-(index+appName.length()) < 8){
            return true;
        }
       
        return false;
    }


    public String getTableReferences_5c(String nameField,String tableName){

        String fieldPrefix = nameField.substring(0,nameField.indexOf("_"));
        String tableReferences = null;
        int index;

        if ((index = this.contentFile.indexOf(fieldPrefix)) != -1){
            tableReferences = this.contentFile.substring(index,index+8);
        }

        return tableReferences;
    }
    

  
}
*/