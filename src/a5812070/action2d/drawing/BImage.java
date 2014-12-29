package a5812070.action2d.drawing;

import a5812070.action2d.gameengine.GameEngine;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BImage {

	Bitmap bitmap;

	private BImage() {}

	/**
	 * ファイルから画像を読み取ります。
	 * @param filepath	画像ファイルへのパス
	 */
	public BImage(String filepath) {
		bitmap = BitmapFactory.decodeStream(GameEngine.getInputStream(filepath));
		if (bitmap == null)
			bitmap = null;
	}

	/**
	 * 画像を描画します。
	 * @param x
	 * @param y
	 */
	public void draw(float x, float y) {
		Draw.image(this, x, y);
	}

	public BImage getSubimage(int x, int y, int w, int h) {
		BImage ret = new BImage();
		ret.bitmap = Bitmap.createBitmap(bitmap, x, y, w, h);
		return ret;
	}

	/**
	 * 画像の横幅を取得します。
	 * @return
	 */
	public int getWidth() { return bitmap.getWidth(); }

	/**
	 * 画像の縦幅を取得します。
	 * @return
	 */
	public int getHeight() { return bitmap.getHeight(); }

	/**
	 * ビットマップを取得します。
	 * @return
	 */
	final Bitmap getBitmap() { return bitmap; }

}
