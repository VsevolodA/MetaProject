package vska.servlet.attribute;

import vska.meta.Attribute;
import vska.tools.sql.AttributeDAO;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Seva
 * Date: 24.02.15
 * Time: 22:53
 * To change this template use File | Settings | File Templates.
 */
public class AttributeUpdateServlet extends HttpServlet {

    public void doGet (HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, String[]> paramNames = request.getParameterMap();

        boolean emptyEnum = false;

        if (paramNames.isEmpty()) {
            emptyEnum = true;
        }

        response.setContentType("text/html");
        final PrintWriter out = response.getWriter();

        out.println("<html><head>");
        out.println("<title>Attribute page</title></head><body>");

        if (!emptyEnum) {
            final Integer attrId = Integer.valueOf(paramNames.get("attrId")[0]);
            final boolean edit = paramNames.get("edit") != null && "yes".equals(paramNames.get("edit")[0]);

            Attribute attribute = AttributeDAO.getInstance().getAttributeById(attrId);

            out.println("<form method=\"get\" action=\""+ request.getContextPath() +"/attribute\"> ");
            out.println("<input type=\"hidden\" name=\"attrId\" value=\""+attrId+"\" >");
            out.println("<input type=\"hidden\" name=\"edit\" value=\"yes\">");
            out.println("<input type=\"submit\" value=\"Edit\">");
            out.println("</form>");
            out.println("<form method=\"post\" action=\""+ request.getContextPath() +"/attribute\"> "+
                    "<table>" +
                    "<tr>" +
                    "<td>Name of attribute" +
                    "</td>" +
                    "<td>" +
                    "<input type=\"text\" value=\""+attribute.getName()+"\"" +
                    "       name=\"attrName\" " +
                    "       "+(!edit ? "readonly" : "")+">" +
                    "</td>" +
                    "</tr>"
            );

            out.println("</table>" +
                    (edit ? "<input type=\"hidden\" name=\"attrId\" value=\""+attribute.getId()+"\" readonly>\n" +
                            "<input type=\"submit\" value=\"Update\">" : "") +
                    "</form>");
        }

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

        out.println("<html>");

        if (!emptyEnum) {
            final String attrId = paramNames.get("attrId")[0];
            final String newAttrName = paramNames.get("attrName")[0];

            final Attribute attribute = AttributeDAO.getInstance().getAttributeById(Integer.valueOf(attrId));

            if (!newAttrName.equals(attribute.getName())) {
                attribute.setName(newAttrName);
                AttributeDAO.getInstance().flushToDB(attribute);
            }

            out.println(
                    "<head>" +
                            "<meta http-equiv=\"refresh\" content=\"1;URL="+request.getContextPath() +"/attribute?attrId="+attrId+"\">" +
                            "</head>" +
                            "<body>");
            out.println("<h2>Update success</h2>");
        }

        out.println("</body></html>");
    }
}
