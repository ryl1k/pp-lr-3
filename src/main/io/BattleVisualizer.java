package main.io;

import main.droids.Droid;

public class BattleVisualizer {

    // ANSI Color codes
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";
    public static final String BRIGHT_RED = "\u001B[91m";
    public static final String BRIGHT_GREEN = "\u001B[92m";
    public static final String BRIGHT_YELLOW = "\u001B[93m";

    public static void showDroidStatus(Droid droid) {
        String healthBar = createHealthBar(droid);
        String droidType = getDroidType(droid);
        String color = getDroidColor(droid);

        System.out.printf("%s[%s] %s%s %s\n",
            color, droidType, droid.getName(), RESET, healthBar);
    }

    public static void showBattleStart(Droid d1, Droid d2) {
        clearScreen();
        System.out.println(BRIGHT_YELLOW + "=" + "=".repeat(48) + "=" + RESET);
        System.out.println(BRIGHT_YELLOW + "             DROID BATTLE ARENA" + RESET);
        System.out.println(BRIGHT_YELLOW + "=" + "=".repeat(48) + "=" + RESET);

        System.out.println(CYAN + "\n>>> FIGHTER 1:" + RESET);
        showDroidStatus(d1);
        showDroidDetails(d1);

        System.out.println(CYAN + "\n>>> FIGHTER 2:" + RESET);
        showDroidStatus(d2);
        showDroidDetails(d2);

        System.out.println(BRIGHT_RED + "\n" + "=".repeat(50) + RESET);
        System.out.println(BRIGHT_RED + "                  FIGHT!" + RESET);
        System.out.println(BRIGHT_RED + "=".repeat(50) + RESET);
    }

    public static void showDroidDetails(Droid droid) {
        String className = droid.getClass().getSimpleName();
        String color = getDroidColor(droid);

        System.out.printf("  %sType: %s | Health: %d | Damage: %d%s",
            color, className, droid.getHealth(), droid.getDamage(), RESET);

        if (className.equals("TankDroid")) {
            System.out.printf("%s | Armor: 20%s", color, RESET);
        }
        System.out.println();
    }

    public static void showRoundHeader(int round) {
        System.out.println(YELLOW + "\n>>> ROUND " + round + " <<<" + RESET);
        System.out.println(YELLOW + "-".repeat(15) + RESET);
    }

    public static void showAttack(Droid attacker, Droid target, boolean isSpecial) {
        String attackIcon = isSpecial ? "*CRIT*" : ">>>";
        String specialText = isSpecial ? " [SPECIAL ABILITY]" : "";
        String color = isSpecial ? BRIGHT_RED : RED;

        System.out.printf("%s%s %s attacks %s%s%s\n",
            color, attackIcon, attacker.getName(), target.getName(), specialText, RESET);
    }

    public static void showHealing(Droid healer, Droid target) {
        System.out.printf("%s+++ %s heals %s%s\n",
            BRIGHT_GREEN, healer.getName(), target.getName(), RESET);
    }

    public static void showDeath(Droid droid) {
        System.out.printf("%sXXX %s has been DESTROYED!%s\n",
            BRIGHT_RED, droid.getName().toUpperCase(), RESET);
    }

    public static void showWinner(Droid winner) {
        System.out.println(BRIGHT_YELLOW + "\n" + "*".repeat(50) + RESET);
        System.out.printf(BRIGHT_YELLOW + "         *** %s WINS! ***\n" + RESET,
            winner.getName().toUpperCase());
        System.out.println(BRIGHT_YELLOW + "*".repeat(50) + RESET);
    }

    public static void showRoundStatus(Droid d1, Droid d2) {
        System.out.println(CYAN + "\n--- Status Update ---" + RESET);
        showDroidStatus(d1);
        showDroidStatus(d2);
        System.out.println();
    }

    private static String createHealthBar(Droid droid) {
        int maxHealth = getMaxHealth(droid);
        int currentHealth = Math.max(0, droid.getHealth());
        int barLength = 20;

        int filledBars = (int) ((double) currentHealth / maxHealth * barLength);
        int emptyBars = barLength - filledBars;

        String healthColor = currentHealth > maxHealth * 0.6 ? GREEN :
                           currentHealth > maxHealth * 0.3 ? YELLOW : RED;

        String healthBar = "#".repeat(filledBars) + ".".repeat(emptyBars);
        String percentage = String.format("%.0f%%", (double) currentHealth / maxHealth * 100);

        return String.format("%s[%s] %d/%d (%s)%s",
            healthColor, healthBar, currentHealth, maxHealth, percentage, RESET);
    }

    private static String getDroidType(Droid droid) {
        String className = droid.getClass().getSimpleName();
        switch (className) {
            case "AssaultDroid": return "ASLT";
            case "TankDroid": return "TANK";
            case "SniperDroid": return "SNPR";
            case "HealerDroid": return "HEAL";
            default: return "DROD";
        }
    }

    private static String getDroidColor(Droid droid) {
        String className = droid.getClass().getSimpleName();
        switch (className) {
            case "AssaultDroid": return BRIGHT_RED;
            case "TankDroid": return BLUE;
            case "SniperDroid": return PURPLE;
            case "HealerDroid": return BRIGHT_GREEN;
            default: return WHITE;
        }
    }

    private static int getMaxHealth(Droid droid) {
        String className = droid.getClass().getSimpleName();
        switch (className) {
            case "AssaultDroid": return 100;
            case "TankDroid": return 150;
            case "SniperDroid": return 50;
            case "HealerDroid": return 75;
            default: return 100;
        }
    }

    public static void clearScreen() {
        try {
            // For Windows
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // For Unix/Linux
                System.out.print("\033[2J\033[H");
            }
        } catch (Exception e) {
            // Fallback - print empty lines
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }

    public static void pause(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {}
    }

    public static void showMenu(String title, String[] options) {
        System.out.println(CYAN + "\n=== " + title + " ===" + RESET);
        for (int i = 0; i < options.length; i++) {
            System.out.printf(WHITE + "%d. %s%s\n", i + 1, options[i], RESET);
        }
        System.out.print(YELLOW + "Choose option: " + RESET);
    }

    public static void showError(String message) {
        System.out.println(BRIGHT_RED + "ERROR: " + message + RESET);
    }

    public static void showSuccess(String message) {
        System.out.println(BRIGHT_GREEN + "SUCCESS: " + message + RESET);
    }
}