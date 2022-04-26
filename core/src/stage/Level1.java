package stage;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import managers.Contact;
import managers.PhysicManager;
import objects.*;
import ok.cool.Main;
import utils.BodyBuilder;
import utils.Constants;

import java.util.ArrayList;

public class Level1 implements Screen {

    Main main;

    //Render
    SpriteBatch batch, uibatch;
    ShapeRenderer shapes;

    //Camera
    OrthographicCamera camera;

    //Map
    TiledMapRenderer tiledMapRenderer;
    TiledMap tiledMap;

    //Items
    ArrayList<Body> items = new ArrayList<Body>();

    //Handle Inputs
    InputMultiplexer inputMultiplexer = new InputMultiplexer();

    //Player and Objectsa
    Player player;
    ArrayList<Entity> entitys = new ArrayList<Entity>();

    //Physics
    World world;
    RayHandler handler;
    Contact contact;
    Box2DDebugRenderer boxRenderer;
    ArrayList<PointLight> lights = new ArrayList<PointLight>();

    //Assets
    AssetManager assets;

    public Level1(Main main, AssetManager assets){
        this.main = main;
        this.assets = assets;
    }

    @Override
    public void show() {
        //Render
        batch = new SpriteBatch();
        uibatch = new SpriteBatch();
        shapes = new ShapeRenderer();

        //Physics
        contact = new Contact();
        world = new World(new Vector2(0, 0), true);
        world.setContactListener(contact);
        boxRenderer = new Box2DDebugRenderer();
        handler = new RayHandler(world);
        handler.setAmbientLight(0f, 0f, 0f, 0.7f);

        //Load Map ---------------------------------------------------------------------------------------------------->
        tiledMap = new TmxMapLoader().load("map/tutorial_map.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        TiledMapTileLayer walls = (TiledMapTileLayer)tiledMap.getLayers().get("HitBox");
        for(int x = 0; x < walls.getWidth(); x++){
            for(int y = 0; y < walls.getHeight(); y++){
                if(walls.getCell(x, y) != null && walls.getCell(x, y).getTile().getId() == 867){
                    BodyBuilder.createBox(world, x*16, y*16+10, 16, 16, true, false, false, "", Constants.BIT_WALL, (short)(Constants.BIT_SENSOR | Constants.BIT_PLAYER), (short)1);
                }
            }
        }
        BodyBuilder.createBox(world, 26*16, 20*16, 25*16, 16, true, false, true, "Wall_down");

        //Load Objects
        TiledMapTileLayer objects = (TiledMapTileLayer)tiledMap.getLayers().get("Objekte");
        for(int x = 0; x < objects.getWidth(); x++){
            for(int y = 0; y < objects.getHeight(); y++){
                if(objects.getCell(x, y) != null && objects.getCell(x, y).getTile().getId() == 37){
                    BodyBuilder.createBox(world, x*16, y*16+10, 16, 35, true, false, false, "", Constants.BIT_WALL, (short)(Constants.BIT_WALL | Constants.BIT_PLAYER), (short) 1);
                    lights.add(new PointLight(handler, 5000, Color.valueOf("#ebb134"), 40, x*16+8, y*16));
                    lights.get(lights.size()-1).setContactFilter((short)(Constants.BIT_PLAYER | Constants.BIT_WALL), (short) 1, (short)(Constants.BIT_PLAYER | Constants.BIT_WALL));
                }
                if(objects.getCell(x, y) != null && objects.getCell(x, y).getTile().getId() == 659){
                    lights.add(new PointLight(handler, 5000, Color.valueOf("#7dc986"), 20, x*16+8, y*16+5));
                    lights.get(lights.size()-1).setContactFilter((short)(Constants.BIT_PLAYER | Constants.BIT_WALL), (short) 1, (short)(Constants.BIT_PLAYER | Constants.BIT_WALL));
                }
                if(objects.getCell(x, y) != null && objects.getCell(x, y).getTile().getId() == 627){
                    BodyBuilder.createBox(world, x*16, y*16+10, 16, 16, true, false, false, new Box(items, world, objects.getCell(x, y), objects.getCell(x, y+1)), Constants.BIT_BREAKABLE, (short)(Constants.BIT_PLAYER | Constants.BIT_SENSOR), (short) 1);
                }
            }
        }
        //------------------------------------------------------------------------------------------------------------->

        //Create Player and Objects
        player = new Player(assets, 500, 400, world);
        entitys.add(new Entity(assets, 470, 440, world, "wizzard", 1000000));
        entitys.get(0).setSpeed(10);
        entitys.get(0).setWaypoints(new Vector2[]{new Vector2(470, 440), new Vector2(501, 428), new Vector2(462, 414), new Vector2(435, 435)}, 5);

        items.add(BodyBuilder.createBox(world, 240, 490, 7, 25, false, true, true, new Cleaver(assets)));

        //Create Camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.zoom = 0.25f;
        camera.position.set(player.getX(), player.getY(), 0.25f);
        camera.update();

        //Handle Inputs
        inputMultiplexer.addProcessor(player);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    public void update(float delta){
        //Update Player
        player.update();
        for(int i = 0; i < entitys.size(); i++) entitys.get(i).update();

        //Update Physic
        PhysicManager.destroyBodys(world);
        PhysicManager.transformBody(world);
        world.step(delta, 6, 2);
        handler.update();

        //update View
        camera.position.set(camera.position.x+(camera.position.x-player.getX())*-0.05f, camera.position.y+(camera.position.y-player.getY())*-0.05f, 0.25f);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        shapes.setProjectionMatrix(camera.combined);
        handler.setCombinedMatrix(camera);
        tiledMapRenderer.setView(camera);
    }

    @Override
    public void render(float delta) {
        //Update
        update(delta);

        ScreenUtils.clear(0, 0, 0, 1);

        //Render Map
        tiledMapRenderer.render();

        //Render Physics
        handler.render();

        //Render Entitys
        batch.begin();
        for(int i = 0; i < entitys.size(); i++){
            batch.draw(entitys.get(i).animate(), entitys.get(i).flip() ? entitys.get(i).getX()+16 : entitys.get(i).getX(), entitys.get(i).getY(), entitys.get(i).flip() ? -16 : 16, 28);
        }
        batch.end();

        //Render items
        batch.begin();
        ArrayList<Body> rem = new ArrayList<Body>();
        for(Body item : items){
            if(item.getUserData() != null){

                batch.draw(((Item)item.getUserData()).getTexture(), item.getPosition().x-3, item.getPosition().y+(float)Math.sin(delta*10)-13, ((Item)item.getUserData()).getTexture().getWidth(), ((Item)item.getUserData()).getTexture().getHeight());

                if(((Item)item.getUserData()).isActive()){
                    if(player.getItem() == null){
                        if(item.getUserData() instanceof Cleaver) item.setUserData(new Cleaver(assets, world, player.getX(), player.getY(), player));

                        player.setItem((Item)item.getUserData());
                        world.destroyBody(item);
                        ((Sound)assets.get("sounds/blop.wav")).play(1);
                        rem.add(item);
                    }else{
                        ((Item)item.getUserData()).setActive(false);
                    }
                }
            }
        }
        items.removeAll(rem);
        batch.end();


        //Render Player
        batch.begin();
        batch.draw(player.animate(), player.flip() ? player.getX()+16 : player.getX(), player.getY(), player.flip() ? -16 : 16, 28);
        //Render Player Item
        if(player.getItem() != null){
            Sprite item = new Sprite(player.getItem().getTexture());
            item.rotate((float)player.getItem().getAngle());
            item.setPosition(player.flip() ? player.getX()+5 : player.getX()+10, player.getY());
            item.draw(batch);
            player.getItem().update(batch);
        }
        batch.end();

        //Render Map bottom
        tiledMap.getLayers().get("Unten").setVisible(true);
        tiledMap.getLayers().get("Unten").setOpacity(contact.wallOpacity);
        tiledMapRenderer.render(new int[]{2});
        tiledMap.getLayers().get("Unten").setVisible(false);

        //Render UI
        uibatch.begin();
        for(int i = 0; i < player.getHealth(); i++){
            uibatch.draw((Texture) assets.get("ui/ui_heart_full.png"), 10+i*48, 850, 48, 48);
        }
        uibatch.end();

        //System.out.println("x: " + player.getX());
        //System.out.println("y: " + player.getY());
        //boxRenderer.render(world, camera.combined);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose () {
        batch.dispose();
        tiledMap.dispose();
        world.dispose();
    }
}
