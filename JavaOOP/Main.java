import java.util.*;
import java.lang.*;
import java.io.*;
import java.time.*;

/**
* This program is used to simulate a bank with different accounts.
* You can view balance, withdraw money or deposit money.
*
* @author Charlotte E Jacobs
* @since 23/01/2020
*/

class Account{
  protected int id;
  protected double balance;
  protected Date dateCreated;

/**
* This is the constructor method for the base class Account.
* @param id is the unique identifier for the accounts
* @param balance is the initial balance of the account
* @dateCreated is the parameter that is set inside the function. It is the date and time that account has been created.
*/
  Account(int id, double balance){
    this.id = id;
    this.balance = balance;
    LocalDateTime temp = java.time.LocalDateTime.now();
    this.dateCreated = java.sql.Timestamp.valueOf(temp);
  }

  /**
  * The get method for the id data field
  * @return the current object's id
  */
  public int getId(){
    return this.id;
  }

  /**
  * The get method for the balance data field
  * @return the current object's balance
  */
  public double getBalance(){
    return this.balance;
  }

  /**
  * The get method for the dateCreated data field
  * @return the current object's date created
  */
  public Date getDateCreated(){
    return this.dateCreated;
  }

  /**
  * The set method for the id data field
  * @param id sets the current object's id
  */
  public void setId(int id){
    this.id = id;
  }

  /**
  * The set method for the balance data field
  * @param balance sets the current object's balance
  */
  public void setBalance(double balance){
    this.balance = balance;
  }

  /**
  * The withdraw method is used to withdraw money from the account that it is called on (object)
  * @param amount the amount of money to withdraw from the account
  * @return returns the balance of the account after the money has been withdrawn
  */
  public double withdraw(double amount){
    this.balance = this.balance - amount;
    return this.balance;
  }

  /**
  * The deposit method is used to deposit money into the account that it is called on (object)
  * @param amount the amount of money to deposit into the account
  * @return returns the balance of the account after the money has been deposited
  */
  public double deposit(double amount){
    this.balance = this.balance + amount;
    return this.balance;
  }

}

class SavingsAccount extends Account{
  private double annualInterestRate;

  /**
  * This is the constructor method for the subclass SavingsAccount which is inherited from Account.
  * The super function gets called inside the constructor to call the constructor of the base class.
  * @param id is the unique identifier for the account
  * @param balance is the initial balance of the account
  * @param annualInterestRate is an extra variable of the SavingsAccount which determines the interest rate and also the monthly interest on that account based on the balance.
  */
  SavingsAccount(int id, double balance, double annualInterestRate){
    super(id, balance);
    this.annualInterestRate = annualInterestRate;
  }

  /**
  * The getMonthlyInterestRate is used to determine the monthly interest rate derived from the annual interest rate.
  * @return The method returns the monthly interest rate value.
  */
  public double getMonthlyInterestRate(){
    double returnVal = (this.annualInterestRate / 100)/12;
    return returnVal;
  }

  /**
  * The getMonthlyInterest is used to determine the monthly interest derived from the balance and the monthly interest rate.
  * It is a monetary value that you will get on the account per month.
  * @return The method returns the monthly interest of the account.
  */
  public double getMonthlyInterest(){
    double returnVal = this.balance * getMonthlyInterestRate();
    return returnVal;
  }
}

class CheckingAccount extends Account{
  private double overdraft;

  /**
  * This is the constructor method for the subclass CheckingAccount which is inherited from Account.
  * The super function gets called inside the constructor to call the constructor of the base class.
  * @param id is the unique identifier for the account
  * @param balance is the initial balance of the account
  * @param overdraft is an extra variable of the CheckingAccount which determines the extra amount of money that can be withdrawn from the account excluding the balance.
  */
  CheckingAccount(int id, double balance, double overdraft){
    super(id, balance);
    this.overdraft = overdraft;
  }

  /**
  * The get method for the overdraft data field
  * @return returns the overdraft value of the account
  */
  public double getOverdraft(){
    return this.overdraft;
  }
}

public class Main{
  /**
  * The main functino is called when the program loads.
  * Firsly 10 new accounts (5 CheckingAccounts and 5 SavingsAccounts) gets created and saved to a list.
  * The selectAccount method is then called to get the user input of the id of the account.
  * The id gets tested to see if it exists in the list. If it does not exist this method gets repeated.
  * If the id does exist in the list the getPrompt method is called which gets the user input what the user wants to do with the account.
  * Different steps gets executed based on the user's choice.
  * If 1. Check the balance is chosen, the program checks to see if it is a SavingsAccount and if it is the monthly interest of the account also gets displayed together with the balance. 
  * If 2. Withdraw is chosen, the program checks to see if it is a CheckingAccount and if it is the overdraft is taken into consideration as well as the balance to determine how much money can be withdrawn.
  * If 4. Exit is chosen the selectAccount function will get called again and the program gets repeated.
  * @param args is not used.
  */
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
    // A flag is set to make an infinite loop so that the program never exits
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

  /**
  * The selectAccount method is used to get the user's input of the id of the account they want to perform the functions on.
  * @return returns the user's input.
  */
  static int selectAccount(){
    Scanner myScanner = new Scanner(System.in);
    System.out.println("Please enter an account id");
    return myScanner.nextInt();
  }

  /**
  * The getPrompt method is used to get the user's input of the function they want to perform on the account.
  * @return returns the user's input.
  */
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
