package engine.layers;

import engine.renderer.Renderable;
import engine.input.MyKeyListener;
import engine.updating.Updatable;
import engine.input.InputHandler;

import java.awt.image.BufferStrategy;

public class Layer {
    private String name;
    boolean blockRender = false;
    private Renderable renderController;
    boolean blockUpdate = false;
    private Updatable updatable;
    boolean blockInput = false;
    private InputHandler inputHandler;

    public Layer(Renderable renderController, Updatable updatable, InputHandler inputHandler) {
        this.renderController = renderController;
        this.updatable = updatable;
        this.inputHandler = inputHandler;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean render(BufferStrategy bs) {
        renderController.render(bs);
        return blockRender;
    }

    public boolean update() {
        updatable.update();
        return blockUpdate;
    }

    public boolean input(MyKeyListener.Input input) {
        inputHandler.input(input);
        return blockInput;
    }

    public void setBlocks(boolean blockRender, boolean blockUpdate, boolean blockInput) {
        this.blockRender = blockRender;
        this.blockUpdate = blockUpdate;
        this.blockInput = blockInput;
    }
}
