import java.util.*;
import java.lang.*;
import java.io.*;
import java.time.*;

class Account{
  protected int id;
  protected double balance;
  protected Date dateCreated;

  Account(int id, double balance){
    this.id = id;
    this.balance = balance;
    LocalDateTime temp = java.time.LocalDateTime.now();
    this.dateCreated = java.sql.Timestamp.valueOf(temp);
  }

  public int getId(){
    return this.id;
  }

  public double getBalance(){
    return this.balance;
  }

  public Date getDateCreated(){
    return this.dateCreated;
  }

  public void setId(int id){
    this.id = id;
  }

  public void setBalance(double balance){
    this.balance = balance;
  }

  public double withdraw(double amount){
    this.balance = this.balance - amount;
    return this.balance;
  }

  public double deposit(double amount){
    this.balance = this.balance + amount;
    return this.balance;
  }

}

class SavingsAccount extends Account{
  private double annualInterestRate;
  SavingsAccount(int id, double balance, double annualInterestRate){
    super(id, balance);
    this.annualInterestRate = annualInterestRate;
  }

  public double getMonthlyInterestRate(){
    double returnVal = (this.annualInterestRate / 100)/12;
    return returnVal;
  }

  public double getMonthlyInterest(){
    double returnVal = this.balance * getMonthlyInterestRate();
    return returnVal;
  }
}

class CheckingAccount extends Account{
  private double overdraft;
  CheckingAccount(int id, double balance, double overdraft){
    super(id, balance);
    this.overdraft = overdraft;
  }

  public double getOverdraft(){
    return this.overdraft;
  }
}

public class Main{
  public static void main(String[] args){
    SavingsAccount save1 = new SavingsAccount(1, 500, 2.5);
    SavingsAccount save2 = new SavingsAccount(2, 1000, 3);
    SavingsAccount save3 = new SavingsAccount(3, 12500, 3.2);
    SavingsAccount save4 = new SavingsAccount(4, 70000, 4.5);
    SavingsAccount save5 = new SavingsAccount(5, 1080, 20);
    CheckingAccount check1 = new CheckingAccount(6, 200, 100);
    CheckingAccount check2 = new CheckingAccount(7, 2050, 1000);
    CheckingAccount check3 = new CheckingAccount(8, 10500, 4000);
    CheckingAccount check4 = new CheckingAccount(9, 100.70, 70);
    CheckingAccount check5 = new CheckingAccount(10, 329.12, 500);

    List<Account> accountsList = new ArrayList<Account>();
    accountsList.add(save1);
    accountsList.add(save2);
    accountsList.add(save3);
    accountsList.add(save4);
    accountsList.add(save5);
    accountsList.add(check1);
    accountsList.add(check2);
    accountsList.add(check3);
    accountsList.add(check4);
    accountsList.add(check5);

    int currAccount = selectAccount();
    Account currAccountObj = null;
    int accountId = -1;
    while (accountId == -1){
      for (Account a: accountsList){
        if(a.getId() == currAccount){
          accountId = currAccount;
          currAccountObj = a;
          int currPrompt = getPrompt();
          double outValue = 0;
          while(currPrompt != -1){
            switch (currPrompt){
              case 1:
                outValue = currAccountObj.getBalance();
                System.out.println("Current balance of account with id " + accountId + " is " + String.format("%.2f", outValue));
                if(currAccountObj instanceof SavingsAccount){
                   double monthlyInt = ((SavingsAccount) currAccountObj).getMonthlyInterest();
                   System.out.println("You will get a montly interest of " + String.format("%.2f", monthlyInt));
                }
                currPrompt = getPrompt();
              break;
              case 2:
                double amountAvailable = currAccountObj.getBalance();
                Scanner myScanner = new Scanner(System.in);
                System.out.println("How much would you like to withdraw?");
                double withdrawAmount = myScanner.nextDouble();
                if(currAccountObj instanceof CheckingAccount){
                  amountAvailable = amountAvailable + ((CheckingAccount) currAccountObj).getOverdraft();
                }

                if (withdrawAmount > amountAvailable){
                  System.out.println("Cannot withdraw this much. Only " + String.format("%.2f", amountAvailable) + " is available for withdrawel.");
                  currPrompt = getPrompt();
                }
                else {
                  outValue = currAccountObj.withdraw(withdrawAmount);
                  if (outValue < 0){
                    System.out.println("You still have " + String.format( "%.2f", (amountAvailable - withdrawAmount)) + " left in your overdraft.");
                  }
                  System.out.println("Current balance of account with id " + accountId + " after withdrawing money is " + String.format("%.2f", outValue));
                  currPrompt = getPrompt();
                }
              break;
              case 3:
                Scanner myScanner2 = new Scanner(System.in);
                System.out.println("How much would you like to deposit?");
                outValue = currAccountObj.deposit(myScanner2.nextDouble());
                System.out.println("Current balance of account with id " + accountId + " after depositing money is " + String.format("%.2f", outValue));
                currPrompt = getPrompt();
              break;
              case 4:
                accountId = -1;
                currPrompt = -1;
                currAccount = selectAccount();
              break;
              default:
                currPrompt = getPrompt();
            }
          }
        }
      }
      if (accountId == -1){
        currAccount = selectAccount();
      }
    }
  }

  static int selectAccount(){
    Scanner myScanner = new Scanner(System.in);
    System.out.println("Please enter an account id");
    return myScanner.nextInt();
  }

  static int getPrompt(){
    Scanner myScanner = new Scanner(System.in);
    System.out.println("Main menu");
    System.out.println("1. check the balance");
    System.out.println("2. withdraw");
    System.out.println("3. deposit");
    System.out.println("4. exit");
    return myScanner.nextInt();
  }
}
