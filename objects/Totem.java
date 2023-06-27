package objects;

import main.Game;

public class Totem extends GameObject{
    private int x, y, type;
    public Totem(int x, int y, int objType) {
        super(x, y, objType);
        initHitbox(58,75);
        xDrawOffset = (int)(Game.SCALE * 11);
        yDrawOffset = (int)(Game.SCALE * 15);
        hitbox.y -= (int)(Game.SCALE * 43);
        hitbox.x -= (int)(Game.SCALE * 10);
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
