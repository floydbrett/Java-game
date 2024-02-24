package game.Help;
import engine.input.MyKeyListener;
import engine.layers.Layer;
import engine.layers.LayerImplement;
import engine.window.MyWindow;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.awt.*;
import java.io.*;
import javax.imageio.ImageIO;
import engine.utils.MainMenuLayer;
import engine.layers.LayerController;
import java.awt.image.BufferStrategy;
import java.util.LinkedList;
import javax.swing.ImageIcon;

public class magicLayer implements LayerImplement{
    private final Layer layer = new Layer(this, this, this);

    // menu data
    private String[] buttons;
    private int buttonIndex = 2;
    protected int buttonWidth = 50, buttonHeight = 20, buttonBuffer = 5;
    protected int x;
    protected int y;
    // battle data


    public magicLayer() {
        layer.setName("magicHelper");
        layer.setBlocks(false, true, false);
        buttons = new String[]{"Main", "Ability", "Magic", "Item"};
    }

    @Override
    public void input(MyKeyListener.Input input) {
        switch(input) {
            case UP:
                if(buttonIndex == 0){
                    buttonIndex = buttons.length - 1;
                }
                else{
                    buttonIndex = (buttonIndex - 1) % buttons.length;
                }
                break;
            case LEFT:
            case DOWN:
                buttonIndex = (buttonIndex + 1) % buttons.length;
                break;
            case RIGHT:
            case START:
                LayerController.clearLayers();
                LayerController.pushLayer((new MainMenuLayer()).getLayer());
                break;
            case A:
                buttonPressed(buttons[buttonIndex]);
                break;
        }
    }

    public void buttonPressed(String buttonName) {
        if(buttonName.equals("Main")) {
            LayerController.popLayer();
            LayerController.pushLayer((new HelperLayer()).getLayer());
        }
        else if(buttonName.equals("Ability")) {
            LayerController.popLayer();
            LayerController.pushLayer((new abilityLayer()).getLayer());
        }
        else if(buttonName.equals("Magic")) {
            LayerController.popLayer();
            LayerController.pushLayer((new magicLayer()).getLayer());
        }
        else if(buttonName.equals("Item")) {
            LayerController.popLayer();
            LayerController.pushLayer((new itemLayer()).getLayer());
        }
    }

    @Override
    public void render(BufferStrategy bs) {
        Graphics2D g = (Graphics2D) bs.getDrawGraphics();

        int winWidth = MyWindow.getCanvas().getWidth();
        int winHeight = MyWindow.getCanvas().getHeight();
        g.setColor(Color.GRAY);
        g.fillRect(0,0, MyWindow.getCanvas().getWidth(), MyWindow.getCanvas().getHeight());
        g.setColor(Color.WHITE);
        Font menuFont = new Font("arial", Font.BOLD, 50);
        g.setFont(menuFont);
        g.drawString("Magic", winWidth - 380, winHeight - 350);
        g.setColor(Color.WHITE);
        

        BufferedImage buffed_image1 = null;
        try{
            buffed_image1 =  ImageIO.read(new File("src/game/Character_Images/alien3.png"));
            Image image_image1 = buffed_image1.getScaledInstance(80, 80, Image.SCALE_DEFAULT);
            g.drawImage(image_image1, winWidth - 560, winHeight- 83 ,null);
        } catch (Exception e){

        }

        BufferedImage buffed_image2 = null;
        try{
            buffed_image2 =  ImageIO.read(new File("src/game/Character_Images/alien4.png"));
            Image image_image2 = buffed_image2.getScaledInstance(80, 80, Image.SCALE_DEFAULT);
            g.drawImage(image_image2, winWidth - 410, winHeight- 83 ,null);
        } catch (Exception e){

        }

        BufferedImage buffed_image3 = null;
        try{
            buffed_image3 =  ImageIO.read(new File("src/game/Character_Images/bluealien.png"));
            Image image_image3 = buffed_image3.getScaledInstance(80, 80, Image.SCALE_DEFAULT);
            g.drawImage(image_image3, winWidth - 260, winHeight- 83 ,null);
        } catch (Exception e){

        }

        BufferedImage buffed_image4 = null;
        try{
            buffed_image4 =  ImageIO.read(new File("src/game/Character_Images/greenalien.png"));
            Image image_image1 = buffed_image4.getScaledInstance(80, 80, Image.SCALE_DEFAULT);
            g.drawImage(image_image1, winWidth - 110, winHeight- 83 ,null);
        } catch (Exception e){

        }
        
        

        Font menuFont2 = new Font("arial", Font.BOLD, 20);
        g.setFont(menuFont2);
        g.drawString("Magic Shield", winWidth - 595, winHeight - 280);
        g.drawString("Fireball", winWidth - 595, winHeight - 245);
        g.drawString("Lightning", winWidth - 595, winHeight - 210);
        g.drawString("", winWidth - 595, winHeight - 120);
        
        Font menuFont3 = new Font("arial", Font.BOLD, 10);
        g.setFont(menuFont3);
        g.drawString("Using Magic Shield blocks an amount of the damage done by enemy attacks for one turn", winWidth - 585, winHeight - 270);
        g.drawString("Fireball attack does damage to the target and has a chance toi set the target on fire for multiple turns", winWidth - 585, winHeight - 235);
        g.drawString("Lightning attack electrocutes enemy and does damage based on character", winWidth - 585, winHeight - 198);

        for(int i = 0; i < buttons.length; i++) {
            g.setColor(buttonIndex ==i?Color.lightGray:Color.BLACK);
            g.fillRect(x, y + (buttonHeight + buttonBuffer)*i, buttonWidth, buttonHeight);
            g.setColor(Color.WHITE);
            g.drawString(buttons[i], x, y + (buttonHeight + buttonBuffer)*i + g.getFont().getSize());
        }  
        g.dispose();    
    }

    @Override
    public void update() {

    }

    @Override
    public Layer getLayer() {
        return layer;
    }
}
