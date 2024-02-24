package game.Help;
import engine.input.MyKeyListener;
import engine.layers.Layer;
import engine.layers.LayerImplement;
import engine.window.MyWindow;
import engine.utils.MainMenuLayer;

import java.awt.image.BufferedImage;
import java.awt.Image;
import java.awt.*;
import java.io.*;
import javax.imageio.ImageIO;
import engine.layers.LayerController;
import engine.layers.LayerController;
import java.awt.image.BufferStrategy;
import java.util.LinkedList;
import javax.swing.ImageIcon;

public class HelperLayer implements LayerImplement{
    private final Layer layer = new Layer(this, this, this);

    // menu data
    private String[] buttons;
    private int buttonIndex = 0;
    protected int buttonWidth = 50, buttonHeight = 20, buttonBuffer = 5;
    protected int x;
    protected int y;

    // battle data
    

    public HelperLayer() {
        layer.setName("Helper");
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
        g.drawString("Game Instructions", winWidth - 520, winHeight - 350);
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
        g.drawString("Rules", winWidth - 595, winHeight - 285);
        g.drawString("Goal", winWidth - 595, winHeight - 235);
        g.drawString("Controls", winWidth - 595, winHeight - 200);
        g.drawString("Flee", winWidth - 595, winHeight - 120);

        Font menuFont3 = new Font("arial", Font.BOLD, 10);
        g.setFont(menuFont3);
        g.drawString("You have the ability to make 1 action of three different options per turn", winWidth - 585, winHeight - 275);
        g.drawString("If your characters health bar goes to 0 you lose the game", winWidth - 585, winHeight - 265);
        g.drawString("Once an enemy is defeated you will advance to the next level of the game", winWidth - 585, winHeight - 255);
        g.drawString("Survive through the levels without losing all of your characters health", winWidth - 585, winHeight - 225);
        g.drawString("Press Z select buttons and menu options", winWidth - 585, winHeight - 190);
        g.drawString("Press ESC to go to pause menu", winWidth - 585, winHeight - 180);
        g.drawString("Press Right Directional Key", winWidth - 585, winHeight - 170);
        g.drawString("Press Left Directional Key", winWidth - 585, winHeight - 160);
        g.drawString("Press Up Directional Key", winWidth - 585, winHeight - 150);
        g.drawString("Press Down Directional Key ", winWidth - 585, winHeight - 140);
        g.drawString("When a user chooses to flee from the battle the player has a probabile chance of escaping from the battle based ", winWidth - 585, winHeight - 110);
        g.drawString("on current player health. If the player escape from the battle then they get to redo the level without penalty. If the ", winWidth - 585, winHeight - 100);
        g.drawString("player dies while fleeing then the player loses the level. ", winWidth - 585, winHeight - 90);

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
