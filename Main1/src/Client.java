import java.util.Random;
public class Client {
    private int priority;
    private int numItems;
    private boolean paysCash;
    private int serviceTime;
    
    private static final int MAX_TIME=43200;
    private static final double P_PAYS_CASH=0.4; //πιθανότητα να πληρώνει σε μετρητά ο πελάτης
    private static final double P_IS_SPECIAL=0.01;//πιθανότητα να ανήκει σε ειδική περίπτωση
    
    public Client(int creationTime) { //constructor που υπολογίζει την προτεραιότητα κάθε πελάτη που δημιουργείται
        Random rnd=new Random(); 

        
        if(rnd.nextFloat() > P_IS_SPECIAL) { //Εύρεση προτεραιότητας αν ανήκει ή οχι σε ειδική περίπτωση
            priority=creationTime;
        }
        else {
            priority=creationTime-MAX_TIME;
        }
        
        numItems=rnd.nextInt(49)+1; //εύρεση του πλήθους ειδών.Το +1 χρησιμοποιείται για να εμφανίζονται σωστά οι αριθμοί
        
        paysCash = true; //Έλεγχος αν πληρώνει ο πελάτης με λεφτά ή οχι
        if (rnd.nextFloat() > P_PAYS_CASH) {
            paysCash = false;
        }

        serviceTime();
    }
    
    private int serviceTime() { //συνάρτηση που υπολογίζει τον συνολικό απαιτούμενο χρόνο εξυπηρέτησης
        
        int totaltime=5*numItems;
        
        if (paysCash) { //ελέγχει αν πληρώνει με λεφτά ή με πιστωτική κάρτα
            totaltime+=90;
        }
        else {
            totaltime+=120;
        }
        Random rnd=new Random();
        totaltime+=rnd.nextInt(120)+1; //τυχαίο χρονικό διάστημα καθυστέρησης.Με το +1 οι τιμές προσαρμόζονται στο διάστημα [1,120]
      
        serviceTime = totaltime; //Εδώ υπολογίζεται τελικά ο συνολικός απαιτούμενος χρόνος εξυπηρέτησης
        return totaltime;        
    }
    public int getTime(){  
        return serviceTime;
    }
    
    Cashier selectCashier(Cashier []cashier){ //συνάρτησης επιλογής ταμεία από τον πελάτη
        if (numItems < 11) { //Έλεγχος για το πρώτο ταμείο που είναι το express και δέχεται μέχρι 10 είδη
            if (cashier[0].isOpen() == false) {
                cashier[0].openCashier();
            }
            return cashier[0]; //ανοίγει το ταμείο express αν ο πελάτης έχει μέχρι 10 είδη
        }
        
        int minClientsInQueue = cashier[1].clientInQueue();
        int pickedCashier = 1;
        
        for (int i = 1 ; i < cashier.length ; i++) {
            if (cashier[i].isOpen() && cashier[i].clientInQueue() < minClientsInQueue){
                minClientsInQueue = cashier[i].clientInQueue();
                pickedCashier = i; //ο πελάτης εντάσσεται στο ταμείο με την μικρότερη ουρά
            }
        }
        return cashier[pickedCashier];
    }
    
    public int getPriority(){ //Επιστρέφει την προτεραιότητα
        return priority;
    }
  
}
