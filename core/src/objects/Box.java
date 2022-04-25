package objects;


import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import java.util.ArrayList;

public class Box {

    private int health = 4;
    private World world;
    private TiledMapTileLayer.Cell cell1, cell2;
    private ArrayList<Body> items;

    public Box(ArrayList<Body> items, World world, TiledMapTileLayer.Cell cell1, TiledMapTileLayer.Cell cel2){
        this.items = items;
        this.world = world;
        this.cell1 = cell1;
        this.cell2 = cel2;
    }

    public void setHealth(int health){
        this.health = health;
    }

    public int getHealth(){
        return this.health;
    }

    public World getWorld(){
        return world;
    }

    public TiledMapTileLayer.Cell getCell1(){
        return this.cell1;
    }
    public TiledMapTileLayer.Cell getCell2(){
        return this.cell2;
    }

}
