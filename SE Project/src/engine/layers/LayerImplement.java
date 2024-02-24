package engine.layers;

import engine.renderer.Renderable;
import engine.updating.Updatable;
import engine.input.InputHandler;

public interface LayerImplement extends Renderable, Updatable, InputHandler {
    Layer getLayer();
}
