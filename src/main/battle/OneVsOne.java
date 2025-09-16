package battle;

import droids.Droid;
import droids.HealerDroid;
import io.BattleSaver;
import io.BattleVisualizer;

public class OneVsOne implements Battle {
    private Droid d1;
    private Droid d2;
    public String result;

    public OneVsOne(Droid d1, Droid d2) {
        this.d1 = d1;
        this.d2 = d2;
    }

    @Override
    public void start() {
        BattleSaver io = new BattleSaver("logs/onevsone_battle_log" + System.currentTimeMillis() + ".txt");

        // Visual battle start
        BattleVisualizer.showBattleStart(d1, d2);
        BattleVisualizer.pause(2000);

        // Log battle start
        io.log("=====================================");
        io.log(d1.toString());
        io.log(d2.toString());
        io.log("Battle Start: " + d1.getName() + " vs " + d2.getName());

        int round = 0;

        while (d1.isAlive() && d2.isAlive()) {
            round++;

            // Visual round header
            BattleVisualizer.showRoundHeader(round);

            // Log round start
            io.log("======================================");
            io.log("Round " + round + ":");

            //
            // D1 takes turn
            //
            d1.takeTurn(d2, null);
            BattleVisualizer.showAttack(d1, d2, isSpecialAbility(d1));
            io.log(d1.getName() + " takes turn against " + d2.getName());

            //
            // D2 takes turn
            //
            if (d2.isAlive()) {
                d2.takeTurn(d1, null);
                BattleVisualizer.showAttack(d2, d1, isSpecialAbility(d2));
                io.log(d2.getName() + " takes turn against " + d1.getName());
            }

            // Check for deaths
            if (!d1.isAlive()) {
                BattleVisualizer.showDeath(d1);
                io.log(d1.getName() + " is destroyed!");
            }
            if (!d2.isAlive()) {
                BattleVisualizer.showDeath(d2);
                io.log(d2.getName() + " is destroyed!");
            }

            // Show round status
            if (d1.isAlive() && d2.isAlive()) {
                BattleVisualizer.showRoundStatus(d1, d2);
                BattleVisualizer.pause(1500);
            }

            // Log round status
            io.log("Status after round " + round + ":");
            io.log(d1.toString());
            io.log(d2.toString());
            io.log("======================================");
        }

        io.log("Battle Over!");
        io.close();
        printBattleResults();
    }

    private boolean isSpecialAbility(Droid droid) {
        // Simple heuristic: assume special ability if it's not base damage
        return Math.random() < 0.3; // Approximate special ability chance
    }

    @Override
    public void printBattleResults() {
        if (d1.isAlive()) {
            result = d1.getName() + " wins!";
            BattleVisualizer.showWinner(d1);
        } else {
            result = d2.getName() + " wins!";
            BattleVisualizer.showWinner(d2);
        }
    }
}