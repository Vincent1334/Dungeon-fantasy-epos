package objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.physics.box2d.*;
import ok.cool.Main;
import utils.BodyBuilder;

public class Player extends Entity implements InputProcessor {

    private Item item;

    public Player(AssetManager assets, int x, int y, World world){
        super(assets, x, y, world, "knight", 5);

    }

    public Item getItem(){
        return item;
    }

    public void setItem(Item item){
        this.item = item;
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.W){
            direction[0] = true;
        }
        if(keycode == Input.Keys.A){
            direction[1] = true;
        }
        if(keycode == Input.Keys.S){
            direction[2] = true;
        }
        if(keycode == Input.Keys.D){
            direction[3] = true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Input.Keys.W){
            direction[0] = false;
        }
        if(keycode == Input.Keys.A){
            direction[1] = false;
        }
        if(keycode == Input.Keys.S){
            direction[2] = false;
        }
        if(keycode == Input.Keys.D){
            direction[3] = false;
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(item instanceof Bow){
           ((Bow)item).shoot(world, x, y, screenX, screenY);
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        if(item != null){
            float radians = (float) Math.atan2(screenY-y, screenX-x);
            item.setAngle(Math.toDegrees(-radians)-5);
        }
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }


}
