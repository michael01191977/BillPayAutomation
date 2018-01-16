package billPay;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class MSDHTMLObject implements HTMLObject {

    private String totalAmountDue;
    private String dueDate;
    private String pastDueBalance;
    private int budgetPriority = 0;
    
    public MSDHTMLObject(){
        getAllHTMLElements();
    }
    
    public MSDHTMLObject(int budgetPriority){
        getAllHTMLElements();
        this.budgetPriority = budgetPriority;
    }
    
    private void getAllHTMLElements() {
        File input = new File("/home/michaelbsmith/Documents/MSD.html");
        try {
            Document doc = Jsoup.parse(input,"UTF-8","https://myaccount.msd.st-louis.mo.us/MSDSSP/AccountSummary.aspx");       
            this.totalAmountDue = doc.select("#body_content_AccountSummaryTabControl_BillingSummaryControl1_lblCurrentBalanceText").text().replaceAll("\\$","").replaceAll("[(]","-").replaceAll("[)]","");
            Double currentBill = Double.parseDouble(doc.select("#body_content_AccountSummaryTabControl_BillingSummaryControl1_lblLastBillingAmountText").text().replaceAll("\\$",""));
            this.pastDueBalance = String.format("%.2f",(Double.parseDouble(this.totalAmountDue) - currentBill)); 
            this.dueDate = formatDate(doc.select("#body_content_AccountSummaryTabControl_BillingSummaryControl1_lblAppOrLatePaymentDateText").first().text().replaceAll("Due Date: ",""));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Time for Change. WebSite May Have Changed.");
        }   
    }
    
    private String formatDate(String existingDate) {
        String formattedDate = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
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
        Bill sewerBill = new Bill();
        sewerBill.setBillCategory("utility-sewer");
        sewerBill.setPriority(this.budgetPriority);
        Double totalAmountDue = Double.parseDouble(this.totalAmountDue);
        sewerBill.setTotalAmountDue(totalAmountDue);
        Double pastDueBalance = Double.parseDouble(this.pastDueBalance);
        sewerBill.setPastDueAmount(pastDueBalance);
        return sewerBill;
    }
}
