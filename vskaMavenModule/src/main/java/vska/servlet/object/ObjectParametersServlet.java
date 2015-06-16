package vska.servlet.object;

import vska.meta.Attribute;
import vska.meta.AttributeValue;
import vska.meta.MetaObject;
import vska.tools.sql.AttributeDAO;
import vska.tools.sql.MetaObjectDAO;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Seva
 * Date: 21.02.15
 * Time: 18:28
 * To change this template use File | Settings | File Templates.
 */
public class ObjectParametersServlet extends HttpServlet {
    public void doGet (HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, String[]> paramNames = request.getParameterMap();

        boolean emptyEnum = false;

        if (paramNames.isEmpty()) {
            emptyEnum = true;
        }

        response.setContentType("text/html");
        final PrintWriter out = response.getWriter();

        out.println("<html><head>");
        out.println("<title>ObjectParameters</title></head><body>");

        if (!emptyEnum) {
            final Integer objectId = Integer.valueOf(paramNames.get("objid")[0]);
            final boolean edit = paramNames.get("edit") != null && "yes".equals(paramNames.get("edit")[0]);

            MetaObject metaObject = MetaObjectDAO.getInstance().getObjectById(objectId);

            final Map<Attribute,AttributeValue> parameters = metaObject.getParameters();
            out.println("<form method=\"get\" action=\""+ request.getContextPath() +"/objectparameters\"> ");
            out.println("<input type=\"hidden\" name=\"objid\" value=\""+objectId+"\" >");
            out.println("<input type=\"hidden\" name=\"edit\" value=\"yes\">");
            out.println("<input type=\"submit\" value=\"Edit\">");
            out.println("</form>");
            out.println("<form method=\"post\" action=\""+ request.getContextPath() +"/objectparameters\"> "+
                    "<table>" +
                    "<tr>" +
                    "<td>Attribute" +
                    "</td>" +
                    "<td>Value" +
                    "</td>" +
                    "</tr>"
            );
            for (Map.Entry<Attribute, AttributeValue> val : parameters.entrySet()) {
                out.println("" +
                        "<tr>" +
                        "<td>" +
                        val.getKey().getName()+
                        "</td>" +
                        "<td>" +
                        "<input type=\"text\" value=\""+val.getValue().getValue()+"\" " +
                        "       name=\""+val.getKey().getName()+"\" " +
                        "       "+(!edit ? "readonly" : "")+">" +
                        "</td>" +
                        "</tr>"
                );
            }
            out.println("</table>" +
                        (edit ? "<input type=\"hidden\" name=\"objid\" value=\""+metaObject.getId()+"\" readonly>\n" +
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
            final String objId = paramNames.get("objid")[0];

            final MetaObject metaObject = MetaObjectDAO.getInstance().getObjectById(Integer.valueOf(objId));
            final Map<Attribute, AttributeValue> newParameters = metaObject.getParameters();
            final Map<Attribute, Boolean> needUpdate = new HashMap<Attribute, Boolean>();

            List<Attribute> attributeIds =
                    AttributeDAO.getInstance().getAttributeByObjectType(metaObject.getObjectType().getName());
            for (Attribute attr : attributeIds) {
                AttributeValue attributeValue = newParameters.get(attr);
                final String newValue = paramNames.get(attr.getName())[0];
                if (newValue.equals(attributeValue.getValue())) {
                    needUpdate.put(attr, Boolean.FALSE);
                } else {
                    attributeValue.setValue(newValue);
                    newParameters.put(attr, attributeValue);
                    needUpdate.put(attr, Boolean.TRUE);
                }
            }

            metaObject.setParameters(newParameters);

            MetaObjectDAO.getInstance().flushToDB(metaObject);

            out.println(
                    "<head>" +
                    "<meta http-equiv=\"refresh\" content=\"1;URL="+request.getContextPath() +"/objectparameters?objid="+objId+"\">" +
                    "</head>" +
                    "<body>");
            out.println("<h2>Update success</h2>");
        }

        out.println("</body></html>");
    }
}
