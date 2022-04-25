package objects;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import utils.BodyBuilder;

public class Cleaver extends Item{

    private AssetManager assets;
    private float activeTime = 0;
    private World world;
    private Entity owner;
    private Body body;


    public Cleaver(AssetManager assets, World world, float x, float y, Entity owner){
        this.assets = assets;
        this.world = world;
        this.owner = owner;
        body = BodyBuilder.createBox(world, x, y, 20, 20, true, false, true, this);

    }

    public Cleaver(AssetManager assets){
        this.assets = assets;
    }

    public void use(){
        activeTime = 500;
    }

    public Entity owner(){
        return this.owner;
    }

    @Override
    public void update(SpriteBatch batch) {
        activeTime --;
        Sprite cleaver = new Sprite((Texture) assets.get("items/weapon_cleaver.png"));
        if(activeTime != 0) cleaver.rotate(1);
        cleaver.setPosition(cleaver.getX(), cleaver.getY());
        cleaver.draw(batch);
        body.setLinearVelocity(2, 2);
    }

    @Override
    public Texture getTexture() {
        return assets.get("items/weapon_cleaver.png");
    }
}
