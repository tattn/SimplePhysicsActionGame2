package a5812070.action2d.drawing;

import a5812070.action2d.gameengine.GamePanel;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;

public class DrawApplication implements IDraw {

	private GamePanel panel;

	private Canvas canvas;

	public DrawApplication(GamePanel panel) throws Exception {
		this.panel = panel;
		canvas = panel.getGraphics().getSurfaceHolder().lockCanvas();
	}

	/**
	 * 描画開始
	 */
	private void begin() {
		canvas.save();
		canvas.scale(panel.getScale(), panel.getScale());
	}

	/**
	 * 描画終了
	 */
	private void end() {
		canvas.restore();
	}

	/**
	 * バックバッファのイメージをディスプレイに表示します。
	 */
	public void show() {
		if (canvas != null)
			panel.getGraphics().getSurfaceHolder().unlockCanvasAndPost(canvas);		//	裏バッファから表バッファへ

		Paint bgPaint = new Paint();
		bgPaint.setStyle(Style.FILL);
		bgPaint.setColor(android.graphics.Color.WHITE);
		canvas = panel.getGraphics().getSurfaceHolder().lockCanvas();				//	バックバッファの作成
		if (canvas == null) return;
		canvas.drawRect(
				0, 0,
				panel.getWidth(), panel.getHeight(),
				bgPaint);
	}

	/**
	 * 画像を表示します。
	 */
	public void image(BImage img, float x, float y) {
		if (canvas == null) return;
		float cx = x + img.getWidth() / 2f + Draw.centerDx;
		float cy = y + img.getHeight() / 2f + Draw.centerDy;

		begin();

		canvas.rotate((float) (Draw.rotation * 180 / Math.PI), cx, cy);

		Paint p = null;
		if (Draw.alpha != Draw.DEFAULT_ALPHA) {
			p = new Paint();
			p.setAlpha(Draw.alpha);
		}
		canvas.drawBitmap(img.getBitmap(), x, y, p);
		end();
	}

	/**
	 * 画像を左右反転して表示します。
	 */
	public void imageInverseH(BImage img, float x, float y) {
		if (canvas == null) return;
		float cx = x + img.getWidth() / 2f + Draw.centerDx;
		float cy = y + img.getHeight() / 2f + Draw.centerDy;

		begin();
		canvas.scale(-1, 1);
		canvas.rotate((float) (Draw.rotation * 180 / Math.PI), cx, cy);

		Paint p = null;
		if (Draw.alpha != Draw.DEFAULT_ALPHA) {
			p = new Paint();
			p.setAlpha(Draw.alpha);
		}

		canvas.drawBitmap(img.getBitmap(), -x - img.getWidth(), y, p);
		end();
	}

	/**
	 * 塗りつぶされた矩形を表示します。
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param c
	 */
	public void fillRect(float x, float y, float width, float height, Color c) {
		if (canvas == null) return;
		Paint p = new Paint();
		p.setColor(c.getColor());
		p.setStyle(Style.FILL);
		begin();
		canvas.drawRect(x, y, x + width, y + height, p);
		end();
	}

}
