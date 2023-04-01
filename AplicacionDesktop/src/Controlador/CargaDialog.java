package Controlador;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class CargaDialog extends JPanel {
    private static final long serialVersionUID = 1L;
    private int angle = 0;
    private Timer timer;
    private String message = "";

    public CargaDialog(String message) {
        super();
        setPreferredSize(new Dimension(200, 200));
        setOpaque(false);
        this.message = message;
        timer = new Timer(50, (e) -> {
            angle += 5;
            if (angle == 360) {
                angle = 0;
            }
            repaint();
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.BLUE);
        g2d.drawArc(25, 25, 150, 150, angle, 90);
        g2d.setColor(new Color(0, 0, 0, 50));
        g2d.fillArc(25, 25, 150, 150, angle, 90);
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Verdana", Font.BOLD, 14));
        g2d.drawString(message, 55, 140);
        g2d.dispose();
    }

    public void stopAnimation() {
        timer.stop();
    }

 
}

    
    

