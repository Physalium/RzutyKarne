package Rzutykarne;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;
import static com.almasb.fxgl.dsl.FXGL.texture;

public class PenaltyShootFactory implements EntityFactory
{
    @Spawns("crosshair") // etykietka żeby spawnować np celownik pisząc "crosshair"
    public Entity newCrosshair(SpawnData data) // data to info jakie podajemy do konstruktora
    {
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.KINEMATIC); // to jest dla jaj nie wiem co to robi w sumie

        return entityBuilder()
                .from(data) // z info ktore podajemy
                .type(EntityType.CROSSHAIR) // etykietka
                .with(physics) // oddzialuje na to fizykka
                .with(new CrosshairComponent()) //podajemy klasę Component która bedzie odpowiadac za zachowanie w grze
                .with(new CollidableComponent(true))//jest wykrywanie kolizji
                .view(texture("target.png")) //
                .build();
    }

    @Spawns("keeper")
    public Entity newGoalkeeper(SpawnData data)
    {
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.KINEMATIC);// to jest dla jaj nie wiem co to robi w sumie
        return entityBuilder()
                .from(data)
                .type(EntityType.GOALKEEPER)
                .with(physics)
                .viewWithBBox(texture("keeper-stand.png")) // ustawiamy teksture, ale tez BBox czyli hitbox na podstawie tej textury
                .with(new CollidableComponent(true))
                .with(new GoalkeeperComponent())
                .scale(1.5, 1.5) // powiekszamy troche go bo byl maly
                .build();
    }

    @Spawns("ball")
    public Entity newBall(SpawnData data)
    {

        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);// to jest dla jaj nie wiem co to robi w sumie
        physics.setFixtureDef(new FixtureDef().density(0.3f).restitution(1.0f));

        return entityBuilder()
                .from(data)
                .type(EntityType.BALL)
                .with(physics)
                .viewWithBBox("football.png")
                .with(new CollidableComponent(true))
                .with(new BallComponent())
                // .view(texture("football.png"))
                .rotationOrigin(25, 25)
//                .with("velocity", new Point2D(BALL_SPEED, BALL_SPEED))
                .build();
    }
}
