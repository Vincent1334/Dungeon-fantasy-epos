package managers;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

public class PhysicManager {

    private static ArrayList<Body> bodys = new ArrayList<Body>();

    public static void addBody(Body body){
        bodys.add(body);
    }

    public static void destroyBodys(World world){
        for(Body b : bodys){
            world.destroyBody(b);
        }
        bodys.clear();
    }

    public static void clear(){
        bodys.clear();
    }
}
