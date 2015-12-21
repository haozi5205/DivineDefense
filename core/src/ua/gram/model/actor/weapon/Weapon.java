package ua.gram.model.actor.weapon;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import ua.gram.DDGame;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.actor.tower.Tower;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public abstract class Weapon extends Actor {

    protected Tower tower;
    protected Enemy target;

    public Weapon(Tower tower, Enemy target) {
        this.tower = tower;
        this.target = target;
        this.setDebug(DDGame.DEBUG);
    }

    /**
     * Weapon should be start in it's start() method.
     * Weapon will be updated if game is not paused and there is a target.
     *
     * @param delta
     */
    @Override
    public void act(float delta) {
        super.act(delta);
        if (!DDGame.PAUSE) {
            if (target != null) {
                update(delta);
                this.setVisible(true);
                this.setZIndex(target.getY() > tower.getY() ? 0 : tower.getZIndex() + 1);
            } else {
                this.setVisible(false);
            }
        }
    }

    /**
     * Weapon should be drawn in it's render(Batch) method.
     * Weapon will be drawn if game is not paused and there is a target.
     *
     * @param batch
     * @param parentAlpha
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (!DDGame.PAUSE && target != null) render(batch);
    }

    /**
     * Update your weapon here.
     */
    public abstract void update(float delta);

    /**
     * Draw your weapon here.
     *
     * @param batch draw on it
     */
    public abstract void render(Batch batch);

    public void setTarget(Enemy target) {
        this.target = target;
    }

    public void setSource(Tower source) {
        this.tower = source;
    }

    public void resetTarget() {
        target = null;
    }
}