package managers;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

public class PhysicManager {

    private static ArrayList<Body> bodys = new ArrayList<Body>();
    private static ArrayList<Body> bodyTransform = new ArrayList<Body>();
    private static ArrayList<Vector2> vecTransform = new ArrayList<Vector2>();

    public static void addBody(Body body){
        bodys.add(body);
    }

    public static void destroyBodys(World world){
        for(Body b : bodys){
            world.destroyBody(b);
        }
        bodys.clear();
    }

    public static void addTransformBody(Body body, Vector2 pos){
        bodyTransform.add(body);
        vecTransform.add(pos);
    }

    public static void transformBody(World world){
        for(int i = 0; i < bodyTransform.size(); i++){
            bodyTransform.get(i).setTransform(vecTransform.get(i).x, vecTransform.get(i).y, 0);
        }
        bodyTransform.clear();
        vecTransform.clear();
    }

    public static void clear(){
        bodys.clear();
        bodyTransform.clear();
        vecTransform.clear();
    }
}
