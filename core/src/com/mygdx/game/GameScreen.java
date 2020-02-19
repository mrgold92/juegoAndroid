package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

import entities.AliadoEntity;
import entities.ColisionadorEnemigos;
import entities.EnemigoEntity;
import entities.LaserEntity;
import entities.PlayerEntity;

class GameScreen extends BaseScreen {

    private Stage stage;
    private World world;
    private PlayerEntity player;
    private EnemigoEntity enemigo;
    private AliadoEntity aliado;
    private ColisionadorEnemigos[] colisionadorEnemigos;
    private SpriteBatch batch;
    private Texture texturaFondo;


    public GameScreen(MainGame game) {
        super(game);
        batch = new SpriteBatch();
        texturaFondo = new Texture(Gdx.files.internal("fondoJuego.jpg"));

        stage = new Stage(new FitViewport(640, 320));
        world = new World(new Vector2(0, 0), true);


    }

    @Override
    public void show() {
        super.show();
        stage.setDebugAll(true);


        //texturas

        Texture texturaPlayer = game.getManager().get("player.png");
        Texture texturaEnemigo = game.getManager().get("enemigo.png");

        Texture texturaAliado = game.getManager().get("aliado.png");

        //new entities

        player = new PlayerEntity(texturaPlayer, world, new Vector2(0.90f, 0.90f), game);
        enemigo = new EnemigoEntity(texturaEnemigo, world, new Vector2(3.40f, 10.0f));
        aliado = new AliadoEntity(texturaAliado, world, new Vector2(7f, 4.0f));


        colisionadorEnemigos = new ColisionadorEnemigos[4];
        colisionadorEnemigos[0] = new ColisionadorEnemigos(null, world, new Vector2(-9f, 0), stage.getHeight(), 20f);
        colisionadorEnemigos[1] = new ColisionadorEnemigos(null, world, new Vector2(0, 0), 10f, stage.getWidth());
        colisionadorEnemigos[2] = new ColisionadorEnemigos(null, world, new Vector2(640 - 40, 200), stage.getHeight() - 400, -20f);
        colisionadorEnemigos[3] = new ColisionadorEnemigos(null, world, new Vector2(0, 320f), 10f, stage.getWidth());

        stage.addActor(player);
        stage.addActor(enemigo);

        stage.addActor(aliado);

        for (ColisionadorEnemigos c : colisionadorEnemigos) {
            stage.addActor(c);
        }

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        //color de fondo
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        world.step(delta, 6, 2);

        stage.act();
        batch.begin();
        batch.draw(texturaFondo, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();


        stage.draw();


        world.setContactListener(new ContactListener() {

            private boolean areCollider(Contact contact, Object objetoA, Object objetoB) {

                return ((contact.getFixtureA().getBody().getUserData().equals(objetoA)
                        && contact.getFixtureB().getBody().getUserData().equals(objetoB))
                        || (contact.getFixtureA().getBody().getUserData().equals(objetoB)
                        && contact.getFixtureB().getBody().getUserData().equals(objetoA)));

            }

            @Override
            public void beginContact(Contact contact) {
                if (areCollider(contact, "enemigo", "colisionadorEnemigos")) {
                    enemigo.remove();
                } else if (areCollider(contact, "player", "enemigo")) {
                    enemigo.remove();
                } else if (areCollider(contact, "aliado", "colisionadorEnemigos")) {
                    aliado.setLimite(true);
                } else if (areCollider(contact, "laser", "enemigo")) {
                    enemigo.remove();
                    player.removeLaser();
                    System.out.println("chocan");
                }
            }

            @Override
            public void endContact(Contact contact) {
                if (areCollider(contact, "aliado", "colisionadorEnemigos")) {
                    aliado.setLimite(false);
                    aliado.movement = 1.9f;
                }
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });
    }

    @Override
    public void hide() {

        super.hide();
        player.detach();
        for (ColisionadorEnemigos e : colisionadorEnemigos) {
            e.detach();
        }
        enemigo.detach();

        aliado.detach();
    }

    @Override
    public void dispose() {
        super.dispose();
        stage.dispose();
        world.dispose();
    }


}
