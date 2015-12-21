package ua.gram.model.state.enemy.level1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.GdxRuntimeException;
import ua.gram.DDGame;
import ua.gram.controller.enemy.EnemySpawner;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.group.EnemyGroup;

import java.util.Random;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class DeadState extends InactiveState {

    public DeadState(DDGame game) {
        super(game);
    }

    @Override
    public void preManage(Enemy enemy) throws GdxRuntimeException {
        super.preManage(enemy);
        enemy.getAnimationProvider().get(enemy).free(enemy);
        Gdx.app.log("INFO", enemy + " frees animation");

        EnemySpawner spawner = enemy.getSpawner();
        EnemyGroup group = enemy.getEnemyGroup();

        enemy.clearActions();
        spawner.free(enemy);
        group.clear();
        group.remove();
        Gdx.app.log("INFO", enemy + " is dead");
    }

    @Override
    public void postManage(Enemy enemy) {
        getGame().getPlayer().addCoins(enemy.reward);
        float value = new Random().nextFloat();

        //10% chance to get a gem
        if (value >= .45 && value < .55) {
            getGame().getPlayer().addGems(1);
            Gdx.app.log("INFO", "Player got 1 gem");
        }
    }
}