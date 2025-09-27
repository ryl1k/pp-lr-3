package main.droids;

public class HealerDroid extends Droid {
    private int healAmount;
    private int maxHealth;

    public HealerDroid(String name, int health, int damage, int armor) {
        super(name, 75, 10);
        this.healAmount = 25;
        this.maxHealth = 75;
    }

    public int getHealAmount() {
        return healAmount;
    }
    public void setHealAmount(int healAmount) {
        this.healAmount = healAmount;
    }

    public void Heal(Droid target) {
        int targetMaxHealth = (target instanceof HealerDroid) ?
            ((HealerDroid) target).maxHealth :
            (target instanceof TankDroid) ? 150 :
            (target instanceof AssaultDroid) ? 100 : 50;

        int newHealth = Math.min(targetMaxHealth, target.getHealth() + this.getHealAmount());
        target.setHealth(newHealth);
    }


    public boolean Ability() {
        if (Math.random() < 0.5) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "HealerDroid{" +
                "healAmount=" + healAmount +
                "} " + super.toString();
    }

    @Override
    public void takeTurn(Droid opponent, Droid ally) {
        if(this.Ability()) {
            if(ally != null && ally.isAlive() && (ally.getHealth() <= this.getHealth())) {
                this.Heal(ally);
            }
            else{
                this.Heal(this);
            }
        }
        opponent.takeDamage(this.getDamage());
    }
}
