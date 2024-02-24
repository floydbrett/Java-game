package engine.utils;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

/** --------------------------------------------------------------------------------------------------------------------
 * Description:
 *
 -------------------------------------------------------------------------------------------------------------------- */
public abstract class LoopThread implements Runnable {

    /*=============================================== VARIABLES ======================================================*/

    private static final CountDownLatch globalLatch = new CountDownLatch(1);
    private final CountDownLatch latch = new CountDownLatch(1);
    private final Thread thread = new Thread(this);
    private int delta = 60;
    private AtomicBoolean running = new AtomicBoolean(true),
            paused = new AtomicBoolean(false);
    private Logger logger = Logger.getLogger(getClass().toString());
    private Level level = Level.FINE;

    /*============================================== CONSTRUCTORS ====================================================*/
    public LoopThread(String name) {
        thread.setName(name);
        init();
    }
    /*================================================ METHODS =======================================================*/

    protected abstract void onInit();
    protected abstract void update();

    /** ----------------------------------------------------------------------------------------------------------------
     * Calls an implementation's onInit() and starts the thread
     ---------------------------------------------------------------------------------------------------------------- */
    private void init() {
        onInit();

        latch.countDown();
        thread.start();
    }

    /** ----------------------------------------------------------------------------------------------------------------
     * Waits on the global latch and then runs the loop
     ---------------------------------------------------------------------------------------------------------------- */
    public void run() {
        try { globalLatch.await(); } catch (InterruptedException e) { e.printStackTrace(); }

        long lastTime, time;

        while(running.get())
            try {
                if(!paused.get()) {
                    // time the update
                    lastTime = System.currentTimeMillis();
                    update();
                    time = System.currentTimeMillis();

                    // wait until appropriate time has passed for the given delta
                    long dist = (1000 / delta) - (time - lastTime);
                    if(dist > 0) {
                        Thread.sleep(dist);
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                setRunning(false);
            }
        logger.log(level, "Exiting thread "+thread.getName());
    }

    public void stop() {
        logger.log(level, "Stopping thread "+thread.getName());
        setRunning(false);
    }

    /*=============================================== FUNCTIONS ======================================================*/

    public static void startLoops() {
        globalLatch.countDown();
    }

    /*============================================ GETTERS / SETTERS =================================================*/

    public int getDelta() {
        return delta;
    }

    public Thread getThread() {
        return thread;
    }

    public CountDownLatch getLatch() {
        return latch;
    }

    public boolean isRunning() {
        return running.get();
    }

    public boolean isPaused() {
        return paused.get();
    }

    public synchronized void setDelta(int delta) {
        this.delta = delta;
    }

    public synchronized void setRunning(boolean running) {
        logger.log(level, "Set running to "+running+" in thread "+thread.getName());
        this.running.set(running);
    }

    public synchronized void setPaused(boolean paused) {
        logger.log(level, "Set paused to "+paused+" in thread "+thread.getName());
        this.paused.set(paused);
    }

}
