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
        String[] mainOptions = {
            CREATE_NEW_ACCOUNT,
            DEPOSIT,
            WITHDRAWALS,
            TRANSFER,
            CHECK_ACCOUNT_BALANCE,
            DELETE_ACCOUNT,
            EXIT
        };

        String screen = DASHBOARD;

        do {
            switch (screen) {
                case DASHBOARD:
                    int option = handleDashboardOption(mainOptions);
                    screen = handleOption(option, mainOptions);
                    break;

                case CREATE_NEW_ACCOUNT:
                    screen = createNewAccount(mainOptions);
                    break;

                case DEPOSIT:
                    screen = deposit(mainOptions);
                    break;

                case WITHDRAWALS:
                    screen = withdrawal(mainOptions);
                    break;

                case TRANSFER:
                    screen = transfer(mainOptions);
                    break;

                case CHECK_ACCOUNT_BALANCE:
                    screen = checkAccountBalance(mainOptions);
                    break;

                case DELETE_ACCOUNT:
                    screen = deleteAccount(mainOptions);
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

    private static void displayScreen(String screenTitle, String[] options) {
        final String APP_TITLE = String.format("%s%s%s", COLOR_BLUE_BOLD, screenTitle, RESET);
        System.out.println(CLEAR);
        System.out.println("\t" + APP_TITLE + "\n");

        for (int i = 0; i < options.length; i++) {
            System.out.printf("\t%d. %s\n", i + 1, options[i]);
        }
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

    private static int handleDashboardOption(String[] options) {
        displayScreen(DASHBOARD, options);
        return getUserChoice(1, options.length);
    }

    private static String handleOption(int option, String[] options) {
        return options[option - 1];
    }

    // Implement other methods for createNewAccount, deposit, withdrawal, transfer, checkAccountBalance, and deleteAccount here...
}
