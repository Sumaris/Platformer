package com.alfredradu.platformer.entities;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import com.alfredradu.platformer.input.InputManager;

public class Player extends DynamicEntity {
    static final String TAG = "Player";
    static final float PLAYER_RUN_SPEED = 6.0f; // m/s
    static final float PLAYER_JUMP_FORCE = -(GRAVITY/2);
    static final float MIN_INPUT_TO_TURN = 0.05f; //5% joy input before we start turning animations
    private final int LEFT = 1;
    private final int RIGHT = -1;
    private int _facing = LEFT; //initial facing dir

    public Player (final String spriteName, final int xpos, final int ypos){
        super(spriteName, xpos, ypos);
        _width = DEFAULT_DIMENSION/2; //set different values for player size
        _height = DEFAULT_DIMENSION/2;
        loadBitmap(spriteName,xpos,ypos);
    }

    @Override
    public void render(Canvas canvas, Matrix transform, Paint paint) {
        transform.preScale(_facing, 1.0f);
        if(_facing == RIGHT){
            final float offset = _game.worldToScreenX(_width);
            transform.postTranslate(offset, 0);
        }
        super.render(canvas, transform, paint);
    }

    @Override
    public void update(final double dt) {
        final InputManager controls = _game.getControls();
        final float direction = controls._horizontalFactor;
        _velX = direction * PLAYER_RUN_SPEED;
        updateFacingDirection(direction);
        if(controls._isJumping && _isOnGround){
            _velY = PLAYER_JUMP_FORCE;
            _isOnGround = false;
        }
        super.update(dt);
    }

    private void updateFacingDirection(final float controlDirection){
        if(Math.abs(controlDirection) < MIN_INPUT_TO_TURN){ return; }
        if(controlDirection < 0){ _facing = LEFT; }
        else if(controlDirection > 0){ _facing = RIGHT; }
    }

}
