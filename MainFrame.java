package app;

import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class MainFrame extends JFrame{
    private JRadioButton onTimeRadio;
    private JRadioButton countdownRadio;

    private JTextField onTimeField;
    private JTextField countdownField;

    private JButton chooseColorButton;
    private JLabel colorPreviewLabel;

    private JComboBox<String> speedComboBox;

    private JButton startButton;
    private JButton stopButton;

    private Color selectedColor= Color.BLUE;

    private Timer mainTimer;

    private BlinkWindow blinkWindow;

    private JLabel currentTimeLabel;

    private Timer clockTimer;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    private JButton stopClockButton;
    private JButton restartClockButton;

    public MainFrame(){
        initComponents();
        layoutComponents();
        addActions();
        startClock();

        setTitle("Timer Settings");
        setSize(520, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void initComponents(){
        onTimeRadio= new JRadioButton("On time(HH:mm:ss)");
        countdownRadio = new JRadioButton("Countdown (sec)");

        ButtonGroup group = new ButtonGroup();
        group.add(onTimeRadio);
        group.add(countdownRadio);

        onTimeRadio.setSelected(true);

        onTimeField = new JTextField(10);
        countdownField = new JTextField(10);
        countdownField.setEnabled(false);

        chooseColorButton = new JButton("Choose Color");

        colorPreviewLabel = new JLabel("RGB: 0, 0, 255");
        colorPreviewLabel.setOpaque(true);
        colorPreviewLabel.setBackground(selectedColor);
        colorPreviewLabel.setForeground(Color.WHITE);
        colorPreviewLabel.setPreferredSize(new Dimension(140,25));
        colorPreviewLabel.setBorder(BorderFactory.createLineBorder(Color.black));

        speedComboBox= new JComboBox<>(new String[]{
                "1000 ms",
                "2000 ms",
                "3000 ms",
                "4000 ms",
                "5000 ms"
        });

        startButton = new JButton("Start Countdown");
        stopButton = new JButton("Stop");

        stopClockButton= new JButton("Stop Clock");
        restartClockButton= new JButton("Restart Clock");

        currentTimeLabel= new JLabel("Current Time: 00:00:00");
        currentTimeLabel.setFont(new Font("Arial", Font.BOLD, 16));
    }

    private void layoutComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(currentTimeLabel, gbc);

        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(onTimeRadio, gbc);

        gbc.gridx = 1;
        panel.add(onTimeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(countdownRadio, gbc);

        gbc.gridx = 1;
        panel.add(countdownField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(chooseColorButton, gbc);

        gbc.gridx = 1;
        panel.add(colorPreviewLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Blink speed: "), gbc);

        gbc.gridx = 1;
        panel.add(speedComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(startButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        panel.add(stopButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(stopClockButton, gbc);

        gbc.gridx = 1;
        panel.add(restartClockButton, gbc);

        add(panel);
    }

    private void addActions(){
        onTimeRadio.addActionListener(e -> {
            onTimeField.setEnabled(true);
            countdownField.setEnabled(false);
            countdownField.setText("");
        });

        countdownRadio.addActionListener(e -> {
            onTimeField.setEnabled(false);
            countdownField.setEnabled(true);
            onTimeField.setText("");
        });

        chooseColorButton.addActionListener(e -> {
            Color chosenColor= JColorChooser.showDialog(
                    this,
                    "Choose Color",
                    selectedColor
            );

            if (chosenColor != null) {
                selectedColor = chosenColor;
                colorPreviewLabel.setBackground(selectedColor);
                colorPreviewLabel.setText(
                        "RGB: " +
                                selectedColor.getRed()+ ", "+
                                selectedColor.getGreen()+ ", "+
                                selectedColor.getBlue()
                );
                if(selectedColor.getRed()+ selectedColor.getGreen()+ selectedColor.getBlue()< 382){
                    colorPreviewLabel.setForeground(Color.WHITE);
                } else{
                    colorPreviewLabel.setForeground(Color.BLACK);
                }
            }
        });

        startButton.addActionListener(e -> startTimer());

        stopButton.addActionListener(e -> {
            if (mainTimer != null) {
                mainTimer.stop();
            }

            if(blinkWindow != null){
                blinkWindow.stopBlinking();
            }

            setControlsEnabled(true);
        });

        stopClockButton.addActionListener(e -> {
            if (clockTimer != null) {
                clockTimer.stop();
            }
        });

        restartClockButton.addActionListener(e -> {
            if (clockTimer != null) {
                clockTimer.start();
            }
        });
    }

    private boolean toggleColor= true;

    private void startClock(){
        clockTimer= new Timer(1000, e -> {
            String currentTime= LocalTime.now().format(formatter);
            currentTimeLabel.setText("Current Time: " + currentTime);

            if (toggleColor){
                currentTimeLabel.setForeground(selectedColor);
            } else{
                currentTimeLabel.setForeground(Color.RED);
            }

            toggleColor= !toggleColor;
        });

        clockTimer.start();
    }

    private void startTimer(){
        int delay;

        try{
            if(countdownRadio.isSelected()){
                int seconds= Integer.parseInt(countdownField.getText());
                if (seconds <= 0) throw new NumberFormatException();

                delay= seconds * 1000;
            } else{
                String timeText= onTimeField.getText();
                String[] parts= timeText.split(":");

                if(parts.length != 3)
                    throw new Exception();

                int h= Integer.parseInt(parts[0]);
                int m= Integer.parseInt(parts[1]);
                int s= Integer.parseInt(parts[2]);

                java.time.LocalTime now= java.time.LocalTime.now();
                java.time.LocalTime target= java.time.LocalTime.of(h,m,s);

                delay= (int) java.time.Duration.between(now, target).toMillis();

                if(delay< 0)
                    delay += 24 * 60 * 60 * 1000; //ziua urmatoare
            }
        } catch(Exception e){
            JOptionPane.showMessageDialog(this, "Invalid input!");
            return;
        }

        //dezactivam controalele
        setControlsEnabled(false);

        mainTimer= new Timer(delay, e -> {
            int blinkDelay= Integer.parseInt(
                    speedComboBox.getSelectedItem().toString().replace(" ms", "")
            );

            blinkWindow= new BlinkWindow(selectedColor, blinkDelay);
            blinkWindow.startBlinking();
        });

        mainTimer.setRepeats(false);
        mainTimer.start();
    }

    private void setControlsEnabled(boolean enabled){
        onTimeRadio.setEnabled(enabled);
        countdownRadio.setEnabled(enabled);
        onTimeField.setEnabled(enabled && onTimeRadio.isSelected());
        countdownField.setEnabled(enabled && countdownRadio.isSelected());
        chooseColorButton.setEnabled(enabled);
        speedComboBox.setEnabled(enabled);
        startButton.setEnabled(enabled);
    }

}
