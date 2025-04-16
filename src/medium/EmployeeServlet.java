import java.io.*;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class EmployeeServlet extends HttpServlet {
    Connection con;

    public void init() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "root");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException {
        PrintWriter out = res.getWriter();
        res.setContentType("text/html");

        try {
            Statement stmt = con.createStatement();
            ResultSet rs;

            String id = req.getParameter("id");
            String all = req.getParameter("all");

            if (id != null && !id.isEmpty()) {
                rs = stmt.executeQuery("SELECT * FROM employees WHERE id = " + id);
            } else if ("true".equals(all)) {
                rs = stmt.executeQuery("SELECT * FROM employees");
            } else {
                out.println("Invalid request.");
                return;
            }

            while (rs.next()) {
                out.println("<p>ID: " + rs.getInt("id") + ", Name: " + rs.getString("name") +
                            ", Dept: " + rs.getString("department") + ", Email: " + rs.getString("email") + "</p>");
            }
        } catch(Exception e) {
            out.println("Error: " + e.getMessage());
        }
    }

    public void destroy() {
        try { con.close(); } catch(Exception e) {}
    }
}
