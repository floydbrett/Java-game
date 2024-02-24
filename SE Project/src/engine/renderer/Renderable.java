package engine.renderer;

import java.awt.image.BufferStrategy;

public interface Renderable {
    void render(BufferStrategy bs);
}
