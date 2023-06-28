package objects;

import main.Game;

public class Flag extends GameObject{
    public Flag(int x, int y, int objType) {
        super(x, y, objType);
        doAnimation = true;
        initHitbox(54, 93);

        xDrawOffset = (int) (7 * Game.SCALE);
        yDrawOffset = (int) (0 * Game.SCALE);

        hitbox.y -= (int) (60 * Game.SCALE);
    }
    public void update() {
        updateAnimationTick();
    }
}
