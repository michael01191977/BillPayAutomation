package billPay;

import java.io.IOException;
import java.time.Instant;

public class Bill {
        private int priority;
        private String companyName;
        private String billCategory;
        private String webSiteAddress;
        private String userName;
        private String password;
        private double totalAmountDue;
        private double pastDueAmount;
        private String dueDate;
        
        public Bill(){
           
           checkRep();
        }

    private void checkRep() {
        // TODO assert the representation invariant
        
    }
    
    public int getPriority(){
        int priority = this.priority;
        return priority;
    }
    public String getCompanyName(){
        String companyName = this.companyName;
        return companyName;
    }
    public String getBillCategory(){
        String billCategory = this.billCategory;
        return billCategory;
    }
    public String getWebSiteAddress(){
        String webSiteAddress = this.webSiteAddress;
        return webSiteAddress;
    }
    public String getUserName(){
        String userName = this.userName;
        return userName;
    }
    public String getPassword(){
        String password = this.password;
        return password;
    }
    public double getTotalAmountDue(){
        double totalAmountDue = this.totalAmountDue;
        return totalAmountDue;
    }
    public double getPastDueAmount(){
        double pastDueAmount = this.pastDueAmount;
        return pastDueAmount;
    }
    public String getDueDate(){
        String dueDate = this.dueDate;
        return dueDate;
    }
    
    public void setPriority(int priority){
        this.priority = priority;
    }
    public void setCompanyName(String companyName){
        this.companyName = companyName;
    }
    public void setBillCategory(String billCategory){
        this.billCategory = billCategory;
    }
    public void setWebSiteAddress(String webSiteAddress){
        this.webSiteAddress = webSiteAddress;
    }
    public void setUserName(String userName){
        this.userName = userName;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public void setTotalAmountDue(double totalAmountDue){
        this.totalAmountDue = totalAmountDue;
    }
    public void setPastDueAmount(double pastDueAmount){
        this.pastDueAmount = pastDueAmount;
    }
    public void setDueDate(Instant dueDate){
        this.dueDate = String.valueOf(dueDate);
    }
    
    public void setDueDate(String dueDate){
        this.dueDate = dueDate;
    }
    
    public void goToWebSite() {     
        try{
            Runtime.getRuntime().exec(new String[]{"/usr/bin/google-chrome", this.webSiteAddress});
        }
        catch(IOException e){
                e.printStackTrace();
        }  
    } 
}



