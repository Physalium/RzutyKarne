package Rzutykarne;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;

import static com.almasb.fxgl.dsl.FXGL.*;

public class PenaltyShootApp extends GameApplication
{
    private CrosshairComponent playerCrosshair;
    private BallComponent football;
    private GoalkeeperComponent keeper;

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
                var crosshairPos = playerCrosshair.getEntity().getCenter();
                var ballPos = football.getEntity().getCenter();
                var relativePos = new Point2D(crosshairPos.getX() - ballPos.getX(), crosshairPos.getY() - ballPos.getY());
                System.out.println("Celownik: " + crosshairPos + "Pilka: " + ballPos + " relative: " + relativePos);

                football.shoot(relativePos, crosshairPos);

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
        FXGL.getGameState().<Boolean>addListener("ballShoot", (oldvalue, newvalue) -> playerCrosshair.changeSpeed(newvalue));

    }

    private void initGameObjects()
    {
        Entity crosshair = spawn("crosshair", getAppWidth() / 2, getAppHeight() / 2);
        playerCrosshair = crosshair.getComponent(CrosshairComponent.class);
        Entity ball = spawn("ball", getAppWidth() / 2, getAppHeight() - 50);
        football = ball.getComponent(BallComponent.class);
        Entity goalkeeper = spawn("keeper", getAppWidth() / 2, getAppHeight() / 2);
        keeper = goalkeeper.getComponent(GoalkeeperComponent.class);
    }


    @Override
    protected void initPhysics()
    {
        getPhysicsWorld().setGravity(0, 0);
    }
}