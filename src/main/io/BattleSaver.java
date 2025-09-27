package main.io;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.*;
import static java.nio.file.StandardOpenOption.*;

public class BattleSaver implements AutoCloseable {
    private final Path path;
    private final BufferedWriter writer;

    public BattleSaver(String filePath) {
        this(Paths.get(filePath));
    }

    public BattleSaver(Path path) {
        this.path = path;
        try {
            // створюємо директорію, якщо її немає
            Path dir = path.getParent();
            if (dir != null) Files.createDirectories(dir);

            // відкриваємо на дозапис, створюємо якщо нема
            this.writer = Files.newBufferedWriter(path, CREATE, APPEND);
            writer.write("=== New Battle Log ===\n");
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException("Cannot open log file: " + path, e);
        }
    }

    public void log(String message) {
        try {
            writer.write(message);
            writer.write('\n');
            writer.flush(); // для консолі
        } catch (IOException e) {
            throw new RuntimeException("Cannot write to log: " + path, e);
        }
    }

    @Override
    public void close() {
        try {
            writer.close();
        } catch (IOException e) {
            // остання спроба, просто повідомляємо
            System.err.println("Failed to close log: " + path + " -> " + e.getMessage());
        }
    }

    // зручно автоматично робити ім'я з міткою часу
    public static BattleSaver createWithTimestamp(String dir) {
        String name = "battle_" + System.currentTimeMillis() + ".txt";
        return new BattleSaver(Paths.get(dir, name));
    }
}
