package billPay;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


public class CharterHTMLObject implements HTMLObject {

    private String totalAmountDue;
    private String dueDate;
    private String pastDueBalance;
    
    public CharterHTMLObject(){
        //TODO: Change to accomodate changes to Charter webpage.
        getAllHTMLElements();
    }
    
    private void getAllHTMLElements() {
        File input = new File("/home/michaelbsmith/Documents/Charter.html");
        try {
            Document doc = Jsoup.parse(input,"UTF-8","https://www.spectrum.net/billing-and-transactions/");       
            this.totalAmountDue = doc.select("span:contains(Total Due by ) + span + span").text().replaceAll("\\$","");
            double currentBalance = Double.parseDouble(doc.select("li:contains(Current Services) > span").text().replaceAll("\\$",""));
            this.pastDueBalance = String.format("%.2f",Double.parseDouble(this.totalAmountDue) - currentBalance);
            this.dueDate = formatDate(doc.select("span:contains(Total Due by )").text().replaceAll("Total Due by ",""));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Time for Change. WebSite May Have Changed.");
        }   
    }

    private String formatDate(String existingDate){
        String formattedDate = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy", Locale.US);
            Date date = sdf.parse(existingDate);
            sdf.applyPattern("MM/dd/yyyy");
            formattedDate = sdf.format(date);
        } catch (ParseException e) {
            System.out.println("Date being parsed: " + existingDate);
            e.printStackTrace();
        }
        return formattedDate;
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
}
