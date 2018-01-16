package billPay;

import java.io.File;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class AmerenHTMLObject implements HTMLObject {
    private String totalAmountDue;
    private String dueDate;
    private String pastDueBalance;
    private int budgetPriority = 0;
    
    public AmerenHTMLObject(){
        getAllHTMLElements();
    }
    
    public AmerenHTMLObject(int budgetPriority){
        getAllHTMLElements();
        this.budgetPriority = budgetPriority;
    }
    
    private void getAllHTMLElements() {
        File input = new File("/home/michaelbsmith/Documents/Ameren.html");
        try {
            Document doc = Jsoup.parse(input,"UTF-8","https://www.ameren.com/home");       
            this.totalAmountDue = doc.select("div.label.data").first().text().replaceAll("\\$","");
            this.pastDueBalance = doc.select("span.redBold").text().replaceAll("\\$","");
            if (doc.select("span.redBold").text().replaceAll("\\$","") != ""){
                //System.out.println("test"); TODO: This may need to be removed.
                
            }
            this.dueDate = doc.select("div.note.detail2").first().text();
            int subStringStart = this.dueDate.indexOf(" ");
            int stringLength = this.dueDate.length();
            this.dueDate = this.dueDate.substring(subStringStart,stringLength).replaceAll("\\s","");
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
        if(pastDueBalance == ""){
            pastDueBalance = "0.00";
        }
        return pastDueBalance;
    }
    
    public void setBudgetPriority(int budgetPriority){
        this.budgetPriority = budgetPriority;
    }
    
    public Bill getBill(){
        Bill electricBill = new Bill();
        electricBill.setBillCategory("utility-electricity");
        electricBill.setPriority(this.budgetPriority);
        Double totalAmountDue = Double.parseDouble(this.totalAmountDue);
        electricBill.setTotalAmountDue(totalAmountDue);
        Double pastDueBalance = Double.parseDouble(this.pastDueBalance);
        electricBill.setPastDueAmount(pastDueBalance);
        return electricBill;
    }
}
