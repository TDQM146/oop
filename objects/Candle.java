package objects;

import main.Game;

public class Candle extends GameObject{
    public Candle(int x, int y, int objType) {
        super(x, y, objType);
        doAnimation = true;
        initHitbox(10, 20);

        xDrawOffset = (int) (11 * Game.SCALE);
        yDrawOffset = (int) (5 * Game.SCALE);

        hitbox.y -= (int) (2 * Game.SCALE);
    }
    public void update() {
        updateAnimationTick();
    }
}
