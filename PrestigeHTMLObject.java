package billPay;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class PrestigeHTMLObject implements HTMLObject {

    private String totalAmountDue;
    private String dueDate;
    private String pastDueBalance;
    private int budgetPriority = 0;
    
    public PrestigeHTMLObject(){
        getAllHTMLElements();
    }
    
    public PrestigeHTMLObject(int budgetPriority){
        getAllHTMLElements();
        this.budgetPriority = budgetPriority;
    }
    
    private void getAllHTMLElements() {
        File input = new File("/home/michaelbsmith/Documents/Prestige.html");
        try {
            Document doc = Jsoup.parse(input,"UTF-8","https://customer.myprestige.com/Home");        
            Double paymentAmount = 286.31; //TODO: Contractual Payment Amount Not on Webpage as of now.
            Double actualAmountDue = Double.parseDouble(doc.select(".summary > h4 > span").first().text().replaceAll("\\$",""));
            if(actualAmountDue > paymentAmount){
                this.totalAmountDue = String.format("%.2f",actualAmountDue);
                this.pastDueBalance = String.format("%.2f",actualAmountDue - paymentAmount);
            } else {
                this.totalAmountDue = String.format("%.2f",paymentAmount);
                this.pastDueBalance = "0.00";
            }
            Element dueDateElement = doc.select(".Summary > h4").first();
            String unformattedDueDate = dueDateElement.child(1).text();
            int unformattedDateSpace1 = unformattedDueDate.indexOf(" ");
            int unformattedDateSpace2 = unformattedDateSpace1 + unformattedDueDate.substring(unformattedDueDate.indexOf(" ") + 1,unformattedDueDate.length()).indexOf(" ");
            String unformattedDueDateMonth = unformattedDueDate.substring(0,unformattedDueDate.indexOf(" ")); 
            String dueDateMonth = "";
            switch (unformattedDueDateMonth.toLowerCase()){
                case "january":
                    dueDateMonth = "01";   
                    break;
                case "february":
                    dueDateMonth = "02";   
                    break;
                case "march":
                    dueDateMonth = "03";   
                    break;
                case "april":
                    dueDateMonth = "04";   
                    break;
                case "may":
                    dueDateMonth = "05";   
                    break;
                case "june":
                    dueDateMonth = "06";   
                    break;
                case "july":
                    dueDateMonth = "07";   
                    break;
                case "august":
                    dueDateMonth = "08";   
                    break;
                case "september":
                    dueDateMonth = "09";   
                    break;
                case "october":
                    dueDateMonth = "10";   
                    break;
                case "november":
                    dueDateMonth = "11";   
                    break;
                case "december":
                    dueDateMonth = "12";   
                    break;
                }             
            String dueDateDay = unformattedDueDate.substring(unformattedDateSpace1 + 1,unformattedDateSpace2);     
            String dueDateYear = unformattedDueDate.substring(unformattedDateSpace2 + 2, unformattedDueDate.length());
            this.dueDate = formatDate(dueDateMonth + "/" + dueDateDay + "/" + dueDateYear);
        } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Time for Change. WebSite May Have Changed.");
        }   
    }
    private String formatDate(String existingDate){
        String formattedDate = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
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

    public void setBudgetPriority(int budgetPriority){
        this.budgetPriority = budgetPriority;
    }
    
    public Bill getBill(){
        Bill vanPayment = new Bill();
        vanPayment.setBillCategory("debt-Prestige");
        vanPayment.setPriority(this.budgetPriority);
        Double totalAmountDue = Double.parseDouble(this.totalAmountDue);
        vanPayment.setTotalAmountDue(totalAmountDue);
        Double pastDueBalance = Double.parseDouble(this.pastDueBalance);
        vanPayment.setPastDueAmount(pastDueBalance);
        return vanPayment;
    }
}
