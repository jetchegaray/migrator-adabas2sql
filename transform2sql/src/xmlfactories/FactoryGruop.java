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
import model.Group;

/**
 *
 * @author javimetal
 */
public class FactoryGruop {

    public static Group createGroup(Element xmlGroup,List fieldsInGroup) {
        Group group = new Group();
        
        group.setName(xmlGroup.getAttributeValue("name"));

        try {
            if (xmlGroup.getAttribute("groupEntry") != null){
               group.setGroupEntry(xmlGroup.getAttribute("groupEntry").getBooleanValue());
            }
        } catch (DataConversionException ex) {
            Logger.getLogger(FactoryGruop.class.getName()).log(Level.SEVERE, null, ex);
        }

        group.setFields(fieldsInGroup);
        
        return group;
    }
    
}
