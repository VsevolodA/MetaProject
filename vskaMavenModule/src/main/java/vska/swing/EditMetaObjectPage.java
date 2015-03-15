package vska.swing;

import vska.meta.*;
import vska.tools.AttributeDAO;
import vska.tools.MetaObjectDAO;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Seva
 * Date: 03.03.15
 * Time: 0:39
 * To change this template use File | Settings | File Templates.
 */
public class EditMetaObjectPage extends JFrame {

    private MetaObject metaObject;
    private JPanel panel;
    private Map<Attribute, JTextField> jTextFieldsOfParameters;
    private JButton jButtonUpdate;

    public EditMetaObjectPage (MetaObject metaObject) {
        this.metaObject = metaObject;
        this.jTextFieldsOfParameters = new HashMap<Attribute, JTextField>();
        jButtonUpdate = new JButton();
        init();
    }

    private void initJTextFieldsOfParameters() {
        Map<Attribute,AttributeValue> parameters = MetaObjectDAO.getInstance().getParameters(metaObject);

        for (Attribute attribute : parameters.keySet()) {
            JTextField jTextField = new JTextField();
            jTextField.setText(parameters.get(attribute).getValue());
            final Border border = new LineBorder(Color.BLUE);
            jTextField.setBorder(border);
            jTextFieldsOfParameters.put(attribute, jTextField);
        }
    }

    private void init() {
        panel = new JPanel();
        panel.setLayout(new GridLayout(10, 5));

        initJTextFieldsOfParameters();
        addJTextFieldsOfParameters();

        jButtonUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {

            }
        });

        //panel.add();

        add(panel);

        setTitle("Page of updating metaobject");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void addJTextFieldsOfParameters() {
        for (JTextField jTextField : jTextFieldsOfParameters.values()) {
            panel.add(jTextField);
        }
    }


}