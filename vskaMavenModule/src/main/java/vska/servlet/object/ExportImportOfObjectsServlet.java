package vska.servlet.object;

import vska.meta.MetaObject;
import vska.tools.sql.MetaObjectDAO;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * Created by Seva on 14.04.2015.
 */

public class ExportImportOfObjectsServlet extends HttpServlet {
    public void doGet (HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        final PrintWriter out = response.getWriter();

        out.println("<html><head>");
        out.println("<h2>Export/Import of objects</h2>");

        out.println(
                "<form enctype=\"multipart/form-data\" method=\"post\" action = \"" + request.getContextPath() +
                        "/ei\" >"
        );

        out.println("<table border=\"1\"><tr><td valign=\"top\">");
        out.println("Operation </td> <td valign=\"top\">");
        out.println("<input type=\"text\" name=\"operation\" size=\"20\">");
        out.println("</td></tr>");
        out.println("<tr><td>ObjectId for export operation: </td> <td valign=\"top\">");
        out.println("<input type=\"text\" name=\"objectid\" size=\"20\">");
        out.println("</td></tr>");
        out.println("<tr><td>File for import operation: </td> <td valign=\"top\">");
        out.println("<input type=\"file\" name=\"file\" size=\"20\">");
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
        out.println("<title>Result Operation</title></head><body>");

        if (!emptyEnum) {
            String operation = paramNames.get("operation")[0];

            if ("export".equals(operation)) {
                String objectId;
            } else if ("import".equals(operation)) {

                paramNames.get("file");
            }
        }

        out.println("</body></html>");
    }
}
