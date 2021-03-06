package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public abstract class Enemy extends Actor {
    //Abstract because it's not instantiable, and we can do cool stuff with this later
    //protected for subclasses

    protected int moveCtr;
    protected long lastShotTime;
    protected Array<Bullet> enemyBullets;
    protected Vector2 moveDirection;
    protected Texture enemyImage;
    protected float enemyHeight;
    protected float enemyWidth;
    protected float xPos = MathUtils.random(Constants.GAMESCREEN_WIDTH / 3,
            2 * Constants.GAMESCREEN_WIDTH / 3);
    protected float yPos = MathUtils.random(Constants.GAMESCREEN_HEIGHT / 3,
            2 * Constants.GAMESCREEN_HEIGHT / 3);

    public Enemy() {
            //fill in later with properties
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(enemyImage, getX(), getY(), getWidth(), getHeight());
    }

    public void attack(Vector2 direction, float speed) {
        Bullet bullet = new Bullet(direction.nor().scl(70).scl(3), speed, false,
                this.getX() + this.getWidth()/2 - Constants.BULLET_WIDTH,
                this.getY() + this.getHeight()/2 - Constants.BULLET_HEIGHT,
                Constants.BULLET_WIDTH, Constants.BULLET_HEIGHT);
        // + Constants.USER_WIDTH / 4
        enemyBullets.add(bullet);
        lastShotTime = TimeUtils.nanoTime();
    }

    public void checkBoundsCollision () {
        if (getX() + getWidth() >= Constants.GAMESCREEN_WIDTH) {
            setX(Constants.GAMESCREEN_WIDTH - getWidth());
        } else if (getX() < 0) {
            setX(0);
        }

        if (getY() >= Constants.GAMESCREEN_HEIGHT - getHeight()) {
            setY(Constants.GAMESCREEN_HEIGHT - getHeight());
        } else if (getY() < 0) {
            setY(0);
        }
    }
    public int hitAction() {
        remove();
        return 0; //report net increase in enemies
    }
    public boolean overlaps(Actor a) {
        return Collisions.overlap(this, a);
    }

}
