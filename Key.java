package objects;

import main.Game;

public class Key extends GameObject{
    public Key(int x, int y, int objType) {
        super(x, y, objType);
        doAnimation = true;
        initHitbox(8, 15);

        xDrawOffset = (int) (8 * Game.SCALE);
        yDrawOffset = (int) (5 * Game.SCALE);


    }
    public void update() {
        updateAnimationTick();
    }

}
