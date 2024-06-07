package it.arnaldo.arnaldowest;

import it.kibo.fp.lib.Menu;

public class Utils {    
    
    
    public static void print_animation(String msg) throws InterruptedException {
        final int DELAY = 40;

        for (int i = 0; i < msg.length(); i++) {
            System.out.print(msg.charAt(i));
            Menu.wait(DELAY);
        }
    }

    public static void wait_flush(int time) throws InterruptedException {
        Menu.wait(time);
        Menu.clearConsole();
    }

    

}
