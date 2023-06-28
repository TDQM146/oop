package objects;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.Enemy;
import entities.Player;
import gamestates.Playing;
import levels.Level;
import main.Game;
import utilz.LoadSave;
import static utilz.Constants.ObjectConstants.*;
import static utilz.HelpMethods.CanCannonSeePlayer;
import static utilz.HelpMethods.IsProjectileHittingLevel;
import static utilz.Constants.Projectiles.*;

public class ObjectManager {

	private Playing playing;
	private BufferedImage[][] potionImgs, containerImgs;
	private BufferedImage[] cannonImgs, grassImgs, keyImgs, shipHelmImgs, flagImgs, candleImgs, candleLightImgs, windowImgs;
	private BufferedImage[][] treeImgs;
	private BufferedImage spikeImg, cannonBallImg, seashellImgs, totemImgs;
	private ArrayList<Potion> potions;
	private ArrayList<GameContainer> containers;
	private ArrayList<Projectile> projectiles = new ArrayList<>();
	private ArrayList<Key> keys;
	private ArrayList<ShipHelm> shipHelms;
	private ArrayList<Flag> flags;
	private ArrayList<Candle> candles;
	private ArrayList<CandleLight> candleLights;
	private ArrayList<Window> windows;


	private Level currentLevel;

	public ObjectManager(Playing playing) {
		this.playing = playing;
		currentLevel = playing.getLevelManager().getCurrentLevel();
		loadImgs();
	}

	public void checkSpikesTouched(Player p) {
		for (Spike s : currentLevel.getSpikes())
			if (s.getHitbox().intersects(p.getHitbox()))
				p.kill();
	}

	public void checkSpikesTouched(Enemy e) {
		for (Spike s : currentLevel.getSpikes())
			if (s.getHitbox().intersects(e.getHitbox()))
				e.hurt(200);
	}

	public void checkObjectTouched(Rectangle2D.Float hitbox) {
		for (Potion p : potions)
			if (p.isActive()) {
				if (hitbox.intersects(p.getHitbox())) {
					p.setActive(false);
					applyEffectToPlayer(p);
				}
			}
		for (Key k : keys) {
			if (hitbox.intersects(k.getHitbox())) {
				k.setActive(false);
			}
		}
	}

	public void applyEffectToPlayer(Potion p) {
		if (p.getObjType() == RED_POTION)
			playing.getPlayer().changeHealth(RED_POTION_VALUE);
		else
			playing.getPlayer().changePower(BLUE_POTION_VALUE);
	}

	public void checkObjectHit(Rectangle2D.Float attackbox) {
		for (GameContainer gc : containers)
			if (gc.isActive() && !gc.doAnimation) {
				if (gc.getHitbox().intersects(attackbox)) {
					gc.setAnimation(true);
					int type = 0;
					if (gc.getObjType() == BARREL)
						type = 1;
					potions.add(new Potion((int) (gc.getHitbox().x + gc.getHitbox().width / 2), (int) (gc.getHitbox().y - gc.getHitbox().height / 2), type));
					return;
				}
			}
	}

	public void loadObjects(Level newLevel) {
		currentLevel = newLevel;
		potions = new ArrayList<>(newLevel.getPotions());
		containers = new ArrayList<>(newLevel.getContainers());
		keys = new ArrayList<>(newLevel.getKeys());
		shipHelms = new ArrayList<>(newLevel.getShipHelms());
		flags = new ArrayList<>(newLevel.getFlags());
		candles = new ArrayList<>(newLevel.getCandles());
		candleLights = new ArrayList<>(newLevel.getCandleLights());
		windows = new ArrayList<>(newLevel.getWindows());
		projectiles.clear();
	}

	private void loadImgs() {
		BufferedImage potionSprite = LoadSave.GetSpriteAtlas(LoadSave.POTION_ATLAS);
		potionImgs = new BufferedImage[2][7];

		for (int j = 0; j < potionImgs.length; j++)
			for (int i = 0; i < potionImgs[j].length; i++)
				potionImgs[j][i] = potionSprite.getSubimage(12 * i, 16 * j, 12, 16);

		BufferedImage containerSprite = LoadSave.GetSpriteAtlas(LoadSave.CONTAINER_ATLAS);
		containerImgs = new BufferedImage[2][8];

		for (int j = 0; j < containerImgs.length; j++)
			for (int i = 0; i < containerImgs[j].length; i++)
				containerImgs[j][i] = containerSprite.getSubimage(40 * i, 30 * j, 40, 30);

		spikeImg = LoadSave.GetSpriteAtlas(LoadSave.TRAP_ATLAS);

		cannonImgs = new BufferedImage[7];
		BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.CANNON_ATLAS);

		for (int i = 0; i < cannonImgs.length; i++)
			cannonImgs[i] = temp.getSubimage(i * 40, 0, 40, 26);

		cannonBallImg = LoadSave.GetSpriteAtlas(LoadSave.CANNON_BALL);
		treeImgs = new BufferedImage[2][4];
		BufferedImage treeOneImg = LoadSave.GetSpriteAtlas(LoadSave.TREE_ONE_ATLAS);
		for (int i = 0; i < 4; i++)
			treeImgs[0][i] = treeOneImg.getSubimage(i * 39, 0, 39, 92);

		BufferedImage treeTwoImg = LoadSave.GetSpriteAtlas(LoadSave.TREE_TWO_ATLAS);
		for (int i = 0; i < 4; i++)
			treeImgs[1][i] = treeTwoImg.getSubimage(i * 62, 0, 62, 54);

		BufferedImage grassTemp = LoadSave.GetSpriteAtlas(LoadSave.GRASS_ATLAS);
		grassImgs = new BufferedImage[2];
		for (int i = 0; i < grassImgs.length; i++)
			grassImgs[i] = grassTemp.getSubimage(32 * i, 0, 32, 32);

		seashellImgs = LoadSave.GetSpriteAtlas(LoadSave.SEASHELL);

		totemImgs = LoadSave.GetSpriteAtlas(LoadSave.TOTEM);

		BufferedImage keySprite = LoadSave.GetSpriteAtlas(LoadSave.KEY);
		keyImgs = new BufferedImage[8];
		for (int i = 0; i < keyImgs.length; i++)
			keyImgs[i] = keySprite.getSubimage(24*i,0, 24, 24);

		BufferedImage shipHelmSprite = LoadSave.GetSpriteAtlas(LoadSave.SHIP_HELM);
		shipHelmImgs = new BufferedImage[10];
		for (int i = 0; i < shipHelmImgs.length; i++)
			shipHelmImgs[i] = shipHelmSprite.getSubimage(31*i, 0, 31, 32);

		BufferedImage FlagSprite = LoadSave.GetSpriteAtlas(LoadSave.FLAG);
		flagImgs = new BufferedImage[9];
		for (int i = 0; i < flagImgs.length; i++)
			flagImgs[i] = FlagSprite.getSubimage(54*i, 0, 50, 93);

		BufferedImage CandleSprite = LoadSave.GetSpriteAtlas(LoadSave.CANDLE);
		candleImgs = new BufferedImage[6];
		for (int i = 0; i < candleImgs.length; i++)
			candleImgs[i] = CandleSprite.getSubimage(32*i, 0, 32, 32);

		BufferedImage CandleLightSprite = LoadSave.GetSpriteAtlas(LoadSave.CANDLE_LIGHT);
		candleLightImgs = new BufferedImage[4];
		for (int i = 0; i < candleLightImgs.length; i++)
			candleLightImgs[i] = CandleLightSprite.getSubimage(32*i, 0, 32, 32);

		BufferedImage WindowSprite = LoadSave.GetSpriteAtlas(LoadSave.WINDOW);
		windowImgs = new BufferedImage[74];
		for (int i = 0; i < windowImgs.length; i++)
			windowImgs[i] = WindowSprite.getSubimage(32*i, 0, 32, 32);

	}

	public void update(int[][] lvlData, Player player) {
		updateBackgroundTrees();
		for (Potion p : potions)
			if (p.isActive())
				p.update();

		for (GameContainer gc : containers)
			if (gc.isActive())
				gc.update();

		updateCannons(lvlData, player);
		updateProjectiles(lvlData, player);

		for(Key k : keys)
			if (k.isActive())
				k.update();

		for (ShipHelm sh : shipHelms)
			sh.update();

		for (Flag f : flags)
			f.update();

		for (Candle c : candles)
			c.update();
		
		for (CandleLight cl : candleLights)
			cl.update();

		for (Window w : windows)
			w.update();

	}

	private void updateBackgroundTrees() {
		for (BackgroundTree bt : currentLevel.getTrees())
			bt.update();
	}

	private void updateProjectiles(int[][] lvlData, Player player) {
		for (Projectile p : projectiles)
			if (p.isActive()) {
				p.updatePos();
				if (p.getHitbox().intersects(player.getHitbox())) {
					player.changeHealth(-25);
					p.setActive(false);
				} else if (IsProjectileHittingLevel(p, lvlData))
					p.setActive(false);
			}
	}

	private boolean isPlayerInRange(Cannon c, Player player) {
		int absValue = (int) Math.abs(player.getHitbox().x - c.getHitbox().x);
		return absValue <= Game.TILES_SIZE * 5;
	}

	private boolean isPlayerInfrontOfCannon(Cannon c, Player player) {
		if (c.getObjType() == CANNON_LEFT) {
			if (c.getHitbox().x > player.getHitbox().x)
				return true;

		} else if (c.getHitbox().x < player.getHitbox().x)
			return true;
		return false;
	}

	private void updateCannons(int[][] lvlData, Player player) {
		for (Cannon c : currentLevel.getCannons()) {
			if (!c.doAnimation)
				if (c.getTileY() == player.getTileY())
					if (isPlayerInRange(c, player))
						if (isPlayerInfrontOfCannon(c, player))
							if (CanCannonSeePlayer(lvlData, player.getHitbox(), c.getHitbox(), c.getTileY()))
								c.setAnimation(true);

			c.update();
			if (c.getAniIndex() == 4 && c.getAniTick() == 0)
				shootCannon(c);
		}
	}

	private void shootCannon(Cannon c) {
		int dir = 1;
		if (c.getObjType() == CANNON_LEFT)
			dir = -1;

		projectiles.add(new Projectile((int) c.getHitbox().x, (int) c.getHitbox().y, dir));
	}

	public void draw(Graphics g, int xLvlOffset) {
		drawPotions(g, xLvlOffset);
		drawContainers(g, xLvlOffset);
		drawTraps(g, xLvlOffset);
		drawCannons(g, xLvlOffset);
		drawProjectiles(g, xLvlOffset);
		drawGrass(g, xLvlOffset);
		drawSeashells(g, xLvlOffset);
		drawTotems(g, xLvlOffset);
		drawKey(g, xLvlOffset);
		drawShipHelms(g, xLvlOffset);
		drawFlags(g, xLvlOffset);
		drawCandles(g, xLvlOffset);
		drawCandleLight(g, xLvlOffset);
		drawWindows(g, xLvlOffset);

	}

	private void drawWindows(Graphics g, int xLvlOffset) {
		for (Window w : windows)
			g.drawImage(windowImgs[w.getAniIndex()], (int) (w.getHitbox().x - w.getxDrawOffset() - xLvlOffset), (int) (w.getHitbox().y - w.getyDrawOffset()), WINDOW_WIDTH,(int) (WINDOW_HEIGHT),
					null);
	}

	private void drawCandleLight(Graphics g, int xLvlOffset) {
		for (CandleLight cl : candleLights)
			g.drawImage(candleLightImgs[cl.getAniIndex()], (int) (cl.getHitbox().x - cl.getxDrawOffset() - xLvlOffset), (int) (cl.getHitbox().y - cl.getyDrawOffset()), CANDLE_LIGHT_WIDTH,(int) (CANDLE_LIGHT_HEIGHT),
					null);
	}

	private void drawCandles(Graphics g, int xLvlOffset) {
		for (Candle c : candles)
			g.drawImage(candleImgs[c.getAniIndex()], (int) (c.getHitbox().x - c.getxDrawOffset() - xLvlOffset), (int) (c.getHitbox().y - c.getyDrawOffset()), 4*CANDLE_WIDTH,(int) (1.5*CANNON_HEIGHT),
					null);
		
	}

	private void drawFlags(Graphics g, int xLvlOffset) {
		for (Flag f : flags)
			g.drawImage(flagImgs[f.getAniIndex()], (int) (f.getHitbox().x - f.getxDrawOffset() - xLvlOffset), (int) (f.getHitbox().y - f.getyDrawOffset()), FLAG_WIDTH,FLAG_HEIGHT,
						null);
	}

	private void drawShipHelms(Graphics g, int xLvlOffset) {
		for (ShipHelm sh : shipHelms)
			g.drawImage(shipHelmImgs[sh.getAniIndex()], (int) (sh.getHitbox().x - sh.getxDrawOffset() - xLvlOffset), (int) (sh.getHitbox().y - sh.getyDrawOffset()), SHIP_HELM_WIDTH,SHIP_HELM_HEIGHT,
					null);
	}

	private void drawKey(Graphics g, int xLvlOffset) {
		for (Key k : keys)
			if (k.isActive()) {
				g.drawImage(keyImgs[k.getAniIndex()], (int) (k.getHitbox().x - k.getxDrawOffset() - xLvlOffset), (int) (k.getHitbox().y - k.getyDrawOffset()), 4* KEY_WIDTH,2* KEY_HEIGHT,
					null);
		}
	}

	private void drawTotems(Graphics g, int xLvlOffset) {
		for (Totem t : currentLevel.getTotems()) {
			int x = (int) (t.getHitbox().x - xLvlOffset);
			int width = TOTEM_WIDTH;

			if (t.getObjType() == TOTEM_RIGHT) {
				x += width;
				width *= -1;
			}
			g.drawImage(totemImgs, x, (int) (t.getHitbox().y),(int) (1.2 *width), TOTEM_HEIGHT, null);
		}
	}

	private void drawSeashells(Graphics g, int xLvlOffset) {
		for (Seashell s : currentLevel.getSeashells()) {
			int x = (int) (s.getHitbox().x - xLvlOffset);
			int width = SEASHELL_WIDTH;

			if (s.getObjType() == SEA_SHELL_RIGHT) {
				x += width + (int)(Game.SCALE*15);
				width *= -1;
			}
			g.drawImage(seashellImgs, x, (int) (s.getHitbox().y),(int)(1.5*width), 2*SEASHELL_HEIGHT, null);
		}
	}

	private void drawGrass(Graphics g, int xLvlOffset) {
		for (Grass grass : currentLevel.getGrass())
			g.drawImage(grassImgs[grass.getType()], grass.getX() - xLvlOffset, grass.getY(), (int) (32 * Game.SCALE), (int) (32 * Game.SCALE), null);
	}

	public void drawBackgroundTrees(Graphics g, int xLvlOffset) {
		for (BackgroundTree bt : currentLevel.getTrees()) {

			int type = bt.getType();
			if (type == 9)
				type = 8;
			g.drawImage(treeImgs[type - 7][bt.getAniIndex()], bt.getX() - xLvlOffset + GetTreeOffsetX(bt.getType()), (int) (bt.getY() + GetTreeOffsetY(bt.getType())), GetTreeWidth(bt.getType()),
					GetTreeHeight(bt.getType()), null);
		}
	}

	private void drawProjectiles(Graphics g, int xLvlOffset) {
		for (Projectile p : projectiles)
			if (p.isActive())
				g.drawImage(cannonBallImg, (int) (p.getHitbox().x - xLvlOffset), (int) (p.getHitbox().y), CANNON_BALL_WIDTH, CANNON_BALL_HEIGHT, null);
	}

	private void drawCannons(Graphics g, int xLvlOffset) {
		for (Cannon c : currentLevel.getCannons()) {
			int x = (int) (c.getHitbox().x - xLvlOffset);
			int width = CANNON_WIDTH;

			if (c.getObjType() == CANNON_RIGHT) {
				x += width;
				width *= -1;
			}
			g.drawImage(cannonImgs[c.getAniIndex()], x, (int) (c.getHitbox().y), width, CANNON_HEIGHT, null);
		}
	}

	private void drawTraps(Graphics g, int xLvlOffset) {
		for (Spike s : currentLevel.getSpikes())
			g.drawImage(spikeImg, (int) (s.getHitbox().x - xLvlOffset), (int) (s.getHitbox().y - s.getyDrawOffset()), SPIKE_WIDTH, SPIKE_HEIGHT, null);

	}

	private void drawContainers(Graphics g, int xLvlOffset) {
		for (GameContainer gc : containers)
			if (gc.isActive()) {
				int type = 0;
				if (gc.getObjType() == BARREL)
					type = 1;
				g.drawImage(containerImgs[type][gc.getAniIndex()], (int) (gc.getHitbox().x - gc.getxDrawOffset() - xLvlOffset), (int) (gc.getHitbox().y - gc.getyDrawOffset()), CONTAINER_WIDTH,
						CONTAINER_HEIGHT, null);
			}
	}

	private void drawPotions(Graphics g, int xLvlOffset) {
		for (Potion p : potions)
			if (p.isActive()) {
				int type = 0;
				if (p.getObjType() == RED_POTION)
					type = 1;
				g.drawImage(potionImgs[type][p.getAniIndex()], (int) (p.getHitbox().x - p.getxDrawOffset() - xLvlOffset), (int) (p.getHitbox().y - p.getyDrawOffset()), POTION_WIDTH, POTION_HEIGHT,
						null);
			}
	}

	public void resetAllObjects() {
		loadObjects(playing.getLevelManager().getCurrentLevel());
		for (Potion p : potions)
			p.reset();
		for (GameContainer gc : containers)
			gc.reset();
		for (Cannon c : currentLevel.getCannons())
			c.reset();
		for (Key k : keys)
			k.reset();
	}
}
