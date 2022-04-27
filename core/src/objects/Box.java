package objects;


import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.physics.box2d.*;
import utils.Constants;

import java.util.ArrayList;

public class Box {

    private int health = 4;
    private World world;
    private TiledMapTileLayer.Cell cell1, cell2;
    private ArrayList<Body> items;

    public Box(ArrayList<Body> items, World world, TiledMapTileLayer.Cell cell1, TiledMapTileLayer.Cell cel2, float x, float y){
        this.items = items;
        this.world = world;
        this.cell1 = cell1;
        this.cell2 = cel2;
        createBody(world, x, y);
    }

    public void setHealth(int health){
        this.health = health;
    }

    public int getHealth(){
        return this.health;
    }

    public World getWorld(){
        return world;
    }

    public TiledMapTileLayer.Cell getCell1(){
        return this.cell1;
    }
    public TiledMapTileLayer.Cell getCell2(){
        return this.cell2;
    }

    private void createBody(World world, float x, float y){
        Body pBody;
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.StaticBody;

        def.position.set(x+8, y+8);
        def.fixedRotation = true;
        pBody = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(16/2, 16/2);

        FixtureDef fd = new FixtureDef();
        fd.shape = shape;
        fd.density = 1.0f;
        fd.filter.categoryBits = Constants.BIT_BREAKABLE;
        fd.filter.maskBits = Constants.BIT_PLAYER | Constants.BIT_WALL | Constants.BIT_SENSOR;
        pBody.createFixture(fd);
        shape.dispose();
    }
}
