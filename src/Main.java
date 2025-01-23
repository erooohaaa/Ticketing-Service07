import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        String connectionURL = "jdbc:postgresql://localhost:5432/TicketSystemDB";
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {

            Class.forName("org.postgresql.Driver");


            con = DriverManager.getConnection(connectionURL, "postgres", "0000");


            stmt = con.createStatement();

            rs = stmt.executeQuery("SELECT * FROM Event_Categories");


            while (rs.next()) {
                System.out.println(
                        rs.getInt("category_id") + ". " +
                                rs.getString("category_name")
                );
            }
        } catch (Exception e) {
            System.out.println("Exception occurred!");
            e.printStackTrace();
        } finally {
            try {

                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (Exception throwable) {
                System.out.println("Exception occurred during resource closing!");
                throwable.printStackTrace();
            }
        }
        System.out.println("Finished!");
    }
}