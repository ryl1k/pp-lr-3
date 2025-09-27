package main.battle;

import main.droids.Droid;
import main.droids.HealerDroid;
import main.io.BattleSaver;
import main.io.BattleVisualizer;
import java.util.ArrayList;
import java.util.List;

public class TeamBattle implements Battle {
    private List<Droid> team1;
    private List<Droid> team2;
    private String result;

    public boolean isTeamAlive(List<Droid> team) {
        for (Droid droid : team) {
            if (droid.isAlive()) {
                return true;
            }
        }
        return false;
    }

    public Droid chooseTarget(List<Droid> team) {
        List<Droid> alive = new ArrayList<>();
        for (Droid d : team) {
            if (d.isAlive()) {
                alive.add(d);
            }
        }

        if (alive.isEmpty()) {
            return null;
        }

        int r = (int) (Math.random() * alive.size());
        return alive.get(r);
    }

    public Droid chooseAlly(List<Droid> team, Droid currentDroid) {
        List<Droid> aliveAllies = new ArrayList<>();
        for (Droid d : team) {
            if (d.isAlive() && d != currentDroid && d.getHealth() < getMaxHealth(d) * 0.5) {
                aliveAllies.add(d);
            }
        }

        if (aliveAllies.isEmpty()) {
            return null;
        }

        // Choose ally with lowest health percentage
        Droid worstAlly = aliveAllies.get(0);
        double lowestHealthRatio = (double) worstAlly.getHealth() / getMaxHealth(worstAlly);

        for (Droid ally : aliveAllies) {
            double healthRatio = (double) ally.getHealth() / getMaxHealth(ally);
            if (healthRatio < lowestHealthRatio) {
                lowestHealthRatio = healthRatio;
                worstAlly = ally;
            }
        }

        return worstAlly;
    }

    private int getMaxHealth(Droid droid) {
        String className = droid.getClass().getSimpleName();
        switch (className) {
            case "AssaultDroid": return 100;
            case "TankDroid": return 150;
            case "SniperDroid": return 50;
            case "HealerDroid": return 75;
            default: return 100;
        }
    }

    public TeamBattle(List<Droid> team1, List<Droid> team2) {
        this.team1 = team1;
        this.team2 = team2;
    }

    @Override
    public void start() {
        BattleSaver io = new BattleSaver("logs/team_battle_log" + System.currentTimeMillis() + ".txt");

        // Show team battle start
        showTeamBattleStart();
        BattleVisualizer.pause(2000);

        // Log battle start
        io.log("=====================================");
        io.log("TEAM BATTLE START");
        io.log("Team 1:");
        for (Droid d : team1) {
            io.log("  " + d.toString());
        }
        io.log("Team 2:");
        for (Droid d : team2) {
            io.log("  " + d.toString());
        }
        io.log("=====================================");

        int round = 0;

        while (isTeamAlive(team1) && isTeamAlive(team2)) {
            round++;

            // Visual round header
            BattleVisualizer.showRoundHeader(round);
            io.log("Round " + round + ":");

            // Team 1 turn
            System.out.println(BattleVisualizer.BRIGHT_GREEN + "\n>>> TEAM 1 TURN <<<" + BattleVisualizer.RESET);
            for (Droid d1 : team1) {
                if (!d1.isAlive()) continue;

                Droid target = chooseTarget(team2);
                if (target == null) break; // All enemies dead

                Droid ally = null;
                if (d1 instanceof HealerDroid) {
                    ally = chooseAlly(team1, d1);
                }

                d1.takeTurn(target, ally);
                BattleVisualizer.showAttack(d1, target, isSpecialAbility(d1));
                io.log(d1.getName() + " attacks " + target.getName());

                if (!target.isAlive()) {
                    BattleVisualizer.showDeath(target);
                    io.log(target.getName() + " destroyed!");
                }

                if (!isTeamAlive(team2)) break;
                BattleVisualizer.pause(800);
            }

            if (!isTeamAlive(team2)) break;

            // Team 2 turn
            System.out.println(BattleVisualizer.BRIGHT_RED + "\n>>> TEAM 2 TURN <<<" + BattleVisualizer.RESET);
            for (Droid d2 : team2) {
                if (!d2.isAlive()) continue;

                Droid target = chooseTarget(team1);
                if (target == null) break;

                Droid ally = null;
                if (d2 instanceof HealerDroid) {
                    ally = chooseAlly(team2, d2);
                }

                d2.takeTurn(target, ally);
                BattleVisualizer.showAttack(d2, target, isSpecialAbility(d2));
                io.log(d2.getName() + " attacks " + target.getName());

                if (!target.isAlive()) {
                    BattleVisualizer.showDeath(target);
                    io.log(target.getName() + " destroyed!");
                }

                if (!isTeamAlive(team1)) break;
                BattleVisualizer.pause(800);
            }

            // Show round status
            showRoundStatus(round);
            io.log("End of round " + round);
            BattleVisualizer.pause(1500);
        }

        io.log("Team Battle Over!");
        io.close();
    }

    private void showTeamBattleStart() {
        BattleVisualizer.clearScreen();
        System.out.println(BattleVisualizer.BRIGHT_YELLOW + "=" + "=".repeat(48) + "=" + BattleVisualizer.RESET);
        System.out.println(BattleVisualizer.BRIGHT_YELLOW + "              TEAM BATTLE ARENA" + BattleVisualizer.RESET);
        System.out.println(BattleVisualizer.BRIGHT_YELLOW + "=" + "=".repeat(48) + "=" + BattleVisualizer.RESET);

        System.out.println(BattleVisualizer.BRIGHT_GREEN + "\n>>> TEAM 1 <<<" + BattleVisualizer.RESET);
        for (Droid d : team1) {
            BattleVisualizer.showDroidStatus(d);
        }

        System.out.println(BattleVisualizer.BRIGHT_RED + "\n>>> TEAM 2 <<<" + BattleVisualizer.RESET);
        for (Droid d : team2) {
            BattleVisualizer.showDroidStatus(d);
        }

        System.out.println(BattleVisualizer.BRIGHT_YELLOW + "\n" + "=".repeat(50) + BattleVisualizer.RESET);
        System.out.println(BattleVisualizer.BRIGHT_YELLOW + "                BATTLE START!" + BattleVisualizer.RESET);
        System.out.println(BattleVisualizer.BRIGHT_YELLOW + "=".repeat(50) + BattleVisualizer.RESET);
    }

    private void showRoundStatus(int round) {
        System.out.println(BattleVisualizer.CYAN + "\n--- Round " + round + " Status ---" + BattleVisualizer.RESET);

        System.out.println(BattleVisualizer.BRIGHT_GREEN + "Team 1:" + BattleVisualizer.RESET);
        for (Droid d : team1) {
            if (d.isAlive()) {
                BattleVisualizer.showDroidStatus(d);
            }
        }

        System.out.println(BattleVisualizer.BRIGHT_RED + "Team 2:" + BattleVisualizer.RESET);
        for (Droid d : team2) {
            if (d.isAlive()) {
                BattleVisualizer.showDroidStatus(d);
            }
        }
    }

    private boolean isSpecialAbility(Droid droid) {
        return Math.random() < 0.3; // Approximate special ability chance
    }

    @Override
    public void printBattleResults() {
        if (isTeamAlive(team1) && !isTeamAlive(team2)) {
            result = "Team 1 wins!";
        } else if (!isTeamAlive(team1) && isTeamAlive(team2)) {
            result = "Team 2 wins!";
        } else {
            result = "Draw!";
        }

        System.out.println(BattleVisualizer.BRIGHT_YELLOW + "\n" + "*".repeat(50) + BattleVisualizer.RESET);
        System.out.println(BattleVisualizer.BRIGHT_YELLOW + "         *** " + result.toUpperCase() + " ***" + BattleVisualizer.RESET);
        System.out.println(BattleVisualizer.BRIGHT_YELLOW + "*".repeat(50) + BattleVisualizer.RESET);

        System.out.println(BattleVisualizer.BRIGHT_GREEN + "\nTEAM 1 SURVIVORS:" + BattleVisualizer.RESET);
        for (Droid d : team1) {
            if (d.isAlive()) {
                BattleVisualizer.showDroidStatus(d);
            }
        }

        System.out.println(BattleVisualizer.BRIGHT_RED + "\nTEAM 2 SURVIVORS:" + BattleVisualizer.RESET);
        for (Droid d : team2) {
            if (d.isAlive()) {
                BattleVisualizer.showDroidStatus(d);
            }
        }
    }
}
