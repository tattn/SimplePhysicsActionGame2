package a5812070.action2d.gameengine;

import a5812070.action2d.drawing.Graphics;
import a5812070.action2d.tmxmap.MapObject;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.LinearLayout;

public class DisplayView extends SurfaceView
implements SurfaceHolder.Callback{

	static final int BALL_R = 30;
	int cx = BALL_R, cy = BALL_R;
	int screen_width, screen_height;

	Graphics g;

	GamePanel panel;

	public DisplayView(GamePanel context) {
		super(context);
		this.panel = context;
		this.g = new Graphics(getHolder());
		g.getSurfaceHolder().addCallback(this);
		setFocusable(true);
		requestFocus();
		setX(200);
	}

	@Override
	public void surfaceChanged(
		SurfaceHolder holder,
		int format,
		int width,
		int height) {
		screen_width = width;
		screen_height = height;
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
	}

	/**
	 * タッチイベント
	 */
	public boolean onTouchEvent(MotionEvent event) {
		int x = (int)event.getX();
		int y = (int)event.getY();
		switch(event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			GamePanel.getInput().updatePointPressed(x, y);
			break;
		case MotionEvent.ACTION_MOVE:
			GamePanel.getInput().updatePointMoved(x, y);
			break;
		case MotionEvent.ACTION_UP:
			GamePanel.getInput().updatePointReleased(x, y);
			break;
		case MotionEvent.ACTION_CANCEL:
			break;
		}
		return true;
	}

	protected void onMeasure(int widthMeasureSpec,int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(panel.getWidth(), panel.getHeight());
	}

	/**
	 * このビューに描画するためのグラフィックスを取得します。
	 * @return
	 */
	public final Graphics getGraphics() { return g; }
}

class KeyView extends View {
	Paint paint = new Paint();

	Bitmap img;

	int imgWidth, imgHeight;

	static final int keyScale = 20;
	float scale;

	@SuppressLint("DrawAllocation")
	public KeyView(GamePanel context, String imgpath, MapObject mobject) {
		super(context);
		scale = context.getScale();
		img = BitmapFactory.decodeStream(GameEngine.getInputStream(imgpath));
		imgWidth = img.getWidth();
		imgHeight = img.getHeight();
		setX(mobject.getX() - keyScale);
		setY(mobject.getY() - keyScale);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(imgWidth + keyScale, imgHeight + keyScale);
		setLayoutParams(params);
	}

	 @Override
	public void onDraw(Canvas c) {
		 c.drawBitmap(img, keyScale, keyScale, paint);
	}
}

/**
 * 方向キークラス
 * @author user
 *
 */
class DirKeyView extends KeyView {

	int downX, downY;

	public DirKeyView(GamePanel context, MapObject mobject) {
		super(context, "img/dirKey.png", mobject);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		if (event.getAction() == MotionEvent.ACTION_DOWN ||
			event.getAction() == MotionEvent.ACTION_MOVE) {

			downX = (int)event.getX();
			downY = (int)event.getY();

			//if (!(downX < 0 || downX > 50 || downY < 10 || downY > 110)) {
			if (downX < 80 + keyScale) {
				GameEngine.getInput().updateKeyPressed(InputBase.VK_SHIFT);
				GameEngine.getInput().updateKeyPressed(InputBase.VK_LEFT);
			//} else if (!(downX < 110 || downX > 160 || downY < 10 || downY > 110)) {
			} else if (downX >= 80 + keyScale) {
				GameEngine.getInput().updateKeyPressed(InputBase.VK_SHIFT);
				GameEngine.getInput().updateKeyPressed(InputBase.VK_RIGHT);
			}
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			GameEngine.getInput().updateKeyReleased(InputBase.VK_SHIFT);
			GameEngine.getInput().updateKeyReleased(InputBase.VK_LEFT);
			GameEngine.getInput().updateKeyReleased(InputBase.VK_RIGHT);
		}

		return true;
	}
}

/**
 * ジャンプキークラス
 * @author user
 *
 */
class JumpKeyView extends KeyView {

	public JumpKeyView(GamePanel context, MapObject mobject) {
		super(context, "img/jumpKey.png", mobject);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		if (event.getAction() == MotionEvent.ACTION_DOWN ||
			event.getAction() == MotionEvent.ACTION_MOVE) {
			GamePanel.getInput().updateKeyPressed(InputBase.VK_Z);

		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			GamePanel.getInput().updateKeyReleased(InputBase.VK_Z);
		}

		return true;
	}
}

/**
 * 攻撃キークラス
 * @author user
 *
 */
class AttackKeyView extends KeyView {

	public AttackKeyView(GamePanel context, MapObject mobject) {
		super(context, "img/attackKey.png", mobject);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		if (event.getAction() == MotionEvent.ACTION_DOWN ||
			event.getAction() == MotionEvent.ACTION_MOVE) {
			GamePanel.getInput().updateKeyPressed(InputBase.VK_X);

		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			GamePanel.getInput().updateKeyReleased(InputBase.VK_X);
		}

		return true;
	}
}

/**
 * メニューキークラス
 * @author user
 *
 */
class MenuKeyView extends KeyView {

	public MenuKeyView(GamePanel context, MapObject mobject) {
		super(context, "img/menuKey.png", mobject);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		if (event.getAction() == MotionEvent.ACTION_DOWN ||
			event.getAction() == MotionEvent.ACTION_MOVE) {
			GamePanel.getInput().updateKeyPressed(InputBase.VK_C);

		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			GamePanel.getInput().updateKeyReleased(InputBase.VK_C);
		}

		return true;
	}
}


