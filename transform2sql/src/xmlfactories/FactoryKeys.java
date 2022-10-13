/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package xmlfactories;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.DataConversionException;
import org.jdom.Element;
import model.Key;

/**
 *
 * @author javimetal
 */
public class FactoryKeys {

    public static Key createKey(Element xmlChild) {
        
        Key key = new Key();
        key.setName(xmlChild.getAttributeValue("name"));
          
        try {
            if (xmlChild.getAttribute("bestUnique") != null) {
                key.setBestUnique(xmlChild.getAttribute("bestUnique").getBooleanValue());
            }

            if (xmlChild.getAttribute("unique") != null) {
                key.setUnique(xmlChild.getAttribute("unique").getBooleanValue());
            }
            if (xmlChild.getAttribute("size") != null) {
                key.setSize(xmlChild.getAttribute("size").getIntValue());
            }

            if (xmlChild.getChild("segments") != null && xmlChild.getChild("segments").getChildren("segment") != null){

                List compousedKey = new ArrayList();
                Iterator itCompousedKey = xmlChild.getChild("segments").getChildren("segment").iterator();
                Element element;

                while (itCompousedKey.hasNext()){

                    element = ((Element)itCompousedKey.next());
//                    if (!element.getAttributeValue("name").equals(key.getName())){
                       compousedKey.add(element.getAttributeValue("name"));
//                    }
                }
                key.setCompousedKey(compousedKey);
            }
        } catch (DataConversionException ex) {
            Logger.getLogger(FactoryKeys.class.getName()).log(Level.SEVERE, null, ex);
        }

        return key;
    }
    
}
