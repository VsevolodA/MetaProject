package vska.tools;

import java.sql.*;

/**
 * Created with IntelliJ IDEA.
 * User: Seva
 * Date: 15.02.15
 * Time: 23:36
 * To change this template use File | Settings | File Templates.
 */
public class JDBCPostgre {

    final public static String OBJECTTYPES_TABLE = "object_types";
    final public static String METAOBJECTS_TABLE = "meta_objects";
    final public static String ATTRIBUTE_TABLE = "attribute";
    final public static String PARAMS_TABLE = "params";
    final public static String REFERENCE_TABLE = "reference";

    public static Integer getIDForTable (String table) throws SQLException{
        final Connection connection = getConnection();

        final String maxIdQuery = "select max(id) from " + table;
        final PreparedStatement stCount = connection.prepareStatement(maxIdQuery);
        ResultSet resultSet = stCount.executeQuery();
        resultSet.next();
        return resultSet.getInt(1) + 1;
    }

    public static Connection getConnection() {
        Connection c = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/MetaProject",
                            "postgres", "wudiwud");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        //System.out.println("Opened database successfully");
        return c;
    }


}
