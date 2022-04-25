package stage;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;
import ok.cool.Main;

public class Load implements Screen {

    private AssetManager assets;
    private Main main;

    private SpriteBatch batch;
    private Animation animation;

    public Load(Main main, AssetManager assets){
        this.assets = assets;
        this.main = main;
        batch = new SpriteBatch();
    }

    @Override
    public void show() {
        assets.load("characters/wizzard.atlas", TextureAtlas.class);
        assets.load("map/wall_top_right.png", Texture.class);
        assets.finishLoadingAsset("characters/wizzard.atlas");
        assets.finishLoadingAsset("map/wall_top_right.png");
        assets.load("characters/knight.atlas", TextureAtlas.class);
        assets.load("items/weapon_bow.png", Texture.class);
        assets.load("items/weapon_cleaver.png", Texture.class);
        assets.load("items/weapon_arrow.png", Texture.class);
        assets.load("ui/ui_heart_empty.png", Texture.class);
        assets.load("ui/ui_heart_full.png", Texture.class);
        assets.load("sounds/pew.mp3", Sound.class);
        assets.load("sounds/blop.wav", Sound.class);

        animation = new Animation(1/10f, ((TextureAtlas)assets.get("characters/wizzard.atlas")).findRegions("wizzard_run_anim"));
        animation.setPlayMode(Animation.PlayMode.LOOP);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        assets.update();

        batch.begin();
        for(int i = 0; i < 57; i++){
            batch.draw((Texture) assets.get("map/wall_top_right.png"), i*16, 30, 32, 32);
        }
        batch.draw((TextureRegion) animation.getKeyFrame(delta), assets.getProgress()*900, 35, 32, 56);
        batch.end();

        if(assets.isFinished()){
            main.nextScreen("level1");
        }
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
    public void dispose() {
        batch.dispose();
    }
}
