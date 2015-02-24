package vska.servlet.objecttype;

import vska.meta.ObjectType;
import vska.tools.ObjectTypeDAO;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * Created with IntelliJ IDEA.
 * User: Seva
 * Date: 19.02.15
 * Time: 1:51
 * To change this template use File | Settings | File Templates.
 */
public class ObjectTypeListServlet extends HttpServlet {

    public void doGet (HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        final PrintWriter out = response.getWriter();

        out.println("<html><head>");
        out.println("<h2>List of object types</h2>");

        out.println("<table borfer=\"1\" style=\"width:100%\">");

        out.println("<tr>");
        out.println("<td>ID</td>");
        out.println("<td>NAME</td>");
        out.println("</tr>");

        for (ObjectType val : ObjectTypeDAO.getInstance().getListOfObjectTypes()) {

            out.println("<tr>");
            out.println("<td>"+val.getId()+"</td>");
            out.println("<td><a href=\"/metaproject/objecttypeupdate?otId="+val.getId()+"\">"+val.getName()+"</a></td>");
            out.println("</tr>");

        }
        out.println("</table>");

        out.println("</body></html>");
    }

    /*
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
    */
}
