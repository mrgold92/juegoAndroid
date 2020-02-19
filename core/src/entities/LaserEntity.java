package entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.BodyEditorLoader;


public class LaserEntity extends Actor {

    private Texture textura;
    private World world;
    public Body body;
    private Vector2 posicion;
    private FixtureDef fixture;


    public LaserEntity(Texture textura, World world, Vector2 posicion) {
        this.textura = textura;
        this.world = world;
        this.posicion = posicion;


        BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("laser.json"));


        //fisicas
        BodyDef def = new BodyDef();
        def.position.set(posicion.x, posicion.y);
        def.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(def);
        body.setUserData("laser");


        //forma
        fixture = new FixtureDef();
        fixture.density = 1;
        fixture.friction = 0.5f;
        fixture.restitution = 0.5f;


        float scala = .1f;
        loader.attachFixture(body, "laser", fixture, scala);
        setSize(50f, 50f);


    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        setPosition(posicion.x, posicion.y);
        batch.draw(textura, getX(), getY(), getWidth(), getHeight());


    }

    public void detach() {
        world.destroyBody(body);


    }

    @Override
    public void act(float delta) {


        super.act(delta);
        body.setLinearVelocity(2f, 0);

    }

}
