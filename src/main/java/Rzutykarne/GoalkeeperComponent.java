package Rzutykarne;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.Texture;
import javafx.scene.image.Image;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.getGameState;

public class GoalkeeperComponent extends Component
{
    protected PhysicsComponent physics;
    private Texture textureStand;
    private Texture textureUp;

    public GoalkeeperComponent()
    {

        textureStand = new Texture(new Image("assets/textures/keeper-stand.png"));
        textureUp = new Texture(new Image("assets/textures/keeper-top.png"));

    }


    void up()
    {
        entity.getViewComponent().clearChildren(); //usuwam teksture stojacego bramkarza
        entity.getViewComponent().addChild(textureUp); // dodaje teksture bramkarza w locie
        System.out.println();
        physics.setLinearVelocity(0, -400);

        FXGL.getGameTimer().runOnceAfter(() -> {
            physics.setLinearVelocity(0, 0);
            if (!getGameState().getString("goalText").equals("Brak gola."))
                getGameState().setValue("goalText", "Gol!");

        }, Duration.seconds(0.2)); // przestaje lecieć po 0.2 sekundy
    }
}
