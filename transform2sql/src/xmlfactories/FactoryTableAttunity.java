/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package xmlfactories;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.DataConversionException;
import org.jdom.Element;
import model.TableAttunity;

/**
 *
 * @author javimetal
 */
public class FactoryTableAttunity {

    public static TableAttunity createTable(Element xmlRoot,List fields,List keys){

        TableAttunity tableAttunity = new TableAttunity();

        tableAttunity.setName(xmlRoot.getAttributeValue("name"));
        tableAttunity.setDataSource(xmlRoot.getAttributeValue("datasource"));

        try {
            tableAttunity.setBookmarkSize(xmlRoot.getAttribute("bookmarkSize").getIntValue());
        } catch (DataConversionException ex) {
            Logger.getLogger(FactoryTableAttunity.class.getName()).log(Level.SEVERE, null, ex);
        }

        tableAttunity.setFields(fields);
        tableAttunity.setKeys(keys);

        return tableAttunity;
    }
}
