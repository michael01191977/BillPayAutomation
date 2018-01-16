package billPay;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GeicoHTMLObject implements HTMLObject {

    private String totalAmountDue;
    private String dueDate;
    private String pastDueBalance;
    
    public GeicoHTMLObject(){
        getAllHTMLElements();
    }
    
    private void getAllHTMLElements() {
        File input = new File("/home/michaelbsmith/Documents/geico.html");
        try {
            Document doc = Jsoup.parse(input,"UTF-8","https://portfolio.geico.com/portfolio/?traceback=static&login=finish");       
            this.totalAmountDue = doc.select("span[id=form:futureAmtDue]").text().replaceAll("\\$","");
            this.pastDueBalance = "0.00"; //Geico will cancel the policy if payment is not made.
            this.dueDate = doc.select("span[id=form:futureDueDate]").text().replaceAll("\\$","");
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
}
