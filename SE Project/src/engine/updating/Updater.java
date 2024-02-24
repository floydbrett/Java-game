package engine.updating;

import engine.utils.LoopThread;
import engine.layers.LayerController;

public class Updater extends LoopThread {

    public Updater() {
        super("engine.updating.Updater");
        setDelta(20);
    }

    @Override
    protected void onInit() {

    }

    @Override
    protected void update() {
        LayerController.layerUpdate();
    }
}
