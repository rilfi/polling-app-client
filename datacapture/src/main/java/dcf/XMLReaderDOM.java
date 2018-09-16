package dcf;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class XMLReaderDOM {

    static int  stu =0;

    public static void main(String[] args) {

        System.out.println(getFlyEntries("flight_report-2018_09_11_01_17_23.xml").size());

       // System.out.println(getFlyEntries("flight_report-2018_09_09_13_08_10.xml").size());
        System.out.println(getStudents("stuRef.xml").size());



    }

    public static List<FlyingEntry>getFlyEntries(String fileName){

        String filePath = fileName;
        File xmlFile = new File(filePath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        List<FlyingEntry> empList = new ArrayList<>();
        List<String> notValidList=new ArrayList<>();
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nodeList = doc.getElementsByTagName("flight");
            //now XML is loaded as Document in memory, lets convert it to Object List
            int j=0;
            int k=0;
            for (int i = 0; i < nodeList.getLength(); i++) {

                    FlyingEntry flyingEntry=getEmployee(nodeList.item(i));
                    if(flyingEntry!=null){
                        k++;
                        empList.add(flyingEntry);
                    }
                   /* else{
                       // notValidList.add(nodeList.item(i).getNodeValue());
                        System.out.println(nodeList.item(i).getNodeValue());

                    }*/

                    j++;


            }
            //lets print Employee list information
           System.out.println("j ="+j);
           System.out.println("k ="+ k);

        } catch (SAXException | ParserConfigurationException | IOException e1) {
            e1.printStackTrace();
        }
        return  empList;
    }

    public static List<Student>getStudents(String fileName){

        String filePath = fileName;
        File xmlFile = new File(filePath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        List<Student> students = new ArrayList<>();
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nodeList = doc.getElementsByTagName("row");
            //now XML is loaded as Document in memory, lets convert it to Object List
            int j=0;
            int k=0;
            for (int i = 0; i < nodeList.getLength(); i++) {

                Student student=getStudent(nodeList.item(i));
                if(student!=null){
                    k++;
                    students.add(student);
                }
                   /* else{
                       // notValidList.add(nodeList.item(i).getNodeValue());
                        System.out.println(nodeList.item(i).getNodeValue());

                    }*/

                j++;


            }
            //lets print Employee list information
            System.out.println("j ="+j);
            System.out.println("k ="+ k);

        } catch (SAXException | ParserConfigurationException | IOException e1) {
            e1.printStackTrace();
        }
        return  students;
    }


    private static FlyingEntry getEmployee(Node node)  {
        //XMLReaderDOM domReader = new XMLReaderDOM();
        FlyingEntry emp = new FlyingEntry();
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;

            try {

                emp.setDate(getTagValue("date", element));
                emp.setAircraftType(getTagValue("aircraft-type", element).replaceAll(" ",""));
                if(emp.getAircraftType().equals("C152")){
                    emp.setAircraftType("C150");

                }
                emp.setStudentName(getTagValue("students", element));
                emp.setStudentReference(Long.valueOf(getTagValue("students-reference",element)));
                try{

                    String techoTime= getTagValue("tacho-time", element);
                    emp.setDuration(Double.valueOf(techoTime));
                    return emp;
                    }
                    catch (Exception e) {

                        System.out.println("techno null");
                    }

                    try {

                        String blockTime = getTagValue("block-time", element);
                        Double part1 = Double.valueOf(blockTime.split(":")[0]);
                        Double part2 = Double.valueOf(blockTime.split((":"))[1]);
                        Double deci = part2 / 60;
                        emp.setDuration(part1 + deci);
                        return emp;
                    }
                    catch (Exception e){
                        System.out.println("Invalid row");
                        return null;
                    }

            }
            catch (Exception e){
                stu++;
                System.out.println(stu);

                return null;
            }


        }
return null;

    }

    private static Student getStudent(Node node)  {
        //XMLReaderDOM domReader = new XMLReaderDOM();
        Student stu = new Student();
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;

            try {

                stu.setName(getTagValue("name", element));
                stu.setReferenceNo(Long.valueOf(getTagValue("reference", element).replaceAll(" ","")));
                stu.setContactNo("077");
                stu.setEmail(stu.getReferenceNo()+"@aaa-fta.com");





            }
            catch (Exception e){

                System.out.println(stu);

                return null;
            }


        }
        return stu;

    }


    private static String getTagValue(String tag, Element element) throws NullPointerException {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodeList.item(0);
        String finalString="";



            String nodestr = node.getNodeValue();
            finalString = nodestr.replaceFirst("\\s++$", "");
            finalString=finalString.toUpperCase();
            if (finalString.equals("0")||finalString.equals("")||finalString.equals("0:00")||finalString.equals("0.0")) {
                throw new NullPointerException();
            }





        return finalString;
    }

}