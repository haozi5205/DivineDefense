package ua.gram.model.stage;

import com.badlogic.gdx.Gdx;

import ua.gram.DDGame;
import ua.gram.model.prototype.GamePrototype;
import ua.gram.model.table.LevelSelectTable;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class LevelSelectStage extends AbstractStage {

    public LevelSelectStage(DDGame game, GamePrototype prototype) {
        super(game);
        LevelSelectTable table = new LevelSelectTable(game, prototype.levelConfig.levels);
        table.setVisible(true);
        this.addActor(table);
        Gdx.app.log("INFO", "LevelSelectStage is OK");
    }

}