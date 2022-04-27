package objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;

public abstract class Item {

    protected double angle = 0;
    private boolean active = false;
    private boolean firstUse = true;
    protected Body body;

    public Item(){
    }

    public void setAngle(double angle){
        this.angle = angle;
    }

    public double getAngle(){
        return angle;
    }

    public void setActive(boolean active){
        this.active = active;
    }

    public boolean isFirstUse(){
        if(firstUse){
            firstUse = false;
            return this.isFirstUse();
        }
        return false;
    }

    public Body getBody(){
        return body;
    }

    public boolean isActive(){
        return active;
    }

    public abstract void update(SpriteBatch batch);

    public abstract Texture getTexture();
}
