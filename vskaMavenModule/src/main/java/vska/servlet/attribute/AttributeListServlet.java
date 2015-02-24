package vska.servlet.attribute;

import vska.meta.Attribute;
import vska.meta.MetaObject;
import vska.tools.AttributeDAO;
import vska.tools.MetaObjectDAO;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Seva
 * Date: 21.02.15
 * Time: 1:06
 * To change this template use File | Settings | File Templates.
 */
@SuppressWarnings("SpellCheckingInspection")
public class AttributeListServlet extends HttpServlet {
    public void doGet (HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        final PrintWriter out = response.getWriter();

        out.println("<html><head>");
        out.println("<h2>List of attribute of Object Type</h2>");

        out.println(
                "<form method=\"post\" action = \"" + request.getContextPath() +
                        "/attributelist\" >"
        );

        out.println("Name of Object Type:");
        out.println("<input type=\"text\" name=\"objecttype\" size=\"20\">");
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
        out.println("<title>List of attributes of object type</title></head><body>");

        if (!emptyEnum) {
            String objectType = paramNames.get("objecttype")[0];
            List<Attribute> attributes = AttributeDAO.getInstance().getAttributeByObjectType(objectType);

            for (Attribute attribute: attributes) {
                out.println("<a href=\"/metaproject/attribute?attrId="+attribute.getId()+"\">"+attribute.getName()+"</a>");
                out.println("<br>");
            }

        }

        out.println("</body></html>");
    }
}
