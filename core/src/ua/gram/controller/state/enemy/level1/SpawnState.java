package ua.gram.controller.state.enemy.level1;

import com.badlogic.gdx.math.Vector2;

import ua.gram.DDGame;
import ua.gram.controller.Counters;
import ua.gram.controller.enemy.DirectionHolder;
import ua.gram.controller.enemy.EnemySpawner;
import ua.gram.controller.state.enemy.EnemyStateManager;
import ua.gram.model.Animator;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.enums.Types;
import ua.gram.model.map.Path;
import ua.gram.utils.Log;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class SpawnState extends InactiveState {

    private final String SPAWN_STATE_COUNT = "spawn_state_count";
    private Vector2 spawnIndex;

    public SpawnState(DDGame game, EnemyStateManager manager) {
        super(game, manager);
    }

    @Override
    protected Types.EnemyState getType() {
        return Types.EnemyState.SPAWN;
    }

    @Override
    public void preManage(Enemy enemy) {
        if (enemy.hasParentEnemy()) {
            minionSpawn(enemy);
        } else {
            normalSpawn(enemy);
        }

        getManager().getAnimationChanger()
                .update(enemy, getType());

        super.preManage(enemy);

        Counters counters = enemy.getCounters();
        counters.set(SPAWN_STATE_COUNT, 0);

        enemy.setVisible(true);

        DirectionHolder directionHolder = enemy.getDirectionHolder();

        int currentX = (int) (spawnIndex.x * DDGame.TILE_HEIGHT);
        int currentY = (int) (spawnIndex.y * DDGame.TILE_HEIGHT);

        directionHolder.setCurrentPosition(currentX, currentY);
        enemy.setPosition(currentX, currentY);

        Log.info(enemy + " is spawned at "
                + Path.toString(directionHolder.getCurrentPositionIndex()));
    }

    private void normalSpawn(Enemy enemy) {
        EnemySpawner spawner = enemy.getSpawner();
        Vector2 initial = spawner.getLevel().getPrototype().initialDirection;
        Vector2 prev = Path.opposite(initial);
        spawner.createPath(enemy, spawnIndex, prev);
        enemy.getDirectionHolder().setCurrentDirection(initial.x, initial.y);
    }

    private void minionSpawn(Enemy enemy) {
        EnemySpawner spawner = enemy.getSpawner();
        Enemy summoner = enemy.getParentEnemy();
        Vector2 prev = summoner.getDirectionHolder().getPreviousDirection();

        spawner.createPath(enemy, spawnIndex, prev);

        Vector2 current = summoner.getDirectionHolder().getCurrentDirection();
        DirectionHolder holder = enemy.getDirectionHolder();
        Animator<?, Path.Direction> enemyAnimator = enemy.getAnimator();

        holder.setCurrentDirection(current.x, current.y);

        Path.Direction inheritedDirection = summoner.getAnimator().getSecondaryType();
        enemyAnimator.setSecondaryType(inheritedDirection);

        Log.info("Minion " + enemy + " inherits direction: "
                + inheritedDirection + " " + Path.toString(current));
    }

    @Override
    public void manage(Enemy enemy, float delta) {
        EnemyStateManager manager = enemy.getSpawner().getStateManager();
        Counters counters = enemy.getCounters();

        float count = counters.get(SPAWN_STATE_COUNT);
        if (count >= enemy.getSpawnDuration()) {
            counters.set(SPAWN_STATE_COUNT, 0);
            manager.swapLevel1State(enemy, manager.getActiveState());
            manager.swapLevel2State(enemy, manager.getWalkingState(enemy));
        } else {
            counters.set(SPAWN_STATE_COUNT, count + delta);
        }
    }

    @Override
    public void postManage(Enemy enemy) {
        super.postManage(enemy);
        Counters counters = enemy.getCounters();
        counters.set(SPAWN_STATE_COUNT, 0);
    }

    public void setSpawnIndex(Vector2 spawnIndex) {
        this.spawnIndex = spawnIndex.cpy();
    }

}
