package objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Item {

    private double angle = 0;
    private boolean active = false;
    private boolean firstUse = true;

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

    public boolean isActive(){
        return active;
    }

    public abstract void update(SpriteBatch batch);

    public abstract Texture getTexture();
}
