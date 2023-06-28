package objects;

import main.Game;

public class CandleLight extends GameObject{
    public CandleLight(int x, int y, int objType) {
        super(x, y, objType);
        doAnimation = true;
        initHitbox(32, 32);

        xDrawOffset = (int) (0 * Game.SCALE);
        yDrawOffset = (int) (0 * Game.SCALE);

        hitbox.x -= (int) (6 * Game.SCALE);
        hitbox.y += (int) (15 * Game.SCALE);
    }
    public void update() {
        updateAnimationTick();
    }
}
