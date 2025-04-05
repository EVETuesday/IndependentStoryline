package com.is.client.gui.utils;

public abstract class AnimationTimeline {

    private long animationDuration;
    private long animationState = 0;
    private long animStartedAt = -1;
    private long animToStop = -1;
    private long maxAnimState = -1;
    private boolean playing = true;

    protected AnimationTimeline(long animationDuration) {
        this.animationDuration = animationDuration;
    }

    public void update() {
        if (shouldIncrease() && playing && animStartedAt == -1) {
            animStartedAt = System.currentTimeMillis();
        } else if (!shouldIncrease() && playing && animationState > 0) {
            if (animToStop == -1) {
                animToStop = System.currentTimeMillis() + maxAnimState;
            }
            animationState = animToStop - System.currentTimeMillis();
            animationState = Math.max(animationState, 0);
            if (animationState == 0) {
                animStartedAt = -1;
                animToStop = -1;
                maxAnimState = -1;
            }
        }
        if (shouldIncrease() && playing && animationState < animationDuration) {
            animationState = Math.min(System.currentTimeMillis() - animStartedAt, animationDuration);
            maxAnimState = animationState;
        }
    }

    public void reset() {
        this.animationState = 0;
        this.animStartedAt = -1;
        this.animToStop = -1;
        this.maxAnimState = -1;
    }

    public void setAnimationState(long animationState) {
        this.animationState = animationState;
        update();
    }

    public void setAnimationDuration(long animationDuration) {
        this.animationDuration = animationDuration;
        update();
    }

    public float getPercentage() {
        return (float) animationState / animationDuration;
    }

    public void pause() {
        playing = false;
    }

    public void resume() {
        playing = true;
    }

    public boolean isEnded() {
        return playing && (shouldIncrease() && animationState == animationDuration || !shouldIncrease() && animationState == 0);
    }

    protected abstract boolean shouldIncrease();

}
