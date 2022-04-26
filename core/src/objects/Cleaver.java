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

public class Cleaver extends Item{

    private AssetManager assets;
    private float activeTime = 0;
    private World world;
    private Entity owner;
    private Body body;
    private boolean dangerous = false;


    public Cleaver(AssetManager assets, World world, float x, float y, Entity owner){
        this.assets = assets;
        this.world = world;
        this.owner = owner;
        body = BodyBuilder.createBox(world, x, y, 20, 20, false, true, true, this, Constants.BIT_ITEM, Constants.BIT_ITEM, (short) 1);

    }

    public Cleaver(AssetManager assets){
        this.assets = assets;
    }

    public void use(){
        activeTime = 100;
    }

    public boolean isDangerous(){
        return this.dangerous;
    }

    public Entity owner(){
        return this.owner;
    }

    @Override
    public void update(SpriteBatch batch) {
        if(activeTime != 0){
            activeTime --;
            super.angle = Math.sin(activeTime/2)*20;
            dangerous = true;
        }else{
            dangerous = false;
        }
        PhysicManager.addTransformBody(body, new Vector2(owner.getX()+8, owner.getY()));
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
