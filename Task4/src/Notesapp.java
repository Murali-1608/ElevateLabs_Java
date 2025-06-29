import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

class NotesApp
{

    private static final String FILE_NAME = "notes.txt";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void writeNote(String note, boolean append) {
        try (FileWriter writer = new FileWriter(FILE_NAME, append)) {
            String timestamp = LocalDateTime.now().format(formatter);
            writer.write("[" + timestamp + "] " + note + System.lineSeparator());
            System.out.println("✅ Note saved!");
        } catch (IOException e) {
            System.out.println("❌ Error writing to file: " + e.getMessage());
        }
    }

    public static void readAllNotes()
    {
        System.out.println("\n📄 All Notes:");
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            int count = 0;
            while ((line = reader.readLine()) != null) {
                System.out.println("📝 " + line);
                count++;
            }
            System.out.println("\n📌 Total Notes: " + count);
        } catch (IOException e) {
            System.out.println("⚠️ Could not read notes: " + e.getMessage());
        }
    }

    public static void readLastNNotes(int n) {
        List<String> notes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                notes.add(line);
            }

            System.out.println("\n📃 Last " + n + " Notes:");
            notes.stream()
                    .skip(Math.max(0, notes.size() - n))
                    .forEach(note -> System.out.println("📝 " + note));

        } catch (IOException e) {
            System.out.println("⚠️ Could not read notes: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("📓 Welcome to Smart Notes App");

        while (running) {
            System.out.println("\n✨ Menu:");
            System.out.println("1️  Add Note (Append)");
            System.out.println("2️  Overwrite All Notes");
            System.out.println("3️  View All Notes");
            System.out.println("4️  View Last N Notes");
            System.out.println("5️  Exit");

            System.out.print("\nEnter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("🖊️ Enter your note: ");
                    String note1 = scanner.nextLine();
                    writeNote(note1, true);
                    break;
                case 2:
                    System.out.print("⚠️ This will erase all previous notes. Continue? (yes/no): ");
                    String confirm = scanner.nextLine();
                    if (confirm.equalsIgnoreCase("yes")) {
                        System.out.print("🆕 Enter new note: ");
                        String note2 = scanner.nextLine();
                        writeNote(note2, false);
                    }
                    break;
                case 3:
                    readAllNotes();
                    break;
                case 4:
                    System.out.print("🔢 How many recent notes to view? ");
                    int n = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    readLastNNotes(n);
                    break;
                case 5:
                    System.out.println("👋 Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("❌ Invalid choice, try again.");
            }
        }

        scanner.close();
    }
}

