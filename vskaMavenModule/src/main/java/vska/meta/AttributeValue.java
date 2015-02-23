package vska.meta;

import vska.tools.MetaObjectDAO;

/**
 * Created with IntelliJ IDEA.
 * User: Seva
 * Date: 14.02.15
 * Time: 20:50
 * To change this template use File | Settings | File Templates.
 */
public class AttributeValue {

    private Attribute attribute;
    private String value;
    private MetaObject reference;

    public AttributeValue (Attribute attribute, String value) {
        final Integer attrTypeId = attribute.getTypeId();
        if (Attribute.ATTR_TYPE_REFERENCE_ID.equals(attrTypeId)) {
            final MetaObject reference = value != null ? MetaObjectDAO.getInstance().getObjectById(Integer.valueOf(value)) : null;
            this.attribute = attribute;
            this.reference = reference;
            this.value = reference != null ? reference.getName() : null;
        } else {
            this.attribute = attribute;
            this.value = value;
        }
    }

    public AttributeValue (Attribute attribute, MetaObject reference) {
        this.attribute = attribute;
        this.reference = reference;
        this.value = reference != null ? reference.getName() : null;
    }

    @Override
    public boolean equals (Object object) {
        if (!(object instanceof AttributeValue)) {
            return false;
        }

        return value.equals(((AttributeValue) object).value) &&
               attribute.equals(((AttributeValue) object).attribute);
    }

    @Override
    public int hashCode () {
        int hash = 17;
        hash = hash * 31 + (attribute == null ? 0 : attribute.hashCode());
        hash = hash * 31 + (value == null ? 0 : value.hashCode());
        return hash;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public MetaObject getReference() {
        return reference;
    }

    public void setReference(MetaObject reference) {
        this.reference = reference;
    }
}
