package a5812070.action2d.drawing;

public class Color {

	int colorCode;

	public Color(int r, int g, int b) {
		colorCode = android.graphics.Color.rgb(r, g, b);
	}

	public Color(int r, int g, int b, int a) {
		colorCode = android.graphics.Color.argb(a, r, g, b);
	}

	public final int getColor() { return colorCode; }
}
