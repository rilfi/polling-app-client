package dcf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class DataCaptureFileDownload {
	static String date="";
	private static final String REST_URI = "http://localhost:5000/api/flyingExpense";
	private static final String REST_URI_STU = "http://localhost:5000/api/students";

	private static Client client = ClientBuilder.newClient();
	public static void main(String[] args) throws IOException, InterruptedException {

		sendDataTest();
		//sendDataTestStudent();
		/*DataCaptureFileDownload dcfd=new DataCaptureFileDownload();
		WebDriver driver=dcfd.login();
		String date=getDate();
		List<FlyingEntry> oldCSV=new LinkedList<>();
		List<FlyingEntry> newCSV=new LinkedList<>();
		List<FlyingEntry> diffCSV=new LinkedList<>();
		int iterationNo=0;
		while(true){
			System.out.println("Iteration # "+iterationNo);
			long startTime=System.currentTimeMillis();
			String StartTimeHM=getTimeHM();
			System.out.println("---------------------------------");
			System.out.println("Iteration started Time "+ getTime());
			
			deleteAllXML();
			downloadfile(driver);
			oldCSV=newCSV;
			newCSV=readXML();
			diffCSV=getCSVdifferent(newCSV, oldCSV);
			System.out.println("# of new entries ="+diffCSV.size());
			*//*for(String s:diffCSV) {
				String onBlockTime=s.split("\",\"")[24];
				System.out.println("*************************** New Entry********************");
				System.out.println("*********************************************************");
				System.out.println("on_block time "+onBlockTime);
				System.out.println("Time Gap between capture time and onBlock time "+getTimeDiff(onBlockTime, StartTimeHM));
				System.out.println("*********************************************************");
				//System.out.println(s);
			}
			if(diffCSV.size()>0) {

				for(FlyingEntry fe:diffCSV){
					sendData(fe);
				}


			}*//*


			Thread.sleep(5000);
			System.out.println("Iteration ended Time "+ getTime());
			iterationNo++;
			long endTime=System.currentTimeMillis();
			System.out.println("Time taken for a iteration = "+(endTime-startTime)/1000+"seconds");

			
			
		}
*/
        
        
	}
	
	public static List<FlyingEntry>getCSVdifferent(List<FlyingEntry>newCSV,List<FlyingEntry>oldCSV){
		List<FlyingEntry>temp=new LinkedList<FlyingEntry>(newCSV);
		temp.removeAll(oldCSV);
		return temp;

		
	}
	
	public static List<FlyingEntry>readXML() throws IOException, InterruptedException{
		Thread.sleep(2000);
		File download=new File("C:\\Users\\s1\\Downloads");
		List<FlyingEntry>flyingEntries=new ArrayList<>();
        String fileName = null;
        if(download.isDirectory()) {
        	String[]files=download.list();
        	for(String s: files) {
        		if(s.endsWith(".xml")) {
        		fileName="C:\\Users\\s1\\Downloads\\"+s;
        		flyingEntries=XMLReaderDOM.getFlyEntries(fileName);
        		break;
        		}
        		
        	}
        	
        }
       // System.out.println(fileName);
       // File csvFile=new File(fileName);
        
       // List<String> f2 = new LinkedList<String>(FileUtils.readLines(csvFile));
       // f2.remove(0);
       // f2.remove(f2.size()-1);
/*        System.out.println("------row length start----------");
        for(String s:f2) {
        	System.out.println(s.length());
        }
        System.out.println("------row length end----------");*/
        
        
        
        System.out.println("# of enties in xml file ="+flyingEntries.size());
        Thread.sleep(5000);
        deleteAllXML();
        Thread.sleep(5000);
        

		return flyingEntries;
		
	}
	public static void deleteAllXML() throws IOException, InterruptedException{
		File download=new File("C:\\Users\\s1\\Downloads");
        String fileName = null;
        if(download.isDirectory()) {
        	String[]files=download.list();
        	for(String s: files) {
        		if(s.endsWith(".xml")) {
        		fileName="C:\\Users\\s1\\Downloads\\"+s;
        		File csvFile=new File(fileName);
        		csvFile.delete();
        		}
        		
        	}
        	
        }
		Thread.sleep(2000);

		
	}
	public  WebDriver login() {
		//WebDriver wd;
		System.setProperty("webdriver.chrome.driver","D:\\aaa\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();


		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("safebrowsing.enabled", "true");
		options.setExperimentalOption("prefs", prefs);
        options.addArguments("headless");
        //options.addArguments("window-size=1200x600");
		ChromeDriver wd = new ChromeDriver(options);

		//wd=new HtmlUnitDriver();
    	wd.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);    	
    	wd.get(getUrl(1));
    	WebElement email=wd.findElement(By.id("user_session_email"));
    	WebElement password=wd.findElement(By.id("user_session_password"));
    	WebElement login=wd.findElement(By.name("commit"));
        email.clear();			
        password.clear();
    	email.sendKeys("rilfi@live.com");					
        password.sendKeys("Ktv4mdialo@g");
        login.submit();
        return wd;
		
	}
	public static void downloadfile( WebDriver wd) throws InterruptedException {
		if(!date.equals(getDate())) {
			wd.get(getUrl(1));
		}

        wd.findElement(By.linkText("Export as")).click();
        wd.findElement(By.linkText("XML")).click();
        Thread.sleep(10000);
        
		
	}
    public static String getDate() {
    	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");  
    	   LocalDateTime now = LocalDateTime.now();  
    	   return dtf.format(now);  
    }
    public static String getTime() {
    	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.n");  
    	   LocalDateTime now = LocalDateTime.now();  
    	   return dtf.format(now);  
    }
    public static String getTimeHM() {
    	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");  
    	   LocalDateTime now = LocalDateTime.now();  
    	   return dtf.format(now);  
    }
    public static String getUrl(int page) {
    	String date=getDate();
        String url="https://aaoa.flightlogger.net/report/flight?page="+page+"&from="+date+"&to="+date;
		//String url="https://aaoa.flightlogger.net/report/flight?page=1&from=01.01.2018&to=11.09.2018";
        return url;
    }


    public static void sendData(FlyingEntry flyingEntry){

		Response response=client.target(REST_URI).request(MediaType.APPLICATION_JSON).post(Entity.entity(flyingEntry, MediaType.APPLICATION_JSON));

	}
	public static void sendDataTest(){
		List<FlyingEntry> flyingEntries=XMLReaderDOM.getFlyEntries("flight_report-2018_09_16_01_32_01.xml");
	//	List<FlyingEntry> flyingEntries=XMLReaderDOM.getFlyEntries("flight_report-2018_09_09_13_08_10.xml");

		for(FlyingEntry fe:flyingEntries){
			Response response=client.target(REST_URI).request(MediaType.APPLICATION_JSON).post(Entity.entity(fe, MediaType.APPLICATION_JSON));

		}

	}
	public static void sendDataTestStudent(){
		List<Student> students=XMLReaderDOM.getStudents("stuRef.xml");
		for(Student su:students){
			Response response=client.target(REST_URI_STU).request(MediaType.APPLICATION_JSON).post(Entity.entity(su, MediaType.APPLICATION_JSON));

		}
	}
    public static void dataPersist(List<String> insertList) {
    	String cs = "jdbc:mysql://localhost:3306/aaa?useSSL=false";
        String user = "root";
        String password = ""; 

        //String sql = "INSERT INTO Testing(Id) VALUES(?)";
        //String sql2="INSERT INTO `mytable` (`id`,`account`, `date`, `aircraft`, `aircraft_type`, `type_of_activity`, `kind_of_operation`, `program_name`, `program_revision`, `pics`, `pics_reference`, `instructors`, `instructors_reference`, `students`, `students_reference`, `renters`, `renters_reference`, `crew`, `crew_reference`, `customer`, `departure`, `arrival`, `off_block`, `airborne`, `landing`, `on_block`, `tacho_start`, `tacho_finish`, `block_time`, `airborne_time`, `tacho_time`, `pilot_flying`, `fuel_coefficient`, `fuel`, `flight_rules`, `flight_type`, `day_night`, `cross_country`, `if_time`, `asymmetric_time`, `landings`, `touch_and_go`, `approaches`, `go_around`, `comment`) VALUES (NULL, '1', '2018-07-09', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '11', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', b'1', b'1', '1')";
        //String sql1="INSERT INTO `mytable` (`id`,`account`, `date`, `aircraft`, `aircraft_type`, `type_of_activity`, `kind_of_operation`, `program_name`, `program_revision`, `pics`, `pics_reference`, `instructors`, `instructors_reference`, `students`, `students_reference`, `renters`, `renters_reference`, `crew`, `crew_reference`, `customer`, `departure`, `arrival`, `off_block`, `airborne`, `landing`, `on_block`, `tacho_start`, `tacho_finish`, `block_time`, `airborne_time`, `tacho_time`, `pilot_flying`, `fuel_coefficient`, `fuel`, `flight_rules`, `flight_type`, `day_night`, `cross_country`, `if_time`, `asymmetric_time`, `landings`, `touch_and_go`, `approaches`, `go_around`, `comment`) VALUES (NULL,";

       Connection con = null;
	try {
		con = DriverManager.getConnection(cs, user, password);
		PreparedStatement pst = null ;
	
       for(String row:insertList) {
    	   if(row.startsWith("\"account\"")||row.length()<10) {
    		   continue;
    	   }
           String sql1="INSERT INTO `mytable` (`id`,`account`, `date`, `aircraft`, `aircraft_type`, `type_of_activity`, `kind_of_operation`, `program_name`, `program_revision`, `pics`, `pics_reference`, `instructors`, `instructors_reference`, `students`, `students_reference`, `renters`, `renters_reference`, `crew`, `crew_reference`, `customer`, `departure`, `arrival`, `off_block`, `airborne`, `landing`, `on_block`, `tacho_start`, `tacho_finish`, `block_time`, `airborne_time`, `tacho_time`, `pilot_flying`, `fuel_coefficient`, `fuel`, `flight_rules`, `flight_type`, `day_night`, `cross_country`, `if_time`, `asymmetric_time`, `landings`, `touch_and_go`, `approaches`, `go_around`, `comment`) VALUES (NULL,";

    	   
    	   //System.out.println(row);
    	   row=row.replaceAll(",\"\",", ",NULL,");
    	   row=row.replaceAll(",\"\",", ",NULL,");
    	   row=row.replaceAll("'", "''");
    	   row=row.replaceAll(",\"", ",\'");
    	   row=row.replaceAll("\",", "\',");
    	   row="..."+row+"...";
    	   row=row.replace("\"...", "\'");
    	   row=row.replace("...\"", "\'");
    	   
    	   //System.out.println(row);
    	   sql1+=row+")";
    	   //System.out.println(sql1);

    	   
    	   
			
			try {
			pst= con.prepareStatement(sql1) ;
			pst.execute();
			}catch (Exception e)  {
				
				System.out.println(sql1);
				e.printStackTrace();
				// TODO: handle exception
			}
		} 

    pst.close();
    con.close();
       
	}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	

       }
    
    public static String getTimeDiff(String time1,String time2) {
    	

    	SimpleDateFormat format = new SimpleDateFormat("HH:mm");
    	Date date1 = null;
		try {
			date1 = format.parse(time1);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	Date date2 = null;
		try {
			date2 = format.parse(time2);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	long difference = date2.getTime() - date1.getTime(); 
    	long millis = difference % 1000;
    	long second = (difference / 1000) % 60;
    	long minute = (difference / (1000 * 60)) % 60;
    	long hour = (difference / (1000 * 60 * 60)) % 24;

    	String time = String.format("%02d:%02d:%02d.%d", hour, minute, second, millis);
    	
		return time;
    	
    }
        

            

        } 
    	
    


