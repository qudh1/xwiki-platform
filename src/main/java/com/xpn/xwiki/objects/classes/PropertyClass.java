/**
 * ===================================================================
 *
 * Copyright (c) 2003 Ludovic Dubost, All rights reserved.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details, published at
 * http://www.gnu.org/copyleft/gpl.html or in gpl.txt in the
 * root folder of this distribution.
 *
 * Created by
 * User: Ludovic Dubost
 * Date: 9 d�c. 2003
 * Time: 13:41:33
 */
package com.xpn.xwiki.objects.classes;

import com.xpn.xwiki.XWikiContext;
import com.xpn.xwiki.XWikiException;
import com.xpn.xwiki.objects.BaseCollection;
import com.xpn.xwiki.objects.BaseProperty;
import com.xpn.xwiki.objects.PropertyInterface;
import com.xpn.xwiki.objects.meta.MetaClass;
import com.xpn.xwiki.objects.meta.PropertyMetaClass;
import org.apache.ecs.xhtml.input;
import org.dom4j.Element;
import org.dom4j.dom.DOMElement;
import org.hibernate.mapping.Property;
import org.hibernate.mapping.Column;

import java.util.Iterator;
import java.util.List;

public class PropertyClass extends BaseCollection implements PropertyClassInterface, PropertyInterface {
    private BaseClass object;
    private int id;
    private PropertyMetaClass pMetaClass;

    public PropertyClass() {
    }

    public PropertyClass(String name, String prettyname, PropertyMetaClass xWikiClass) {
        super();
        setName(name);
        setPrettyName(prettyname);
        setxWikiClass(xWikiClass);
        setUnmodifiable(false);
    }

    public BaseClass getxWikiClass() {
        if (pMetaClass==null) {
        MetaClass metaClass = MetaClass.getMetaClass();
        pMetaClass = (PropertyMetaClass)metaClass.get(getClassType());
        }
        return pMetaClass;
    }

    public void setxWikiClass(BaseClass xWikiClass) {
        this.pMetaClass = (PropertyMetaClass) xWikiClass;
    }

    public BaseCollection getObject() {
            return object;
   }

    public void setObject(BaseCollection object) {
            this.object = (BaseClass)object;
    }

    public int getId() {
        if (getObject()==null)
            return id;
        else
            return getObject().getId();
    }

    public void setId(int id) {
        this.id = id;
    }

    public void checkField(String name) throws XWikiException {
        // Let's stop checking
        /*if ((getxWikiClass(context).safeget(name)==null)&&
            (getxWikiClass(context).safeget("meta" + name)==null)){
            Object[] args = { name, getxWikiClass(context).getName() };
            throw new XWikiException( XWikiException.MODULE_XWIKI_CLASSES, XWikiException.ERROR_XWIKI_CLASSES_FIELD_DOES_NOT_EXIST,
                    "Field {0} does not exist in class {1}", null, args);
        }
        */
    }

    public String toString(BaseProperty property) {
        return property.toText();
    }

    public BaseProperty fromString(String value) {
        return null;
    }

    public BaseProperty newPropertyfromXML(Element ppcel) {
        String value = ppcel.getText();
        return fromString(value);
    }

    public void displayHidden(StringBuffer buffer, String name, String prefix, BaseCollection object, XWikiContext context) {
       input input = new input();
       PropertyInterface prop = (PropertyInterface) object.safeget(name);
       if (prop!=null) input.setValue(prop.toFormString());

       input.setType("hidden");
       input.setName(prefix + name);
       buffer.append(input.toString());
    }

    public void displaySearch(StringBuffer buffer, String name, String prefix, BaseCollection object, XWikiContext context) {
        input input = new input();
        BaseProperty prop = (BaseProperty) object.safeget(name);
        if (prop!=null) input.setValue(prop.toFormString());

        input.setType("text");
        input.setName(prefix + name);
        buffer.append(input.toString());
    }

    public void displayView(StringBuffer buffer, String name, String prefix, BaseCollection object, XWikiContext context) {
        buffer.append(((BaseProperty)object.safeget(name)).toText());
    }

    public void displayEdit(StringBuffer buffer, String name, String prefix, BaseCollection object, XWikiContext context) {
        input input = new input();

        BaseProperty prop = (BaseProperty) object.safeget(name);
        if (prop!=null) input.setValue(prop.toFormString());

        input.setType("text");
        input.setName(prefix + name);
        buffer.append(input.toString());
    }

    public String displayHidden(String name, String prefix, BaseCollection object, XWikiContext context) {
      StringBuffer buffer = new StringBuffer();
      displayHidden(buffer, name, prefix, object, context);
      return buffer.toString();
    }
    public String displayHidden(String name, BaseCollection object, XWikiContext context) {
      return displayHidden(name, "", object, context);
    }

    public String displaySearch(String name, String prefix, BaseCollection object, XWikiContext context) {
      StringBuffer buffer = new StringBuffer();
      displaySearch(buffer, name, prefix, object, context);
      return buffer.toString();
    }
    public String displaySearch(String name, BaseCollection object, XWikiContext context) {
      return displaySearch(name, "", object, context);
    }

    public String displayView(String name, String prefix, BaseCollection object, XWikiContext context) {
      StringBuffer buffer = new StringBuffer();
      displayView(buffer, name, prefix, object, context);
      return buffer.toString();
    }
    public String displayView(String name, BaseCollection object, XWikiContext context) {
      return displayView(name, "", object, context);
    }

    public String displayEdit(String name, String prefix, BaseCollection object, XWikiContext context) {
      StringBuffer buffer = new StringBuffer();
      displayEdit(buffer, name, prefix, object, context);
      return buffer.toString();
    }

    public String displayEdit(String name, BaseCollection object, XWikiContext context) {
      return displayEdit(name, "", object, context);
    }


    public BaseClass getxWikiClass(XWikiContext context) {
        return getxWikiClass();
    }

    public String getClassName() {
        BaseClass bclass = getxWikiClass();
        return (bclass==null) ? "" : bclass.getName();
    }

    // In property classes we need to store this info in the HashMap for fields
    // This way it is readable by the displayEdit/displayView functions..
    public String getName() {
        return getStringValue("name");
    }

    public void setName(String name) {
      setStringValue("name", name);
    }

    public String getPrettyName() {
        return getStringValue("prettyName");
    }

    public void setPrettyName(String prettyName) {
        setStringValue("prettyName", prettyName);
    }

    public int getNumber() {
        return getIntValue("number");
    }

    public void setNumber(int number) {
      setIntValue("number", number);
    }

    public String getClassType() {
        return getClass().getName();
    }

    public void setClassType(String type) {
    }

    public Object clone() {
        PropertyClass pclass = (PropertyClass) super.clone();
        pclass.setObject(getObject());
        pclass.setClassType(getClassType());
        return pclass;
    }

    public Element toXML(BaseClass bclass) {
        return toXML();
    }

    public Element toXML() {
         Element pel = new DOMElement(getName());
         Iterator it = getFieldList().iterator();
         while (it.hasNext()) {
           BaseProperty bprop = (BaseProperty)it.next();
           pel.add(bprop.toXML());
         }
        Element el = new DOMElement("classType");
        el.addText(getClassType());
        pel.add(el);
        return pel;
     }

    public void fromXML(Element pcel) {
     List list = pcel.elements();
     BaseClass bclass = getxWikiClass();

     for (int i=0;i<list.size();i++) {
        Element ppcel = (Element) list.get(i);
        String name = ppcel.getName();
        PropertyClass pclass = (PropertyClass) bclass.safeget(name);
        if (pclass!=null) {
         BaseProperty bprop = pclass.newPropertyfromXML(ppcel);
         bprop.setObject(this);
         safeput(name, bprop);
        }
     }
    }

    public String toFormString() {
        return toString();
    }

    public void initLazyCollections() {
    }

    public boolean isUnmodifiable() {
        return (getIntValue("unmodifiable")==1);
    }

    public void setUnmodifiable(boolean unmodifiable) {
        if (unmodifiable)
         setIntValue("unmodifiable", 1);
        else
         setIntValue("unmodifiable", 0);
    }

    public BaseProperty fromStringArray(String[] strings) {
        return fromString(strings[0]);
    }

    public boolean isValidColumnTypes(Property hibprop) {
        return true;
    }

    public BaseProperty fromValue(Object value) {
        BaseProperty property= newProperty();
        property.setValue(value);
        return property;
    }

    public BaseProperty newProperty() {
        return new BaseProperty();
    }
}
