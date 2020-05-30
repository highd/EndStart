package com.highd120.endstart.block;


public class TileCrafterCore extends TileEntityBase {
	private boolean active;

    @Override
    public void update() {
    }

    public void activate() {
    	active = !active;
    }

    public boolean isActive() {
    	return active;
    }

    public void breakEvent() {
    }
}
