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
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.mygdx.game.BodyEditorLoader;

public class AliadoEntity extends Actor {

    private Texture textura;
    private World world;
    private Body body;
    private Vector2 posicion;
    private boolean limite;
    public float movement = -1.9f;

    public AliadoEntity(Texture textura, World world, Vector2 posicion) {
        this.textura = textura;
        this.world = world;
        this.posicion = posicion;


        BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("aliado.json"));

        //fisicas
        BodyDef def = new BodyDef();
        def.position.set(posicion.x, posicion.y);
        def.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(def);
        body.setUserData("aliado");


        //forma
      /*  PolygonShape enemigoShape = new PolygonShape();
        enemigoShape.setAsBox(0.5f, 0.5f);
        fixture = body.createFixture(enemigoShape, 1);
        fixture.setUserData("enemigo");
        enemigoShape.dispose();*/

        FixtureDef fixture = new FixtureDef();
        fixture.density = 1;
        fixture.friction = 0.5f;
        fixture.restitution = 0.5f;


        float scala = .1f;
        loader.attachFixture(body, "aliado", fixture, scala);
        setSize(60f, 50f);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        setPosition((body.getPosition().x - 0.5f) * 45f, (body.getPosition().y - 0.5f) * 45f);

        batch.draw(textura, getX(), getY(), getWidth(), getHeight());
    }

    public void detach() {
        //body.destroyFixture(fixture);
        world.destroyBody(body);

    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (!limite) {
            body.setLinearVelocity(0, movement);
        } else {
            body.setLinearVelocity(0, movement);
        }


    }

    public void die() {

        addAction(Actions.removeActor());
        world.destroyBody(body);


    }

    public void setLimite(boolean limite) {
        this.limite = limite;
    }

}
