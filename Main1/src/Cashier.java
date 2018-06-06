
public class Cashier {

    private ClientQueue queue;
    private boolean open;
    Client currentClient;
    int serviceTime;
    int idleTime;
    int cashierNumber;

    public Cashier(int i) { //constructor 
        open = false;
        cashierNumber = i;
        queue = new ClientQueue(); 
    }

    public void openCashier() { //συνάρτηση που ανοίγει το ταμίο
        open = true;
        System.out.println("Open cashier " + (cashierNumber+1));
    }

    public void closeCashier() { //συνάρτηση που κλείνει το ταμίο
        open = false;
        System.out.println("Close cashier " + (cashierNumber+1));
    }

    public boolean isOpen() { //επιστρέφει την κατάσταση του ταμείου
        return open;
    }

    public int getIdleTime() { //επιστρέφει το χρόνο που το ταμείο είναι άδειο
        return idleTime;
    }

    public int getCashierNumber() { //επιστρέφει τον αριθμό του ταμείου
        return cashierNumber;
    }

    public void ServiceQueue() {  //συνάρτηση εξυπηρέτησης του πελάτη από τον ταμία
        if (open == false) {
            return;
        }

        if (currentClient == null && queue.size() != 0) {
            currentClient = queue.removeClient();
            serviceTime = 1;
            idleTime = 0;
        } else if (currentClient != null) { //αν υπάρχει πελάτης αυξάνεται ο χρονος που εξυπηρετείται και κάθε φορά ελέγχει η συνάρτηση αν ισούται με τον χρόνο που χρειάζεται για να εξυπηρετηθεί
            serviceTime++;
            if (serviceTime == currentClient.getTime()) {
                currentClient = queue.removeClient();
                System.out.println("Client from cashier " + (cashierNumber+1) + " served"); //Προσθέτω 1 για να εμφανίζεται σωστα η αρίθμηση των ταμείων
                serviceTime = 0;
            } 
        } else { //Αν δεν υπάρχει πελάτης στον ταμία ,αυξάνεται ο χρόνος που μένει άδειο το ταμίο κατά 1
           
            idleTime++;
            serviceTime = 0;
        }
    }

    public int clientInQueue() { //επιστρέφει το σύνολο των πελατών στο ταμείο
        if (currentClient == null) { //ελέγχει αν εξυπηρετείται πελάτης 
            return queue.size();
        }
        return queue.size() + 1; //Σε αυτήν την περίπτωση βάζω +1 για να μετράει και τον πελάτει που εξυπηρετεί ο ταμίας
    }

    public void enqueueClient(Client c) { //εισάγει πελάτη
        if (open == false) { //ελέγχει αν είναι κλειστό το ταμείο
            return;
        }
        queue.addClient(c);
    }
}
