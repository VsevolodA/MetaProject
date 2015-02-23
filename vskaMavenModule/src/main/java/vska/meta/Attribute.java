package vska.meta;

/**
 * Created with IntelliJ IDEA.
 * User: Seva
 * Date: 14.02.15
 * Time: 20:50
 * To change this template use File | Settings | File Templates.
 */
public class Attribute {

    public static Integer ATTR_TYPE_TEXT_ID = 0;
    public static Integer ATTR_TYPE_NUMBER_ID = 1;
    public static Integer ATTR_TYPE_REFERENCE_ID = 2;

    public static String ATTR_TYPE_TEXT = "Text";
    public static String ATTR_TYPE_NUMBER = "Number";
    public static String ATTR_TYPE_REFERENCE = "Reference";

    private Integer id;
    private String name;
    private Integer typeId;

    public static Integer getTypeId (final String typeName) {
        if (ATTR_TYPE_TEXT.equals(typeName)) {
            return ATTR_TYPE_TEXT_ID;
        } else if (ATTR_TYPE_NUMBER.equals(typeName)) {
            return ATTR_TYPE_NUMBER_ID;
        } else if (ATTR_TYPE_REFERENCE.equals(typeName)) {
            return ATTR_TYPE_REFERENCE_ID;
        } else {
            return ATTR_TYPE_TEXT_ID;
        }
    }

    @Override
    public boolean equals (Object object) {
        if (!(object instanceof Attribute)) {
            return false;
        }

        return id.equals(((Attribute) object).id);
    }

    @Override
    public int hashCode () {
        return id.hashCode();
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

    public Attribute (Integer id, String name, Integer typeId) {
        this.id = id;
        this.name = name;
        this.typeId = typeId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }
}
