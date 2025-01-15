/**
 * The Contactos class provides a simple phone book manager that allows users to add, delete, and display contacts.
 * Contacts are stored in a HashMap and persisted in a file named "phonebook.dat".
 */
import java.io.*;
import java.util.*;

/**
 * Main class for the Phone Book Manager application.
 */
public class Contactos {
    private static final String FILE_NAME = "phonebook.dat"; // The name of the file to store contacts
    private static Map<String, String> phoneBook = new HashMap<>(); // Map to store contacts

    /**
     * The main method that starts the Phone Book Manager application.
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        loadPhoneBook(); // Load existing contacts from file
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
                    addContact(scanner); // Add a new contact
                    break;
                case 2:
                    deleteContact(scanner); // Delete an existing contact
                    break;
                case 3:
                    showContacts(); // Display all contacts
                    break;
                case 4:
                    savePhoneBook(); // Save contacts to file before exiting
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 4);

        scanner.close(); // Close the scanner
    }

    /**
     * Loads the phone book from the specified file.
     */
    private static void loadPhoneBook() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            phoneBook = (Map<String, String>) ois.readObject(); // Read contacts from file
        } catch (FileNotFoundException e) {
            System.out.println("Phone book file not found. Starting with an empty phone book.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading phone book: " + e.getMessage());
        }
    }

    /**
     * Saves the current phone book to the specified file.
     */
    private static void savePhoneBook() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(phoneBook); // Write contacts to file
        } catch (IOException e) {
            System.out.println("Error saving phone book: " + e.getMessage());
        }
    }

    /**
     * Adds a new contact to the phone book.
     * @param scanner Scanner object to read user input
     */
    private static void addContact(Scanner scanner) {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter phone number: ");
        String phone = scanner.nextLine();
        phoneBook.put(name, phone); // Add contact to the map
        System.out.println("Contact added.");
    }

    /**
     * Deletes a contact from the phone book.
     * @param scanner Scanner object to read user input
     */
    private static void deleteContact(Scanner scanner) {
        System.out.print("Enter name of the contact to delete: ");
        String name = scanner.nextLine();
        if (phoneBook.remove(name) != null) {
            System.out.println("Contact deleted."); // Confirm deletion
        } else {
            System.out.println("Contact not found."); // Contact not found
        }
    }

    /**
     * Displays all contacts in the phone book.
     */
    private static void showContacts() {
        if (phoneBook.isEmpty()) {
            System.out.println("No contacts found."); // No contacts available
        } else {
            System.out.println("Phone Book:");
            for (Map.Entry<String, String> entry : phoneBook.entrySet()) {
                System.out.println("Name: " + entry.getKey() + ", Phone: " + entry.getValue()); // Display each contact
            }
        }
    }
}
