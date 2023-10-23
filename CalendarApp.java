package calender;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.Calendar;
import java.util.HashMap;

public class CalendarApp {
    private JFrame frame;
    private JPanel calendarPanel;
    private JTextArea reminderTextArea;
    private HashMap<String, String> reminders;

    public CalendarApp() {
        frame = new JFrame("Calendar with Reminders and Birthdays");
        frame.setLayout(new BorderLayout());

        reminders = new HashMap<String, String>();

        calendarPanel = new JPanel(new GridLayout(6, 7));
        frame.add(calendarPanel, BorderLayout.CENTER);

        JPanel reminderPanel = new JPanel();
        reminderPanel.setLayout(new BoxLayout(reminderPanel, BoxLayout.Y_AXIS));

        JLabel reminderLabel = new JLabel("Reminders:");
        reminderTextArea = new JTextArea(10, 20);
        reminderTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(reminderTextArea);
        JButton addButton = new JButton("Add Reminder/Birthday");

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String date = JOptionPane.showInputDialog("Enter Date (MM/DD):");
                String reminder = JOptionPane.showInputDialog("Enter Reminder/Birthday:");
                reminders.put(date, reminder);
                updateCalendar();
            }
        });

        reminderPanel.add(reminderLabel);
        reminderPanel.add(scrollPane);
        reminderPanel.add(addButton);

        frame.add(reminderPanel, BorderLayout.EAST);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        updateCalendar();
    }

    private void updateCalendar() {
        calendarPanel.removeAll();
        String[] daysOfWeek = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (String day : daysOfWeek) {
            JLabel label = new JLabel(day);
            label.setHorizontalAlignment(JLabel.CENTER);
            calendarPanel.add(label);
        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        int month = calendar.get(Calendar.MONTH);
        while (calendar.get(Calendar.MONTH) == month) {
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            final JButton button = new JButton(Integer.toString(day));
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String selectedDate = button.getText();
                    String reminder = reminders.get(month + 1 + "/" + selectedDate);
                    if (reminder == null) {
                        reminder = "No reminders/birthdays for this date.";
                    }
                    reminderTextArea.setText(reminder);
                }
            });
            calendarPanel.add(button);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        frame.revalidate();
        frame.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new CalendarApp();
            }
        });
    }
}
