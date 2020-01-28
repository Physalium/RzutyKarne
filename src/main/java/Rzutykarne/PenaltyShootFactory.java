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
    @Spawns("crosshair")
    public Entity newCrosshair(SpawnData data)
    {
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.KINEMATIC);

        return entityBuilder()
                .from(data)
                .type(EntityType.CROSSHAIR)
                .with(physics)
                .with(new CrosshairComponent())
                .with(new CollidableComponent(true))
                .view(texture("target.png"))
                .build();
    }

    @Spawns("keeper")
    public Entity newGoalkeeper(SpawnData data)
    {
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.KINEMATIC);

        return entityBuilder()
                .from(data)
                .type(EntityType.GOALKEEPER)
                .with(physics)
                .with(new CollidableComponent(true))
                .with(new GoalkeeperComponent())
                .view(texture("keeper-stand.png"))
                .build();
    }

    @Spawns("ball")
    public Entity newBall(SpawnData data)
    {

        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);
        physics.setFixtureDef(new FixtureDef().density(0.3f).restitution(1.0f));

        return entityBuilder()
                .from(data)
                .type(EntityType.BALL)
                .with(physics)
                .with(new CollidableComponent(true))
                .with(new BallComponent())
                .view(texture("football.png"))
                .rotationOrigin(25, 25)
//                .with("velocity", new Point2D(BALL_SPEED, BALL_SPEED))
                .build();
    }
}
