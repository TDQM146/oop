package objects;

import main.Game;

public class Seashell extends GameObject{
    private int x, y, type;
    public Seashell(int x, int y, int type) {
        super(x, y, type);
        initHitbox(32,21);
        xDrawOffset = (int)(Game.SCALE * 9);
        yDrawOffset = (int)(Game.SCALE * 17);
        hitbox.y -= (int) (10 * Game.SCALE);
        hitbox.x -= (int) (5 * Game.SCALE);
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;

    }

    public int getType() {
        return type;
    }
}
