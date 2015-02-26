package vska.servlet.object;

import vska.meta.MetaObject;
import vska.tools.MetaObjectDAO;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created with IntelliJ IDEA.
 * User: Seva
 * Date: 20.02.15
 * Time: 0:38
 * To change this template use File | Settings | File Templates.
 */
public class ObjectListServlet extends HttpServlet {
    public void doGet (HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        final PrintWriter out = response.getWriter();

        out.println("<html><head>");
        out.println("<h2>List of object</h2>");

        out.println("<table borfer=\"1\" style=\"width:100%\">");

        out.println("<tr>");
        out.println("<td>ID</td>");
        out.println("<td>NAME</td>");
        out.println("<td>OBJECT TYPE</td>");
        out.println("</tr>");

        for (MetaObject val : MetaObjectDAO.getInstance().getListOfObject()) {

            out.println("<tr>");
            out.println("<td>"+val.getId()+"</td>");
            out.println("<td><a href=\"/metaproject/objectparameters?objid="+val.getId()+"\">"+val.getName()+"</a></td>");
            out.println("<td><a href=\"/metaproject/objecttypeupdate?otId="+val.getObjectType().getId()+"\">"+val.getObjectType().getName()+"</a></td>");
            out.println("</tr>");

        }
        out.println("</table>");

        out.println("</body></html>");
    }
}
