package main.droids;


public class Droid {
    private String name;
    private int health;
    private int damage;

    public Droid(String name, int health, int damage) {
        this.name = name;
        this.health = health;
        this.damage = damage;
    }

    public String getName() {
        return name;
    }
    public int getHealth() {
        return health;
    }
    public int getDamage() {
        return damage;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setHealth(int health) {
        this.health = Math.max(0, health);
    }
    public void setDamage(int damage) {
        this.damage = damage;
    }

    @Override
    public String toString() {
        return "name='" + name + '\'' +
                ", health=" + health +
                ", damage=" + damage +
                '}';
    }

    public void takeDamage(int damage) {
        this.health -= damage;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public void attack(Droid opp){
        opp.takeDamage(this.getDamage());
    }

    public void takeTurn(Droid opponent, Droid ally) {
        attack(opponent);
    }

}
