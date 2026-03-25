package app;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            String[] options= {"Settings", "Close"};

            int choice= JOptionPane.showOptionDialog(
                    null,
                    "Choose option",
                    "Timer App",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            if (choice== 0)
                new MainFrame();
            else
                System.exit(0);
                }
        );
    }
}
