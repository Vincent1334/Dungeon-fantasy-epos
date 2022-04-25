package objects;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.*;


public class Arrow {

    private AssetManager assets;
    private World world;
    private Body body;
    private int lifetime = 0;
    private float x, y, angle;

    public Arrow(AssetManager assets, World world, float x, float y, float targetX, float targetY, float angle){
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.assets = assets;
        this.world = world;
        body = createCircle(world, x+5, y+4, 1);

        float vx = targetX-x;
        if(vx > 450) vx = -vx;
        float vy = targetY-y;
        if(vy < 450) vy = -vy;
        body.setLinearVelocity(vx, vy);
    }

    public void update(){
        x = body.getPosition().x;
        y = body.getPosition().y;
        if(!body.isAwake()) lifetime ++;

    }

    public void kill(){
        world.destroyBody(body);
    }

    public Texture getTexture(){
        return assets.get("items/weapon_arrow.png");
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }

    public int getLifeTime(){
        return lifetime;
    }

    public float getAngle() {
        return angle;
    }

    public Body createCircle(com.badlogic.gdx.physics.box2d.World world, float x, float y, float radius) {
        Body pBody;
        BodyDef def = new BodyDef();

        def.type = BodyDef.BodyType.DynamicBody;

        def.position.set(x+8 , y );
        def.fixedRotation = false;
        pBody = world.createBody(def);

        CircleShape shape = new CircleShape();
        shape.setRadius(radius );

        FixtureDef fd = new FixtureDef();
        fd.shape = shape;
        fd.isSensor = true;


        pBody.createFixture(fd).setUserData(this);
        shape.dispose();
        return pBody;
    }
}
