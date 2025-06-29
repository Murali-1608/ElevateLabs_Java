import java.util.*;

class Book
{
    private final int id;
    private final String title;
    private boolean isIssued = false;

    public Book(int id, String title)
    {
        this.id = id;
        this.title = title;
    }

    public int getId()
    {
        return id;
    }
    public String getTitle()
    {
        return title;
    }
    public boolean isIssued()
    {
        return isIssued;
    }

    public void issue()
    {
        isIssued = true;
    }
    public void returned()
    {
        isIssued = false;
    }

    @Override
    public String toString()
    {
        return id + " - " + title + " [" + (isIssued ? "Issued" : "Available") + "]";
    }
}

class User
{
    private final int userId;
    private final String name;
    private final ArrayList<Book> borrowedBooks = new ArrayList<>();

    public User(int userId, String name)
    {
        this.userId = userId;
        this.name = name;
    }

    public void borrow(Book book)
    {
        borrowedBooks.add(book);
        book.issue();
    }

    public void returnBook(Book book)
    {
        borrowedBooks.remove(book);
        book.returned();
    }

    public ArrayList<Book> getBorrowedBooks()
    {
        return borrowedBooks;
    }

    public String getName()
    {
        return name;
    }

    @Override
    public String toString()
    {
        return userId + " - " + name;
    }
}

class Library
{
    private final ArrayList<Book> books = new ArrayList<>();
    private final ArrayList<User> users = new ArrayList<>();

    public void addBook(Book book)
    {
        books.add(book);
        System.out.println(" Book added: " + book.getTitle());
    }

    public void deleteBook(int bookId)
    {
        Book book = findBook(bookId);
        if (book != null)
        {
            if (!book.isIssued())
            {
                books.remove(book);
                System.out.println(" Book deleted: " + book.getTitle());
            }
            else
            {
                System.out.println(" Cannot delete. Book is currently issued.");
            }
        }
        else
        {
            System.out.println(" Book not found.");
        }
    }

    public void addUser(User user)
    {
        users.add(user);
    }

    public void issueBook(int bookId, int userId)
    {
        Book book = findBook(bookId);
        User user = findUser(userId);
        if (book != null && user != null && !book.isIssued())
        {
            user.borrow(book);
            System.out.println(" Book issued to " + user.getName());
        }
        else
        {
            System.out.println(" Issue failed.");
        }
    }

    public void returnBook(int bookId, int userId)
    {
        Book book = findBook(bookId);
        User user = findUser(userId);
        if (book != null && user != null && book.isIssued())
        {
            user.returnBook(book);
            System.out.println(" Book returned by " + user.getName());
        }
        else
        {
            System.out.println(" Return failed.");
        }
    }

    public Book findBook(int id)
    {
        return books.stream().filter(b -> b.getId() == id).findFirst().orElse(null);
    }

    public User findUser(int id)
    {
        return users.stream().filter(u -> u.toString().startsWith(id + " -")).findFirst().orElse(null);
    }

    public void showBooks()
    {
        System.out.println("\n Available Books:");
        books.forEach(System.out::println);
    }

    public void showUsers()
    {
        System.out.println("\n Registered Users:");
        users.forEach(System.out::println);
    }
}

class LibraryManageSys {
    public static void main(String[] args)
    {
        Library library = new Library();
        library.addBook(new Book(101, "Java Basics"));
        library.addBook(new Book(102, "Data Structures"));
        library.addUser(new User(1, "Alice"));
        library.addUser(new User(2, "Bob"));

        Scanner sc = new Scanner(System.in);
        boolean running = true;
        while (running)
        {
            System.out.println("\n Library System Menu");
            System.out.println("1. Show Books");
            System.out.println("2. Show Users");
            System.out.println("3. Issue Book");
            System.out.println("4. Return Book");
            System.out.println("5. Add New Book");
            System.out.println("6. Delete Book");
            System.out.println("7. Exit");
            System.out.print("Your choice: ");

            int choice = sc.nextInt();
            switch (choice)
            {
                case 1 -> library.showBooks();
                case 2 -> library.showUsers();
                case 3 ->
                {
                    System.out.print("Enter Book ID: ");
                    int bookId = sc.nextInt();
                    System.out.print("Enter User ID: ");
                    int userId = sc.nextInt();
                    library.issueBook(bookId, userId);
                }
                case 4 ->
                {
                    System.out.print("Enter Book ID: ");
                    int bookId = sc.nextInt();
                    System.out.print("Enter User ID: ");
                    int userId = sc.nextInt();
                    library.returnBook(bookId, userId);
                }
                case 5 ->
                {
                    System.out.print("Enter New Book ID: ");
                    int newId = sc.nextInt();
                    sc.nextLine(); // consume newline
                    System.out.print("Enter Book Title: ");
                    String title = sc.nextLine();
                    library.addBook(new Book(newId, title));
                }
                case 6 ->
                {
                    System.out.print("Enter Book ID to Delete: ");
                    int delId = sc.nextInt();
                    library.deleteBook(delId);
                }
                case 7 ->
                {
                    System.out.println("Exiting Library System. Goodbye!");
                    running = false;
                }
                default -> System.out.println("Invalid option.");
            }
        }
        sc.close();
    }
}
