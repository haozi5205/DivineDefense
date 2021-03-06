package ua.gram.controller.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.Arrays;

import ua.gram.DDGame;
import ua.gram.utils.Log;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class ErrorStage extends AbstractStage {

    public ErrorStage(final DDGame game, final String error, final Exception e) {
        super(game);
        this.setDebugAll(DDGame.DEBUG);
        byte gap = 5;
        Log.exc(error, e);
        Label errorLabel = new Label("ERROR", game.getResources().getSkin(), "header1altwhite");
        errorLabel.setPosition((DDGame.WORLD_WIDTH - errorLabel.getWidth()) / 2f, DDGame.WORLD_HEIGHT / 2f + 20);
        errorLabel.setVisible(true);

        final Label thrownErrorLabel = new Label(error, game.getResources().getSkin(), "header2altwhite");
        thrownErrorLabel.setPosition((DDGame.WORLD_WIDTH - thrownErrorLabel.getWidth()) / 2f, (DDGame.WORLD_HEIGHT - thrownErrorLabel.getHeight()) / 2f);
        thrownErrorLabel.setVisible(true);

        Button reportBut = new TextButton("REPORT", game.getResources().getSkin(), "default");
        reportBut.setVisible(true);
        reportBut.setSize(200, 60);
        reportBut.setPosition(DDGame.WORLD_WIDTH / 2f - reportBut.getWidth() - gap, DDGame.WORLD_HEIGHT / 2f - reportBut.getHeight() - 50);
        reportBut.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String report = error + "\r\nException: " + e + "\r\n" + Arrays.toString(e.getStackTrace());
                boolean result = game.getSecurity().sendBugReport(report);
                thrownErrorLabel.setText(result ? "Report send successfully!" : "Report was not successful!");
                thrownErrorLabel.setPosition((DDGame.WORLD_WIDTH - thrownErrorLabel.getWidth()) / 2f, (DDGame.WORLD_HEIGHT - thrownErrorLabel.getHeight()) / 2f);
            }
        });

        Button errorBut = new TextButton("EXIT", game.getResources().getSkin(), "default");
        errorBut.setVisible(true);
        errorBut.setSize(200, 60);
        errorBut.setPosition(DDGame.WORLD_WIDTH / 2f + gap, DDGame.WORLD_HEIGHT / 2f - errorBut.getHeight() - 50);
        errorBut.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        this.addActor(errorBut);
        this.addActor(reportBut);
        this.addActor(errorLabel);
        this.addActor(thrownErrorLabel);
    }

}
