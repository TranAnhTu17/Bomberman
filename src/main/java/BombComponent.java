import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.BoundingBoxComponent;
import javafx.util.Duration;

import java.util.ArrayList;

public class BombComponent extends Component {
    private int radius;
    private ArrayList<Entity> listFire = new ArrayList<Entity>();

    public BombComponent(int radius) {
        this.radius = radius;
    }

    public void explode() {
        listFire.add(FXGL.spawn("Fire", new SpawnData(entity.getX(), entity.getY())));
        listFire.add(FXGL.spawn("Fire", new SpawnData(entity.getX() + 32, entity.getY())));
        listFire.add(FXGL.spawn("Fire", new SpawnData(entity.getX() - 32, entity.getY())));
        listFire.add(FXGL.spawn("Fire", new SpawnData(entity.getX(), entity.getY() + 32)));
        listFire.add(FXGL.spawn("Fire", new SpawnData(entity.getX(), entity.getY() - 32)));


        FXGL.getGameTimer().runOnceAfter(() -> {
            for(int i=0;i<listFire.size();i++) {
                listFire.get(i).removeFromWorld();
            }
        }, Duration.seconds(0.3));


        entity.removeFromWorld();
    }

}
