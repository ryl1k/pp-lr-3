package main;

import droids.*;
import battle.*;
import io.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        BattleVisualizer.clearScreen();
        showWelcome();

        while (true) {
            showMainMenu();
            int choice = getIntInput(1, 5);

            switch (choice) {
                case 1:
                    customOneVsOneBattle();
                    break;
                case 2:
                    customTeamBattle();
                    break;
                case 3:
                    replayBattle();
                    break;
                case 4:
                    DroidFactory.showDroidTypeInfo();
                    break;
                case 5:
                    BattleVisualizer.showSuccess("Thanks for playing!");
                    scanner.close();
                    return;
                default:
                    BattleVisualizer.showError("Invalid choice");
            }

            System.out.println(BattleVisualizer.YELLOW + "\nPress ENTER to continue..." + BattleVisualizer.RESET);
            scanner.nextLine();
        }
    }

    private static void showWelcome() {
        System.out.println(BattleVisualizer.BRIGHT_YELLOW + "=" + "=".repeat(48) + "=" + BattleVisualizer.RESET);
        System.out.println(BattleVisualizer.BRIGHT_YELLOW + "           DROID BATTLE ARENA" + BattleVisualizer.RESET);
        System.out.println(BattleVisualizer.BRIGHT_YELLOW + "         Welcome to the Arena!" + BattleVisualizer.RESET);
        System.out.println(BattleVisualizer.BRIGHT_YELLOW + "=" + "=".repeat(48) + "=" + BattleVisualizer.RESET);
        BattleVisualizer.pause(1500);
    }

    private static void showMainMenu() {
        BattleVisualizer.showMenu("Main Menu", new String[]{
            "1v1 Duel (Custom fighters)",
            "Team Battle (Variable squad sizes)",
            "Replay Previous Battle",
            "View Droid Types Info",
            "Exit Arena"
        });
    }

    private static void customOneVsOneBattle() {
        BattleVisualizer.clearScreen();
        System.out.println(BattleVisualizer.CYAN + "=== 1v1 DUEL SETUP ===" + BattleVisualizer.RESET);

        System.out.println(BattleVisualizer.BRIGHT_GREEN + "\nPlayer 1 - Choose your fighter:" + BattleVisualizer.RESET);
        Droid d1 = DroidFactory.selectPredefinedDroid(scanner);

        System.out.println(BattleVisualizer.BRIGHT_RED + "\nPlayer 2 - Choose your fighter:" + BattleVisualizer.RESET);
        Droid d2 = DroidFactory.selectPredefinedDroid(scanner);

        System.out.println(BattleVisualizer.YELLOW + "\nStarting duel in 3 seconds..." + BattleVisualizer.RESET);
        BattleVisualizer.pause(3000);

        OneVsOne battle = new OneVsOne(d1, d2);
        battle.start();
    }

    private static void customTeamBattle() {
        BattleVisualizer.clearScreen();
        System.out.println(BattleVisualizer.CYAN + "=== TEAM BATTLE SETUP ===" + BattleVisualizer.RESET);

        // Ask for team sizes
        System.out.print(BattleVisualizer.YELLOW + "Enter Team 1 size (1-5): " + BattleVisualizer.RESET);
        int team1Size = getIntInput(1, 5);

        System.out.print(BattleVisualizer.YELLOW + "Enter Team 2 size (1-5): " + BattleVisualizer.RESET);
        int team2Size = getIntInput(1, 5);

        List<Droid> team1 = createTeam("Team 1", team1Size);
        List<Droid> team2 = createTeam("Team 2", team2Size);

        System.out.println(BattleVisualizer.YELLOW + "\nStarting team battle in 3 seconds..." + BattleVisualizer.RESET);
        System.out.println(BattleVisualizer.CYAN + "Team 1 (" + team1Size + " fighters) vs Team 2 (" + team2Size + " fighters)" + BattleVisualizer.RESET);
        BattleVisualizer.pause(3000);

        TeamBattle battle = new TeamBattle(team1, team2);
        battle.start();
        battle.printBattleResults();
    }

    private static List<Droid> createTeam(String teamName, int size) {
        List<Droid> team = new ArrayList<>();

        System.out.println(BattleVisualizer.BRIGHT_YELLOW + "\n>>> Building " + teamName + " <<<" + BattleVisualizer.RESET);

        for (int i = 1; i <= size; i++) {
            System.out.println(BattleVisualizer.CYAN + "\nFighter " + i + " of " + size + ":" + BattleVisualizer.RESET);
            Droid droid = DroidFactory.selectPredefinedDroid(scanner);
            team.add(droid);
        }

        System.out.println(BattleVisualizer.BRIGHT_GREEN + "\n" + teamName + " Complete!" + BattleVisualizer.RESET);
        for (Droid d : team) {
            BattleVisualizer.showDroidStatus(d);
        }

        return team;
    }

    private static void replayBattle() {
        BattleVisualizer.clearScreen();
        System.out.println(BattleVisualizer.CYAN + "=== BATTLE REPLAY CENTER ===" + BattleVisualizer.RESET);

        // Select battle log file
        String path = BattleLoader.selectBattleLog(scanner);
        if (path == null) {
            return; // No valid file selected
        }

        // Show preview
        BattleLoader.showLogPreview(path);

        // Ask for replay settings
        System.out.println(BattleVisualizer.YELLOW + "\nReplay Settings:" + BattleVisualizer.RESET);
        BattleVisualizer.showMenu("Replay Speed", new String[]{
            "Slow (2000ms delay) - Easy to follow",
            "Normal (1000ms delay) - Recommended",
            "Fast (500ms delay) - Quick overview",
            "Turbo (100ms delay) - Speed run",
            "Custom delay"
        });

        int speedChoice = getIntInput(1, 5);
        long delay;

        switch (speedChoice) {
            case 1: delay = 2000; break;
            case 2: delay = 1000; break;
            case 3: delay = 500; break;
            case 4: delay = 100; break;
            case 5:
                System.out.print(BattleVisualizer.YELLOW + "Enter custom delay (ms): " + BattleVisualizer.RESET);
                delay = Long.parseLong(scanner.nextLine());
                break;
            default: delay = 1000;
        }

        System.out.println(BattleVisualizer.BRIGHT_GREEN + "\nStarting replay in 3 seconds..." + BattleVisualizer.RESET);
        BattleVisualizer.pause(3000);
        BattleVisualizer.clearScreen();

        try {
            BattleLoader.replay(path, delay);
        } catch (Exception e) {
            BattleVisualizer.showError("Could not load replay file: " + e.getMessage());
        }
    }

    private static int getIntInput(int min, int max) {
        while (true) {
            try {
                int input = scanner.nextInt();
                scanner.nextLine(); // consume newline
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
}
