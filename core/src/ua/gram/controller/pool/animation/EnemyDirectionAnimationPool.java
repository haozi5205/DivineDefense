package ua.gram.controller.pool.animation;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.EnumMap;
import java.util.EnumSet;

import ua.gram.controller.animation.enemy.EnemyAnimationManager;
import ua.gram.model.enums.Types;
import ua.gram.model.map.Path;
import ua.gram.model.prototype.enemy.EnemyPrototype;
import ua.gram.utils.Log;

/**
 * Holds pools for Enemy and each direction, available in the game.
 * @author Gram <gram7gram@gmail.com>
 */
public class EnemyDirectionAnimationPool implements DirectionPool {

    private final EnumMap<Path.Direction, AnimationPool> identityMap;

    public EnemyDirectionAnimationPool(EnemyPrototype prototype,
                                       EnemyAnimationManager provider,
                                       Types.EnemyState type) {
        identityMap = new EnumMap<Path.Direction, AnimationPool>(Path.Direction.class);

        for (Path.Direction direction : EnumSet.allOf(Path.Direction.class)) {
            TextureRegion[] regions = provider.getAnimationRegion(prototype, type, direction);
            String name = provider.getAnimationName(prototype, type, direction);
            identityMap.put(direction, new AnimationPool(regions, name));
        }

        Log.info("DirectionPool for " + prototype.name + " " + type.name() + " is OK");
    }

    public AnimationPool get(Path.Direction direction) {
        if (direction == null) {
            direction = Path.Direction.DOWN;
            Log.warn("Direction is not set. Using default: " + direction.name());
        }
        return identityMap.get(direction);
    }

}
