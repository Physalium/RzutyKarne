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
    private CrosshairComponent playerCrosshair; // to są klasy odpowiedzialne za zachowanie obiektów w grze
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
                if (FXGL.getGameState().getBoolean("ballShoot")) // jeżeli piłka została już kopnięta to wychodzimy z tej funkcji
                    return;
                var crosshairPos = playerCrosshair.getEntity().getCenter();
                var ballPos = football.getEntity().getCenter();
                var relativePos = new Point2D(crosshairPos.getX() - ballPos.getX(), crosshairPos.getY() - ballPos.getY());
                //System.out.println("Celownik: " + crosshairPos + "Pilka: " + ballPos + " relative: " + relativePos);

                football.shoot(relativePos, crosshairPos); // podajemy do funkcji strzału pozycję celownika w odniesieniu do piłki
                getGameState().setValue("ballShoot", true); // stała w naszej aplikacji która mówi czy piłka została juz kopnięta
                keeper.up(); // funkcja rozpoczynająca skok bramkarza
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
        getGameWorld().addEntityFactory(new PenaltyShootFactory()); // Obiekty "entity" tworzymy poprzez "factory" czyli taką jedną klasę która ma wszystkie metody konstruujące obiekty (wtedy wszystkie konstruktory ma się obok siebie w jednym pliku)
        initBackground(); // tworzymy tło
        initGameObjects(); //tworzymy obiekty w grze
        getGameState().setValue("goalText", "Bramkarz jest gotowy, strzelaj."); // zmienna której tekst wyświetlamy na ekranie
        getGameState().setValue("ballShoot", false);
        FXGL.getGameState().<Boolean>addListener("ballShoot", (oldvalue, newvalue) -> playerCrosshair.changeSpeed(newvalue)
        ); // funkcja lambda - jeżeli piłka jest w locie, to wyłączamy ruszanie celownikiem poprzez zmienienie jego predkosci do 0

        Text goal = new Text();
        goal.setFont(new Font("verdana", 30));
        goal.setFill(Color.BURLYWOOD);
        goal.setTranslateX(100); // x = 50
        goal.setTranslateY(100); // y = 100
        goal.textProperty().bind(FXGL.getGameState().stringProperty("goalText"));

        FXGL.getGameScene().addUINode(goal); // dodajemy do aplikacji ten tekst

    }

    private void initGameObjects()
    {

        Entity goalkeeper = spawn("keeper", getAppWidth() / 2 - 48, getAppHeight() / 2 - 48); //odwolanie do konstruktora w Factory
        keeper = goalkeeper.getComponent(GoalkeeperComponent.class); // przypisanie entity uzyskanego z Factory odpowiedniej klasy Component (Component jest odpowiedzialny za zachowanie obiektu w grze, a Entity za jego wyświetlanie itd od strony programu)
        Entity crosshair = spawn("crosshair", getAppWidth() / 2, getAppHeight() / 2);
        playerCrosshair = crosshair.getComponent(CrosshairComponent.class);
        Entity ball = spawn("ball", getAppWidth() / 2, getAppHeight() - 50);
        football = ball.getComponent(BallComponent.class);
    }


    @Override
    protected void initPhysics()
    {
        getPhysicsWorld().setGravity(0, 0); // to dla jaj
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.BALL, EntityType.GOALKEEPER)
        {

            @Override
            protected void onCollisionBegin(Entity ball, Entity keeper)
            {

                getGameState().setValue("goalText", "Brak gola."); //jeżeli jest kolizja bramkarz piłka -> nie ma gola


            }
        });

    }
}