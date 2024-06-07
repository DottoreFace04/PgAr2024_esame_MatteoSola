package it.arnaldo.arnaldowest;

import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.lang.model.util.ElementScanner14;
import javax.management.relation.RoleStatus;

import it.kibo.fp.lib.InputData;
import it.kibo.fp.lib.Menu;

public class Game {
 
    public static Map<Integer, Person> gamers = new HashMap<>();

    private ArrayList<Card> discarded_cards = new ArrayList<>();

    private Deque<Card> deck = new ArrayDeque<>();

    public void init_deck(){

        for (int i = 0; i < 50; i++ ) {
            deck.add(Card.BANG);
        }
        for (int i = 0; i < 24; i++) {
            deck.add(Card.MISSED);
        }
        for (int i = 0; i < 3; i++) {
            deck.add(Card.SCHOFIELD);
        }
        deck.add(Card.REMINGTON);
        deck.add(Card.REV_CARABINE);
        deck.add(Card.WINCHESTER);

        shuffleDeque(deck);
    }

    public static void shuffleDeque(Deque<Card> deque) {
        // Estrai tutti gli elementi dal Deque e mettili in una lista
        List<Card> list = new ArrayList<>(deque);
        
        // Mescola la lista
        Collections.shuffle(list);
        
        // Svuota il Deque originale
        deque.clear();
        
        // Reinserisci gli elementi mescolati nel Deque
        deque.addAll(list);
    }
     
    public void inserting_cards(){
        // se la distanza nella classe ENUM è NULL non è una crata ARMA

        for (int i = 0; i < gamers.size(); i++) {
            ArrayList<Card> cards = new ArrayList<>();
            int hp = gamers.get(i).getHp(); 

            // Aggiungi un numero di carte pari ai punti vita del giocatore
            for (int j = 0; j < hp && !deck.isEmpty(); j++) {
                cards.add(deck.poll());
            }

            gamers.get(i).setCard(cards);
        }
    }

    public void deck_restoration(){
        deck.addAll(discarded_cards);
        discarded_cards.clear();
        shuffleDeque(deck);
    }

    // utilizzo l'indice nella MAP per determinare a quale giocatore tocca 
    public void init_game() throws InterruptedException{
        // inserire le persone nella mappa
        
        init_deck();

        Person scheriff = new Person( Roles.SHERIFF.get_role(), 5);
        gamers.put(0, scheriff);  

        final String[] ENTRIES      = {"4", "5", "6", "7"};
        Menu menu = new Menu(Constants.MSG_CHOOSE_NUM_PLAYERS, ENTRIES, false, true, true);
        Constants.NUMBER_OF_GAMER = menu.choose();

        switch (Constants.NUMBER_OF_GAMER) {
            case 1:             // aggiungo due fuorilegge
                GamersGemeration.four_gamer(gamers);
                break;

            case 2:             // aggiungo un vice
                GamersGemeration.five_gamer(gamers);
                break;

            case 3:             //aggiungo un fuori legge
                GamersGemeration.six_gamer(gamers);
                break;

            case 4:             // aggiungo un vice
                GamersGemeration.seven_gamer(gamers);
                break;

            default:
                break;
        }
        
        inserting_cards();
        
        Utils.print_animation(Constants.MSG_INIT_GAME);
        Utils.print_animation("Tavolo creato\n");
    }

    public int round(int gamer_index) throws InterruptedException{
        if (deck.isEmpty()) {
            deck_restoration();
        }

        gamer_index = 0;
    
        // Estrai due carte per il giocatore
        for (int i = 0; i < 2; i++) {
            if (deck.isEmpty()) {
                deck_restoration();
            }
            Card drawnCard = deck.poll();
            gamers.get(gamer_index).getCard().add(drawnCard);
        }
    
        boolean go1 = InputData.readYesOrNo("Vuoi giocare una carta");
        if (!go1) {
            // Se il giocatore non vuole giocare una carta, inizia il processo di scarto
            do {
                System.out.println("Carte in possesso:");
                ArrayList<Card> m = gamers.get(gamer_index).getCard();
                for (int i = 0; i < m.size(); i++) {
                    System.out.println(i + " " + m.get(i));
                }
    
                int n = InputData.readIntegerBetween("Seleziona carta da scartare:\n", 0, m.size() - 1);
                Card card_to_discard = m.remove(n);
                discarded_cards.add(card_to_discard);
    
                gamers.get(gamer_index).setCard(m);
    
            } while (gamers.get(gamer_index).getCard().size() > gamers.get(gamer_index).getHp());
    
            // Passa al prossimo turno
            return 0;
        }

        // SELEZIONE UN ARMA IN CASO DI AFFERMAIZIONE POSITIVA
        // TODO scratare un arma qunada ce nera una gia selezionata

        boolean go2 = InputData.readYesOrNo("\nVuoi selezionare un arma");
        if(go2){
            ArrayList<Card> m = gamers.get(gamer_index).getCard();
            ArrayList<Card> gun_card = new ArrayList<>();

            // controllo l'esistenza di una carta arma
            for (int i = 0; i < m.size(); i++) {
                if(m.get(i).get_portata()!= null){
                    gun_card.add(m.get(i));
                }
            }
            if (gun_card.isEmpty()) {
                System.out.println("\nNon ci sono armi selezionabili nella carte in mano");
            }
            else{
            
                ArrayList<Integer> selection = new ArrayList<>(); 

                for (int i = 0; i < m.size(); i++) {
                    if (m.get(i).get_portata() != null) {
                        selection.add(i,1);   // ricordo in un arraylist a che posizione dell'array con tutte le carte si trova: 1 se è presente
                    }
                    else{
                        selection.add(i, 0);
                    }                                 
                    System.out.println(i + " " + m.get(i));
                }
                boolean selectio = false;

                do {
                    int n = InputData.readIntegerBetween("Seleziona arma:\n", 0, m.size() - 1);
                    
                    if (selection.get(n) != 1) {
                        System.out.println("\nIn quella posizione non cè un arma...");
                    }
                    else{
                        Card card_selected = m.get(n);
                        gamers.get(gamer_index).setChosen_gun_card(card_selected);
                        selectio = true;
                    }

                } while (!selectio);

            }

        }
        
        // ho selzionato una arma con una certa distanza, oppure non ho selezionato niente e ho la distanza ferma a 1
        // adesso devo scsegliere a chi sparare

        //Utils.wait_flush(2000);

        //stampare le carte in possesso, l'amra selezionata (se non ne seleziona è la colt base dist 1), e lista giocatori con propria distanza 
        
        ArrayList<Card> m = gamers.get(gamer_index).getCard();
        for (int i = 0; i < m.size(); i++) {
            System.out.println(i + " " + m.get(i));
        }

        // stampo l'arma che utilizzo
        System.out.println("Arma selezionata:");
        Card card = gamers.get(gamer_index).getChosen_gun_card();
        if(card != null){
            System.out.println(card + " " + card.get_portata());
        }
        else{
            System.out.println("hai una colt, pui sparare a una distanza di 1");
        }

        //stampo i giocatori e le rispettive distante da essi
        // TODO sistemare quando il giocatore è lo sschreiffo
        System.out.println("\nGiocatori:");
        for (int i = 0; i < Game.gamers.size(); i++) {
            if(i == 0){
                System.out.println(i + " Scheriffo" + range(gamer_index, 0 )); 
            }
            else if(i == gamer_index){
                System.out.println(i + " Giocatore che stagiocando" );
            }
            else{
                System.out.println( i + ". " + "Giocatore " + i + ": " + range(gamer_index, i)); // i indica anche l'indice dove si trova il giocatre nella HASHMAP
            }
            
        }

        // dopo aver stampato le informazioni, bisogna selezionare un giocatore
        // la visualizzazione dei giocatori è come è inserita nella HASHMAP
        // TODO elimanere giocatore tramite METODO
        // TODO implement Metodo per MISSING

       boolean fire = false;

        do {
            int n = InputData.readIntegerBetween("Seleziona un giocatore a cui sparare:\n", 0, m.size() - 1);

            Card card_selected;
            int index_of_card_selected;
            boolean found = false;
            for (int i = 0; i < m.size(); i++) {
                if(m.get(i) == Card.BANG){
                    //trovato carta
                    card_selected = m.get(i);
                    index_of_card_selected = i;     // salvo l'indice dove si trova la carta BANG utilizzata
                    found = true;
                    break;
                }
            }
            if (!found) { // non ho puù carte BANG, ritono 0 per un nuovo turno
                System.out.println("\nNon hai più carte BANG, si passa al giocatore successivo");
                return 0;
            }
            else{
                // ho trovato la carta BANG adesso confronto la distanza tra io e il giocatore selezionato con l'arma che ho selezionato in precedenza
                int real_dist = range(gamer_index, n); 
                int relativ_dist = 0;
                
                if(card != null){
                    relativ_dist = gamers.get(gamer_index).getChosen_gun_card().get_portata();
                }
                else{
                    relativ_dist = 1;  // non ho selezionato nessuna arma, ho la Colt di base
                }
 
                if (relativ_dist >= real_dist ) {
                    // richiamo il metodo MISSED
                    missed_card_usage(n);

                    
                    // scartare la carta BANG
                    Card card_to_discard = m.remove(index_of_card_selected);  // TODO chiedere ????????????????????????????????
                    discarded_cards.add(card_to_discard);
                    gamers.get(gamer_index).setCard(m);
                    fire = true;
                }
                else{
                    // non posso sparare schelgo un altro giocatore a cui sparare
                    fire = false;
                }

            }

            
        } while (!fire);

        
        
 
        // TODO se alla fine ho posche carte (minori della vita ne aggiungo), se ne ho maggiori scarto

        return 1;
    }
    
    //richiamo il metodo quando vengo sparato, decido se utilizzare la carta o prendere danno
    public int missed_card_usage(int gamer_i_hit){
        // YesorNo
        //selecting whit if/else
        //aggiornamento vita
        int index_of_card_selected;
        Card card_selected;
        ArrayList<Card> m = gamers.get(gamer_i_hit).getCard();
        boolean use_it = InputData.readYesOrNo("\nVuoi utilizzare una Carda MISSED");
        if (!use_it) {
            // prendo semplecemnte danno, aggiornamento vita
            System.out.println("\nNon hai utilizzato la carta MISSED, prendi danno");
                gamers.get(gamer_i_hit).setHp(gamers.get(gamer_i_hit).getHp() - 1);  // sottraggo 1 quando vengo colpito 
                return 0;
        }
        else{
            boolean found = false;
            for (int i = 0; i < m.size(); i++) {
                if(m.get(i) == Card.MISSED){
                    //trovato carta
                    System.out.println("carta trovata");
                    card_selected = m.get(i);  
                    found = true;
                    index_of_card_selected = i;          // salvo l'indice dove si trova la carta MISSED utilizzata
                    break;
                }
            }
            if (!found) { // non ho puù carte MISSED, ritono 0 per un nuovo turno
                System.out.println("\nNon hai più carte MISSED, prendi danno");
                gamers.get(gamer_i_hit).setHp(gamers.get(gamer_i_hit).getHp() - 1);  // sottraggo 1 quando vengo colpito 
                return 0;
            }
            else{
                System.out.println("\nHai utilizzato una carta, per il momento non prendi danno.");

                // devo eliminare la carta dal mazzo in mano e scartlara 
                // scartare la carta BANG
                
                Card card_to_discard = m.remove(index_of_card_selected);  // TODO chiedere ????????????????????????????????
                discarded_cards.add(card_to_discard);
                gamers.get(gamer_i_hit).setCard(m);

                return 1; // ritono 1 se scielgo la carta MISSED e cè lo'ho nel mazzo di carte
            }
        } 
    }

    public int range(int start,  int end){   // NON FUNZIONA
        int dist = (end - start + Constants.NUMBER_OF_GAMER) % Constants.NUMBER_OF_GAMER;
        return dist;
    }


   



}
