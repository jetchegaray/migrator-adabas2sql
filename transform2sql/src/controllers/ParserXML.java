/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controllers;


import model.TableAttunity;
import org.jdom.DataConversionException;
import xmlfactories.FactoryTableAttunity;
import xmlfactories.FactoryKeys;
import xmlfactories.FactoryGruop;
import xmlfactories.FactoryField;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import model.Key;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import views.DialogOnlyAccept;

/**
 *
 * @author javimetal
 */
public class ParserXML {

    private Element rootElement;

    public ParserXML() {}

    
    public void build(BufferedReader xmlAttunity){

        SAXBuilder builder= new SAXBuilder(false);
        Document document = null;

        try {
            document = (Document) builder.build(xmlAttunity);
        } catch (Exception ex){
             DialogOnlyAccept dialog = new DialogOnlyAccept(null, true,"El Archivo XML no se encuentra bien formado");
             dialog.setVisible(true);
             dialog.dispose();
        } 

        this.rootElement = document.getRootElement().getChild("table");
    }

    

 
    private List getFields(Element element){

        List fields = new ArrayList();
        Element xmlChild = null;
        List l = element.getChildren();
        List xmlChildren = null;

        if (element.getChild("fields") == null)
            return null;

        xmlChildren = element.getChild("fields").getChildren();
        Iterator itXmlChildren = xmlChildren.iterator();
        
        while(itXmlChildren.hasNext()){
            try {
                xmlChild = (Element) itXmlChildren.next();
                //si es un tag field paso al que sigue.
                if (!xmlChild.getName().equals("field")) {
                    continue;
                }
                if (xmlChild.getAttributeValue("name").equals("ISN")) {
                    continue;
                }
                if (xmlChild.getAttribute("counter") != null && xmlChild.getAttribute("counter").getBooleanValue()) {
                    Element xmlChildPeriodic = (Element) itXmlChildren.next();
                    if (xmlChildPeriodic.getAttribute("counterName") != null && xmlChildPeriodic.getAttributeValue("counterName").equals(xmlChild.getAttributeValue("name"))) {
                        if (xmlChildPeriodic.getAttribute("size") != null && xmlChildPeriodic.getAttribute("dimension1") != null) {
                            int aux = -1;
                            try {
                                aux = xmlChildPeriodic.getAttribute("dimension1").getIntValue() * xmlChildPeriodic.getAttribute("size").getIntValue();
                            } catch (DataConversionException ex) {
                            }
                            if (aux != -1) {
                                xmlChildPeriodic.getAttribute("size").setValue(String.valueOf(aux));
                                xmlChild = xmlChildPeriodic;
                            }
                        }
                    } else {
                        //es un grupo multiple de los que van a la lista de grupos
                        //por lo tanto el campo que tenia el conuter se ignora
                        //el campo que levante depues es un campo cualquiera,el siguiente.
                        xmlChild = xmlChildPeriodic;
                        continue;
                    }
                }
                fields.add(FactoryField.createField(xmlChild));

            } catch (DataConversionException ex) {
            }
        }
        return fields;
    }




    
    
    public List getGroups(){

        List groups = new ArrayList();
        List fieldsInGroup;
        Element xmlChild = null;
        List xmlChildren = null;

         if (this.rootElement.getChild("fields") == null || this.rootElement.getChild("fields").getChildren("group") == null)
             return null;

        xmlChildren = this.rootElement.getChild("fields").getChildren("group");
        Iterator itXmlChildren = xmlChildren.iterator();


        while(itXmlChildren.hasNext()){

            xmlChild = (Element)itXmlChildren.next();

       //     //si es un tag field paso al que sigue.
         //   if (!xmlChild.getName().equals("group"))
           //     continue;
           if (xmlChild.getAttribute("groupEntry") != null){
               // no lo creo porque es grupo de auditoria
                fieldsInGroup = getFields(xmlChild);
                groups.add(FactoryGruop.createGroup(xmlChild,fieldsInGroup));
            }
        }

        return groups;
    }




    private List getKeys(){

        List keys = new ArrayList();
        Element xmlChild = null;
        List xmlChildren = null;
       
        if (this.rootElement.getChild("keys") == null)
            return null;

        xmlChildren = this.rootElement.getChild("keys").getChildren();
        Iterator itXmlChildren = xmlChildren.iterator();

        while(itXmlChildren.hasNext()){
            xmlChild = (Element)itXmlChildren.next();
            Key key = FactoryKeys.createKey(xmlChild);
            if (key.isUnique())
                keys.add(key);
        }

        return keys;
    }



    public TableAttunity getTable(){

        return FactoryTableAttunity.createTable(this.rootElement,getFields(this.rootElement),getKeys());
    }


}
