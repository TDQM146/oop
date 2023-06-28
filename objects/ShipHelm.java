package objects;

import main.Game;

public class ShipHelm extends GameObject{
    public ShipHelm(int x, int y, int objType) {
        super(x, y, objType);
        doAnimation = true;
        initHitbox(31, 32);

        xDrawOffset = (int) (0 * Game.SCALE);
        yDrawOffset = (int) (0 * Game.SCALE);

    }
    public void update() {
        updateAnimationTick();
    }
}
