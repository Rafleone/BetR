package bet.model;

import javafx.scene.control.Slider;

public class Bet {
    private int id;
    private String name;
    private int age;
    private String country;
    private int bet;
    private String event;
    private String game;
    private String payment;

    public Bet(String name, int age, String country, int bet, String event, String game, String payment) {
        this.name = name;
        this.age = age;
        this.country = country;
        this.bet = bet;
        this.event = event;
        this.game = game;
        this.payment = payment;
    }

    public Bet() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getBet() {
        return bet;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public Bet(int id, String name, int age, String country, int bet, String event, String game, String payment) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.country = country;
        this.bet = bet;
        this.event = event;
        this.game = game;
        this.payment = payment;
    }

    @Override
    public String toString() {
        return "Bet " +
                "id= " + id +
                ", name= " + name +
                ", age= " + age +
                ", country= " + country +
                ", bet= " + bet +
                ", event= " + event +
                ", game= " + game +
                ", payment= " + payment;
    }
}
