import java.util.Scanner;

public class BankingApp {
    private static final Scanner SCANNER = new Scanner(System.in);

    private static final String CLEAR = "\033[H\033[2J";
    private static final String COLOR_BLUE_BOLD = "\033[34;1m";
    private static final String COLOR_RED_BOLD = "\033[31;1m";
    private static final String COLOR_GREEN_BOLD = "\033[33;1m";
    private static final String RESET = "\033[0m";

    private static final String DASHBOARD = "Welcome to Smart Banking";
    private static final String CREATE_NEW_ACCOUNT = "Create New Account";
    private static final String DEPOSIT = "Deposit";
    private static final String WITHDRAWALS = "Withdrawals";
    private static final String TRANSFER = "Transfer";
    private static final String CHECK_ACCOUNT_BALANCE = "Check Account Balance";
    private static final String DELETE_ACCOUNT = "Delete Account";
    private static final String EXIT = "Exit";

    private static final String ERROR_MSG = String.format("\t%s%s%s\n", COLOR_RED_BOLD, "%s", RESET);
    private static final String SUCCESS_MSG = String.format("\t%s%s%s\n", COLOR_GREEN_BOLD, "%s", RESET);

    private static String[][] accounts = new String[0][3];
    private static int accountIdCounter = 1;

    public static void main(String[] args) {
        String screen = DASHBOARD;

        do {
            displayScreen(screen);

            switch (screen) {
                case DASHBOARD:
                    int option = getUserChoice(1, 7);
                    screen = handleDashboardOption(option);
                    break;

                case CREATE_NEW_ACCOUNT:
                    screen = createNewAccount();
                    break;

                case DEPOSIT:
                    System.out.print("\tEnter your account number: ");
                    String depositAccountNumber = SCANNER.nextLine().strip();
                    deposit(depositAccountNumber);
                    break;

                case WITHDRAWALS:
                    System.out.print("\tEnter your account number: ");
                    String withdrawalAccountNumber = SCANNER.nextLine().strip();
                    withdrawal(withdrawalAccountNumber);
                    break;

                case TRANSFER:
                    System.out.print("\tEnter your account number: ");
                    String transferSourceAccountNumber = SCANNER.nextLine().strip();
                    transfer(transferSourceAccountNumber);
                    break;

                case CHECK_ACCOUNT_BALANCE:
                    System.out.print("\tEnter your account number: ");
                    String checkBalanceAccountNumber = SCANNER.nextLine().strip();
                    checkAccountBalance(checkBalanceAccountNumber);
                    break;

                case DELETE_ACCOUNT:
                    System.out.print("\tEnter your account number to delete: ");
                    String deleteAccountNumber = SCANNER.nextLine().strip();
                    screen = deleteAccount(deleteAccountNumber);
                    break;

                case EXIT:
                    System.out.println(CLEAR);
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        } while (true);
    }

    private static void displayScreen(String screenTitle) {
        final String APP_TITLE = String.format("%s%s%s", COLOR_BLUE_BOLD, screenTitle, RESET);
        System.out.println(CLEAR);
        System.out.println("\t" + APP_TITLE + "\n");
    }

    private static int getUserChoice(int min, int max) {
        int option;
        do {
            System.out.print("\tEnter an option to continue: ");
            option = SCANNER.nextInt();
            SCANNER.nextLine();
        } while (option < min || option > max);
        return option;
    }

    private static String handleDashboardOption(int option) {
        switch (option) {
            case 1:
                return CREATE_NEW_ACCOUNT;
            case 2:
                return DEPOSIT;
            case 3:
                return WITHDRAWALS;
            case 4:
                return TRANSFER;
            case 5:
                return CHECK_ACCOUNT_BALANCE;
            case 6:
                return DELETE_ACCOUNT;
            case 7:
                System.out.println(CLEAR);
                System.exit(0);
        }
        return DASHBOARD; // Default to the dashboard
    }

    private static String createNewAccount() {
        String newAccountId = String.format("SDB-%05d", accountIdCounter);
        String newName;
        boolean valid;

        do {
            valid = true;
            System.out.print("\tEnter A/C Name: ");
            newName = SCANNER.nextLine().strip();
            if (newName.isBlank()) {
                System.out.printf(ERROR_MSG, "A/C name can't be empty");
                valid = false;
                continue;
            }
            for (int i = 0; i < newName.length(); i++) {
                if (!(Character.isLetter(newName.charAt(i)) || Character.isSpaceChar(newName.charAt(i)))) {
                    System.out.printf(ERROR_MSG, "Invalid A/C name");
                    valid = false;
                    break;
                }
            }
        } while (!valid);

        int initialDeposit;

        do {
            valid = true;
            System.out.print("Enter your Deposited Amount Here: ");
            initialDeposit = SCANNER.nextInt();
            SCANNER.nextLine();

            if (initialDeposit >= 5000) { // Changed from > to >=
                System.out.println("Initial Deposit: " + initialDeposit);
                System.out.println();
            } else {
                System.out.printf(ERROR_MSG, "Not Sufficient Amount In Your A/C");
                valid = false;
                continue;
            }
        } while (!valid);

        String[] newAccount = {newAccountId, newName, String.valueOf(initialDeposit)};
        String[][] newAccounts = new String[accounts.length + 1][3];
        for (int i = 0; i < accounts.length; i++) {
            newAccounts[i] = accounts[i];
        }
        newAccounts[newAccounts.length - 1] = newAccount;
        accounts = newAccounts;

        accountIdCounter++;

        System.out.printf(SUCCESS_MSG, String.format("%s:%s has been saved successfully", newAccountId, newName));
        System.out.print("\tDo you want to continue adding (Y/n)? ");
        if (SCANNER.nextLine().strip().equalsIgnoreCase("Y")) {
            return CREATE_NEW_ACCOUNT;
        } else {
            return DASHBOARD; // Return to the dashboard screen
        }
    }

    private static void deposit(String accountNumber) {
        int amount;
        boolean valid;

        do {
            valid = true;
            System.out.print("\tEnter the amount to deposit: ");
            amount = SCANNER.nextInt();
            SCANNER.nextLine();

            if (amount > 0) {
                // Find the account by accountNumber and update the balance
                for (String[] account : accounts) {
                    if (account[0].equals(accountNumber)) {
                        int currentBalance = Integer.parseInt(account[2]);
                        int newBalance = currentBalance + amount;
                        account[2] = String.valueOf(newBalance);
                        System.out.printf(SUCCESS_MSG, String.format("%d has been deposited to %s. New balance: %d", amount, account[1], newBalance));
                        return;
                    }
                }
                System.out.printf(ERROR_MSG, "Account not found");
            } else {
                System.out.printf(ERROR_MSG, "Invalid amount");
                valid = false;
            }
        } while (!valid);
    }

    private static void withdrawal(String accountNumber) {
        int amount;
        boolean valid;

        do {
            valid = true;
            System.out.print("\tEnter the amount to withdraw: ");
            amount = SCANNER.nextInt();
            SCANNER.nextLine();

            if (amount > 0) {
                // Find the account by accountNumber and update the balance
                for (String[] account : accounts) {
                    if (account[0].equals(accountNumber)) {
                        int currentBalance = Integer.parseInt(account[2]);
                        if (currentBalance >= amount) {
                            int newBalance = currentBalance - amount;
                            account[2] = String.valueOf(newBalance);
                            System.out.printf(SUCCESS_MSG, String.format("%d has been withdrawn from %s. New balance: %d", amount, account[1], newBalance));
                            return;
                        } else {
                            System.out.printf(ERROR_MSG, "Insufficient balance");
                            valid = false;
                            break;
                        }
                    }
                }
                System.out.printf(ERROR_MSG, "Account not found");
            } else {
                System.out.printf(ERROR_MSG, "Invalid amount");
                valid = false;
            }
        } while (!valid);
    }

    private static void transfer(String sourceAccountNumber) {
        String targetAccountNumber;
        int amount;
        boolean valid;

        do {
            valid = true;
            System.out.print("\tEnter the target account number: ");
            targetAccountNumber = SCANNER.nextLine().strip();

            if (targetAccountNumber.isEmpty() || !accountExists(targetAccountNumber)) {
                System.out.printf(ERROR_MSG, "Invalid target account");
                valid = false;
                continue;
            }

            System.out.print("\tEnter the amount to transfer: ");
            amount = SCANNER.nextInt();
            SCANNER.nextLine();

            if (amount <= 0) {
                System.out.printf(ERROR_MSG, "Invalid amount");
                valid = false;
                continue;
            }

            // Find the source and target accounts and perform the transfer
            String[] sourceAccount = null;
            String[] targetAccount = null;
            for (String[] account : accounts) {
                if (account[0].equals(sourceAccountNumber)) {
                    sourceAccount = account;
                }
                if (account[0].equals(targetAccountNumber)) {
                    targetAccount = account;
                }
            }

            if (sourceAccount != null && targetAccount != null) {
                int sourceBalance = Integer.parseInt(sourceAccount[2]);
                if (sourceBalance >= amount) {
                    int targetBalance = Integer.parseInt(targetAccount[2]);
                    sourceAccount[2] = String.valueOf(sourceBalance - amount);
                    targetAccount[2] = String.valueOf(targetBalance + amount);
                    System.out.printf(SUCCESS_MSG, String.format("%d has been transferred from %s to %s", amount, sourceAccount[1], targetAccount[1]));
                    return;
                } else {
                    System.out.printf(ERROR_MSG, "Insufficient balance in the source account");
                    valid = false;
                }
            } else {
                System.out.printf(ERROR_MSG, "Source or target account not found");
                valid = false;
            }
        } while (!valid);
    }

    private static void checkAccountBalance(String accountNumber) {
        for (String[] account : accounts) {
            if (account[0].equals(accountNumber)) {
                int balance = Integer.parseInt(account[2]);
                System.out.printf(SUCCESS_MSG, String.format("Account balance for %s: %d", account[1], balance));
                return;
            }
        }
        System.out.printf(ERROR_MSG, "Account not found");
    }

    private static String deleteAccount(String accountNumber) {
        int indexToDelete = -1;

        for (int i = 0; i < accounts.length; i++) {
            if (accounts[i][0].equals(accountNumber)) {
                indexToDelete = i;
                break;
            }
        }

        if (indexToDelete != -1) {
            String deletedAccountName = accounts[indexToDelete][1];
            // Create a new array to store the accounts without the deleted one
            String[][] newAccounts = new String[accounts.length - 1][3];
            int newIndex = 0;
            for (int i = 0; i < accounts.length; i++) {
                if (i != indexToDelete) {
                    newAccounts[newIndex++] = accounts[i];
                }
            }
            accounts = newAccounts;
            System.out.printf(SUCCESS_MSG, String.format("%s has been deleted successfully", deletedAccountName));
        } else {
            System.out.printf(ERROR_MSG, "Account not found");
        }

        return DASHBOARD; // Return to the dashboard screen
    }

    private static boolean accountExists(String accountNumber) {
        for (String[] account : accounts) {
            if (account[0].equals(accountNumber)) {
                return true;
            }
        }
        return false;
    }
}
