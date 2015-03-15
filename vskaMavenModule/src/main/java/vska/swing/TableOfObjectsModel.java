/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vska.swing;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import vska.meta.Attribute;
import vska.meta.MetaObject;
import vska.tools.MetaObjectDAO;

/**
 *
 * @author Seva
 */
public class TableOfObjectsModel implements TableModel {
    
    private List<MetaObject> objects;   
    
    private Set<TableModelListener> listeners = new HashSet<TableModelListener>();
    
    public MetaObject getObject(int rowNumber) {
        if (rowNumber >= 0 && rowNumber < objects.size())
            return objects.get(rowNumber);
        return objects.get(objects.size()-1);
    }
            
    public TableOfObjectsModel () {
         this.objects = MetaObjectDAO.getInstance().getListOfObject();
    }
    public int getRowCount() {
        return objects.size();                
    }

    public int getColumnCount() {
        return 2;
    }

    public String getColumnName(int columnIndex) {
        switch (columnIndex)  {
            case 0: return "Name";
            case 1: return "Id";
            default : return "";
        }
    }

    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        final MetaObject metaObject = objects.get(rowIndex);
        if (columnIndex == 0) {
            return metaObject.getName();
        }
        if (columnIndex == 1) {
            return metaObject.getId();
        }
        return "";
    }

    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void addTableModelListener(TableModelListener l) {
        listeners.add(l);
    }

    public void removeTableModelListener(TableModelListener l) {
        listeners.remove(l);
    }
    
}
