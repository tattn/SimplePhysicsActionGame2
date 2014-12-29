package a5812070.action2d.drawing;

import android.view.SurfaceHolder;

public class Graphics {

	SurfaceHolder surfaceHolder;

	public Graphics(SurfaceHolder s) { surfaceHolder = s; }

	/**
	 * ビューのサーフェイスホルダーを取得します。
	 * @return
	 */
	public final SurfaceHolder getSurfaceHolder() { return surfaceHolder; }

}
