class BellTone {
    final BellChoir Tone;
    final ToneLength length;

    BellTone(BellChoir Tone, ToneLength length) {
        this.Tone = Tone;
        this.length = length;
    }
}

enum ToneLength {
    WHOLE(1.0f),
    HALF(0.5f),
    QUARTER(0.25f),
    EIGTH(0.125f),
	SIXTEENTH(0.0625f);

    private final int timeMs;

    private ToneLength(float length) {
        timeMs = (int)(length * BellChoir.MEASURE_LENGTH_SEC * 1000);
    }

    public int timeMs() {
        return timeMs;
    }
}

