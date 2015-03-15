package vska.swing;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.w3c.dom.Attr;
import vska.meta.Attribute;
import vska.meta.AttributeValue;
import vska.meta.MetaObject;
import vska.tools.MetaObjectDAO;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.*;

/**
 * Created by Seva on 07.03.2015.
 */
public class MetaObjectTableModel implements TableModel {
    private Set<TableModelListener> listeners = new HashSet<TableModelListener>();

    private MetaObject metaObject;
    private Map<Attribute, AttributeValue> parameters;
    private List<Attribute> attributes;
    private List<AttributeValue> attributeValues;

    public MetaObjectTableModel(MetaObject metaObject) {

        this.metaObject= metaObject;
        this.parameters = MetaObjectDAO.getInstance().getParameters(metaObject);
        this.attributes = new ArrayList<Attribute>(parameters.keySet());
        this.attributeValues = new ArrayList<AttributeValue>(parameters.values());
    }

    @Override
    public int getRowCount() {
        return parameters.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Attribute";
            case 1:
                return "Value";
        }
        return "";
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {

        switch (columnIndex) {
            case 0:
                return false;
            case 1:
                return true;
        }
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        final Attribute attribute = attributes.get(rowIndex);
        if (columnIndex == 0) {
            return attribute.getName();
        }
        if (columnIndex == 1) {
            return parameters.get(attribute).getValue();
        }
        return "";
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (columnIndex == 1) {

            final Attribute attribute = attributes.get(rowIndex);
            final AttributeValue attributeValue = new AttributeValue(attribute, (String)aValue);

            parameters.put(attribute, attributeValue);
            metaObject.setParameters(parameters);

            final Map<Attribute, Boolean> needUpdate = new HashMap<Attribute, Boolean>();
            for (Attribute attr : attributes) {
                needUpdate.put(attr, Boolean.FALSE);
            }
            needUpdate.put(attribute, Boolean.TRUE);
            MetaObjectDAO.getInstance().flushToDB(metaObject, needUpdate);
        }
    }

    @Override
    public void addTableModelListener(TableModelListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeTableModelListener(TableModelListener listener) {
        listeners.remove(listener);
    }
}
