package billPay;

public class Income {

    private String source;
    private double amount;
    
    public Income(){
        
        checkRep();
     }

    // assert the rep invariant
    private void checkRep() {
        // TODO Auto-generated method stub
    }
 
    public void setSource(String source){
        this.source = source;
    }
 
    public void setAmount(double amount){
        this.amount = amount;
    }

    public String getSource(){
        String source = this.source;
        return source;
    }
    
    public double getAmount(){
        double amount = this.amount;
        return amount;
    }
}
