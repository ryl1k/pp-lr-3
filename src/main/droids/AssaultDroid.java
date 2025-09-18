package droids;


public class AssaultDroid extends Droid {
    private double abilityChance;

    public AssaultDroid(String name, int health, int damage, int armor) {
        super(name, 100,25);
        this.abilityChance = 0.3;
    }

    public double getAbilityChance() {
        return abilityChance;
    }
    public void setAbilityChance(double abilityChance) {
        this.abilityChance = abilityChance;
    }

    public boolean Ability() {
        if(Math.random() < abilityChance) {
            abilityChance = 0.3;
            return true;
        }
        abilityChance += 0.2;
        return false;
    }

    @Override
    public String toString() {
        return "AssaultDroid{" +
                "ability=30% to double move" +
                "} " + super.toString();
    }

    @Override
    public void takeTurn(Droid opponent, Droid ally) {
        if(Ability()) {
            opponent.takeDamage(this.getDamage());
        }
        opponent.takeDamage(this.getDamage());
    }
}
