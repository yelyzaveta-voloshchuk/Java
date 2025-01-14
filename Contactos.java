import java.io.*;
import java.util.*;

public class Contactos{
    private static final String FILE_NAME = "phonebook.dat";
    private static Map<String, String> phoneBook = new HashMap<>();

    public static void main(String[] args) {
        loadPhoneBook();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("Phone Book Manager");
            System.out.println("1. Add Contact");
            System.out.println("2. Delete Contact");
            System.out.println("3. Show Contacts");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    addContact(scanner);
                    break;
                case 2:
                    deleteContact(scanner);
                    break;
                case 3:
                    showContacts();
                    break;
                case 4:
                    savePhoneBook();
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 4);

        scanner.close();
    }

    private static void loadPhoneBook() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            phoneBook = (Map<String, String>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Phone book file not found. Starting with an empty phone book.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading phone book: " + e.getMessage());
        }
    }

    private static void savePhoneBook() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(phoneBook);
        } catch (IOException e) {
            System.out.println("Error saving phone book: " + e.getMessage());
        }
    }

    private static void addContact(Scanner scanner) {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter phone number: ");
        String phone = scanner.nextLine();
        phoneBook.put(name, phone);
        System.out.println("Contact added.");
    }

    private static void deleteContact(Scanner scanner) {
        System.out.print("Enter name of the contact to delete: ");
        String name = scanner.nextLine();
        if (phoneBook.remove(name) != null) {
            System.out.println("Contact deleted.");
        } else {
            System.out.println("Contact not found.");
        }
    }

    private static void showContacts() {
        if (phoneBook.isEmpty()) {
            System.out.println("No contacts found.");
        } else {
            System.out.println("Phone Book:");
            for (Map.Entry<String, String> entry : phoneBook.entrySet()) {
                System.out.println("Name: " + entry.getKey() + ", Phone: " + entry.getValue());
            }
        }
    }
}