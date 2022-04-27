package objects;

import com.badlogic.gdx.physics.box2d.*;
import utils.Constants;

public class HitBox {

    public HitBox(World world, float x, float y, float width, float height){
        createBody(world, x, y, width, height);
    }

    private void createBody(World world, float x, float y, float width, float height){
        Body pBody;
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.StaticBody;

        def.position.set(x+8, y);
        def.fixedRotation = true;
        pBody = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width/2, height/2);

        FixtureDef fd = new FixtureDef();
        fd.shape = shape;
        fd.density = 1.0f;
        fd.filter.categoryBits = Constants.BIT_WALL;
        fd.filter.maskBits = Constants.BIT_PLAYER | Constants.BIT_WALL | Constants.BIT_SENSOR;
        pBody.createFixture(fd);
        shape.dispose();

    }
}
