
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;

public class test {
	Player[] playerArray;
	Map<Integer, List<Tone>> mapPlayerTone = new HashMap<Integer, List<Tone>>();

	public test(List<Note> notes, AudioFormat af) {
		// get # of notes
		Set<Tone> setOfTones = new HashSet<>();
		for (int i = 0; i < notes.size(); i++) {
			Note currentNote = notes.get(i);
			if (!(currentNote.tone == Tone.REST)) {
				setOfTones.add(currentNote.tone);
			}
		}
		int numberOfTones = setOfTones.size();
		// System.out.println(numberOfTones);

		int numPlayers = (numberOfTones + 1) / 2;
		playerArray = new Player[numPlayers];

		Tone[] toneArray = new Tone[numberOfTones];
		setOfTones.toArray(toneArray);

		// assign 2 tones to each player
		for (int p = 0; p < numPlayers; p++) {
			int firstToneIndex = p * 2;
			int secondToneIndex = firstToneIndex + 1;
			Tone firstTone = toneArray[firstToneIndex];
			Tone secondTone = null;
			if (secondToneIndex < toneArray.length) {
				secondTone = toneArray[secondToneIndex];
			}
			List<Tone> playerToneArray = new ArrayList<>();
			playerToneArray.add(firstTone);
			playerToneArray.add(secondTone);

			for (int j = 0; j < notes.size(); j++) {
				mapPlayerTone.put(p, playerToneArray);
			}
			System.out.println(playerToneArray);

			// Create the list of notes based on the 2 tones to give each player
			List<Note> playersNotes = new ArrayList<>();
			for (int i = 0; i < notes.size(); i++) {
				Note currentNote = notes.get(i);
				if (currentNote.tone == firstTone || currentNote.tone == secondTone) {
					playersNotes.add(currentNote);
				}
			}

			// pass the assigned notes to the players
			playerArray[p] = new Player(playersNotes, p);
		} // end greater for

	}// end constructor

	public static void main(String[] args) throws Exception {
		List<Note> song = ConfigureSong.loadNotes("MarioTheme.txt");
		final AudioFormat af = new AudioFormat(Tone.SAMPLE_RATE, 8, 1, true, false);
		Conductor t = new Conductor(song, af);
		t.playSong(song);
	}

	@SuppressWarnings("rawtypes")
	public void playSong(List<Note> notes) {
		// go through the list of all notes and based on the tone tell the
		// player to play
		for (int songIterator = 0; songIterator < notes.size(); songIterator++) {
			System.out.println(notes.get(songIterator));
			// hey player play
			Tone tonePlaying = (notes.get(songIterator).tone);
			if (mapPlayerTone.containsValue(tonePlaying)) {
				System.out.println("works");
				for (Map.Entry entry : mapPlayerTone.entrySet()) {
					if (tonePlaying.equals(entry.getValue())) {
						// System.out.println(entry.getKey());
					}
				} // end for map

			} else {
				// System.out.println("Does not work");
			}
		} // end 1 for

	}// end play song

}// end class
