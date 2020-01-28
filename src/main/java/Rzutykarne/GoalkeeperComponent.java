package Rzutykarne;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.Texture;
import javafx.scene.image.Image;
import javafx.util.Duration;

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

    @Override
    public void onAdded()
    {
        entity.getViewComponent().addChild(textureStand);
    }


    void up()
    {

        entity.getViewComponent().removeChild(textureStand);
        entity.getViewComponent().addChild(textureUp);
        physics.setLinearVelocity(0, -400);

        FXGL.getGameTimer().runOnceAfter(() -> {
            physics.setLinearVelocity(0, 0);
        }, Duration.seconds(0.2));
    }
}
