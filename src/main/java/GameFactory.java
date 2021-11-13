import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyDef;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import component.BombComponent;
import component.FireComponent;
import component.GhostComponent;
import component.PlayerComponent;
import javafx.scene.paint.Color;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

public class GameFactory implements EntityFactory {
    @Spawns("brick")
    public Entity newBrick(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(GameType.BRICK)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new PhysicsComponent())
                .build();
    }
    @Spawns("wood")
    public Entity newWood(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(GameType.WOOD)
                .view("wood.png")
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("player")
    public Entity newPlayer(SpawnData data) {
        PhysicsComponent physics = new PhysicsComponent();
        physics.setFixtureDef(new FixtureDef().friction(0).density(0.1f));
        BodyDef bd = new BodyDef();
        bd.setFixedRotation(true);
        bd.setType(BodyType.DYNAMIC);
        physics.setBodyDef(bd);

        return FXGL.entityBuilder(data)
                .type(GameType.PLAYER)
                .viewWithBBox(new Rectangle(60, 60, Color.BLUE))
                .with(physics)
                .with(new PlayerComponent())
                .buildAndAttach();
    }
    @Spawns("Bomb")
    public Entity newBomb(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(GameType.BOMB)
                .viewWithBBox("bomb.png")
                .with(new BombComponent(15))
                .atAnchored(new Point2D(0, 0), new Point2D(data.getX() , data.getY() ))
                .with(new CollidableComponent(true))
                .zIndex(-1)
                .build();
    }
    @Spawns("Fire")
    public Entity newFire(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(GameType.FIRE)
                .viewWithBBox("fire.png")
                .with(new FireComponent())
                .atAnchored(new Point2D(0, 0), new Point2D(data.getX() , data.getY() ))
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("Ghost")
    public Entity newGhost(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(GameType.GHOST)
                .viewWithBBox("ghost.png")
                .with(new GhostComponent())
                .with(new CollidableComponent(true))
                .build();
    }

}
