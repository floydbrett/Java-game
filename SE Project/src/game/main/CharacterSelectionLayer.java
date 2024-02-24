package game.main;
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

public class CharacterSelectionLayer implements LayerImplement{
    private final Layer layer = new Layer(this, this, this);

    // menu data
    private String[] buttons;
    private int buttonIndex = 0;
    protected int buttonWidth = 60, buttonHeight = 20, buttonBuffer = 5;
    protected int x;
    protected int y;

    // battle data
    

    public CharacterSelectionLayer() {
        layer.setName("CharacterSelectionLayer");
        layer.setBlocks(false, true, false);
        buttons = new String[]{"Character1", "Character2", "Character3", "Character4"};
    }

    @Override
    public void input(MyKeyListener.Input input) {
        switch(input) {
            case UP:
            case LEFT:
                if(buttonIndex == 0){
                    buttonIndex = buttons.length - 1;
                }
                else{
                    buttonIndex = (buttonIndex - 1) % buttons.length;
                }
            break;
            case DOWN:
            case RIGHT:
                buttonIndex = (buttonIndex + 1) % buttons.length;
                break;
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
        //Change Main Character Class images
        if(buttonName.equals("Character1")) {
            
        }
        else if(buttonName.equals("Character2")) {
            
        }
        else if(buttonName.equals("Character3")) {
            
        }
        else if(buttonName.equals("Character4")) {
            
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
        Font menuFont = new Font("Courier", Font.BOLD, 50);
        g.setFont(menuFont);
        g.drawString("Character Selection", (winWidth / 2) - 285, winHeight - 350);

        BufferedImage buffed_image1 = null;
        try{
            buffed_image1 =  ImageIO.read(new File("src/game/Character_Images/Character1.png"));
            Image image_image1 = buffed_image1.getScaledInstance(80, 80, Image.SCALE_DEFAULT);
            g.drawImage(image_image1, winWidth - 560, winHeight- 145 ,null);
        } catch (Exception e){

        }

        BufferedImage buffed_image2 = null;
        try{
            buffed_image2 =  ImageIO.read(new File("src/game/Character_Images/Character2.png"));
            Image image_image2 = buffed_image2.getScaledInstance(80, 80, Image.SCALE_DEFAULT);
            g.drawImage(image_image2, winWidth - 410, winHeight- 145 ,null);
        } catch (Exception e){

        }

        BufferedImage buffed_image3 = null;
        try{
            buffed_image3 =  ImageIO.read(new File("src/game/Character_Images/Character3.png"));
            Image image_image3 = buffed_image3.getScaledInstance(80, 80, Image.SCALE_DEFAULT);
            g.drawImage(image_image3, winWidth - 260, winHeight- 145 ,null);
        } catch (Exception e){

        }

        BufferedImage buffed_image4 = null;
        try{
            buffed_image4 =  ImageIO.read(new File("src/game/Character_Images/Character4.png"));
            Image image_image1 = buffed_image4.getScaledInstance(80, 80, Image.SCALE_DEFAULT);
            g.drawImage(image_image1, winWidth - 110, winHeight- 145 ,null);
        } catch (Exception e){

        }

        g.setFont(new Font("arial", Font.BOLD, 10));
        for(int i = 0; i < buttons.length; i++) {
            g.setColor(buttonIndex ==i?Color.RED:Color.BLACK);
            g.fillRect(x + 50 + (i * 150), y + 340, buttonWidth, buttonHeight);
            g.setColor(Color.WHITE);
            g.drawString(buttons[i], x + 53 + (i * 150), y + 355);
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
