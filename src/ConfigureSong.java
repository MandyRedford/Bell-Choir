import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConfigureSong {

	private static Tone parseTone(String noteName) {
		// checks and handles the first value(i.e the note name)
		// If you give me garbage, I'll give it back
		if (noteName == null) {
			System.err.println("Error: Note is null");
			return Tone.INVALID;
		} // end if for null

		for (Tone tone : Tone.values()) {
			if (noteName.trim().toUpperCase().equals(tone.name())) {
				return tone;
			} // end if trim
		} // end for
		System.err.println("ERROR:Tone name is not on valid list: " + noteName);
		return Tone.INVALID;
	}

	private static Duration parseDuration(String num) {
		// error checks the length of a note
		try {
			int length = Integer.parseInt(num);
			if (length != 1 && length != 2 && length != 4 && length != 8 && length != 16) {
				System.err.println("Error: Invalid note Length " + length);
				return Duration.INVALID;
			}
			// switch case to take number and change to the BellNote formated
			// length
			switch (length) {
			case 1:
				return Duration.WHOLE;
			case 2:
				return Duration.HALF;
			case 4:
				return Duration.QUARTER;
			case 8:
				return Duration.EIGTH;
			case 16:
				return Duration.SIXTEENTH;
			}

		} catch (NumberFormatException ignored) {
		}

		return Duration.INVALID;
	}

	private static Note parseNote(String line) {
		// takes the line and splits it into
		String[] fields = line.split("\\s+");
		if (fields.length == 2) {
			Tone nn = parseTone(fields[0]);
			Duration nl = parseDuration(fields[1]);
			Note note = new Note(nn, nl);
			return note;
		} else if (line.startsWith("//")) {
			return new Note(Tone.IGNORE, Duration.EIGTH);
		}
		return null;
	}

	static List<Note> loadNotes(String filename) {
		final List<Note> notes = new ArrayList<>();
		final File file = new File(filename);
		if (file.exists()) {// if there is a file
			try (FileReader fileReader = new FileReader(file); BufferedReader br = new BufferedReader(fileReader)) {
				String line = null;
				while ((line = br.readLine()) != null) {
					Note n = parseNote(line);// sends line to parseNote and sets it to Note n
					if (n != null) {
						if (!line.contains("//")) {
							notes.add(n);// adds n to the total of song
						}
					} else {
						System.err.println("Error: Invalid note '" + line + "'");
					}
				}
			} catch (IOException ignored) {
			}
		} else {
			System.err.println("File '" + filename + "' not found");
		}
		return notes;
	}

}
