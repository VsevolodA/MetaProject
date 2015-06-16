package vska.tools.xml;

import com.thoughtworks.xstream.io.xml.DomDriver;
import vska.meta.MetaObject;
import com.thoughtworks.xstream.*;
import vska.tools.sql.MetaObjectDAO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: Seva
 * Date: 14.02.15
 * Time: 21:35
 * To change this template use File | Settings | File Templates.
 */
public class XMLHelper {

    private XMLHelper() {
    }

    public static MetaObject deSerialiseFromXML (String s) {
        try {
            final XStream xstream = new XStream(new DomDriver());
            return (MetaObject) xstream.fromXML(s);
        }
        catch(ClassCastException e) {
            throw new RuntimeException(e);
        }
    }

    public static String serialiseToXML(MetaObject metaObject) {
        XStream xstream = new XStream();
        return xstream.toXML(metaObject);
    }

    public static File createFileWithXML (String xml, String name) {
        File file = new File(name);
        PrintWriter output = null;
        try {
            output = new PrintWriter(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        output.write(xml);
        output.close();
        return file;
    }

    public static File exportViaXML (MetaObject object) {
        final String nameOfFile = object.getName() + "_xmlImport.xml";
        final String xml = serialiseToXML(object);
        return createFileWithXML(xml, nameOfFile);
    }

    public static void importFromXML (File file) {
        //File file = new File("C:/testXML.txt");
        Scanner input = null;
        try {
            input = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        StringBuilder stringBuilder = new StringBuilder();
        while (input.hasNextLine()) {
            stringBuilder.append(input.nextLine());
        }
        //System.out.println(stringBuilder.toString());
        MetaObject metaObject = deSerialiseFromXML(stringBuilder.toString());
        //System.out.println(metaObject.getId() + " " + metaObject.getName());
        MetaObjectDAO.getInstance().flushToDB(metaObject);
    }

    public static XMLHelper createXMLHelper() {
        return new XMLHelper();
    }

    public static void main(String[] args) {
        MetaObject metaObject = MetaObjectDAO.getInstance().getObjectById(33);
        File file = exportViaXML(metaObject);
        file.mkdir();
        System.out.println(file.getName());
    }
}
