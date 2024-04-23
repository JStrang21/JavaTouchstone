import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static class Bank {
        //ArrayList used to add/delete customers from bank database
        static ArrayList<User> customers = new ArrayList<User>();

        public static User getUser(String user, String pass) {
            //iterate over customers
            for (User u : customers) {
                //If username and password of customer matches input then correct user
                if (u.userName.equals(user) && u.password.equals(pass)) {
                    return u;
                }
            }
            return null;
        }
    }

    public static class User {
        String first;
        String last;
        String userName;
        int accountNumber;
        String password;
        int checkingAccount;
        int balance;

        //Constructor
        public User(String f, String l, String u, String p) {
            first = f;
            last = l;
            userName = u;
            password = p;
            accountNumber = gen();
            checkingAccount = gen();
            balance = 0;
        }

        //Source -> https://stackoverflow.com/questions/7815196/how-to-generate-a-random-five-digit-number-java
        //Random 5-digit number generator -> for account and checking numbers
        public int gen() {
            Random r = new Random( System.currentTimeMillis() );
            return 10000 + r.nextInt(20000);
        }
    }

    public static void printUser(User u) {
        System.out.println("First: " + u.first);
        System.out.println("Last: " + u.last);
        System.out.println("Username: " + u.userName);
        System.out.println("Password: " + u.password);
        System.out.println("Accountnumber: " + u.accountNumber);
        System.out.println("Checking Account Number: " + u.checkingAccount);
        System.out.println("Current Balance: " + u.balance);
    }

    public static void main(String[] args) {
        //Create scanner to get user input from CL
        Scanner scanner = new Scanner(System.in);

        //Make Bank object
        Bank genericBank = new Bank();

        System.out.println("Welcome to Generic Bank!");
        System.out.println("Do you have an account with us? Y/N: ");
        //Get answer from input and make lowercase
        String answer = scanner.nextLine().toLowerCase();

        //If user already has an account
        if (answer.equals("y")) {
            //Get username and password from text input
            System.out.println("Enter your Username: ");
            String userName = scanner.nextLine();

            System.out.println("Enter your Password: ");
            String password = scanner.nextLine();

            //Get user from Bank object's ArrayList
            User returningUser = Bank.getUser(userName, password);

            //If user not found
            if (returningUser == null) {
                System.out.println("Wrong username &/or password. Please try again.");
                return;
            }

            //If user is found
            if (returningUser != null) {
                System.out.println("Enter a command (check, transfer, deposit, withdraw): ");

                //Scan input for what user wants to do
                String input = scanner.nextLine();

                if (input.equals("check")) {
                    System.out.println(returningUser.balance);
                }
                //User wants to transfer money
                else if (input.equals("transfer")) {
                    System.out.println("Enter amount you wish to transfer: ");
                    //Get amount they wish to transfer
                    int amount = scanner.nextInt();

                    //Cant transfer if user doesn't have funds
                    if (returningUser.balance < amount) {
                        System.out.println("Not enough funds in account to complete transfer.");
                        return;
                    }

                    //Get account number of recipient
                    System.out.println("Enter account number of recipient: ");
                    int number = scanner.nextInt();

                    //Iterate through customers
                    for (User u : Bank.customers) {
                        //If account number of customer matches inputted number then correct user
                        if (u.accountNumber == number) {
                            //Add money to recipient and take from user
                            u.balance += amount;
                            returningUser.balance -= amount;
                            System.out.println("Transfer successful, account number " + number + " has received the funds.");
                        }
                        //If no user found then fail
                        else {
                            System.out.println("No user found with that account number.");
                        }
                    }
                }

                //User wants to deposit money
                else if (input.equals("deposit")) {
                    System.out.println("Enter the amount you wish to deposit: ");
                    //Get amount of money user wishes to deposit
                    int amount = scanner.nextInt();

                    //Add money to user's account
                    returningUser.balance += amount;
                    System.out.println("Successful transfer, new balance is: " + returningUser.balance);
                }

                //User wishes to withdraw money
                else if (input.equals("withdraw")) {
                    System.out.println("Enter the amount you wish to withdraw: ");
                    //Get amount that the user wishes to withdraw
                    int amount = scanner.nextInt();

                    //If user doesn't have enough money to withdraw then fail
                    if (returningUser.balance < amount) {
                        System.out.println("Not enough funds in account to complete transfer.");
                        return;
                    }

                    //Take money out of user's account
                    returningUser.balance -= amount;
                    System.out.println("Successful transfer, new balance is: " + returningUser.balance);
                }
                else {
                    System.out.println("Invalid command please try again.");
                    return;
                }
            }
        }
        else {
            //Signup for new user
            System.out.println("Enter your Username: ");
            String userName = scanner.nextLine();

            System.out.println("Enter your Password: ");
            String password = scanner.nextLine();

            System.out.println("Enter your First Name: ");
            String firstName = scanner.nextLine();

            System.out.println("Enter your Last Name: ");
            String lastName = scanner.nextLine();

            //Create new user object with inputted data
            User newUser = new User(firstName, lastName, userName, password);

            //Print results
            printUser(newUser);

            //Add new user to list of customers in bank's database
            Bank.customers.add(newUser);
        }
    }
}