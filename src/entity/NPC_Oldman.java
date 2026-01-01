package entity;

import main.GamePanel;

import java.awt.*;
import java.util.Random;

public class NPC_Oldman extends  Entity {
    public NPC_Oldman(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 1;
        getImage();
        setDialogue();
    }

    public void getImage() {

        up1 = setup("/npc/oldman_up_1");
        up2 = setup("/npc/oldman_up_2");
        down1 = setup("/npc/oldman_down_1");
        down2 = setup("/npc/oldman_down_2");
        left1 = setup("/npc/oldman_left_1");
        left2 = setup("/npc/oldman_left_2");
        right1 = setup("/npc/oldman_right_1");
        right2 = setup("/npc/oldman_right_2");


    }
    public void setDialogue(){
        dialogues[0] = "Greetings young traveler. \nWhat brings you to these lands?";
        dialogues[1] = "Oh, i apologize for asking.";
        dialogues[2] = "I am but an old man who is too \ncurious.";
    }

    public void setAction() {
     super.setAction();
    }
    public void speak(){
      super.speak();
    }

}
