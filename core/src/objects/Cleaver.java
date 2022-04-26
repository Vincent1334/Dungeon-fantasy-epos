package objects;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import managers.PhysicManager;
import utils.BodyBuilder;
import utils.Constants;

import java.util.ArrayList;

public class Cleaver extends Item{

    private AssetManager assets;
    private float activeTime = 0;
    private World world;
    private Entity owner;
    private Body body;
    private ArrayList<Body> hitObjects = new ArrayList<Body>();

    public Cleaver(AssetManager assets, World world, float x, float y, Entity owner){
        this.assets = assets;
        this.world = world;
        this.owner = owner;
        body = BodyBuilder.createBox(world, x+20, y, 20, 20, false, true, true, this, Constants.BIT_SENSOR, Constants.BIT_BREAKABLE, (short) -1);

    }

    public Cleaver(AssetManager assets){
        this.assets = assets;
    }

    public void use(){
        activeTime = 100;
    }

    public Entity owner(){
        return this.owner;
    }

    public ArrayList<Body> getHitObjects(){
        return hitObjects;
    }

    @Override
    public void update(SpriteBatch batch) {
        if(activeTime != 0){
            activeTime --;
            super.angle = Math.sin(activeTime/2)*20;
        }else{
            hitObjects.clear();
        }

        //Update Physics and Events
        body.setLinearVelocity(owner.getBody().getLinearVelocity());
        for(Body bo : hitObjects){
            if(bo.getUserData() instanceof Box){
                Box tmp = (Box) bo.getUserData();
                tmp.setHealth(tmp.getHealth()-2);
                if(tmp.getHealth() <= 0){
                    PhysicManager.addBody(bo);
                    tmp.getCell1().setTile(null);
                    tmp.getCell2().setTile(null);
                }
            }
        }
    }

    @Override
    public void setAngle(double angle){
        if(activeTime == 0){
            super.angle = angle;
        }
    }

    @Override
    public Texture getTexture() {
        return assets.get("items/weapon_cleaver.png");
    }
}
