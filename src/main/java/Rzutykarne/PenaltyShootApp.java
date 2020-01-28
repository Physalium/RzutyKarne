package Rzutykarne;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

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
                if (FXGL.getGameState().getBoolean("ballShoot"))
                    return;
                var crosshairPos = playerCrosshair.getEntity().getCenter();
                var ballPos = football.getEntity().getCenter();
                var relativePos = new Point2D(crosshairPos.getX() - ballPos.getX(), crosshairPos.getY() - ballPos.getY());
                //System.out.println("Celownik: " + crosshairPos + "Pilka: " + ballPos + " relative: " + relativePos);

                football.shoot(relativePos, crosshairPos);
                getGameState().setValue("ballShoot", true);
                keeper.up();
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
        getGameState().setValue("goalText", "Bramkarz jest gotowy, strzelaj.");
        getGameState().setValue("ballShoot", false);
        FXGL.getGameState().<Boolean>addListener("ballShoot", (oldvalue, newvalue) -> playerCrosshair.changeSpeed(newvalue)
        );

        Text goal = new Text();
        goal.setFont(new Font("verdana", 30));
        goal.setFill(Color.BURLYWOOD);
        goal.setTranslateX(100); // x = 50
        goal.setTranslateY(100); // y = 100
        goal.textProperty().bind(FXGL.getGameState().stringProperty("goalText"));

        FXGL.getGameScene().addUINode(goal); // add to the scene graph

    }

    private void initGameObjects()
    {

        Entity goalkeeper = spawn("keeper", getAppWidth() / 2 - 48, getAppHeight() / 2 - 48);
        keeper = goalkeeper.getComponent(GoalkeeperComponent.class);
        Entity crosshair = spawn("crosshair", getAppWidth() / 2, getAppHeight() / 2);
        playerCrosshair = crosshair.getComponent(CrosshairComponent.class);
        Entity ball = spawn("ball", getAppWidth() / 2, getAppHeight() - 50);
        football = ball.getComponent(BallComponent.class);
    }


    @Override
    protected void initPhysics()
    {
        getPhysicsWorld().setGravity(0, 0);
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.BALL, EntityType.GOALKEEPER)
        {

            @Override
            protected void onCollisionBegin(Entity ball, Entity keeper)
            {
                System.out.println(keeper.getX());
                System.out.println(ball.getX());
                if (!keeper.getComponent(GoalkeeperComponent.class).physics.isMoving())
                {
                    getGameState().setValue("goalText", "Gol!");

                }

            }
        });

    }
}