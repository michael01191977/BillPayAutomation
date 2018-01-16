package billPay;

public interface HTMLObject {
    String getDueDate();
    String getTotalAmountDue();
    String getPastDueBalance();
    Bill getBill();
}
