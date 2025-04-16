import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class AttendanceServlet extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String date = req.getParameter("date");
        String status = req.getParameter("status");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "root");

            PreparedStatement pst = con.prepareStatement("INSERT INTO attendance VALUES (?, ?, ?, ?)");
            pst.setString(1, id);
            pst.setString(2, name);
            pst.setString(3, date);
            pst.setString(4, status);
            pst.executeUpdate();

            req.setAttribute("studentName", name);
            RequestDispatcher rd = req.getRequestDispatcher("attendance-success.jsp");
            rd.forward(req, res);
        } catch(Exception e) {
            res.getWriter().println("Database error: " + e.getMessage());
        }
    }
}
