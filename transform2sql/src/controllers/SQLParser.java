package controllers;

import views.ViewTable;
import views.ViewIndex;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import model.ListForeignKeys;
import model.TableComments;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.Statement;
import views.ViewForeignKey;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author javimetal
 */
public class SQLParser {

    private List viewTables;
    private List createTables;
    private List viewIndex; //son desde los create index que se crean y se le pasan a la view table,
                            //para mostrarlos.
    private List tableComments;
    //una fila createComments identifica un elemento de esta lista
    private List viewCases;
    private List viewForeignKeys;


    public SQLParser(){
        this.viewTables = new ArrayList();
        this.createTables =new ArrayList();
        this.viewIndex =new ArrayList();
        this.tableComments = new ArrayList();
        this.viewCases = new ArrayList();
        this.viewForeignKeys = new ArrayList();
    }


    public void parse(String sqlDLL) throws JSQLParserException{
        
        Statement statement = null;
        CCJSqlParserManager pm = new CCJSqlParserManager();
        this.processSeparateCreateTable(sqlDLL);
        Iterator itCreates = this.createTables.iterator();

        while (itCreates.hasNext()){
            statement = pm.parse(new StringReader((String)itCreates.next()));
            ViewTable viewTable = new ViewTable();
            viewTable.processStatement(statement);
            setIndexToViewTable(viewTable);
            this.getViewTables().add(viewTable);
        }
        addCommentsToViewTables();
      //  return this.getViewTables();
    }



    
    private void processSeparateCreateTable(String sqlDLL) {
 
        String toSearch= ";";
        StringTokenizer tokenizer = new StringTokenizer(sqlDLL,toSearch);
        String token;

        while (tokenizer.hasMoreTokens()){
            token = tokenizer.nextToken();
            
            if (token.contains("CREATE") && token.contains("TABLE") && !token.contains("COMMENT")){
                 this.createTables.add(token);
            }else if (token.contains("INDEX")){
                 createViewIndex(token.toUpperCase());
            }else if (token.contains("COMMENT")){
                 createTableComments(token.toUpperCase());
            }else if (token.contains("FOREIGN")){
                this.getViewForeignKeys().add(new ViewForeignKey(token));
            }else{
                 this.getViewCases().add(token);
            }

        }
    }



    
    private void createViewIndex(String createIndex) {
        StringTokenizer tokenizer = new StringTokenizer(createIndex);
        String token;
        ViewIndex viewIndex = new ViewIndex();

        while (tokenizer.hasMoreTokens()){
               
            token = tokenizer.nextToken();
            
            if (token.equals("UNIQUE")){
                viewIndex.setUnique(true);
            }else if (token.equals("INDEX")){
                //el que viene es el nombre del indice
                viewIndex.setName(tokenizer.nextToken());   
            }else if (token.equals("ON")){
                //el que viene es el nombre de la tabla
                viewIndex.setOnTable(tokenizer.nextToken("("));
            }
        }

        //lista de campos que forman el indice, en su defecto, uno solo
        String keys = createIndex.substring(createIndex.indexOf("(")+1,createIndex.indexOf(")"));
        tokenizer = new StringTokenizer(keys,",");
        String tokenKey;
        List compousedKeys = new ArrayList();

        while (tokenizer.hasMoreTokens()){
            tokenKey = tokenizer.nextToken();
            compousedKeys.add(tokenKey);
        }
        viewIndex.setCompousedKey(compousedKeys);
        this.viewIndex.add(viewIndex);
    }



  


    private void createTableComments(String createComment) {
        //cargar el this.commentsOnTable
        StringTokenizer tokenizer = new StringTokenizer(createComment," ");
        String token;
        TableComments tableComments = new TableComments();
        boolean isField = false;
        String nameField = null;
        
        while (tokenizer.hasMoreTokens()){

            token = ((String)tokenizer.nextToken()).trim();
            String nextToken;

            if (token.equals("TABLE") || token.equals("COLUMN")){
                nextToken = tokenizer.nextToken();

                if (nextToken.contains(".")){
                    tableComments.setTableName(nextToken.substring(0,nextToken.indexOf(".")));
                    nameField =  nextToken.substring(nextToken.indexOf(".")+1);
                }else{
                    tableComments.setTableName(nextToken);
                }

            }else if (token.equals("IS")){
                if (nameField == null){
                    tableComments.setCommentOnTable( tokenizer.nextToken(";").replaceAll("'",""));
                } else {
                    String d =  tokenizer.nextToken(";");
                    tableComments.getCommentsOnFields().put(nameField,d);
                }
            }
        }
          this.tableComments.add(tableComments);
    }
    





    
    private void setIndexToViewTable(ViewTable viewTable) {
        Iterator itViewIndex = this.viewIndex.iterator();
        List viewIndexOnTable = new ArrayList();

        while (itViewIndex.hasNext()){
            ViewIndex viewIndex = (ViewIndex) itViewIndex.next();
            if (viewIndex.getOnTable().trim().equals(viewTable.getName())){
                viewIndexOnTable.add(viewIndex);
            }
        }
        viewTable.setIndexs(viewIndexOnTable);
    }




    
    private void addCommentsToViewTables() {
        Iterator itCommentsOnTable = (Iterator)  this.tableComments.iterator();

        while (itCommentsOnTable.hasNext()){
            TableComments tableComment = (TableComments) itCommentsOnTable.next();
            Iterator itViewTables = this.getViewTables().iterator();

            while (itViewTables.hasNext()){
                ViewTable viewTable = (ViewTable) itViewTables.next();

                
                if (viewTable.getName().equals(tableComment.getTableName())){
                    //solo pongo el comentario si no es el de la tabla, si pertenece
                    // el comment a una campo no la sobrescribo.
                    if (tableComment.getCommentOnTable() != null)
                        viewTable.setComments(tableComment.getCommentOnTable());
                    
                    viewTable.getCommentsOnField().putAll(tableComment.getCommentsOnFields());
                }
            }
        }
    }

    /**
     * @return the viewCases
     */
    public List getViewCases() {
        return viewCases;
    }

    /**
     * @return the ViewForeignKey
     */
    public List getViewForeignKeys() {
        return viewForeignKeys;
    }

    /**
     * @return the viewTables
     */
    public List getViewTables() {
        return viewTables;
    }

}
