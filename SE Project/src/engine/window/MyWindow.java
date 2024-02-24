package engine.window;

import engine.input.MyKeyListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

public class MyWindow {
    static JFrame frame = new JFrame();
    static Canvas canvas = new Canvas();

    public static void init() {
        canvas.setSize(600, 400);

        frame.add(canvas);
        frame.pack();

        canvas.createBufferStrategy(2);
        canvas.addKeyListener(new MyKeyListener());

        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void close() {
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }

    public static Canvas getCanvas() {
        return canvas;
    }

    public static JFrame getFrame() {
        return frame;
    }
}
