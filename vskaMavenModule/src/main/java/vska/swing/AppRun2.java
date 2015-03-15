package vska.swing;

import vska.meta.MetaObject;
import vska.tools.MetaObjectDAO;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Seva
 * Date: 27.02.15
 * Time: 23:59
 * To change this template use File | Settings | File Templates.
 */
public class AppRun2 extends JFrame {

    private CreateObjectPage createObjectPage;
    private JPanel jPanel;
    private MetaObject metaObject;

    public CreateObjectPage getCreateObjectPage () {
        return this.createObjectPage;
    }

    public AppRun2() {
        metaObject = MetaObjectDAO.getInstance().getObjectById(5);
        init();
    }

    private void init() {

        Container pane = getContentPane();
        GroupLayout gl = new GroupLayout(pane);
        pane.setLayout(gl);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle(metaObject.getName());

        TableModel model = new MetaObjectTableModel(metaObject);
        JTable table = new JTable(model);

        JButton jButton = new JButton("Select");

        JPanel panel = new JPanel();

        panel.add(table);
        //panel.add(jButton);

        add(panel);


        setPreferredSize(new Dimension(260, 220));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }


    public static void main(String[] args) {
        MetaObject metaObject;
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                JFrame.setDefaultLookAndFeelDecorated(true);
                AppRun2 appRun = new AppRun2();
                appRun.setVisible(true);

                /*MetaObject object = appRun.getCreateObjectPage().getMetaObject();
                if (object != null) {
                    EditMetaObjectPage editMetaObjectPage = new EditMetaObjectPage(object);
                    editMetaObjectPage.setVisible(true);
                }
                */
            }

        });
    }
}
