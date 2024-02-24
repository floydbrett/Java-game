package engine.renderer;

import engine.layers.LayerController;
import engine.utils.LoopThread;
import engine.window.MyWindow;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Renderer extends LoopThread {

    public Renderer() {
        super("engine.rendering.Renderer");
    }

    @Override
    protected void onInit() {
        MyWindow.init();
        setDelta(30);
    }

    @Override
    protected void update() {
        BufferStrategy bs = MyWindow.getCanvas().getBufferStrategy();
        
        Graphics2D g = (Graphics2D) bs.getDrawGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0,0, MyWindow.getCanvas().getWidth(), MyWindow.getCanvas().getHeight());
        g.dispose();

        LayerController.layerRender(bs);
        bs.show();
    }
}
