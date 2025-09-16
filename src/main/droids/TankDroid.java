package droids;

public class TankDroid extends Droid {
    private int armor;

    public TankDroid(String name, int health, int damage, int armor) {
        super(name, 150, 15);
        this.armor = 20;
    }

    public int getArmor() {
        return armor;
    }
    public void setArmor(int armor) {
        this.armor = armor;
    }
    
    @Override
    public void takeDamage(int damage) {
        int effectiveDamage = Math.max(0, damage - armor);
        this.armor = Math.max(0, armor - damage/5);
        setHealth(getHealth() - effectiveDamage);
    }

    @Override
    public String toString() {
        return "TankDroid{" +
                "armor=" + armor +
                "} " + super.toString();
    }

}
