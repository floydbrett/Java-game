package engine.layers;

import engine.input.MyKeyListener;

import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;

public class LayerController {
    private static LinkedList<Layer> layers = new LinkedList<>();

    public static void layerUpdate() {
        boolean cont = true;
        Iterator<Layer> it = getCopyIterator();
        while(it.hasNext() && cont) {
            Layer l = it.next();
            cont = !l.update();
        }
    }

    public static void layerRender(BufferStrategy bs) {
        Stack<Layer> stack = new Stack<>();
        Iterator<Layer> it = getCopyIterator();
        boolean cont = true;
        while(it.hasNext() && cont) {
            Layer l = it.next();
            stack.push(l);
            cont = !l.blockRender;
        }
        while(!stack.empty()) {
            stack.pop().render(bs);
        }
    }

    public static void layerInput(MyKeyListener.Input input) {
        boolean cont = true;
        Iterator<Layer> it = getCopyIterator();
        while(it.hasNext() && cont) {
            Layer l = it.next();
            cont = !l.input(input);
        }
    }

    public static void printLayers() {
        int i = 1;
        System.out.println("--- Layers ---");
        for (Layer l : layers) {
            System.out.println(i++ + ": " + l.getName());
        }
        System.out.println();
    }

    public static void pushLayer(Layer layer) {
        layers.push(layer);
        printLayers();
    }

    public static Layer popLayer() {
        Layer ret = layers.pop();
        printLayers();
        return ret;
    }

    public static ArrayList<Layer> popLayers(int num) {
        ArrayList<Layer> out = new ArrayList<>();
        for(int i = 0; i < num; i++) {
            out.add(popLayer());
        }
        return out;
    }

    public static void clearLayers() {
        Iterator<Layer> it = getCopyIterator();
        while(it.hasNext()) {
            it.next();
            popLayer();
        }
    }

    private static Iterator<Layer> getCopyIterator() {
        LinkedList<Layer> c = new LinkedList<>(layers);
        return c.iterator();
    }

    private static Iterator<Layer> getReverseCopyIterator() {
        LinkedList<Layer> c = new LinkedList<>(layers);
        return c.descendingIterator();
    }

    private static Iterator<Layer> getCopyIterator(Iterator<Layer> beg, Iterator<Layer> end) {
        LinkedList<Layer> c = new LinkedList<>();
        while(beg.hasNext() && !beg.equals(end)) {
            Layer l = beg.next();
            c.push(l);
        }
        return c.iterator();
    }

    private static Iterator<Layer> getReverseCopyIterator(Iterator<Layer> beg, Iterator<Layer> end) {
        LinkedList<Layer> c = new LinkedList<>();
        while(beg.hasNext() && !beg.equals(end)) {
            Layer l = beg.next();
            c.push(l);
        }
        return c.descendingIterator();
    }
}
