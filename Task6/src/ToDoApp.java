import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ToDoApp extends JFrame
{
    private JPanel taskPanel;
    private JTextField taskField;
    private JScrollPane scrollPane;

    public ToDoApp() {
        setTitle("📝 Enhanced To-Do App");
        setSize(500, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout(10, 10));
        taskPanel = new JPanel();
        taskPanel.setLayout(new BoxLayout(taskPanel, BoxLayout.Y_AXIS));

        scrollPane = new JScrollPane(taskPanel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
        taskField = new JTextField();
        JButton addButton = new JButton("➕ Add Task");

        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        inputPanel.add(taskField, BorderLayout.CENTER);
        inputPanel.add(addButton, BorderLayout.EAST);

        JButton deleteButton = new JButton("🗑️ Delete Completed");
        deleteButton.setFocusable(false);
        deleteButton.setPreferredSize(new Dimension(160, 35));

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(deleteButton);

        addButton.addActionListener(e -> addTask());

        deleteButton.addActionListener(e -> deleteCompletedTasks());

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void addTask() {
        String taskText = taskField.getText().trim();
        if (!taskText.isEmpty()) {
            // Create task component
            JCheckBox taskCheckBox = new JCheckBox(taskText + " (added on " + getCurrentDateTime() + ")");
            taskCheckBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            taskCheckBox.setFocusPainted(false);
            taskPanel.add(taskCheckBox);
            taskPanel.revalidate();
            taskField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Enter a task first.");
        }
    }

    private void deleteCompletedTasks() {
        Component[] components = taskPanel.getComponents();
        for (int i = components.length - 1; i >= 0; i--) {
            if (components[i] instanceof JCheckBox) {
                JCheckBox cb = (JCheckBox) components[i];
                if (cb.isSelected()) {
                    taskPanel.remove(cb);
                }
            }
        }
        taskPanel.revalidate();
        taskPanel.repaint();
    }

    private String getCurrentDateTime()
    {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() -> new ToDoApp().setVisible(true));
    }
}
