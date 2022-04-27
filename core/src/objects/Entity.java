package objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import utils.Constants;

public class Entity {

    protected float x;
    protected float y;
    private int health = 5;
    private int id;
    private float speed = 20f;
    private boolean flip = false;
    protected boolean[] direction = new boolean[4];
    protected float time = 0;
    private float timeStamp = 0;
    private Vector2[] waypoints;
    int activePoint = 0;
    private float delay = 0;
    private boolean idle = false;
    protected World world;

    //Animation
    private Animation entityRun, entityRest;
    AssetManager assets;

    //Physics
    private Body body;

    public Entity(AssetManager assets, int x, int y, World world, String type, int healt){
        this.assets = assets;
        this.x = x;
        this.y = y;
        this.health = health;
        this.world = world;
        body = createCircle(world, x, y, 2);
        body.setLinearDamping(20f);
        body.setAngularDamping(1.3f);

        //Animation
        entityRun = new Animation(1/10f, ((TextureAtlas)assets.get("characters/" + type + ".atlas")).findRegions(type + "_run_anim"));
        entityRest = new Animation(1/10f, ((TextureAtlas)assets.get("characters/" + type + ".atlas")).findRegions(type + "_idle_anim"));
        entityRun.setPlayMode(Animation.PlayMode.LOOP);
        entityRest.setPlayMode(Animation.PlayMode.LOOP);
    }

    public void update(){
        //Update Time
        time += Gdx.graphics.getDeltaTime();
        //Update move
        move();

    }

    void move(){
        if(delay == 0){
            //Move
            if(direction[0]){
                body.setLinearVelocity(body.getLinearVelocity().x, speed);
                y = body.getPosition().y;
            }
            if(direction[1]){
                body.setLinearVelocity(-speed, body.getLinearVelocity().y);
                x = body.getPosition().x-8;
                flip = true;
            }
            if(direction[2]){
                body.setLinearVelocity(body.getLinearVelocity().x, -speed);
                y = body.getPosition().y;

            }
            if(direction[3]){
                body.setLinearVelocity(speed, body.getLinearVelocity().y);
                x = body.getPosition().x-8;
                flip = false;
            }
        }else if(timeStamp == 0){
            Vector2 vector = new Vector2(waypoints[activePoint].x-x, waypoints[activePoint].y-y);
            vector.nor();
            body.setLinearVelocity(vector.x*speed, vector.y*speed);
            x = body.getPosition().x;
            y = body.getPosition().y;
            flip = (vector.x < 0) ? true : false;
            if(Math.abs(x- waypoints[activePoint].x) < 0.3f && Math.abs(y- waypoints[activePoint].y) < 0.3f){
                body.setLinearVelocity(0, 0);
                setNextWaypoint();
            }
        }
        if(time-timeStamp > delay) timeStamp = 0;

    }

    public void setNextWaypoint(){
        if(activePoint+1 < waypoints.length){
            activePoint += 1;
        }else{
            activePoint = 0;
        }
        timeStamp = time;
        idle = true;
    }

    public void setWaypoints(Vector2[] points, float delay){
        this.waypoints = points;
        this.delay = delay;
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    public void setHealth(int health){
        this.health = health;
    }

    public void setSpeed(float speed){
        this.speed = speed;
    }

    public float getX(){
        return x;
    }

    public int getHealth(){
        return this.health;
    }

    public float getY(){
        return y;
    }

    public boolean flip(){
        return flip;
    }

    public TextureRegion animate(){
        if(direction[0] || direction[1] ||direction [2] || direction[3]){
            return (TextureRegion) entityRun.getKeyFrame(time, true);
        }
        return ((TextureRegion) entityRest.getKeyFrame(time, true));
    }

    public Body createCircle(World world, float x, float y, float radius) {
        Body pBody;
        BodyDef def = new BodyDef();

        def.type = BodyDef.BodyType.DynamicBody;

        def.position.set(x+8 , y );
        def.fixedRotation = true;
        pBody = world.createBody(def);

        CircleShape shape = new CircleShape();
        shape.setRadius(radius );

        FixtureDef fd = new FixtureDef();
        fd.shape = shape;
        fd.density = 1.0f;
        fd.filter.categoryBits = Constants.BIT_PLAYER;
        fd.filter.maskBits = Constants.BIT_WALL | Constants.BIT_SENSOR | Constants.BIT_BREAKABLE;
        fd.filter.groupIndex = 0;
        pBody.createFixture(fd).setUserData(this);
        shape.dispose();
        return pBody;
    }
}
