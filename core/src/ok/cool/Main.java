package ok.cool;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import stage.Level1;
import stage.Load;

public class Main extends ApplicationAdapter {

    Load load;
    Screen activeScreen;
    AssetManager assets;

    float time;

    @Override
    public void create(){
        assets = new AssetManager();

        load = new Load(this, assets);

        activeScreen = load;
        activeScreen.show();
    }

    @Override
    public void render(){
        time += Gdx.graphics.getDeltaTime();
        activeScreen.render(time);
    }

    @Override
    public void dispose(){
        load.dispose();
    }

    public void nextScreen (String screen){
        if(screen.equals("level1")){
            activeScreen.dispose();
            System.out.println(assets.getLoadedAssets());
            activeScreen = new Level1(this, assets);
            activeScreen.show();
        }
    }


}
