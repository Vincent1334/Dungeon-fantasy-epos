package utils;

import com.badlogic.gdx.physics.box2d.*;

public class BodyBuilder {

    public static Body createBox(World world, float x, float y, int width, int height, boolean isStatic, boolean fixedRotation, boolean isSensor, Object userData) {
        Body pBody;
        BodyDef def = new BodyDef();

        if(isStatic)
            def.type = BodyDef.BodyType.StaticBody;
        else
            def.type = BodyDef.BodyType.DynamicBody;

        def.position.set(x+8, y);
        def.fixedRotation = fixedRotation;
        pBody = world.createBody(def);
        pBody.setUserData(userData);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2);

        FixtureDef fd = new FixtureDef();
        fd.shape = shape;
        fd.density = 1.0f;
        fd.isSensor = isSensor;
        fd.filter.categoryBits = Constants.BIT_WALL;
        fd.filter.maskBits = Constants.BIT_PLAYER | Constants.BIT_WALL | Constants.BIT_SENSOR;
        fd.filter.groupIndex = 0;
        pBody.createFixture(fd).setUserData(userData);
        shape.dispose();
        return pBody;
    }

    public static Body createBox(final World world, float x, float y, float w, float h,
                                 boolean isStatic, boolean canRotate, short cBits, short mBits, short gIndex) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.fixedRotation = canRotate;
        bodyDef.position.set(x+8 , y );

        if(isStatic) {
            bodyDef.type = BodyDef.BodyType.StaticBody;
        } else {
            bodyDef.type = BodyDef.BodyType.DynamicBody;
        }

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(w  / 2, h / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.filter.categoryBits = cBits; // Is a
        fixtureDef.filter.maskBits = mBits; // Collides with
        fixtureDef.filter.groupIndex = gIndex;

        return world.createBody(bodyDef).createFixture(fixtureDef).getBody();
    }

    public static Body createCircle(final World world, float x, float y, float r,
                                    boolean isStatic, boolean canRotate, short cBits, short mBits, short gIndex) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.fixedRotation = canRotate;
        bodyDef.position.set(x+8 , y );

        if(isStatic) {
            bodyDef.type = BodyDef.BodyType.StaticBody;
        } else {
            bodyDef.type = BodyDef.BodyType.DynamicBody;
        }

        CircleShape shape = new CircleShape();
        shape.setRadius(r );

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.filter.categoryBits = cBits; // Is a
        fixtureDef.filter.maskBits = mBits; // Collides with
        fixtureDef.filter.groupIndex = gIndex;

        return world.createBody(bodyDef).createFixture(fixtureDef).getBody();
    }
}
