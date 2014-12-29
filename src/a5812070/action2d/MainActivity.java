package a5812070.action2d;

import a5812070.action2d.gameengine.GameEngine;
import a5812070.action2d.gameengine.GamePanel;


public class MainActivity extends GamePanel {

	@Override
	public void init() {
		GameEngine.setTitle("Action2D");
		GameEngine.init(this, 640, 480);
	}

}
