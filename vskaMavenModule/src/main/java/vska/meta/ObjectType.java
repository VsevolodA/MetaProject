package vska.meta;

import java.math.BigInteger;

/**
 * Created with IntelliJ IDEA.
 * User: Seva
 * Date: 14.02.15
 * Time: 20:49
 * To change this template use File | Settings | File Templates.
 */

public class ObjectType {

    private Integer id;
    private String name;


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

    public ObjectType () {}

    public ObjectType (Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals (Object object) {
        if (!(object instanceof Attribute)) {
            return false;
        }

        return id.equals(((ObjectType) object).id);
    }

    @Override
    public int hashCode () {
        return id.hashCode();
    }
}
