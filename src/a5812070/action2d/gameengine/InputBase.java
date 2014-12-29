package a5812070.action2d.gameengine;


class InputBase
{
	public GameKey left = new GameKey();
	public GameKey right = new GameKey();
	public GameKey up = new GameKey();
	public GameKey down = new GameKey();
	public GameKey c = new GameKey();
	public GameKey x = new GameKey();
	public GameKey z = new GameKey();
	public GameKey shift = new GameKey();

	public int pointX, pointY;

	public boolean pointDowned;
	public boolean pointPressed;
	public boolean pointReleased;

	public static final int VK_LEFT = 0x25;
	public static final int VK_RIGHT = 0x27;
	public static final int VK_UP = 0x26;
	public static final int VK_DOWN = 0x28;
	public static final int VK_SHIFT = 0x10;
	public static final int VK_C = 0x43;
	public static final int VK_X = 0x58;
	public static final int VK_Z = 0x5a;

	InputBase() {
	}

	/**
	*	call this method in last of main-loop.
	*/
	void update() {
		left.update();
		right.update();
		up.update();
		down.update();
		c.update();
		x.update();
		z.update();
		shift.update();
		pointPressed = false;
		pointReleased = false;
	}

	/**
	*	call this method in "keyPressed()".
	*/
	void updateKeyPressed(int keycode) {
		switch(keycode) {
			case VK_LEFT:	left.press(); return;
			case VK_RIGHT:	right.press(); return;
			case VK_UP:	up.press(); return;
			case VK_DOWN:	down.press(); return;
			case VK_SHIFT:	shift.press(); return;
			case VK_C:		c.press(); return;
			case VK_X:		x.press(); return;
			case VK_Z:		z.press(); return;
		}
	}

	/**
	*	call this method in "keyReleased()".
	*/
	void updateKeyReleased(int keycode) {
		switch(keycode) {
			case VK_LEFT:	left.release(); return;
			case VK_RIGHT:	right.release(); return;
			case VK_UP:	up.release(); return;
			case VK_DOWN:	down.release(); return;
			case VK_SHIFT:	shift.release(); return;
			case VK_C:		c.release(); return;
			case VK_X:		x.release(); return;
			case VK_Z:		z.release(); return;
		}
	}

	void updatePointMoved(int x, int y) {
		pointX = (int) (x / GameEngine.getScale());
		pointY = (int) (y / GameEngine.getScale());
	}

	void updatePointPressed(int x, int y) {
		pointPressed = pointDowned = true;
		updatePointMoved(x, y);
	}

	void updatePointReleased(int x, int y) {
		pointDowned = false;
		pointReleased = true;
		updatePointMoved(x, y);
	}
}
