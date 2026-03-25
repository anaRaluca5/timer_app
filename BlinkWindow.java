package app;

import javax.swing.*;
import java.awt.*;

public class BlinkWindow extends JFrame{
    private final Color selectedColor;
    private final int blinkDelay;
    private Timer blinkTimer;
    private boolean isOriginalColor= true;

    public BlinkWindow(Color selectedColor, int blinkDelay){
        this.selectedColor= selectedColor;
        this.blinkDelay= blinkDelay;

        setTitle("Blink Window");
        setSize(300,200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        getContentPane().setBackground(selectedColor);
    }

    public void startBlinking(){
        setVisible(true);

        blinkTimer= new Timer(blinkDelay, e ->{
            if (isOriginalColor)
                getContentPane().setBackground(Color.WHITE);
            else
                getContentPane().setBackground(selectedColor);
            isOriginalColor= !isOriginalColor;
        });
        blinkTimer.start();
    }

    public void stopBlinking(){
        if (blinkTimer != null){
            blinkTimer.stop();
        }
        setVisible(false);
        dispose();
    }
}
