package engine.input;

import engine.layers.LayerController;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

public class MyKeyListener implements KeyListener {

    public enum Input {
        START, SELECT,
        A, B,
        UP, DOWN, LEFT, RIGHT
    }

    private static HashMap<Integer, Input> hotkeys = new HashMap<>(){{
        put(KeyEvent.VK_UP, Input.UP);
        put(KeyEvent.VK_LEFT, Input.LEFT);
        put(KeyEvent.VK_DOWN, Input.DOWN);
        put(KeyEvent.VK_RIGHT, Input.RIGHT);

        put(KeyEvent.VK_Z, Input.A);
        put(KeyEvent.VK_X, Input.B);

        put(KeyEvent.VK_SHIFT, Input.SELECT);
        put(KeyEvent.VK_ESCAPE, Input.START);
    }};
    private static HashMap<Input, Boolean> inputs = new HashMap<>();

    public MyKeyListener() {
        for(Input input : Input.values()) {
            inputs.put(input, false);
        }
    }

    private static Input toInput(KeyEvent e) {
       return hotkeys.get(e.getKeyCode());
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(toInput(e) != null) {
            LayerController.layerInput(toInput(e));
            inputs.put(toInput(e), true);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(toInput(e) != null) {
            inputs.put(toInput(e), false);
        }
    }

    public static boolean getInput(Input input) {
        if(inputs == null) return false;
        return inputs.get(input);
    }
}
