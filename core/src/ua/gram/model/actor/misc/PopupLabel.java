package ua.gram.model.actor.misc;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class PopupLabel extends Label {

    public PopupLabel(CharSequence text, Skin skin, String styleName, Actor target) {
        super(text, skin, styleName);
        this.setVisible(true);
        this.setPosition(
                target.getX() + (target.getWidth() - this.getWidth()) / 2f,
                target.getY() + target.getHeight() + 5);
        target.getParent().addActor(this);
        this.addAction(Actions.sequence(
                        Actions.alpha(0),
                        Actions.parallel(
                                Actions.fadeIn(.4f),
                                Actions.moveBy(0, 5, .4f)),
                        Actions.alpha(1),
                        Actions.moveBy(0, 10, .4f),
                        Actions.parallel(
                                Actions.moveBy(0, 5, .4f),
                                Actions.fadeOut(.4f)
                        ),
                        Actions.alpha(0),
                        Actions.run(new Runnable() {
                            @Override
                            public void run() {
                                remove();
                            }
                        })
                )
        );
    }
}