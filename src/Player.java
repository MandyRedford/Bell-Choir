import java.util.List;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class Player implements Runnable {
	List<Note> playersNotes;
	int playerNumber;
	public boolean time2Play = false;
	public static final int MEASURE_LENGTH_SEC = 1;
	final AudioFormat af =
            new AudioFormat(Tone.SAMPLE_RATE, 8, 1, true, false);

	public Player(List<Note> playersNotes,int playerNumber) {
		// list of notes that this player is responsible for based on the 2
		this.playersNotes = playersNotes;
		this.playerNumber = playerNumber;

		Thread t = new Thread(this);
		t.start();

	}// end constructor

	public synchronized void yourTurn() {
		System.out.println("player playing");
		time2Play = true;
		this.notify();
		while (time2Play) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void run() {
		while (!playersNotes.isEmpty()) {
			synchronized (this) {
				while (!time2Play) {
					try {
						this.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} // end while
				Note nextNote = playersNotes.remove(0);
				try {
					playSong(nextNote);
				} catch (LineUnavailableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				time2Play = false;
				this.notify();
			}
		}
	}

	void playSong(Note nextNote) throws LineUnavailableException {
		
		try (final SourceDataLine line = AudioSystem.getSourceDataLine(af)) {
			line.open();
			line.start();
			
			playNote(line, nextNote);
			
			line.drain();
		}
	}

	private void playNote(SourceDataLine line, Note bn) {
		final int ms = Math.min(bn.noteLength.timeMs(), MEASURE_LENGTH_SEC * 1000);
		final int length = Tone.SAMPLE_RATE * ms / 1000;
		line.write(bn.tone.sample(), 0, length);
		line.write(Tone.REST.sample(), 0, 50);
	}//end playNote


	
}
