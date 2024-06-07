package it.arnaldo.arnaldowest;

import java.util.ArrayList;

public class Person {
    private String  role;
    private int     Hp;

    private Card chosen_gun_card;

    private ArrayList<Card> card = new ArrayList<>(); // l'enum Card contiene sia BANG che MISSED, insieme alle carte dell ARMI con DIST
    //private ArrayList<Card> gun_card = new ArrayList<>();
    
   
    public Person(String role, int hp) {
        this.role = role;
        Hp = hp;
    }

    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }

    public int getHp() {
        return Hp;
    }
    public void setHp(int hp) {
        Hp = hp;
    }

    public Card getChosen_gun_card() {
        return chosen_gun_card;
    }

    public void setChosen_gun_card(Card chosen_card) {
        this.chosen_gun_card = chosen_card;
    }

    public ArrayList<Card> getCard() {
        return card;
    }
    public void setCard(ArrayList<Card> card) {
        this.card = card;
    }

    

}
