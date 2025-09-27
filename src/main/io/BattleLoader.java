package main.io;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import main.io.BattleVisualizer;

public class BattleLoader {

    public static List<String> load(String filePath) {
        try {
            return Files.readAllLines(Paths.get(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Cannot read log file: " + filePath, e);
        }
    }

    public static void replay(String filePath, long delayMs) {
        List<String> lines = load(filePath);
        Scanner scanner = new Scanner(System.in);

        System.out.println(BattleVisualizer.BRIGHT_YELLOW + "*** BATTLE REPLAY STARTING ***" + BattleVisualizer.RESET);
        System.out.println(BattleVisualizer.CYAN + "Controls: [ENTER] - next line, [F] - fast mode, [Q] - quit" + BattleVisualizer.RESET);
        System.out.println(BattleVisualizer.WHITE + "=".repeat(50) + BattleVisualizer.RESET);

        boolean fastMode = false;

        for (String line : lines) {
            visualizeLine(line);

            if (!fastMode) {
                System.out.print(" → ");
                String input = scanner.nextLine().toLowerCase();

                if (input.equals("q")) break;
                if (input.equals("f")) {
                    fastMode = true;
                    System.out.println(BattleVisualizer.BRIGHT_GREEN + "*** FAST MODE ON ***" + BattleVisualizer.RESET);
                }
            } else {
                try { Thread.sleep(Math.min(delayMs, 200)); } catch (InterruptedException ignored) {}
            }
        }

        System.out.println(BattleVisualizer.BRIGHT_YELLOW + "\n*** REPLAY FINISHED! ***" + BattleVisualizer.RESET);
    }

    public static String selectBattleLog(Scanner scanner) {
        List<File> logFiles = findBattleLogs();

        if (logFiles.isEmpty()) {
            BattleVisualizer.showError("No battle logs found in logs/ directory");
            return null;
        }

        BattleVisualizer.showMenu("Select Battle Replay", createLogMenuOptions(logFiles));

        int choice = getIntInput(scanner, 1, logFiles.size() + 1);

        if (choice == logFiles.size() + 1) {
            // Manual entry option
            System.out.print(BattleVisualizer.YELLOW + "Enter custom log file path: " + BattleVisualizer.RESET);
            scanner.nextLine(); // consume newline
            return scanner.nextLine();
        }

        return logFiles.get(choice - 1).getPath();
    }

    private static List<File> findBattleLogs() {
        List<File> logFiles = new ArrayList<>();
        File logsDir = new File("logs");

        if (!logsDir.exists() || !logsDir.isDirectory()) {
            return logFiles;
        }

        File[] files = logsDir.listFiles((dir, name) ->
            name.toLowerCase().endsWith(".txt") &&
            (name.contains("battle_log") || name.contains("onevsone") || name.contains("team_battle"))
        );

        if (files != null) {
            // новіші спочатку
            java.util.Arrays.sort(files, (a, b) -> Long.compare(b.lastModified(), a.lastModified()));

            for (File file : files) {
                logFiles.add(file);
            }
        }

        return logFiles;
    }

    private static String[] createLogMenuOptions(List<File> logFiles) {
        String[] options = new String[logFiles.size() + 1];

        for (int i = 0; i < logFiles.size(); i++) {
            File file = logFiles.get(i);
            String battleType = getBattleType(file.getName());
            String timeAgo = getTimeAgo(file.lastModified());
            options[i] = String.format("%s - %s (%s)", battleType, file.getName(), timeAgo);
        }

        options[logFiles.size()] = "Enter custom file path";
        return options;
    }

    private static String getBattleType(String fileName) {
        if (fileName.contains("onevsone")) {
            return BattleVisualizer.CYAN + "[1v1]" + BattleVisualizer.RESET;
        } else if (fileName.contains("team_battle")) {
            return BattleVisualizer.PURPLE + "[TEAM]" + BattleVisualizer.RESET;
        } else {
            return BattleVisualizer.WHITE + "[BATTLE]" + BattleVisualizer.RESET;
        }
    }

    private static String getTimeAgo(long lastModified) {
        long now = System.currentTimeMillis();
        long diff = now - lastModified;

        long minutes = diff / (1000 * 60);
        long hours = diff / (1000 * 60 * 60);
        long days = diff / (1000 * 60 * 60 * 24);

        if (minutes < 1) {
            return "just now";
        } else if (minutes < 60) {
            return minutes + "m ago";
        } else if (hours < 24) {
            return hours + "h ago";
        } else {
            return days + "d ago";
        }
    }

    private static int getIntInput(Scanner scanner, int min, int max) {
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

    public static void showLogPreview(String filePath) {
        try {
            List<String> lines = load(filePath);
            System.out.println(BattleVisualizer.CYAN + "\n=== LOG PREVIEW ===" + BattleVisualizer.RESET);

            int previewLines = Math.min(10, lines.size());
            for (int i = 0; i < previewLines; i++) {
                System.out.println(BattleVisualizer.WHITE + (i + 1) + ": " + lines.get(i) + BattleVisualizer.RESET);
            }

            if (lines.size() > 10) {
                System.out.println(BattleVisualizer.YELLOW + "... and " + (lines.size() - 10) + " more lines" + BattleVisualizer.RESET);
            }

            System.out.println(BattleVisualizer.CYAN + "Total lines: " + lines.size() + BattleVisualizer.RESET);
        } catch (Exception e) {
            BattleVisualizer.showError("Could not preview file: " + e.getMessage());
        }
    }

    private static void visualizeLine(String line) {
        if (line.contains("Round")) {
            System.out.println(BattleVisualizer.YELLOW + "\n>>> " + line + " <<<" + BattleVisualizer.RESET);
        } else if (line.contains("takes turn") || line.contains("attacks")) {
            System.out.println(BattleVisualizer.RED + ">>> " + line + BattleVisualizer.RESET);
        } else if (line.contains("destroyed") || line.contains("знищений")) {
            System.out.println(BattleVisualizer.BRIGHT_RED + "XXX " + line.toUpperCase() + BattleVisualizer.RESET);
        } else if (line.contains("wins") || line.contains("Battle Over")) {
            System.out.println(BattleVisualizer.BRIGHT_YELLOW + "*** " + line.toUpperCase() + " ***" + BattleVisualizer.RESET);
        } else if (line.contains("health") || line.contains("damage")) {
            System.out.println(BattleVisualizer.CYAN + "--- " + line + BattleVisualizer.RESET);
        } else if (line.contains("===")) {
            System.out.println(BattleVisualizer.WHITE + "=".repeat(50) + BattleVisualizer.RESET);
        } else {
            System.out.println("    " + line);
        }
    }
}
