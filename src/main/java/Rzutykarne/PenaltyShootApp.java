package Rzutykarne;

import com.almasb.fxgl.animation.AnimatedValue;
import com.almasb.fxgl.animation.Animation;
import com.almasb.fxgl.animation.AnimationBuilder;
import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.components.IrremovableComponent;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.GameView;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.GameWorld;
import com.almasb.fxgl.texture.Texture;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.Map;

import static com.almasb.fxgl.dsl.FXGL.*;
import static Rzutykarne.PenaltyShootFactory.*;

public class PenaltyShootApp extends GameApplication
{
    private CrosshairComponent playerCrosshair;
    private BallComponent football;

    public static void main(String[] args)
    {
        launch(args);
    }

    private void initBackground()
    {

        entityBuilder()
                .view(texture("background.jpg")).buildAndAttach();

    }

    @Override
    protected void initInput()
    {
        getInput().addAction(new UserAction("Shoot")
        {
            @Override
            protected void onAction()
            {
                //ball.shoot
              getGameState().setValue("ballShoot", true);
            }

            @Override
            protected void onActionEnd()
            {
                // to do
            }

        }, KeyCode.SPACE);

        getInput().addAction(new UserAction("Up")
        {
            @Override
            protected void onAction()
            {
                playerCrosshair.up();
            }

            @Override
            protected void onActionEnd()
            {
                playerCrosshair.stop();
            }

        }, KeyCode.W);

        getInput().addAction(new UserAction("Down")
        {
            @Override
            protected void onAction()
            {
                playerCrosshair.down();
            }

            @Override
            protected void onActionEnd()
            {
                playerCrosshair.stop();
            }

        }, KeyCode.S);
        getInput().addAction(new UserAction("Left")
        {
            @Override
            protected void onAction()
            {
                playerCrosshair.left();
            }

            @Override
            protected void onActionEnd()
            {
                playerCrosshair.stop();
            }

        }, KeyCode.A);
        getInput().addAction(new UserAction("Right")
        {
            @Override
            protected void onAction()
            {
                playerCrosshair.right();
            }

            @Override
            protected void onActionEnd()
            {
                playerCrosshair.stop();
            }

        }, KeyCode.D);
    }

    @Override
    protected void initSettings(GameSettings settings)
    {
        settings.setWidth(1000);
        settings.setHeight(600);
        settings.setTitle("Rzuty karne");
    }


    @Override
    protected void initGame()
    {
        getGameWorld().addEntityFactory(new PenaltyShootFactory());
        initBackground();
        initGameObjects();
        getGameState().setValue("ballShoot", false);
        FXGL.getGameState().<Boolean>addListener("ballShoot",(oldvalue,newvalue)-> playerCrosshair.changeSpeed(newvalue));

    }

    private void initGameObjects()
    {
        Entity crosshair = spawn("crosshair", getAppWidth() / 2, getAppHeight() / 2);
        playerCrosshair = crosshair.getComponent(CrosshairComponent.class);
        Entity ball = spawn("ball", getAppWidth() / 2, getAppHeight() - 50);
        football = ball.getComponent(BallComponent.class);
    }


    @Override
    protected void initPhysics()
    {
        getPhysicsWorld().setGravity(0, 0);
    }
}