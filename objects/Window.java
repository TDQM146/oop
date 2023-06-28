package objects;

import main.Game;

public class Window extends GameObject{

    public Window(int x, int y, int objType) {
        super(x, y, objType);
        doAnimation = true;
        initHitbox(32, 32);

        xDrawOffset = (int) (0 * Game.SCALE);
        yDrawOffset = (int) (0 * Game.SCALE);

    }
    public void update() {
        updateAnimationTick();
    }
}
