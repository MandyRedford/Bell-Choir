import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;

public class Conductor {
	Player[] playerArray;

	Map<Integer, Tone> mapPlayerTone1 = new HashMap<Integer, Tone>();
	Map<Integer, Tone> mapPlayerTone2 = new HashMap<Integer, Tone>();

	public Conductor(List<Note> notes, AudioFormat af) {
		// get # of notes
		Set<Tone> setOfTones = new HashSet<>();
		for (int i = 0; i < notes.size(); i++) {
			Note currentNote = notes.get(i);
			
			setOfTones.add(currentNote.tone);
			
		}
		int numberOfTones = setOfTones.size();
		System.out.println(numberOfTones);

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
			for (int j = 0; j < notes.size(); j++) {
				mapPlayerTone1.put(p, firstTone);
			}
			for (int j = 0; j < notes.size(); j++) {
				mapPlayerTone2.put(p, secondTone);
			}

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
		ArrayList<Integer> playerOrder = new ArrayList<Integer>(5);

		// go through the notes and create a list of the orders of players
		for (int songIterator = 0; songIterator < notes.size(); songIterator++) {
			Tone tonePlaying = (notes.get(songIterator).tone);
			if (mapPlayerTone1.containsValue(tonePlaying)) {
				// go through and create array of order of players
				for (Map.Entry entry : mapPlayerTone1.entrySet()) {
					if (tonePlaying.equals(entry.getValue())) {
						int map1player = (int) entry.getKey();
						playerOrder.add(map1player);
					}
				}

			} else if (mapPlayerTone2.containsValue(tonePlaying)) {
				for (Map.Entry entry : mapPlayerTone2.entrySet()) {
					if (tonePlaying.equals(entry.getValue())) {
						int map2player = (int) entry.getKey();
						playerOrder.add(map2player);
					}
				}
			}
		}
		System.out.println(playerOrder);
		//go through players order and tell player to play
		for (int i = 0; i<playerOrder.size(); i++){
			for (int p = 0; p<playerArray.length; p++){
				playerArray[p].yourTurn();
			}
			
		}
		
		

	}// end play song

}// end class
