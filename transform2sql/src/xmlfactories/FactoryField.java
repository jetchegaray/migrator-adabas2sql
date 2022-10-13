/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package xmlfactories;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.DataConversionException;
import org.jdom.Element;
import model.Field;

/**
 *
 * @author javimetal
 */
public class FactoryField {

    public static Field createField(Element xmlField){
        Field field = new Field();

        if (xmlField.getAttribute("name") != null)
            field.setName(xmlField.getAttributeValue("name"));

        if (xmlField.getAttribute("datatype") != null)
            field.setDataType(xmlField.getAttributeValue("datatype"));

        if (xmlField.getAttribute("nullSuppressed") != null)
            field.setIsNullSupppressed(xmlField.getAttributeValue("nullSuppressed").equals("true"));

         if (xmlField.getAttribute("counterName") != null)
            field.setCounterName(xmlField.getAttributeValue("counterName"));
        
        try {

            if (xmlField.getAttribute("size") != null)
                field.setSize(xmlField.getAttribute("size").getIntValue());

            if (xmlField.getAttribute("autoincrement") != null)
                field.setAutoincrement(xmlField.getAttribute("autoincrement").getBooleanValue());

            if (xmlField.getAttribute("rewind") != null)
                field.setAutoincrement(xmlField.getAttribute("rewind").getBooleanValue());

            if (xmlField.getAttribute("nonUpdateable") != null)
                field.setAutoincrement(xmlField.getAttribute("nonUpdateable").getBooleanValue());

            if (xmlField.getAttribute("scale") != null)
                field.setScale(xmlField.getAttribute("scale").getIntValue());

            if (xmlField.getAttribute("dimension1") != null)
                field.setDimension1(xmlField.getAttribute("dimension1").getIntValue());

        } catch (DataConversionException ex) {
            Logger.getLogger(FactoryField.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }

        return field;
    }

}
