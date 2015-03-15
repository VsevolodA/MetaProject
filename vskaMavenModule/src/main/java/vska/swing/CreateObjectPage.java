package vska.swing;

import vska.meta.MetaObject;
import vska.tools.MetaObjectDAO;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: Seva
 * Date: 03.03.15
 * Time: 0:24
 * To change this template use File | Settings | File Templates.
 */
public class CreateObjectPage extends JFrame {

    private JPanel panel;
    private JTextField jTextFieldForName;
    private JTextField jTextFieldForType;
    private JButton jButtonCreate;

    private MetaObject metaObject;

    public CreateObjectPage () {
        init();
    }

    private void init() {
        panel = new JPanel();
        panel.setLayout(new GridLayout(10, 5));

        final Border border = new LineBorder(Color.BLUE);
        jTextFieldForName = new JTextField();
        jTextFieldForName.setBorder(border);

        jTextFieldForType = new JTextField();
        jTextFieldForType.setBorder(border);

        panel.add(jTextFieldForName);
        panel.add(jTextFieldForType);


        jButtonCreate = new JButton("Create");

        jButtonCreate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                final String newName = jTextFieldForName.getText();
                final String newType = jTextFieldForType.getText();

                if (!"".equals(newName) &&
                        !"".equals(newType)) {

                    metaObject = MetaObjectDAO.getInstance().createAndReturnObject(newName, newType);
                }
            }
        });

        panel.add(jButtonCreate);

        add(panel);


        setTitle("Page of creating metaobject");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public MetaObject getMetaObject() {
        return metaObject;
    }

    public void setMetaObject(MetaObject metaObject) {
        this.metaObject = metaObject;
    }
}
