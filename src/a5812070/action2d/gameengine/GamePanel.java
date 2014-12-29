package a5812070.action2d.gameengine;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import a5812070.action2d.drawing.Draw;
import a5812070.action2d.drawing.DrawApplication;
import a5812070.action2d.drawing.Graphics;
import a5812070.action2d.scene.Scene;
import a5812070.action2d.scene.SceneTitle;
import a5812070.action2d.tmxmap.ObjectGroup;
import a5812070.action2d.tmxmap.TMXMap;
import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

public class GamePanel extends Activity implements Runnable {

	private DisplayView displayView;
	private static Input input;
	private Thread mainThread;

	float viewScale;

	public void initialize(int width, int height) {
		WindowManager wm = (WindowManager)getSystemService(WINDOW_SERVICE);
		Display disp = wm.getDefaultDisplay();
		Point size = new Point();
		disp.getSize(size);
		float viewScaleX = (float)size.x / width;
		float viewScaleY = (float)size.y / height;
		viewScale = viewScaleX > viewScaleY ? viewScaleY : viewScaleX;	//	画面を小さい方に合わせる
		input = new Input();

		FrameLayout layout = new FrameLayout(this);
		displayView = new DisplayView(this);
		layout.addView(displayView);

		TMXMap map = new TMXMap("key.tmx");
		ObjectGroup og = map.getLayer(0);

		//	方向キーの設置
        DirKeyView dkv = new DirKeyView(this, og.getObject("dir"));
        layout.addView(dkv);

        //	ジャンプボタンの設置
        JumpKeyView jkv = new JumpKeyView(this, og.getObject("jump"));
        layout.addView(jkv);

        //	攻撃ボタンの設置
        AttackKeyView akv = new AttackKeyView(this, og.getObject("attack"));
        layout.addView(akv);

        //	メニューボタンの設置
        MenuKeyView mkv = new MenuKeyView(this, og.getObject("menu"));
        layout.addView(mkv);

        setContentView(layout);

		mainThread = new Thread(this);
		start();
	}

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);						//	タイトルバー非表示
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);	//	ステータスバー非表示


        init();
    }

    public void init() { }

    public void startApplication() { }

    public void start() {
		try {
			Draw.setDraw(new DrawApplication(this));
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Scene.clear();
		//SceneManager.push(new SceneLoading(new ScenePlay("map\\map.tmx"));
		//Scene.push(new SceneTest());
		Scene.push(new SceneTitle());
		mainThread.start();
	}

	@Override
	public void run() {
		while (true) {
			try {
				long oldTime = System.currentTimeMillis();

				Scene.execute();
				input.update();

				//repaint();

				Draw.show();

				long newTime = System.currentTimeMillis();
				long sleepTime = 16 - (newTime - oldTime);
				if (sleepTime < 2) sleepTime = 2;
				Thread.sleep(sleepTime);
			} catch(Exception e){e.printStackTrace();}
		}
	}

	/**
	 * 画面の拡大率を取得します。
	 * @return
	 */
	public final float getScale() { return viewScale; }

	/**
	 * ゲーム画面の横幅を取得します。
	 * @return
	 */
	public final int getWidth() { return (int)(GameEngine.getWidth() * viewScale); }

	/**
	 * ゲーム画面の縦幅を取得します。
	 * @return
	 */
	public final int getHeight() { return (int)(GameEngine.getHeight() * viewScale); }

	/**
	 * 画面に描画するためのグラフィックスを取得します。
	 * @return
	 */
	public final Graphics getGraphics() { return displayView.getGraphics(); }

	/**
	 * 入力情報クラスを取得します。
	 * @return
	 */
	public static final Input getInput() { return input; }

	/**
	 * Asset内のファイルを読み込むためのInputStreamを取得します。
	 * @param path	assetディレクトリをルートとした相対パス
	 * @return
	 */
	public final InputStream getInputStream(String path) {
		try {
			path = new File(path).getCanonicalPath();
			path = path.substring(1);	//	File.getAbsolutePath()だと先頭に/が入ってしまうため
			return getResources().getAssets().open(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
