package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Door_Iron extends  SuperObject{
    GamePanel gp;
    public OBJ_Door_Iron(GamePanel gp){
        this.gp = gp;
        name = "Iron Door";
        try{

            image = ImageIO.read(getClass().getResourceAsStream("/objects/door_iron.png"));

        }catch(
                IOException e) {

            e.printStackTrace();

        }
        collision = true;
    }

}
