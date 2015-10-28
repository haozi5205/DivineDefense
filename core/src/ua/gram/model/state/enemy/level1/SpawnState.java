package ua.gram.model.state.enemy.level1;

import com.badlogic.gdx.Gdx;
import ua.gram.DDGame;
import ua.gram.controller.enemy.EnemyAnimationProvider;
import ua.gram.controller.enemy.EnemySpawner;
import ua.gram.controller.pool.animation.AnimationPool;
import ua.gram.model.Animator;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.state.enemy.EnemyStateManager;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class SpawnState extends InactiveState {

    private float spawnDurationCount;

    public SpawnState(DDGame game) {
        super(game);
    }

    @Override
    public void preManage(Enemy enemy) {
        super.preManage(enemy);
        enemy.setCurrentLevel1StateType(Animator.Types.SPAWN);
        EnemySpawner spawner = enemy.getSpawner();
        spawner.setActionPath(enemy, spawner.getSpawnPosition());
        EnemyAnimationProvider provider = enemy.getAnimationProvider();
        AnimationPool pool = provider.get(
                enemy.getOriginType(),
                enemy.getCurrentLevel1StateType(),
                enemy.getCurrentDirectionType());
        enemy.setAnimation(pool.obtain());
        Gdx.app.log("INFO", enemy + " spawns");
    }

    @Override
    public void manage(Enemy enemy, float delta) {
        EnemyStateManager manager = enemy.getSpawner().getStateManager();
        if (spawnDurationCount >= 1.5 && enemy.getCurrentLevel2State() != manager.getWalkingState()) {
            manager.swapLevel1State(enemy, manager.getActiveState());
            manager.swapLevel2State(enemy, manager.getWalkingState());
            spawnDurationCount = 0;
        } else {
            spawnDurationCount += delta;
        }
    }

    @Override
    public void postManage(Enemy enemy) {

    }
}
