package billPay;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class MeridianHTMLObject implements HTMLObject {

    private String totalAmountDue;
    private String dueDate;
    private String pastDueBalance;
    private int budgetPriority = 0;
    
    public MeridianHTMLObject(){
        getAllHTMLElements();
    }
    
    public MeridianHTMLObject(int budgetPriority){
        getAllHTMLElements();
        this.budgetPriority = budgetPriority;
    }
    
    private void getAllHTMLElements() {
        File input = new File("/home/michaelbsmith/Documents/Meridian.html");
        try {
            Document doc = Jsoup.parse(input,"UTF-8","https://secure.soft-pak.com/webpakmw/index.jsp");       
            this.totalAmountDue = doc.select("td:contains(Balance Due:) + td").text().replaceAll("\\$","");
            this.pastDueBalance = "0.00";//Meridian will cut off service until totalAmountDue is paid.
            Elements dueDateElement = doc.select("#grid0_1 > td + td + td");
            this.dueDate = formatDate(dueDateElement.first().text());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Time for Change. WebSite May Have Changed.");
        }   
    }
    
    private String formatDate(String existingDate) {
        String formattedDate = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
            Date date = sdf.parse(existingDate);
            sdf.applyPattern("MM/dd/yyyy");
            formattedDate = sdf.format(date);
        } catch (ParseException e) {
            System.out.println("Date being parsed: " + existingDate);
            e.printStackTrace();
        }
        //Per Meridian, Due date is 30 days after the bill date
        Calendar calendar = Calendar.getInstance();
        calendar.set(Integer.parseInt(formattedDate.substring(6,10)),Integer.parseInt(formattedDate.substring(0,2)),Integer.parseInt(formattedDate.substring(3,5)));
        calendar.add(calendar.MONTH,1);
        String returnDate = calendar.get(calendar.MONTH) + "/" + calendar.get(calendar.DATE) + "/" + calendar.get(calendar.YEAR);
        return returnDate;
    }
    
    public String getDueDate() {
        String dueDate = this.dueDate;
        return dueDate;
    }

    public String getTotalAmountDue() {
        String totalAmountDue = this.totalAmountDue;
        return totalAmountDue;
    }

    public String getPastDueBalance() {
        String pastDueBalance = this.pastDueBalance;
        return pastDueBalance;
    }
    
    public void setBudgetPriority(int budgetPriority){
        this.budgetPriority = budgetPriority;
    }
    
    public Bill getBill(){
        Bill trashBill = new Bill();
        trashBill.setBillCategory("utility-trash");
        trashBill.setPriority(this.budgetPriority);
        Double totalAmountDue = Double.parseDouble(this.totalAmountDue);
        trashBill.setTotalAmountDue(totalAmountDue);
        Double pastDueBalance = Double.parseDouble(this.pastDueBalance);
        trashBill.setPastDueAmount(pastDueBalance);
        return trashBill;
    }
}
