package managers;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import objects.*;

public class Contact implements ContactListener {

    public float wallOpacity = 1;

    @Override
    public void beginContact(com.badlogic.gdx.physics.box2d.Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if(fa == null || fb == null) return;

        checkWallDownEvent(fa, fb, 0.4f);
        checkItems(fa, fb);
        checkArrows(fa, fb);
        checkBoxs(fa, fb);
    }

    @Override
    public void endContact(com.badlogic.gdx.physics.box2d.Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if(fa == null || fb == null) return;

        checkWallDownEvent(fa, fb, 1);
    }

    @Override
    public void preSolve(com.badlogic.gdx.physics.box2d.Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(com.badlogic.gdx.physics.box2d.Contact contact, ContactImpulse impulse) {

    }

    public void checkWallDownEvent(Fixture fa, Fixture fb, float opacity){
        if(fa.getUserData() != null && fa.getUserData() instanceof String){
            String data = (String) fa.getUserData();
            if(data.equals("Wall_down") && !fb.isSensor()) wallOpacity = opacity;
        }
        if(fb.getUserData() != null && fb.getUserData() instanceof String){
            String data = (String) fb.getUserData();
            if(data.equals("Wall_down") && !fa.isSensor()) wallOpacity = opacity;
        }
    }

    public void checkItems(Fixture fa, Fixture fb){
        if(fa.getUserData() != null && fa.getUserData() instanceof Item){
            ((Item)fa.getUserData()).setActive(true);
        }
        if(fb.getUserData() != null && fb.getUserData() instanceof Item){
            ((Item)fb.getUserData()).setActive(true);
        }
    }

    public void checkBoxs(Fixture fa, Fixture fb){
        boolean destroy = false;
        Box tmp;
        if(fa.getUserData() != null && fa.getUserData() instanceof Box) {
            tmp = (Box) fa.getUserData();
            destroy = true;
            if(fb != null && fb.getUserData() instanceof Entity) return;
        }else if(fb.getUserData() != null && fb.getUserData() instanceof Box) {
            tmp = (Box) fb.getUserData();
            destroy = false;
            if(fa != null && fa.getUserData() instanceof Entity) return;
        }else return;

        tmp.setHealth(tmp.getHealth()-1);
        if(tmp.getHealth() == 0){
            PhysicManager.addBody(destroy ? fa.getBody() : fb.getBody());
            tmp.getCell1().setTile(null);
            tmp.getCell2().setTile(null);
        }
    }

    public void checkArrows(Fixture fa, Fixture fb){
        if(fa.getUserData() != null && fa.getUserData() instanceof Arrow){
            if(fb != null && fb.getUserData() instanceof Arrow) return;
            fa.getBody().setLinearVelocity(0, 0);
        }
        if(fb.getUserData() != null && fb.getUserData() instanceof Arrow){
            if(fa != null && fa.getUserData() instanceof Arrow) return;
            fb.getBody().setLinearVelocity(0, 0);
        }
    }
}
