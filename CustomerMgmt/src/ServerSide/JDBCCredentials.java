package ServerSide;

/**
 * Interface used to hold JDBC credentials
 */
public interface JDBCCredentials {
    /**
     * JDBC driver
     */
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    /**
     * Database username
     */
    static final String USERNAME = "root";
    /**
     * Database password
     */
    static final String PASSWORD = "meng$seng";
     /**
     * URL to database schema
     */
    static final String DB_URL = "jdbc:mysql://localhost:3306/customer_db?verifyServerCertificate=false&useSSL=true";
}
