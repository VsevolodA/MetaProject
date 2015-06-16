package vska.tools.sql;

import vska.meta.Attribute;
import vska.meta.AttributeValue;
import vska.meta.MetaObject;
import vska.meta.ObjectType;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Seva
 * Date: 15.02.15
 * Time: 23:50
 * To change this template use File | Settings | File Templates.
 */
public class MetaObjectDAO {

    private static MetaObjectDAO instance = new MetaObjectDAO();
    private Connection connection;

    public static MetaObjectDAO getInstance() {
        return instance;
    }

    public void createObject (String name, String objectTypeName) {
        connection = JDBCPostgre.getConnection();
        try {
            connection.setAutoCommit(Boolean.FALSE);

            final String createQuery =
                    "INSERT INTO "+JDBCPostgre.METAOBJECTS_TABLE+" (id, object_type_id, name) " +
                            "values (?, ?, ?)";

            final String maxIdQuery = "select max(id) from " + JDBCPostgre.METAOBJECTS_TABLE;

            final int id = JDBCPostgre.getIDForTable(JDBCPostgre.METAOBJECTS_TABLE);

            Integer objectTypeId = ObjectTypeDAO.getInstance().getIdByName(objectTypeName);

            final PreparedStatement statement = connection.prepareStatement(createQuery);
            statement.setInt(1, id);
            statement.setInt(2, objectTypeId);
            statement.setString(3, name);

            statement.execute();
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e2) {
                throw new RuntimeException(e2);
            }
            e.printStackTrace();
        }
    }

    public void createObject (String name, String objectTypeName, Integer id) {
        connection = JDBCPostgre.getConnection();
        try {
            connection.setAutoCommit(Boolean.FALSE);

            final String createQuery =
                    "INSERT INTO "+JDBCPostgre.METAOBJECTS_TABLE+" (id, object_type_id, name) " +
                            "values (?, ?, ?)";

            Integer objectTypeId = ObjectTypeDAO.getInstance().getIdByName(objectTypeName);

            final PreparedStatement statement = connection.prepareStatement(createQuery);
            statement.setInt(1, id);
            statement.setInt(2, objectTypeId);
            statement.setString(3, name);

            statement.execute();
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e2) {
                throw new RuntimeException(e2);
            }
            e.printStackTrace();
        }
    }

    public MetaObject createAndReturnObject (String name, String objectTypeName) {
        connection = JDBCPostgre.getConnection();
        try {
            connection.setAutoCommit(Boolean.FALSE);

            final String createQuery =
                    "INSERT INTO "+JDBCPostgre.METAOBJECTS_TABLE+" (id, object_type_id, name) " +
                            "values (?, ?, ?)";

            final String maxIdQuery = "select max(id) from " + JDBCPostgre.METAOBJECTS_TABLE;

            final int id = JDBCPostgre.getIDForTable(JDBCPostgre.METAOBJECTS_TABLE);

            Integer objectTypeId = ObjectTypeDAO.getInstance().getIdByName(objectTypeName);

            final PreparedStatement statement = connection.prepareStatement(createQuery);
            statement.setInt(1, id);
            statement.setInt(2, objectTypeId);
            statement.setString(3, name);

            statement.execute();

            connection.commit();
            connection.close();
            return getObjectById(id);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e2) {
                throw new RuntimeException(e2);
            }
            e.printStackTrace();
        }
        return null;
    }

    public void deleteObject (String name, String objectTypeName) {
        connection = JDBCPostgre.getConnection();
        try {
            connection.setAutoCommit(Boolean.FALSE);

            final String upadateQuery =
                    "DELETE FROM "+JDBCPostgre.METAOBJECTS_TABLE+" WHERE name = ? and object_type_id = ?";

            final PreparedStatement statement = connection.prepareStatement(upadateQuery );
            statement.setString(1, name);
            statement.setInt(2, ObjectTypeDAO.getInstance().getIdByName(objectTypeName));

            statement.execute();

            connection.commit();
            connection.close();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e2) {
                throw new RuntimeException(e2);
            }
            e.printStackTrace();
        }
    }

    public List<MetaObject> getListOfObject() {
        connection = JDBCPostgre.getConnection();

        List<MetaObject> res = new ArrayList<MetaObject>();
        try {

            final String selectQuery =
                    "SELECT id, object_type_id, name FROM "+JDBCPostgre.METAOBJECTS_TABLE+"";

            final PreparedStatement statement = connection.prepareStatement(selectQuery);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                final Integer id = resultSet.getInt("id");
                final Integer objectTypeId = resultSet.getInt("object_type_id");
                final String name = resultSet.getString("name");

                ObjectType ot = ObjectTypeDAO.getInstance().getObjectType(objectTypeId);

                MetaObject metaObject = new MetaObject(id, name, ot);

                res.add(metaObject);
            }
            connection.close();
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public List<MetaObject> findObjectsByName(String objectName) {
        connection = JDBCPostgre.getConnection();

        List<MetaObject> res = new ArrayList<MetaObject>();
        try {

            final String selectQuery =
                    "SELECT id, object_type_id, name FROM "+JDBCPostgre.METAOBJECTS_TABLE+
                    " WHERE name like ?";

            final PreparedStatement statement = connection.prepareStatement(selectQuery);
            statement.setString(1, objectName);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                final Integer id = resultSet.getInt("id");
                final Integer objectTypeId = resultSet.getInt("object_type_id");
                final String name = resultSet.getString("name");

                ObjectType ot = ObjectTypeDAO.getInstance().getObjectType(objectTypeId);

                MetaObject metaObject = new MetaObject(id, name, ot);

                res.add(metaObject);
            }
            connection.close();
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public MetaObject getObjectById(Integer objectId) {
        connection = JDBCPostgre.getConnection();

        MetaObject res = null;
        try {

            final String selectQuery =
                    "SELECT id, object_type_id, name FROM "+JDBCPostgre.METAOBJECTS_TABLE+
                            " WHERE id = ?";

            final PreparedStatement statement = connection.prepareStatement(selectQuery);
            statement.setInt(1, objectId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                final Integer id = resultSet.getInt("id");
                final Integer objectTypeId = resultSet.getInt("object_type_id");
                final String name = resultSet.getString("name");

                ObjectType ot = ObjectTypeDAO.getInstance().getObjectType(objectTypeId);

                MetaObject metaObject = new MetaObject(id, name, ot);

                res = metaObject;
            }
            connection.close();
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public Map<Attribute, AttributeValue> getParameters (MetaObject object) {
        connection = JDBCPostgre.getConnection();

        try {
            final Map<Attribute, AttributeValue> res = new HashMap<Attribute, AttributeValue>();

            final String getParamsQuery =
                    "SELECT attrs.attr_id,\n" +
                            "       attrs.name,\n" +
                            "       attrs.type_id,\n" +
                            "       params.value\n" +
                            "FROM\n" +
                            "\n" +
                            "(SELECT attr_id,       \n" +
                            "\tvalue\n" +
                            "FROM params\n" +
                            "WHERE object_id = ?) params\n" +
                            "\n" +
                            "RIGHT OUTER JOIN\n" +
                            "(SELECT a.id attr_id,\n" +
                            "        a.type_id,\n" +
                            "        a.name\n" +
                            " FROM attribute a\n" +
                            " INNER JOIN object_types ot ON a.object_type_id = ot.id\n" +
                            " WHERE ot.id = ?)\n" +
                            "attrs ON params.attr_id = attrs.attr_id";
            final PreparedStatement preparedStatement = connection.prepareStatement(getParamsQuery);
            preparedStatement.setInt(1, object.getId());
            preparedStatement.setInt(2, object.getObjectType().getId());

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                final Integer attrId = resultSet.getInt("attr_id");
                final String attrName = resultSet.getString("name");
                final Integer typeId = resultSet.getInt("type_id");
                final String value = resultSet.getString("value");

                final Attribute attribute = new Attribute(attrId, attrName, typeId);
                final AttributeValue attributeValue = new AttributeValue(attribute, value);

                res.put(attribute, attributeValue);
            }
            connection.close();
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void flushToDB (MetaObject metaObject) {
        flushToDB(metaObject, null);
    }

    public void flushToDB (final MetaObject metaObject, Map<Attribute, Boolean> needUpdate) {


        try {

            createObject(metaObject.getName(), metaObject.getObjectType().getName(), metaObject.getId());

            connection = JDBCPostgre.getConnection();
            connection.setAutoCommit(Boolean.FALSE);

            final String updateQuery =
                    "UPDATE params SET value = ? WHERE attr_id = ? and object_id = ?";

            final String insertQuery =
                    "INSERT INTO params (attr_id, object_id, value) SELECT ?, ?, ?";


            final String upsertQuery =
                    "WITH upsert AS ("+updateQuery +" RETURNING *) "+insertQuery+" " +
                    "WHERE NOT EXISTS (SELECT * FROM upsert)";

            final PreparedStatement preparedStatement = connection.prepareStatement(upsertQuery);


            Map<Attribute, AttributeValue> parameters = metaObject.getParameters();

            for (Map.Entry<Attribute, AttributeValue> val : parameters.entrySet()) {
                final Attribute attribute =  val.getKey();
                final AttributeValue newValue =  val.getValue();
                if (needUpdate == null || needUpdate.get(attribute)) {
                    preparedStatement.setString(1, newValue.getValue());
                    preparedStatement.setInt(2, attribute.getId());
                    preparedStatement.setInt(3, metaObject.getId());
                    preparedStatement.setInt(4, attribute.getId());
                    preparedStatement.setInt(5, metaObject.getId());
                    preparedStatement.setString(6, newValue.getValue());

                    preparedStatement.execute();
                }
            }
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e2) {
                throw new RuntimeException(e2);
            }
            e.printStackTrace();
        }
    }
}


