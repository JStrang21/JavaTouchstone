import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static class Bank {
        //ArrayList used to add/delete customers from bank database
        static ArrayList<User> customers = new ArrayList<User>();

        public static User getUser(String user, String pass) {
            for (User u : customers) {
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

                String input = scanner.nextLine();

                if (input.equals("check")) {
                    System.out.println(returningUser.balance);
                }
                else if (input.equals("transfer")) {
                    System.out.println("Enter amount you wish to transfer: ");
                    int amount = scanner.nextInt();

                    if (returningUser.balance < amount) {
                        System.out.println("Not enough funds in account to complete transfer.");
                        return;
                    }

                    System.out.println("Enter account number of recipient: ");
                    int number = scanner.nextInt();

                    for (User u : Bank.customers) {
                        if (u.accountNumber == number) {
                            u.balance += amount;
                            returningUser.balance -= amount;
                            System.out.println("Transfer successful, account number " + number + " has received the funds.");
                        }
                        else {
                            System.out.println("No user found with that account number.");
                        }
                    }
                }

                else if (input.equals("deposit")) {
                    System.out.println("Enter the amount you wish to deposit: ");
                    int amount = scanner.nextInt();

                    returningUser.balance += amount;
                    System.out.println("Successful transfer, new balance is: " + returningUser.balance);
                }

                else if (input.equals("withdraw")) {
                    System.out.println("Enter the amount you wish to withdraw: ");
                    int amount = scanner.nextInt();

                    if (returningUser.balance < amount) {
                        System.out.println("Not enough funds in account to complete transfer.");
                        return;
                    }

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
            System.out.println("Enter your Username: ");
            String userName = scanner.nextLine();

            System.out.println("Enter your Password: ");
            String password = scanner.nextLine();

            System.out.println("Enter your First Name: ");
            String firstName = scanner.nextLine();

            System.out.println("Enter your Last Name: ");
            String lastName = scanner.nextLine();

            User newUser = new User(firstName, lastName, userName, password);

            printUser(newUser);

            Bank.customers.add(newUser);
        }
    }
}