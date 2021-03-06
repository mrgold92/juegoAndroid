package entities;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.MainGame;


public class PlayerEntity extends Actor {

    private Texture textura;
    private World world;
    public Body body;
    private Fixture fixture;
    private Sprite sprite;
    private boolean alive;
    private MainGame game;
    private Vector2 posicion;
    private LaserEntity laser;


    public PlayerEntity(Texture textura, World world, Vector2 posicion, MainGame game) {
        this.textura = textura;
        this.world = world;
        this.sprite = new Sprite(this.textura);
        this.posicion = posicion;
        this.game = game;
        alive = true;


        //fisicas
        BodyDef def = new BodyDef();
        def.position.set(posicion.x, posicion.y);
        def.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(def);

        //forma
        PolygonShape playerShape = new PolygonShape();
        playerShape.setAsBox(0.5f, 0.5f);
        fixture = body.createFixture(playerShape, 1);
        fixture.setUserData("player");
        body.setUserData("player");
        playerShape.dispose();

        setSize(60f, 50f);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        float posx = body.getPosition().x;
        float posy = body.getPosition().y;
        setPosition((body.getPosition().x - 0.5f) * 45f, (body.getPosition().y - 0.5f) * 45f);
        batch.draw(textura, getX(), getY(), getWidth(), getHeight());


    }

    public void detach() {
        body.destroyFixture(fixture);
        world.destroyBody(body);
        laser.detach();
    }

    @Override
    public void act(float delta) {


        super.act(delta);
        float deltaTime = Gdx.graphics.getDeltaTime();

        if (alive) {
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                body.setLinearVelocity(5f, 0);

            } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                body.setLinearVelocity(-5f, 0);

            } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                body.setLinearVelocity(0, 5f);
            } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                body.setLinearVelocity(0, -5f);
            } else {
                body.setLinearVelocity(0, 0);

            }


        } else {
            body.setLinearVelocity(0, 0);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {

            Texture texturaLaserEnemigo = game.getManager().get("laserEnemigo.png");


            laser = new LaserEntity(texturaLaserEnemigo, world, new Vector2(3.5f, .9f));
            getStage().addActor(laser);
        }
    }

    public void stop() {
        alive = false;

    }

    public void removeLaser() {
        laser.remove();
        //laser.detach();

    }
}
