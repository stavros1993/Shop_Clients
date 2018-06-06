import java.util.LinkedList;

public class ClientQueue {
 
    LinkedList<Client> arrayItems; //Λίστα που περιέχει τους πελάτες.(Αρχικά είχα φτιάξει arraylist αλλα επειδή έβγαζε "heap space error" έφτιαξα τελικά λίστα
    public ClientQueue(){
        arrayItems = new LinkedList<Client>(); }//αρχικοποίηση της λίστας με τους πελάτες
    
    void addClient(Client c) {  //συνάρτηση εισαγωγής πελάτη
        if (arrayItems.size() == 0) { //Αν είναι άδειο το ταμείο,βάζει κατευθείαν τον πελάτη,χωρίς εντολής για συγκρίσεις 
            arrayItems.add(c);
            return;
        }
        if (c.getPriority() < 0) { 
            arrayItems.addFirst(c);
            return;
        }
        for (int i = 0; i < arrayItems.size(); i++) { //Βάζει τον πελάτη στην κατάλληλη θέση μέσα στη λίστα.Οι υπόλοιπες θέσεις προσαρμόζονται αυτόματα
            if (c.getPriority()< arrayItems.get(i).getPriority()) {
              arrayItems.add(i, c);
            }
        }
    }
    
    public Client removeClient() { //συνάρτηση εξαγωγής πελάτη από την ουρά
        if (arrayItems.isEmpty()) { //έλεγχος πιθανότητας της λίστας να είναι άδεια
            return null;
        }
        
        Client c = arrayItems.remove(0);
        
        return c;        
    }

    public int size() { //επιστρέφει το μέγεθος της λίστας
        return arrayItems.size();
    }
}
