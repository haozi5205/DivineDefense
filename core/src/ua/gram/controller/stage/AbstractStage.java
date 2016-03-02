package ua.gram.controller.stage;

import com.badlogic.gdx.scenes.scene2d.Stage;

import ua.gram.DDGame;
import ua.gram.controller.listener.DebugListener;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public abstract class AbstractStage extends Stage {

    protected final DDGame game;
    protected StageHolder stageHolder;

    public AbstractStage(DDGame game) {
        super(game.getViewport(), game.getBatch());
        this.game = game;
        addListener(new DebugListener());
    }

    @Override
    public void act() {
        super.act();
        setDebugAll(DDGame.DEBUG);
    }

    public StageHolder getStageHolder() {
        return stageHolder;
    }

    public void setStageHolder(StageHolder stageHolder) {
        this.stageHolder = stageHolder;
    }
}