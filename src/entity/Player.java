package entity;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;
import tile.TileManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends  Entity {

    KeyHandler keyH;
    public final int screenX;
    public final int screenY;
    public boolean isDead = false;


    public Player(GamePanel gp, KeyHandler keyH){
        super(gp);
        this.keyH = keyH;
        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;
        TileManager t = new TileManager(gp);


        setDefaultValues();
        getPlayerImage();
    }
    public void setDefaultValues(){
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down";

        //player status

        maxLife = 6;
        life = maxLife;

    }

    public void getPlayerImage(){

        up1 = setup("/player/boy_up_1");
        up2 = setup("/player/boy_up_2");
        down1 = setup("/player/boy_down_1");
        down2 = setup("/player/boy_down_2");
        down1Breath = setup("/player/boy_down_1_breath");
        down2Breath = setup("/player/boy_down_2_breath");
        left1 = setup("/player/boy_left_1");
        left2 = setup("/player/boy_left_2");
        right1 = setup("/player/boy_right_1");
        right2 = setup("/player/boy_right_2");

    }

    public BufferedImage setup(String imagePath){
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try{
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);

        }
        catch (IOException e){
            e.printStackTrace();
        }
        return image;
    }

    public void update() {
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            // Handle movement
            if (keyH.upPressed) {
                direction = "up";
            }
            if (keyH.downPressed) {
                direction = "down";
            }
            if (keyH.leftPressed) {
                direction = "left";
            }
            if (keyH.rightPressed) {
                direction = "right";
            }

            //check tile collision
            collisionOn = false;
            gp.cChecker.checkTile(this);

            //check object collision
           int objIndex =  gp.cChecker.checkObject(this,true);
           pickUpObject(objIndex);

           //check npc collision
            int npcIndex = gp.cChecker.checkEntity(this,gp.npc);
            interactNPC(npcIndex);

            // Check event

            gp.eHandler.checkEvent();
            // Player dead
            if (life <= 0 && !isDead) {
                gp.gameState = gp.dialogueState;
                gp.ui.currentDialogue ="You died\n Game Over";
                isDead = true;
//                gp.gameState = gp.titleState;

            }
            //if collision is false, player can move
            if(collisionOn == false){
                switch (direction){
                    case "up" : worldY -= speed;
                        break;

                    case "down": worldY += speed;
                        break;

                    case "left": worldX -= speed;
                        break;

                    case "right": worldX +=speed;
                        break;
                }
            }

            // Walking animation logic
            spriteCounter++;
            if (spriteCounter > 12) { // Adjust for desired speed
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
    }

    public void pickUpObject(int i){

        if(i !=999){

            }
        }

public void interactNPC(int i){
    if(i !=999){
//        if(gp.keyH.enterPressed){
//            gp.gameState = gp.playState;
//            dialogueIndex=0;
//        }
//        gp.keyH.enterPressed = false;
        if(gp.keyH.spacePressed){
            gp.gameState = gp.dialogueState;
            gp.npc[i].speak();
        }
        gp.keyH.spacePressed = false;

    }

}

    public void draw(Graphics g2) {
        BufferedImage image = null;

        switch (direction) {
            case "up":
                if (spriteNum == 1) {
                    image = up1;
                } else {
                    image = up2;
                }
                break;

            case "down":
                // Load breathing animation only when idle
                if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
                    if (spriteNum == 1) {
                        image = down1; // Walking frame 1
                    } else {
                        image = down2; // Walking frame 2
                    }
                } else {
                    // Idle breathing frames
                    if (spriteNum == 1) {
                        image = down1;
                    } else {
                        image = down1Breath;
                    }
                }
                break;

            case "left":
                if (spriteNum == 1) {
                    image = left1;
                } else {
                    image = left2;
                }
                break;

            case "right":
                if (spriteNum == 1) {
                    image = right1;
                } else {
                    image = right2;
                }
                break;
        }
        int x = screenX;
        int y = screenY;

        if(screenX > worldX){
            x = worldX;
        }

        if(screenY > worldY){
            y = worldY;
        }
        int rightOffset = gp.screenWidth - screenX;
        if(rightOffset > gp.worldWidth - worldX){
            x = gp.screenWidth  - (gp.worldWidth -worldX);
        }

        int bottomOffset = gp.screenHeight - screenY;
        if(bottomOffset > gp.worldHeight - worldY){
            y = gp.screenHeight  - (gp.worldHeight - worldY);
        }
        g2.drawImage(image, x, y, null);
    }
}
