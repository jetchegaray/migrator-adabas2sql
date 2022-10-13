/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import model.Group;
import model.Table;
import model.TableComments;

/**
 *
 * @author javimetal
 */
public class HandleComments{

   private TableComments tableComment;
//   private String contentFileFields;
   private String contentFile;
   private List groups;
 
  
    public HandleComments(Table table,List groups,File pathXmlInputFile) {
        this.tableComment = new TableComments(table);
//        this.contentFileFields = readFiles(pathXmlInputFile,"_sysdic");
        this.contentFile = readFiles(pathXmlInputFile,table.getName());
     }

    
    private String readFiles(File pathXmlInputFile,String nameTable) {
        BufferedReader br = null;
        char bufferContent[] = null;
        int index = pathXmlInputFile.getAbsolutePath().lastIndexOf("\\");
        String path = pathXmlInputFile.getAbsolutePath().substring(0,index+1);

    ///    File fileTxt = new File();
  //      String pathTxt = pathXmlInputFile.getAbsolutePath().replace(".xml",);
        File fileTxt = new File(path+nameTable+"_syspca.txt");
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream (fileTxt),Charset.forName("ISO8859-1"));
            BufferedReader bufferedReader = new BufferedReader (inputStreamReader);
            bufferContent = new char[(int)fileTxt.length()];
            bufferedReader.read(bufferContent,0, (int) fileTxt.length());
        }
        catch(Exception e){
            return null;
       }
       return String.valueOf(bufferContent, 0, (int) fileTxt.length());
    }

    
   public TableComments getTableComment(){
       this.tableComment.generateComments(this.contentFile);
       return this.tableComment;
   }


   public List getGroupsComment(){

       List TablesComments = new ArrayList();

       if (this.groups != null){
            Iterator itGroup = this.groups.iterator();
            while (itGroup.hasNext()){
                this.tableComment.setNewGroupForGenerateComments((Group)itGroup.next());
                this.tableComment.generateComments(this.contentFile);
                TablesComments.add(this.tableComment);
            }
       }
       return TablesComments;
   }


/*
   private void findComments(){

       String beginComment = "Abstract";
       String comment = null;
       Iterator itFields = this.fields.iterator();
       Field field = null,nextField = null;
       int searchFieldIndex = 0,nextFieldIndex = 0,abstractFieldIndex = 0;


       while (itFields.hasNext()){
           field = (Field)itFields.next();
           nextField = (Field)itFields.next();
           //encuentro el primer campo a buscar
           searchFieldIndex = this.fileContent.indexOf(field.getName());
           
           //busco el comentario a partir de donde econcontre el campo 
           abstractFieldIndex = this.fileContent.indexOf(beginComment,searchFieldIndex);

           // busco el campo que sigue al buscado
           nextFieldIndex = this.fileContent.indexOf(nextField.getName());

           // si antes del comentario del campo que busco existe otro nombre de campo
           // es porque el comnetairo no pertenece al campo en cuestion.
           //hipotesis el archivo esta en el mismo orden que como vienen lo campos en el xml.

           if ((nextFieldIndex - searchFieldIndex) > (abstractFieldIndex - searchFieldIndex)){
            //entonces el campo pertenece al campo en cuestion.
                int pointIndex = this.fileContent.indexOf(".", abstractFieldIndex);
                comment = this.fileContent.substring(abstractFieldIndex+beginComment.length()+1,pointIndex+1);
           }
           itFields = (Iterator) field; //dejo el iterador apuntando al current para que no me pase.
           
           this.comments.put(field.getName(), comment);
       }
   }

   
    public String saveSQL() {

   //     String scriptDDL="COMMENT ON TABLE "//+this.nombreTable;
     //   scriptDDL += " IS "//+this.commentTable.

        String scriptDDL= "";
        Field field = null;
        Iterator itFields = this.fields.iterator();

        while (itFields.hasNext()){
           field = (Field)itFields.next();

            scriptDDL ="COMMENT ON TABLE "+field.getName();
            scriptDDL += " IS "+this.comments;
        }

        return scriptDDL;
    }
*/
   
}
