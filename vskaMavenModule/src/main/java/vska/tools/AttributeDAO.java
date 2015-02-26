package vska.tools;

import vska.meta.Attribute;
import vska.meta.ObjectType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Seva
 * Date: 20.02.15
 * Time: 23:16
 * To change this template use File | Settings | File Templates.
 */
public class AttributeDAO {

    private static AttributeDAO instance = new AttributeDAO();
    private Connection connection;

    public static AttributeDAO getInstance() {
        return instance;
    }

    public void createAttribute(final String attributeName, final Integer typeId, final String objectTypeName) {
        connection = JDBCPostgre.getConnection();
        try {
            connection.setAutoCommit(Boolean.FALSE);
            final String createQuery =
                    " INSERT INTO "+JDBCPostgre.ATTRIBUTE_TABLE+
                    " (id, name, type_id, object_type_id) " +
                    " VALUES (?, ?, ?, ?)";

            final PreparedStatement preparedStatement = connection.prepareStatement(createQuery);

            final Integer attributeId = JDBCPostgre.getIDForTable(JDBCPostgre.ATTRIBUTE_TABLE);
            final Integer objecTypeId = ObjectTypeDAO.getInstance().getIdByName(objectTypeName);

            preparedStatement.setInt(1, attributeId);
            preparedStatement.setString(2, attributeName);
            preparedStatement.setInt(3, typeId);
            preparedStatement.setInt(4, objecTypeId);

            preparedStatement.execute();
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
                throw new RuntimeException(e1);
            }
        }
    }

    public void deleteAttribute(final String attributeName, final String objectTypeName) {
        connection = JDBCPostgre.getConnection();
        try {
            connection.setAutoCommit(Boolean.FALSE);
            final String deleteQuery =
                    "DELETE FROM "+JDBCPostgre.ATTRIBUTE_TABLE+" " +
                            "WHERE name = ? and object_type_id = ?";

            final PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);

            final Integer objecTypeId = ObjectTypeDAO.getInstance().getIdByName(objectTypeName);

            preparedStatement.setString(1, attributeName);
            preparedStatement.setInt(2, objecTypeId);

            preparedStatement.execute();
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
                throw new RuntimeException(e1);
            }
        }
    }

    public void flushToDB(Attribute attribute) {
        connection = JDBCPostgre.getConnection();
        try {
            connection.setAutoCommit(Boolean.FALSE);
            final String updateQuery =
                    "UPDATE attribute SET name = ? WHERE id = ?";

            final PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);

            preparedStatement.setString(1, attribute.getName());
            preparedStatement.setInt(2, attribute.getId());

            preparedStatement.execute();
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
                throw new RuntimeException(e1);
            }
        }
    }

    public List<String> getNamesOfAttributesByObjectType (String objectName) {
        connection = JDBCPostgre.getConnection();
        try {
            final List<String> res = new ArrayList<String>();

            final String getNamesQuery =
                    "SELECT attr.name attrname FROM "+JDBCPostgre.ATTRIBUTE_TABLE+" attr "+
                    " INNER JOIN "+JDBCPostgre.OBJECTTYPES_TABLE+" ot "+
                    " ON attr.object_type_id = ot.id " +
                    " WHERE ot.name = ?";
            final PreparedStatement preparedStatement = connection.prepareStatement(getNamesQuery);
            preparedStatement.setString(1, objectName);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                res.add(resultSet.getString("attrname"));
            }
            connection.close();
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Attribute getAttributeByNameAndOT(String attrName, String objectTypeName) {
        connection = JDBCPostgre.getConnection();
        try {
            Attribute attribute = null;

            final String getNamesQuery =
                    "SELECT attr.id, attr.type_id FROM "+JDBCPostgre.ATTRIBUTE_TABLE+" attr " +
                    "WHERE attr.name = ? and attr.object_type_id = ?";

            final PreparedStatement preparedStatement = connection.prepareStatement(getNamesQuery);
            preparedStatement.setString(1, attrName);
            preparedStatement.setInt(2, ObjectTypeDAO.getInstance().getIdByName(objectTypeName));

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                final Integer attrId = resultSet.getInt("id");
                final Integer typeId = resultSet.getInt("type_id");
                attribute = new Attribute(attrId, attrName, typeId);
            }
            connection.close();
            return attribute;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Integer> getAttributeIdsByObjectType (String objectTypeName) {
        connection = JDBCPostgre.getConnection();
        try {
            final List<Integer> res = new ArrayList<Integer>();

            final String getNamesQuery =
                    "SELECT attr.id attrid FROM "+JDBCPostgre.ATTRIBUTE_TABLE+" attr "+
                            " INNER JOIN "+JDBCPostgre.OBJECTTYPES_TABLE+" ot "+
                            " ON attr.object_type_id = ot.id " +
                            " WHERE ot.name = ?";
            final PreparedStatement preparedStatement = connection.prepareStatement(getNamesQuery);
            preparedStatement.setString(1, objectTypeName);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                res.add(Integer.valueOf(resultSet.getString("attrid")));
            }
            connection.close();
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Attribute> getAttributeByObjectType (String objectTypeName) {
        connection = JDBCPostgre.getConnection();
        try {
            final List<Attribute> res = new ArrayList<Attribute>();
            final ObjectType objectType = ObjectTypeDAO.getInstance().getObjectType(objectTypeName);
            if (objectType == null) {
                return res;
            }

            final String getNamesQuery =
                    "SELECT attr.id, attr.name attrname, attr.type_id FROM "+JDBCPostgre.ATTRIBUTE_TABLE+" attr " +
                            "WHERE attr.object_type_id = ?";
            final PreparedStatement preparedStatement = connection.prepareStatement(getNamesQuery);
            preparedStatement.setInt(1, objectType.getId());

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                final Integer attrId = resultSet.getInt("id");
                final String attrName = resultSet.getString("attrname");
                final Integer typeId = resultSet.getInt("type_id");

                Attribute attribute = new Attribute(attrId, attrName, typeId);
                res.add(attribute);
            }
            connection.close();
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Attribute getAttributeById (Integer attributeId) {
        connection = JDBCPostgre.getConnection();
        try {

            final String getNamesQuery =
                    "SELECT attr.id, attr.name attrname, attr.type_id FROM "+JDBCPostgre.ATTRIBUTE_TABLE+" attr " +
                            "WHERE attr.id = ?";
            final PreparedStatement preparedStatement = connection.prepareStatement(getNamesQuery);
            preparedStatement.setInt(1, attributeId);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                final Integer attrId = resultSet.getInt("id");
                final String attrName = resultSet.getString("attrname");
                final Integer typeId = resultSet.getInt("type_id");

                Attribute attribute = new Attribute(attrId, attrName, typeId);
                connection.close();
                return attribute;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Attribute getAttributeByName (String attributeName) {
        connection = JDBCPostgre.getConnection();
        try {

            final String getNamesQuery =
                    "SELECT attr.id, attr.name attrname, attr.type_id FROM "+JDBCPostgre.ATTRIBUTE_TABLE+" attr " +
                            "WHERE attr.name = ?";
            final PreparedStatement preparedStatement = connection.prepareStatement(getNamesQuery);
            preparedStatement.setString(1, attributeName);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                final Integer attrId = resultSet.getInt("id");
                final String attrName = resultSet.getString("attrname");
                final Integer typeId = resultSet.getInt("type_id");

                Attribute attribute = new Attribute(attrId, attrName, typeId);
                connection.close();
                return attribute;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
