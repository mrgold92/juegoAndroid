package entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class ColisionadorEnemigos extends Actor {

    private Texture textura;
    private World world;
    private Body body;
    private Fixture fixture;
    private Vector2 posicion;
    private float alto, ancho;

    public ColisionadorEnemigos(Texture textura, World world, Vector2 posicion, float alto, float ancho) {
        this.textura = textura;
        this.world = world;
        this.ancho = ancho;
        this.alto = alto;
        this.posicion = posicion;


        //fisicas
        BodyDef def = new BodyDef();
        def.position.set(posicion.x, posicion.y);
        def.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(def);

        //forma
        PolygonShape colisionadorShape = new PolygonShape();
        colisionadorShape.setAsBox(ancho, 0.2f);
        fixture = body.createFixture(colisionadorShape, 1);
        fixture.setUserData("colisionadorEnemigos");
        body.setUserData("colisionadorEnemigos");
        colisionadorShape.dispose();

        setSize(ancho, alto);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        setPosition(posicion.x, posicion.y);


    }

    public void detach() {
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

    }
}


