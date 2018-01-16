package billPay;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class SpireHTMLObject implements HTMLObject {

    private String totalAmountDue;
    private String dueDate;
    private String pastDueBalance;
    private int budgetPriority = 0;
    
    public SpireHTMLObject(){
        getAllHTMLElements();
    }
    
    public SpireHTMLObject(int budgetPriority){
        getAllHTMLElements();
        this.budgetPriority = budgetPriority;
    }
    
    private void getAllHTMLElements() {
        File input = new File("/home/michaelbsmith/Documents/Laclede.html");
        try {
            Document doc = Jsoup.parse(input,"UTF-8","https://myaccount.lacledegas.com/AccountStatus.aspx");
            this.totalAmountDue = doc.select("td:contains(Account Balance:) + td").text().replaceAll("\\$","");
            Double currentBill = Double.parseDouble(doc.select("td:contains(Total Amount Due:) + td").text().replaceAll("\\$",""));
            this.pastDueBalance = String.format("%.2f",(Double.parseDouble(this.totalAmountDue) - currentBill)); 
            this.dueDate = doc.select("tr.celScrollableDataAltColor + tr + tr").first().text().replaceAll("Due Date: ","");
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
        Bill gasBill = new Bill();
        gasBill.setBillCategory("utility-gas");
        gasBill.setPriority(this.budgetPriority);
        Double totalAmountDue = Double.parseDouble(this.totalAmountDue);
        gasBill.setTotalAmountDue(totalAmountDue);
        Double pastDueBalance = Double.parseDouble(this.pastDueBalance);
        gasBill.setPastDueAmount(pastDueBalance);
        return gasBill;
    }
}
