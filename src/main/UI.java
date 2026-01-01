package main;

import object.OBJ_Heart;
import object.SuperObject;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UI {

    GamePanel gp;
    Graphics2D g2;
    Font roboto_40, roboto_80B;
    BufferedImage heart_full, heart_blank, heart_half;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameOver = false;
    public String currentDialogue = "";
    public int commandNum = 0;

    public UI(GamePanel gp) {
        this.gp = gp;
        roboto_40 = new Font("Roboto", Font.PLAIN, 40);
        roboto_80B = new Font("Roboto", Font.BOLD, 80);

        //CREATE HUD OBJECT
        SuperObject heart = new OBJ_Heart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {

        this.g2 = g2;

        g2.setFont(roboto_40);
        g2.setColor(Color.white);

        if(gp.gameState == gp.titleState){
            drawTitleScreen();
        }
        //play state
        if (gp.gameState == gp.playState) {
            drawPlayerLife();
        }
        //pause state
        if (gp.gameState == gp.pauseState) {
            drawPauseScreen();
            drawPlayerLife();

        }

        if(gp.gameState == gp.dialogueState){
            drawDialogueScreen();
            drawPlayerLife();

        }
    }
    public void drawPlayerLife(){

        //Draw Max Life
        int x  = gp.screenWidth - gp.tileSize*4;
        int y = gp.tileSize/2;
        int i = 0;
        while(i <gp.player.maxLife/2){
            g2.drawImage(heart_blank,x,y,null);
            i++;
            x+=gp.tileSize;
        }

        //reset
        x  = gp.screenWidth - gp.tileSize*4;
        y = gp.tileSize/2;
        i = 0;

        //DRAW CURRENT LIFE
        while (i<gp.player.life){
            g2.drawImage(heart_half,x,y,null);
            i++;
            if(i < gp.player.life){
                g2.drawImage(heart_full,x,y,null);
            }
            i++;
            x+=gp.tileSize;
        }

    }
    public void drawTitleScreen() {

        g2.setColor(new Color(70,120,80));
        g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);
        // Title name
        String text = "Blue Boy Adventure";
        float fontSize = 94F; // Start with a large font size
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, fontSize));

        // Reduce the font size if the text is wider than the screen
        int textWidth = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        while (textWidth > gp.screenWidth - 20) { // 20px padding
            fontSize -= 2F; // Reduce font size
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, fontSize));
            textWidth = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        }

        int x = getXForCenteredText(text);
        int y = gp.tileSize * 3;

        //Shadow
        g2.setColor(Color.black);
        g2.drawString(text,x+5,y+5);

        g2.setColor(Color.white);
        g2.drawString(text, x, y);

        //blue boy image

        x = gp.screenWidth/2 -(gp.tileSize*2)/2;
        y +=gp.tileSize*2;
        g2.drawImage(gp.player.down1,x,y,gp.tileSize*2,gp.tileSize*2,null);

        //menu
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,48F));
        //new game
        text = "NEW GAME";
        x = getXForCenteredText(text);
        y=+gp.tileSize*9;
        g2.drawString(text,x,y);
        if(commandNum == 0){
            g2.drawString(">",x-gp.tileSize,y );
        }


        text = "LOAD GAME";
        x = getXForCenteredText(text);
        y=+gp.tileSize*10;
        g2.drawString(text,x,y);
        if(commandNum == 1){
            g2.drawString(">",x-gp.tileSize,y );
        }

        text = "QUIT";
        x = getXForCenteredText(text);
        y=+gp.tileSize*11;
        g2.drawString(text,x,y);
        if(commandNum == 2){
            g2.drawString(">",x-gp.tileSize,y );
        }

    }
    public void drawMenu(){
        // function to draw the menu so it can also be used in the Pause state
    }



    public void drawPauseScreen(){
        g2.setFont(roboto_80B);
        String text = "Paused";
        int x = getXForCenteredText(text);
        int y = gp.screenHeight/2;
        g2.drawString(text, x,y);
    }
    public int getXForCenteredText(String text){
        int length = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
        return gp.screenWidth/2 - length/2;
    }

    public void drawDialogueScreen(){

        //window
        int x = gp.tileSize*2;
        int y = gp.tileSize/2;
        int width = gp.screenWidth - (gp.tileSize*4);
        int height = gp.tileSize*3;

        drawSubWindow(x,y,width,height);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,28F));
        x+=gp.tileSize;
        y+=gp.tileSize;
        for(String line : currentDialogue.split("\n")){
            g2.drawString(line,x,y);
            y+=40;
        }
    }

    public void drawSubWindow(int x,int y, int width, int height){

        Color black = new Color(0,0,0,200);
        g2.setColor(black);
        g2.fillRoundRect(x,y,width,height,35,35);
        Color white = new Color(255,255,255);
        g2.setColor(white);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5,y+5,width-10,height-10,25,25);
    }
}



