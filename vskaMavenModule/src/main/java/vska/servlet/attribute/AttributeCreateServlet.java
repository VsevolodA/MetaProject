package vska.servlet.attribute;

import vska.meta.Attribute;
import vska.tools.AttributeDAO;
import vska.tools.MetaObjectDAO;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Seva
 * Date: 20.02.15
 * Time: 23:24
 * To change this template use File | Settings | File Templates.
 */
public class AttributeCreateServlet extends HttpServlet {
    public void doGet (HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        final PrintWriter out = response.getWriter();

        out.println("<html><head>");
        out.println("<h2>Page of create attribute</h2>");

        out.println(
                "<form method=\"post\" action = \"" + request.getContextPath() +
                        "/createattribute\" >"
        );

        out.println("<table border=\"1\"><tr><td valign=\"top\">");
        out.println("Name of attribute: </td> <td valign=\"top\">");
        out.println("<input type=\"text\" name=\"attributename\" size=\"20\">");
        out.println("</td></tr>");
        out.println("<tr><td>For which Object Type this attribute: </td> <td valign=\"top\">");
        out.println("<input type=\"text\" name=\"objecttype\" size=\"20\">");
        out.println("</td></tr>");
        out.println("<tr><td>Type of attribute: </td> <td valign=\"top\">");
        out.println("<input type=\"text\" list=\"types\" name=\"typeid\" size=\"20\">");
        out.println("<datalist id=\"types\">\n" +
                "    <option value=\"Text\">\n" +
                "    <option value=\"Number\">\n" +
                "    <option value=\"Reference\">\n" +
                "</datalist>");
        out.println("</td></tr>");
        out.println("</table>");

        out.println("<input type=\"submit\" value=\"Submit\">");

        out.println("</form>");
        out.println("</body></html>");
    }

    public void doPost (HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, String[]> paramNames = request.getParameterMap();

        boolean emptyEnum = false;

        if (paramNames.isEmpty()) {
            emptyEnum = true;
        }

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        out.println("<html><head>");
        out.println("<title>Object</title></head><body>");

        if (!emptyEnum) {
            final String attributename = paramNames.get("attributename")[0];
            final String objecType = paramNames.get("objecttype")[0];
            final Integer typeId = Attribute.getTypeId(paramNames.get("typeid")[0]);

            AttributeDAO.getInstance().createAttribute(attributename, typeId, objecType);

            out.println("Attribute\""+attributename+"\" success created");
        }

        out.println("</body></html>");
    }
}
