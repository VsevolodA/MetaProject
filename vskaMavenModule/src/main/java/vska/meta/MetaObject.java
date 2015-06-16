package vska.meta;

import vska.tools.sql.MetaObjectDAO;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Seva
 * Date: 14.02.15
 * Time: 20:49
 * To change this template use File | Settings | File Templates.
 */
public class MetaObject {

    private ObjectType objectType;
    private Integer id;
    private String name;
    private Map<Attribute, AttributeValue> parameters;

    public ObjectType getObjectType() {
        return objectType;
    }

    public void setObjectType(ObjectType objectType) {
        this.objectType = objectType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MetaObject(Integer id, String name, ObjectType objectType) {
        this.id = id;
        this.name = name;
        this.objectType = objectType;
        parameters = MetaObjectDAO.getInstance().getParameters(this);
    }

    public MetaObject() {}

    public MetaObject(Integer id, String name, ObjectType objectType, Map<Attribute, AttributeValue> parameters) {
        this.id = id;
        this.name = name;
        this.objectType = objectType;
        this.parameters = parameters;
    }

    public Map<Attribute, AttributeValue> getParameters() {
        return parameters;
    }

    public void setParameters(Map<Attribute, AttributeValue> parameters) {
        this.parameters = parameters;
    }

    @Override
    public boolean equals (Object object) {
        if (!(object instanceof Attribute)) {
            return false;
        }

        return id.equals(((MetaObject) object).id);
    }

    @Override
    public int hashCode () {
        return id.hashCode();
    }
}
