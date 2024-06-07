package it.arnaldo.arnaldowest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class GamersGemeration {
    
    public static List<Person> tets(){

        List<Person> list = new ArrayList<>();                                          // ogni volta shuffle i ruoli dei giocatori
        Person renegade = new Person(Roles.RENEGADE.get_role(), Constants.HP);
        list.add(renegade);

        Person desperado1 = new Person( Roles.DESPERADO.get_role(), Constants.HP);
        list.add(desperado1);

        Person desperado2 = new Person( Roles.DESPERADO.get_role(), Constants.HP);
        list.add(desperado2);

        return list;
    }

    public static void four_gamer ( Map<Integer, Person> gamers){
       List<Person> list = tets();

        Collections.shuffle(list);

        int j=1;
        for (int i = 0; i < 3; i++) {
            gamers.put(j, list.get(i));
            j++;
        }
    }

    public static void five_gamer ( Map<Integer, Person> gamers){
        
        List<Person> list = tets();
        
        Person vice = new Person(Roles.VICE.get_role(), Constants.HP);
        list.add(vice);

        Collections.shuffle(list);

        int j=1;
        for (int i = 0; i < 4; i++) {
            gamers.put(j, list.get(i));
            j++;
        }

    }

    public static void six_gamer(Map<Integer, Person> gamers){
        List<Person> list = tets();
        
        Person vice = new Person(Roles.VICE.get_role(), Constants.HP);
        list.add(vice);

        Person desperado = new Person( Roles.DESPERADO.get_role(), Constants.HP);
        list.add(desperado);

        Collections.shuffle(list);

        int j=1;
        for (int i = 0; i < 5; i++) {
            gamers.put(j, list.get(i));
            j++;
        }
    }

    public static void seven_gamer (Map<Integer, Person> gamers){
        List<Person> list = tets();
        
        Person vice1 = new Person(Roles.VICE.get_role(), Constants.HP);
        list.add(vice1);

        Person desperado = new Person( Roles.DESPERADO.get_role(), Constants.HP);
        list.add(desperado);
        
        Person vice2 = new Person(Roles.VICE.get_role(), Constants.HP);
        list.add(vice2);


        Collections.shuffle(list);

        for (int i = 0; i < 6; i++) {
            gamers.put(i+1, list.get(i));
        }
    }

}
