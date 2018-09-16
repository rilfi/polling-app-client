package dcf;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.chrome.ChromeDriver;



public class NofRowsColmns {
	static List<String[]> rows = new LinkedList<String[]>();
	static List<String[]> newEntries = new LinkedList<String[]>();
	static List<String[]> headList = new LinkedList<String[]>();
	static List<String[]> displayList = new LinkedList<String[]>();

	
    public static void main(String[] args) throws InterruptedException  {
    	System.out.println(getDate());
    	getTable();
   	}
    public static String getDate() {
    	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");  
    	   LocalDateTime now = LocalDateTime.now();  
    	   return dtf.format(now);  
    }
    public static void getTable() throws InterruptedException{
    	WebDriver wd;
    	System.setProperty("webdriver.chrome.driver","D:\\aaa\\chromedriver.exe");
    	wd = new ChromeDriver();
    	wd.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);    	
    	wd.get("https://aaoa.flightlogger.net");
    	WebElement email=wd.findElement(By.id("user_session_email"));
    	WebElement password=wd.findElement(By.id("user_session_password"));
    	WebElement login=wd.findElement(By.name("commit"));
        email.clear();			
        password.clear();
    	email.sendKeys("rilfi@live.com");					
        password.sendKeys("Ktv4mdialo@g");
        login.submit();
        
        wd.get(getUrl(1));
        String date=getDate();
    	WebElement headingTable=wd.findElement(By.xpath("/html/body/div[3]/div[3]/div[2]/div[1]/div/table/thead/tr"));
    	List<WebElement>headings=headingTable.findElements(By.tagName("th"));
    	String rawString="";       	
    	for (int i = 0; i < headings.size(); i++) {
    		rawString+=headings.get(i).getText()+"-,-";
			
		}
    	String [] rawArray=rawString.split("-,-");
    	headList.add(rawArray);
    	String ddd="";
	    for (int i = 0; i < rawArray.length; i++) {
			String lined="";
			for (int j = 0; j < rawArray[i].length(); j++) {
				lined+="-";
				
			}
			ddd+=lined+",";
		}
    	//System.out.println(rawArray.length);
    	rawArray=ddd.split(",");
    	headList.add(rawArray);
        
        while(true){
        	
        	List<String[]> oldList=rows;
        	rows = new LinkedList<String[]>();
        	wd.get(getUrl(1));
        	int numberofpages=1;
        	try {
        	if(wd.findElement(By.className("pagination")).isDisplayed()) {
        		numberofpages=getNumberofPages(wd);
        	}
        }
        	catch (NoSuchElementException e) {
				
			}
        	for (int i = 1; i <= numberofpages; i++) {
        		if(i!=1) {
        			wd.get(getUrl(i));
        		}
            	WebElement mytable = wd.findElement(By.xpath("/html/body/div[3]/div[3]/div[2]/div[1]/div/table/tbody"));
            	List < WebElement > rows_table = mytable.findElements(By.tagName("tr"));
            	
            	int rows_count = rows_table.size();
            	//Loop will execute till the last row of table.
            	for (int row = 0; row < rows_count; row++) {
            	    //To locate columns(cells) of that specific row.
            	    List < WebElement > Columns_row = rows_table.get(row).findElements(By.tagName("td"));
            	    //To calculate no of columns (cells). In that specific row.
            	    int columns_count = Columns_row.size();
            	    //System.out.println("Number of cells In Row " + row + " are " + columns_count);
            	    //Loop will execute till the last cell of that specific row.
            	    rawString="";
            	    for (int column = 0; column < columns_count; column++) {
            	        // To retrieve text from that specific cell.
            	        String celtext = Columns_row.get(column).getText();
            	        if(celtext.equals("")) {
            	        	celtext="nullll";
            	        }
            	        rawString+=celtext+"-,-";
            	        
            	    }
            	    rawArray=rawString.split("-,-");
            	    //System.out.println(rawArray.length);
            	    rows.add(rawArray);
            	    
            	}
        		
				
			}
        	
        	newEntries=getNewEntries( oldList,rows);
        	displayList = new LinkedList<String[]>();
        	for(String[] hl:headList) {
        		displayList.add(hl);
        		
        	}
        	for(String[]nl:newEntries ) {
        		displayList.add(nl);
        	}
        	
        	System.out.println(new NofRowsColmns().toString());

        	
        	Thread.sleep(60000);
        	
        }
    }
    public static Integer getNumberofPages(WebDriver wdv) {
    	
    	WebElement links=wdv.findElement(By.className("pagination"));
    	
    	List<WebElement>linkList=links.findElements(By.tagName("li"));   	
    	return linkList.size()-2;
    	
    }
    	

    public static String getUrl(int page) {
    	String date=getDate();
        String url="https://aaoa.flightlogger.net/report/flight?page="+page+"&from="+date+"&to="+date;
        return url;
    }
    public static List<String[]>getNewEntries(List<String[]> oldlist,List<String[]>newList ){
    	List<String[]>temp=new LinkedList<String[]>();
    	List<Integer>numberList=new LinkedList<Integer>();
    	List<String[]>returnList=new LinkedList<String[]>();
    	for (int i = 0; i < newList.size(); i++) {
    		for (int j = 0; j < oldlist.size(); j++) {
    			if(newList.get(i)[1].equals(oldlist.get(j)[1]) &&newList.get(i)[2].equals(oldlist.get(j)[2]) && newList.get(i)[21].equals(oldlist.get(j)[21]) ){
    				numberList.add(i);
    				break;
    				
    			}
				
			}
    		
			
		}
    	for (int i = 0; i < newList.size(); i++) {
    		if(!numberList.contains(i)) {
    			returnList.add(newList.get(i));
    		}
		}
    		
    	
    	
		return returnList;
    	
    }
    @Override
    public String toString()
    {
        StringBuilder buf = new StringBuilder();
 
        int[] colWidths = colWidths();
 
        for(String[] row : displayList) {
            for(int colNum = 0; colNum < row.length; colNum++) {
                buf.append(
                    StringUtils.rightPad(
                        StringUtils.defaultString(
                            row[colNum]), colWidths[colNum]));
                buf.append(' ');
            }
 
            buf.append('\n');
        }
 
        return buf.toString();
    }
    private int[] colWidths()
    {
        int cols = -1;
 
        for(String[] row : displayList)
            cols = Math.max(cols, row.length);
 
        int[] widths = new int[cols];
 
        for(String[] row : displayList) {
            for(int colNum = 0; colNum < row.length; colNum++) {
                widths[colNum] =
                    Math.max(
                        widths[colNum],
                        StringUtils.length(row[colNum]));
            }
        }
 
        return widths;
    }
}