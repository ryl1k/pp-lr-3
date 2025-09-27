package main.io;

import main.droids.*;
import java.util.Scanner;

public class DroidFactory {

    public static Droid createCustomDroid(Scanner scanner) {
        BattleVisualizer.showMenu("Droid Factory", new String[]{
            "Assault Droid (High damage, double attack chance)",
            "Tank Droid (High health, armor protection)",
            "Sniper Droid (Critical hits, low health)",
            "Healer Droid (Healing abilities, support)"
        });

        int choice = getIntInput(scanner, 1, 4);
        scanner.nextLine(); // consume newline

        System.out.print(BattleVisualizer.YELLOW + "Enter droid name: " + BattleVisualizer.RESET);
        String name = scanner.nextLine().trim();

        if (name.isEmpty()) {
            name = getDefaultName(choice);
        }

        Droid droid = createDroidByType(choice, name);
        BattleVisualizer.showSuccess("Created " + name + "!");
        BattleVisualizer.showDroidStatus(droid);
        BattleVisualizer.showDroidDetails(droid);

        return droid;
    }

    public static Droid selectPredefinedDroid(Scanner scanner) {
        String[] droidOptions = {
            "Destroyer (Assault Droid)",
            "Guardian (Tank Droid)",
            "Ghost (Sniper Droid)",
            "Medic (Healer Droid)",
            "Create Custom Droid"
        };

        BattleVisualizer.showMenu("Select Your Fighter", droidOptions);
        int choice = getIntInput(scanner, 1, 5);

        if (choice == 5) {
            return createCustomDroid(scanner);
        }

        String[] defaultNames = {"Destroyer", "Guardian", "Ghost", "Medic"};
        Droid droid = createDroidByType(choice, defaultNames[choice - 1]);

        BattleVisualizer.showSuccess("Selected " + droid.getName() + "!");
        BattleVisualizer.showDroidStatus(droid);
        BattleVisualizer.showDroidDetails(droid);

        return droid;
    }

    private static Droid createDroidByType(int type, String name) {
        switch (type) {
            case 1: return new AssaultDroid(name, 100, 25, 0);
            case 2: return new TankDroid(name, 150, 15, 20);
            case 3: return new SniperDroid(name, 50, 50, 0);
            case 4: return new HealerDroid(name, 75, 10, 0);
            default: return new AssaultDroid(name, 100, 25, 0);
        }
    }

    private static String getDefaultName(int type) {
        switch (type) {
            case 1: return "Assault-" + System.currentTimeMillis() % 1000;
            case 2: return "Tank-" + System.currentTimeMillis() % 1000;
            case 3: return "Sniper-" + System.currentTimeMillis() % 1000;
            case 4: return "Healer-" + System.currentTimeMillis() % 1000;
            default: return "Droid-" + System.currentTimeMillis() % 1000;
        }
    }

    private static int getIntInput(Scanner scanner, int min, int max) {
        while (true) {
            try {
                int input = scanner.nextInt();
                if (input >= min && input <= max) {
                    return input;
                }
                BattleVisualizer.showError("Please enter a number between " + min + " and " + max);
            } catch (Exception e) {
                BattleVisualizer.showError("Please enter a valid number");
                scanner.nextLine(); // consume invalid input
            }
        }
    }

    public static void showDroidTypeInfo() {
        System.out.println(BattleVisualizer.CYAN + "\n=== DROID TYPES INFO ===" + BattleVisualizer.RESET);

        System.out.println(BattleVisualizer.BRIGHT_RED + "\n[ASLT] ASSAULT DROID:" + BattleVisualizer.RESET);
        System.out.println("  Health: 100 | Damage: 25");
        System.out.println("  Special: 30% chance for double attack");
        System.out.println("  Strategy: High damage, aggressive tactics");

        System.out.println(BattleVisualizer.BLUE + "\n[TANK] TANK DROID:" + BattleVisualizer.RESET);
        System.out.println("  Health: 150 | Damage: 15 | Armor: 20");
        System.out.println("  Special: Damage reduction, armor degrades over time");
        System.out.println("  Strategy: Defensive, high survivability");

        System.out.println(BattleVisualizer.PURPLE + "\n[SNPR] SNIPER DROID:" + BattleVisualizer.RESET);
        System.out.println("  Health: 50 | Damage: 50");
        System.out.println("  Special: 30% chance for +20 critical damage");
        System.out.println("  Strategy: Glass cannon, high precision");

        System.out.println(BattleVisualizer.BRIGHT_GREEN + "\n[HEAL] HEALER DROID:" + BattleVisualizer.RESET);
        System.out.println("  Health: 75 | Damage: 10 | Heal: 25");
        System.out.println("  Special: 50% chance to heal self or ally");
        System.out.println("  Strategy: Support role, team sustain");

        System.out.println(BattleVisualizer.YELLOW + "\nTEAM COMPOSITION TIPS:" + BattleVisualizer.RESET);
        System.out.println("  • Balanced teams (Tank + Assault + Healer) are versatile");
        System.out.println("  • All Snipers = high risk, high reward");
        System.out.println("  • Multiple Healers = long, strategic battles");
        System.out.println("  • Solo fights favor high damage or high defense");

        System.out.println();
    }
}