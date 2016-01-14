package ua.gram.model.strategy.tower;

import java.util.Collections;
import java.util.List;

import ua.gram.controller.comparator.EnemyHealthComparator;
import ua.gram.model.actor.tower.Tower;
import ua.gram.model.group.EnemyGroup;
import ua.gram.model.strategy.TowerStrategyManager;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class StrongestStrategy implements TowerStrategy {

    private final EnemyHealthComparator healthComparator;

    public StrongestStrategy(TowerStrategyManager manager) {
        this.healthComparator = manager.getHealthComparator();
    }

    @Override
    public List<EnemyGroup> chooseVictims(Tower tower, List<EnemyGroup> victims) {

        healthComparator.setType(EnemyHealthComparator.MAX);

        Collections.sort(victims, healthComparator);

        return victims.size() > 0 ? victims.subList(0, tower.getProperty().getTowerLevel()) : null;
    }

}
