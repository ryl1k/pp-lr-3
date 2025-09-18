package droids;


public class SniperDroid extends Droid {
    private double accuracy;


    public SniperDroid(String name, int health, int damage, int armor) {
        super(name, 50,50);
        this.accuracy = 0.3;
    }

    public double getAccuracy() {
        return accuracy;
    }
    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public boolean Ability() {
        if(Math.random() < accuracy) {
            accuracy = 0.3;
            return true;
        }
        accuracy += 0.1;
        return false;
    }
    
    @Override
    public String toString() {
        return "SniperDroid{" +
                "ability=50% to ignore armor" +
                "} " + super.toString();
    }

    @Override
    public void takeTurn(Droid opponent, Droid ally) {
        if(Ability()) {
            opponent.takeDamage(this.getDamage() + 20);
        }
        else{
            opponent.takeDamage(this.getDamage());
        }
    }
}
