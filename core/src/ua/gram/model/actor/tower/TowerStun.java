package ua.gram.model.actor.tower;

import com.badlogic.gdx.graphics.Color;
import ua.gram.DDGame;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.actor.weapon.LaserWeapon;
import ua.gram.model.actor.weapon.Weapon;
import ua.gram.model.prototype.TowerPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public final class TowerStun extends Tower implements Cloneable {

    public TowerStun(DDGame game, TowerPrototype prototype) {
        super(game, prototype);
    }

    @Override
    public void update(float delta) {
        this.setOrigin(getX() + animationWidth / 2f, getY() + 28);
    }

    @Override
    public void pre_attack(Enemy victim) {

    }

    /**
     * Enable Stun flag for Enemy.
     *
     * @param victim the enemy to attack
     */
    @Override
    public void attack(Enemy victim) {
        victim.isStunned = true;
    }

    /**
     * Disable Stun flag for Enemy.
     *
     * @param victim
     */
    @Override
    public void post_attack(Enemy victim) {
        victim.isStunned = false;
    }

    @Override
    public TowerStun clone() throws CloneNotSupportedException {
        return (TowerStun) super.clone();
    }

    @Override
    public void setWeapon(Weapon weapon) {
        super.setWeapon(weapon);
        ((LaserWeapon) weapon).setBackColor(Color.GREEN);
    }

}
