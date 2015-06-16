package vska.servlet.object;

import vska.tools.sql.MetaObjectDAO;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Seva
 * Date: 19.02.15
 * Time: 23:50
 * To change this template use File | Settings | File Templates.
 */
public class ObjectCreateServlet extends HttpServlet {

    public void doGet (HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        final PrintWriter out = response.getWriter();

        out.println("<html><head>");
        out.println("<h2>Page of object CRUD</h2>");

        out.println(
                "<form method=\"post\" action = \"" + request.getContextPath() +
                        "/createobject\" >"
        );

        out.println("<table border=\"1\"><tr><td valign=\"top\">");
        out.println("Name of object: </td> <td valign=\"top\">");
        out.println("<input type=\"text\" name=\"objectname\" size=\"20\">");
        out.println("</td></tr>");
        out.println("<tr><td>Object Type of object: </td> <td valign=\"top\">");
        out.println("<input type=\"text\" name=\"objecttypeofobject\" size=\"20\">");
        out.println("</td></tr>");

        out.println("<input type=\"submit\" value=\"Submit\">");

        out.println("</table></form>");
        out.println("</body></html>");
    }

    public void doPost (HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, String[]> paramNames = request.getParameterMap();

        boolean emptyEnum = false;

        if (paramNames.isEmpty()) {
            emptyEnum = true;
        }

        response.setContentType("text/html");

        final PrintWriter out = response.getWriter();

        out.println("<html><head>");
        out.println("<title>Object</title></head><body>");

        if (!emptyEnum) {
            String objectName = paramNames.get("objectname")[0];
            String objectTypeOfObject = paramNames.get("objecttypeofobject")[0];

            MetaObjectDAO.getInstance().createObject(objectName, objectTypeOfObject);
            out.println("Object\""+ objectName+"\" success creted");
        }

        out.println("</body></html>");
    }
}
