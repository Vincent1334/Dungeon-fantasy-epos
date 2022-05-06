package objects;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;
import org.w3c.dom.Text;
import utils.Constants;

import java.util.ArrayList;

public class Bow extends Item {

    private AssetManager assets;
    private Texture texture;
    private ArrayList<Arrow> arrows = new ArrayList<Arrow>();

    public Bow(AssetManager assets, World world, float x, float y){
        this.assets = assets;
        texture = assets.get("items/weapon_bow.png");
        createBody(world, x, y);
    }

    public void shoot(World world, float x, float y, float screenX, float screenY){
        arrows.add(new Arrow(assets, world, x, y, screenX, screenY, (float)getAngle()));
        ((Sound)assets.get("sounds/pew.mp3")).play(1);
    }

    @Override
    public void update(SpriteBatch batch) {
        Sprite arrow = new Sprite((Texture) assets.get("items/weapon_arrow.png"));
        ArrayList<Arrow> rem = new ArrayList<Arrow>();
        for(Arrow arrowl : arrows){
            arrowl.update();
            arrow.rotate(arrowl.getAngle()-90);
            arrow.setPosition(arrowl.getX(), arrowl.getY());
            arrow.draw(batch);
            arrow.rotate(-(arrowl.getAngle()-90));
            if(arrowl.getLifeTime() == 150){
                arrowl.kill();
                rem.add(arrowl);
            }
        }
        arrows.removeAll(rem);
    }

    @Override
    public Texture getTexture() {
        return texture;
    }

    private void createBody(World world, float x, float y){
        Body pBody;
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.StaticBody;

        def.position.set(x+8, y+8);
        def.fixedRotation = true;
        pBody = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(7/2, 25/2);

        FixtureDef fd = new FixtureDef();
        fd.shape = shape;
        fd.density = 1.0f;
        fd.isSensor = true;
        fd.filter.categoryBits = Constants.BIT_SENSOR;
        fd.filter.maskBits = Constants.BIT_PLAYER | Constants.BIT_WALL;
        pBody.createFixture(fd).setUserData(this);
        shape.dispose();
        super.body = pBody;
    }
}
