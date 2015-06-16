package vska.tools.sql;

import vska.meta.ObjectType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Seva
 * Date: 16.02.15
 * Time: 0:08
 * To change this template use File | Settings | File Templates.
 */
public class ObjectTypeDAO {

    private static ObjectTypeDAO instance = new ObjectTypeDAO();

    private ObjectTypeDAO () {}

    public static ObjectTypeDAO getInstance() {
        return instance;
    }

    public ObjectType getObjectType (Integer id) {
        final Connection connection = JDBCPostgre.getConnection();

        try {
            if (id == null) {
                return null;
            }
            final String selectQuery =
                    "SELECT id, name FROM "+JDBCPostgre.OBJECTTYPES_TABLE+" WHERE id = ?";

            final PreparedStatement statement = connection.prepareStatement(selectQuery);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            ObjectType objectType = new ObjectType(resultSet.getInt("id"), resultSet.getString("name"));

            return objectType;
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

    public ObjectType getObjectType (String name) {
        return getObjectType(getIdByName(name));
    }

    public Integer getIdByName (String name) {
        final Connection connection = JDBCPostgre.getConnection();

        try {
            final String selectQuery =
                    "select id from "+JDBCPostgre.OBJECTTYPES_TABLE+" where name = ?";

            final PreparedStatement statement = connection.prepareStatement(selectQuery);
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            final int id = resultSet.getInt("id");
            return id;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void readObjectType (int id) throws SQLException {
        final Connection connection = JDBCPostgre.getConnection();

        final String selectQuery =
                "SELECT FROM "+JDBCPostgre.OBJECTTYPES_TABLE+" WHERE id = ?";

        final PreparedStatement statement = connection.prepareStatement(selectQuery);
        statement.setInt(1, id);

        statement.execute();
    }

    public void readObjectType (String name) throws SQLException {
        final Connection connection = JDBCPostgre.getConnection();

        final String selectQuery =
                "SELECT FROM "+JDBCPostgre.OBJECTTYPES_TABLE+" WHERE id = ?";

        final PreparedStatement statement = connection.prepareStatement(selectQuery);
        statement.setString(1, name);

        statement.execute();
    }

    public List<ObjectType> getListOfObjectTypes() {
        final Connection connection = JDBCPostgre.getConnection();

        List<ObjectType> res = new ArrayList<ObjectType>();
        try {

            final String selectQuery =
                    "SELECT id, name FROM "+JDBCPostgre.OBJECTTYPES_TABLE+"";

            final PreparedStatement statement = connection.prepareStatement(selectQuery);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                final String name = resultSet.getString("name");
                final Integer id = resultSet.getInt("id");
                ObjectType ot = new ObjectType(id, name);
                res.add(ot);
            }

            return res;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public void createObjectType (int id, String name) {
        final Connection connection = JDBCPostgre.getConnection();

        try {
            connection.setAutoCommit(Boolean.FALSE);

            final String createQuery =
                    "INSERT INTO "+JDBCPostgre.OBJECTTYPES_TABLE+" (id, name) " +
                    "values (?, ?)";

            final PreparedStatement statement = connection.prepareStatement(createQuery);
            statement.setInt(1, id);
            statement.setString(2, name);

            statement.execute();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e2) {
                throw new RuntimeException(e2);
            }
            e.printStackTrace();
        }
    }

    public void createObjectType (String name) {
        final Connection connection = JDBCPostgre.getConnection();

        try {
            connection.setAutoCommit(Boolean.FALSE);

            final String createQuery =
                    "INSERT INTO "+JDBCPostgre.OBJECTTYPES_TABLE+" (id, name) " +
                            "values (?, ?)";

            final String maxIdQuery = "select max(id) from object_types";

            final PreparedStatement stCount = connection.prepareStatement(maxIdQuery);
            ResultSet resultSet = stCount.executeQuery();
            resultSet.next();
            final int id = resultSet.getInt(1) + 1;

            final PreparedStatement statement = connection.prepareStatement(createQuery);
            statement.setInt(1, id);
            statement.setString(2, name);

            statement.execute();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e2) {
                throw new RuntimeException(e2);
            }
            e.printStackTrace();
        }
    }

    public void updateObjectType (int id, String newName) throws SQLException {
        final Connection connection = JDBCPostgre.getConnection();

        final String upadateQuery =
                "UPDATE "+JDBCPostgre.OBJECTTYPES_TABLE+" SET name = ? WHERE id = ?";

        final PreparedStatement statement = connection.prepareStatement(upadateQuery );
        statement.setString(1, newName);
        statement.setInt(2, id);

        statement.execute();
    }

    public void flushToDB (ObjectType objectType) {
        final Connection connection = JDBCPostgre.getConnection();
        try {
            connection.setAutoCommit(Boolean.FALSE);

            final String upadateQuery =
                    "UPDATE "+JDBCPostgre.OBJECTTYPES_TABLE+" SET name = ? WHERE id = ?";

            final PreparedStatement statement = connection.prepareStatement(upadateQuery );
            statement.setString(1, objectType.getName());
            statement.setInt(2, objectType.getId());

            statement.execute();

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    public void deleteObjectType (String name){
        final Connection connection = JDBCPostgre.getConnection();

        try {
            connection.setAutoCommit(Boolean.FALSE);

            final String upadateQuery =
                    "DELETE FROM "+JDBCPostgre.OBJECTTYPES_TABLE+" WHERE name = ?";

            final PreparedStatement statement = connection.prepareStatement(upadateQuery );
            statement.setString(1, name);

            statement.execute();

            connection.commit();
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
