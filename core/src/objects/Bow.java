package objects;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class Bow extends Item {

    private AssetManager assets;
    private Texture texture;
    private ArrayList<Arrow> arrows = new ArrayList<Arrow>();

    public Bow(AssetManager assets){
        this.assets = assets;
        texture = assets.get("items/weapon_bow.png");
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
}
