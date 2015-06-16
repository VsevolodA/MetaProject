package vska.servlet.objecttype;

import vska.tools.sql.ObjectTypeDAO;

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
 * Time: 1:43
 * To change this template use File | Settings | File Templates.
 */
public class ObjectTypeDelete extends HttpServlet {

    public void doGet (HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        final PrintWriter out = response.getWriter();

        out.println("<html><head>");
        out.println("<h2>Delete object type</h2>");

        out.println(
                "<form method=\"post\" action = \"" + request.getContextPath() +
                        "/deleteobjecttype\" >"
        );

        out.println("<table border=\"0\"<tr><td valign=\"top\">");
        out.println("Name of object type: </td> <td valign=\"top\">");
        out.println("<input type=\"text\" name=\"objecttype\" size=\"20\">");
        out.println("</td></tr>");

        out.println("<input type=\"submit\" value=\"Submit\">");

        out.println("</table></form>");
        out.println("</body></html>");
    }

    public void doPost (HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, String[]> paramNames = request.getParameterMap();

        String paramName;
        boolean emptyEnum = false;

        if (paramNames.isEmpty()) {
            emptyEnum = true;
        }

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        out.println("<html><head>");
        out.println("<title>Object type</title></head><body>");

        if (!emptyEnum) {
            paramName = paramNames.get("objecttype")[0];

            ObjectTypeDAO.getInstance().deleteObjectType(paramName);
            out.println("Object type \""+ paramName+"\" success deleted");
        }

        out.println("</body></html>");
    }
}
