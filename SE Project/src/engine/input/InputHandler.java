package engine.input;

public interface InputHandler {

    /**
     * Returns true to block an input from propagating through next layers.
     */
    void input(MyKeyListener.Input input);
}
