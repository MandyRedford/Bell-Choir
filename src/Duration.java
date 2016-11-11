/**
 * Returns an Image object that can then be painted on the screen. 
 * The url argument must specify an absolute {@link URL}. The name
 * argument is a specifier that is relative to the url argument. 
 * <p>
 * This method always returns immediately, whether or not the 
 * image exists. When this applet attempts to draw the image on
 * the screen, the data will be loaded. The graphics primitives 
 * that draw the image will incrementally paint on the screen. 
 *
 * @param  url  an absolute URL giving the base location of the image
 * @param  name the location of the image, relative to the url argument
 * @return      the image at the specified URL
 * @see         Image
 */

public enum Duration {
	INVALID(0.0f),
	WHOLE(1.0f),
	HALF(0.5f), 
	QUARTER(0.25f), 
	EIGTH(0.125f), 
	SIXTEENTH(0.0625f);

	private final int timeMs;

	private Duration(float length) {
		timeMs = (int) (length * BellChoir.MEASURE_LENGTH_SEC * 1000);
	}

	public int timeMs() {
		return timeMs;
	}
}
