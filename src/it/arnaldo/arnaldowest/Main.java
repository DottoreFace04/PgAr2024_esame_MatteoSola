package it.arnaldo.arnaldowest;


public class Main {
    public static void main(String[] args) throws InterruptedException {
        Game game = new Game();
        game.init_game();

        // printa tavolo creato

        //printare la mappa
        /* 
        System.out.println("Giocatori:");
        for (int i = 0; i < Game.gamers.size(); i++) {
            System.out.println("Giocatore " + i + ": " + Game.gamers.get(i).getRole());
        }

        ArrayList<Card> m = Game.gamers.get(0).getCard();

        for (int i = 0; i < Game.gamers.get(0).getCard().size(); i++) {
            System.out.println(m.get(i));
        }*/
       
        //game.round(0);
        boolean f =false;
        do {
            game.round(0);

            System.out.println("\n");
        } while (!f);
        Utils.wait_flush(2000);

      


    }   
}
