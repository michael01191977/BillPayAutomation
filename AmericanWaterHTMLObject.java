package billPay;

import java.io.File;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class AmericanWaterHTMLObject implements HTMLObject {

    private String totalAmountDue;
    private String dueDate;
    private String pastDueBalance;
    private int budgetPriority = 0;
    
    public AmericanWaterHTMLObject(){
        getAllHTMLElements();
    }
    
    public AmericanWaterHTMLObject(int budgetPriority){
        getAllHTMLElements();
        this.budgetPriority = budgetPriority;
    }
    
    private void getAllHTMLElements() {
        File input = new File("/home/michaelbsmith/Documents/AmericanWater.html");
        try {
            Document doc = Jsoup.parse(input,"UTF-8","https://wss.amwater.com/selfservice-web/accountDetail.do");       
            this.totalAmountDue = doc.select("span.billAmount").first().text().replaceAll("\\$","");
            this.pastDueBalance = doc.select("div.divTableHead:contains(Past Due Balance:) + div").text().replaceAll("\\$","");
            this.dueDate = doc.select("span:contains(Current amount due on:) + span").text();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Time for Change. WebSite May Have Changed.");
        }   
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
        Bill waterBill = new Bill();
        waterBill.setBillCategory("utility-water");
        waterBill.setPriority(this.budgetPriority);
        Double totalAmountDue = Double.parseDouble(this.totalAmountDue);
        waterBill.setTotalAmountDue(totalAmountDue);
        Double pastDueBalance = Double.parseDouble(this.pastDueBalance);
        waterBill.setPastDueAmount(pastDueBalance);
        return waterBill;
    }
}
