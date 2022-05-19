package cinema;

import java.util.Scanner;

/**
 * @author Faniel S. Abraham
 */

public class Cinema {

    static Scanner scanner = new Scanner(System.in);
    private int row;                      //total rows in the cinema
    private int seat;                     //total seats in each row
    private int userRow = 0;              //customer selected row
    private int userSeat = 0;             //customer selected seat
    private int occupiedSeats = 0;        //Total occupied seats
    private int currentIncome = 0;        //current income (based on booked seats)

    /**
     * Creates the empty cinema room when none of the seats are taken.
     * S represents empty seats.
     */
    char[][] createCinemaSeats() {
        char[][] cinema = new char[row + 1][seat + 1];
        cinema[0][0] = ' ';

        // j represents the column numbers (i.e. first row)
        for (int j = 1; j < cinema[0].length; j++) {
            cinema[0][j] = (char) ('0' + j);
        }

        for (int i = 1; i < cinema.length; i++) {
            cinema[i][0] = (char) ('0' + i);    // row numbers (first column)
            for (int j = 1; j < cinema[i].length; j++) {
                cinema[i][j] = 'S';
            }
        }
        return cinema;
    }

    /**
     * Updates cinema seats when booked (i.e. change 'S' to 'B')
     * @param cinema - the cinema seats in a 2D array
     */
    void updateCinemaSeats(char[][] cinema) {
        for (int i = 0; i < cinema.length; i++) {
            if (i == userRow) {
                for (int j = 0; j < cinema[i].length; j++) {
                    if (j == userSeat) {
                        cinema[i][j] = 'B';
                    }
                }
            }
        }
    }

    /**
     * displays current state of the cinema seats when called
     * @param cinema the cinema seats in a 2D array. B represents taken seats, S represents free seats.
     */
    void displaySeats(char[][] cinema) {
        System.out.printf("%nCinema:%n");
        for (char[] chars : cinema) {
            for (char aChar : chars) {
                System.out.print(" " + aChar);
            }
            System.out.println();
        }
    }

    /**
     * Calculates the price of customer seat and Increments the total occupancy & current Income
     */
    void calculateTicketPrice() {
        int firstHalf = row / 2;
        if (row * seat > 60) {
            if (userRow <= firstHalf) {
                System.out.println("Ticket price: $" + 10);
                occupiedSeats += 1;
                currentIncome += 10;
            } else {
                System.out.println("Ticket price: $" + 8);
                occupiedSeats += 1;
                currentIncome += 8;
            }
        } else {
            System.out.println("Ticket price: $" + 10 + "\n");
            occupiedSeats += 1;
            currentIncome += 10;
        }
    }

    /**
     * Accept customer seat selection and assign the values to userRow and userSeat
     */
    void acceptUserSeat() {
        System.out.println("\nEnter a row number:");
        userRow = scanner.nextInt();
        System.out.println("Enter a seat number in that row:");
        userSeat = scanner.nextInt();
    }

    /**
     * Validate if user seat selection is correct. If correct, changes free seat (S)
     * to booked (B). If not correct, asks customer to input correct seat numbers. loops
     * until correct information acquired. Finally, prints ticket price.
     * @param cinema - the cinema seats in a 2D array
     */
    void bookTicket(char[][] cinema) {
        acceptUserSeat();
        while (true) {
            if (userRow > row || userRow < 1 ||
                    userSeat > seat || userSeat < 1) {
                System.out.println("Wrong input!");
                acceptUserSeat();
            } else if (cinema[userRow][userSeat] == 'B') {
                System.out.println("That ticket has already been purchased!");
                acceptUserSeat();
            } else {
                updateCinemaSeats(cinema);
                calculateTicketPrice();
                break;
            }
        }
    }

    /**
     * Prints 4 statistics
     */
    void Statistics() {
        System.out.printf("\nNumber of purchased tickets: %d", occupiedSeats);
        double percentage = (((occupiedSeats * 100.0) / 100.0) / (row * seat)) * 100;
        System.out.printf("\nPercentage: %.2f%s", percentage, "%");
        System.out.printf("\nCurrent income: $%d", currentIncome);
        System.out.printf("\nTotal income: $%d", calculateTotalIncome());
    }

    /**
     * Calculate total income based on ticket prices and cinema capacity.
     * Prices are 10$ for rooms <60 capacity.
     * For rooms >60 seats, 10$ for first half and $8 for the rest
     */
    int calculateTotalIncome() {
        int firstHalf = row / 2;
        int totalIncome;
        if (row * seat > 60) {
            totalIncome = (firstHalf * seat * 10) + ((row - firstHalf) * seat * 8);
        } else {
            totalIncome = row * seat * 10;
        }
        return totalIncome;
    }

    /**
     * Displays 4 menu options
     */
    int menu() {
        System.out.println("\n1. Show the seats" +
                "\n2. Buy a ticket" +
                "\n3. Statistics" +
                "\n0. Exit");

        return scanner.nextInt();
    }

    void main(String[] args) {
        System.out.println("Enter the number of rows:");
        row = scanner.nextInt();                                //total rows in cinema
        System.out.println("Enter the number of seats in each row:");
        seat = scanner.nextInt();                               //seats in each row
        char[][] cinema = createCinemaSeats();
        int option;
        boolean flag = true;

        option = menu();

        while (flag) {
            switch (option) {
                case 1:
                    displaySeats(cinema);
                    option = menu();
                    if (option == 0) {
                        flag = false;
                    }
                    break;
                case 2:
                    bookTicket(cinema);
                    option = menu();
                    if (option == 0) {
                        flag = false;
                    }
                    break;
                case 3:
                    Statistics();
                    option = menu();
                    if (option == 0) {
                        flag = false;
                    }
                    break;
                case 0:
                    // exit the program
                    flag = false;
                    break;
            }
        }
    }
}