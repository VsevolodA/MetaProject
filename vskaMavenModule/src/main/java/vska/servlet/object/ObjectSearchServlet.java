package vska.servlet.object;

import vska.meta.MetaObject;
import vska.tools.sql.MetaObjectDAO;

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
 * Time: 17:47
 * To change this template use File | Settings | File Templates.
 */
public class ObjectSearchServlet extends HttpServlet {

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

            List<MetaObject> objectsByName = MetaObjectDAO.getInstance().findObjectsByName(objectName);
            out.println("<h3>Result of search</h3>");
            for (MetaObject object : objectsByName) {
                out.println("<a href=\"/metaproject/objectparameters?objid="+object.getId()+"\">" +
                                object.getName()+"</a>");
            }
        }

        out.println("</body></html>");
    }
}
