
import java.util.Random;
import java.util.Scanner;

public class Shop {

    Cashier cashiers[]; //Ο πίνακας που περιέχει τα 10 ταμία
    int chance;
    Scanner input = new Scanner(System.in);
    private static final int MAX_TIME = 43200; //Η προσομοίωση τρέχει για μια μέρα(43200 δευτερόλεπτα)
    private static final double CREATE_CLIENT = 0.1; //πιθανότητα δημιουργίας πελάτη.Έβαλα μικρή πιθανότητα δημιουργίας γιατί αν δημιουργούνται πολλοί πελάτες,δεν κλείνουν ποτέ ταμεία ,κι έτσι δεν εμφανίζονται μηνύματα σχετικά με το κλείσιμο ταμείων.
    private static final int NO_OF_CASHIERS = 10; //Αριθμός των συνολικών ταμείων
    private static final double CLIENT_RATIO = 1; //Αριθμός ατόμων που πρέπει να είναι σε κάθε ταμείο κατά μέσο όρο για να ανοίξει ένα νέο
    private static final double IDLE_THRESHOLD = 5; //Αριθμός πελατών που περιμένουν στην ουρά

    public Shop() {
        cashiers = new Cashier[NO_OF_CASHIERS]; //δημιουργία και αρχικόποιηση του πίνακα

        for (int i = 0; i < NO_OF_CASHIERS; i++) { 
            cashiers[i] = new Cashier(i); 
        }

        cashiers[0].openCashier(); //Ανοίγουν 5 ταμία στην αρχή
        cashiers[1].openCashier();
        cashiers[2].openCashier();
        cashiers[3].openCashier();
        cashiers[4].openCashier();
        cashiers[5].openCashier();
    }

    void runSim() {
        Cashier c;
        for (int i = 0; i <= MAX_TIME; i++) { //Σε κάθε δεύτερο,για μια μέρα γίνονται τα παρακάτω
            
            Random rnd = new Random(); 

            if (rnd.nextDouble() <= CREATE_CLIENT) {
                Client newclient = new Client(i); //Δημιουργία πελάτη

                c = newclient.selectCashier(cashiers); //Κλήση της συνάρτησης που υλοποιεί την επιλογή ταμεία
                
                c.enqueueClient(newclient); //Κλήση συνάρτησης που εισάγει πελάτη
            }
            for (int j = 0; j < NO_OF_CASHIERS; j++) { //Κλήση συνάρτησης εξυπηρέτησης πελατών για όλα τα ταμεία
                cashiers[j].ServiceQueue();
            }
            adjustCashiers(); //Προσαρμόζεται η κατάσταση των ταμείων

        }
    } //Εδώ τελειώνει η συνάρτηση προσομοίωσης 

    void adjustCashiers() { // καθορίζει πόσοι πελάτες το μέγιστο πρέπει να περιμένουν κατά μέσο όρο στα ανοικτά ταμεία, ώστε να ανοίξει ένα νέο
        int totalClients = 0, openCashiers = 0;
        
        for (int i = 0; i < NO_OF_CASHIERS; i++) {
            totalClients += cashiers[i].clientInQueue();
            if (cashiers[i].isOpen()) {
                openCashiers++;
            }
        }
        if ((double) totalClients / (double) openCashiers > CLIENT_RATIO && openCashiers < 10) { //ανοίγει ταμείο που προηγουμένως ήταν κλειστό ,αν υπάρχουν πολλοί πελάτες σε αναμονή συνολικά
            for (int i = 0; i < NO_OF_CASHIERS; i++) {
                if (cashiers[i].isOpen() == false) {
                    cashiers[i].openCashier();
                    System.out.println("Cashier opened " + (i+1));
                    break;
                }
            }
        } else {
            for (int i = NO_OF_CASHIERS - 1; i > -1; i--) { //κλείνει ταμείο αν είναι άδειο για συγκεκριμένη χρονική στιγμή
                if (openCashiers > 1 && cashiers[i].isOpen() && cashiers[i].getIdleTime() == IDLE_THRESHOLD) {
                    cashiers[i].closeCashier();
                    break;
                }

            }
        }

    }
}
