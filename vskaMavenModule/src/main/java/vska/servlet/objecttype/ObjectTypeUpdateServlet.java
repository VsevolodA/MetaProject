package vska.servlet.objecttype;

import vska.meta.Attribute;
import vska.meta.ObjectType;
import vska.tools.AttributeDAO;
import vska.tools.ObjectTypeDAO;

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
 * Time: 23:17
 * To change this template use File | Settings | File Templates.
 */
public class ObjectTypeUpdateServlet extends HttpServlet {

    public void doGet (HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, String[]> paramNames = request.getParameterMap();

        boolean emptyEnum = false;

        if (paramNames.isEmpty()) {
            emptyEnum = true;
        }

        response.setContentType("text/html");
        final PrintWriter out = response.getWriter();

        out.println("<html><head>");
        out.println("<title>Object Type page</title></head><body>");

        if (!emptyEnum) {
            final Integer otId = Integer.valueOf(paramNames.get("otId")[0]);
            final boolean edit = paramNames.get("edit") != null && "yes".equals(paramNames.get("edit")[0]);

            ObjectType objectType = ObjectTypeDAO.getInstance().getObjectType(otId);

            out.println("<form method=\"get\" action=\""+ request.getContextPath() +"/objecttypeupdate\"> ");
            out.println("<input type=\"hidden\" name=\"otId\" value=\""+otId+"\" >");
            out.println("<input type=\"hidden\" name=\"edit\" value=\"yes\">");
            out.println("<input type=\"submit\" value=\"Edit\">");
            out.println("</form>");
            out.println("<form method=\"post\" action=\""+ request.getContextPath() +"/objecttypeupdate\"> "+
                    "<table>" +
                    "<tr>" +
                    "<td>Name of attribute" +
                    "</td>" +
                    "<input type=\"text\" value=\""+objectType.getName()+"\"" +
                    "       name=\"otName\" " +
                    "       "+(!edit ? "readonly" : "")+">" +
                    "<td>" +
                    "</td>" +
                    "</tr>"
            );

            out.println("</table>" +
                    (edit ? "<input type=\"hidden\" name=\"otId\" value=\""+objectType.getId()+"\" readonly>\n" +
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
            final String otId = paramNames.get("otId")[0];
            final String newOtName = paramNames.get("otName")[0];

            final ObjectType objectType = ObjectTypeDAO.getInstance().getObjectType(Integer.valueOf(otId));

            if (!newOtName.equals(objectType.getName())) {
                objectType.setName(newOtName);
                ObjectTypeDAO.getInstance().flushToDB(objectType);
            }

            out.println(
                    "<head>" +
                            "<meta http-equiv=\"refresh\" content=\"1;URL="+request.getContextPath() +"/objecttypeupdate?otId="+otId+"\">" +
                            "</head>" +
                            "<body>");
            out.println("<h2>Update success</h2>");
        }

        out.println("</body></html>");
    }
}
