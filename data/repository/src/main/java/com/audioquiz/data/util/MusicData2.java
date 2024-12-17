package com.audioquiz.data.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MusicData2 {

    private MusicData2() {
        // Prevent instantiation
    }
 //   public static final List<Integer> FREQUENCIES;
 //   public static final ArrayList<Integer> GRAPH_FREQUENCIES;
    public static final Map<Integer, Double> LOGARITHMIC_POSITIONS;
    public static final Map<String, Double> NOTE_FREQUENCIES;
    public static final Map<String, Integer> INTERVAL_NUMBERS;
    public static final Map<String, List<String>> INTERVAL_QUALITIES;
    public static final Map<Integer, List<String>> INTERVAL_SEMITONES;
    public static final Map<Integer, String> INTERVAL_BUTTONS;

    public static final int[] FREQUENCIES = {
            80, 100, 150, 200, 300, 400, 500, 600, 700, 800, 900, 1000, 2000,
            3000, 4000, 5000, 6000, 7000, 8000, 9000, 10000, 12500, 15000
    };

    public static final int[] GRAPH_FREQUENCIES = {
            0, 100, 200, 300, 400, 500, 700, 1000, 2000, 3000, 4000, 5000,
            7000, 10000, 12000, 15000, 20000
    };

    public enum IntervalQuality {
        PERFECT, MAJOR, MINOR, AUGMENTED, DIMINISHED
    }

    public enum IntervalNumber {
        UNISON(0), SECOND(1), THIRD(2), FOURTH(3),
        FIFTH(4), SIXTH(5), SEVENTH(6), OCTAVE(7),
        NINTH(8), TENTH(9), ELEVENTH(10), TWELFTH(11);

        private final int number;
        IntervalNumber(int number) { this.number = number; }
        public int getNumber() { return number; }
        }

    static {
      /*  ArrayList<Integer> frequencies = new ArrayList<>();
        frequencies.add(80);
        frequencies.add(100);
        frequencies.add(150);
        frequencies.add(200);
        frequencies.add(300);
        frequencies.add(400);
        frequencies.add(500);
        frequencies.add(600);
        frequencies.add(700);
        frequencies.add(800);
        frequencies.add(900);
        frequencies.add(1000);
        frequencies.add(2000);
        frequencies.add(3000);
        frequencies.add(4000);
        frequencies.add(5000);
        frequencies.add(6000);
        frequencies.add(7000);
        frequencies.add(8000);
        frequencies.add(9000);
        frequencies.add(10000);
        frequencies.add(12500);
        frequencies.add(15000);

        FREQUENCIES2 = frequencies;*/

      /*  ArrayList<Integer> graphFrequencies = new ArrayList<>();
        graphFrequencies.add(0);
        graphFrequencies.add(100);
        graphFrequencies.add(200);
        graphFrequencies.add(300);
        graphFrequencies.add(400);
        graphFrequencies.add(500);
        graphFrequencies.add(700);
        graphFrequencies.add(1000);
        graphFrequencies.add(2000);
        graphFrequencies.add(3000);
        graphFrequencies.add(4000);
        graphFrequencies.add(5000);
        graphFrequencies.add(7000);
        graphFrequencies.add(10000);
        graphFrequencies.add(12000);
        graphFrequencies.add(15000);
        graphFrequencies.add(20000);
        GRAPH_FREQUENCIES = graphFrequencies;*/

        Map<Integer, Double> logarithmicPositions = new HashMap<>();
        logarithmicPositions.put(100, 0.0);
        logarithmicPositions.put(200, 8.333333333333334);
        logarithmicPositions.put(300, 16.666666666666668);
        logarithmicPositions.put(400, 25.0);
        logarithmicPositions.put(500, 33.333333333333336);
        logarithmicPositions.put(600, 41.66666666666667);
        logarithmicPositions.put(700, 50.0);
        logarithmicPositions.put(800, 58.333333333333336);
        logarithmicPositions.put(900, 66.66666666666667);
        logarithmicPositions.put(1000, 75.0);
        logarithmicPositions.put(2000, 83.33333333333334);
        logarithmicPositions.put(3000, 91.66666666666667);
        logarithmicPositions.put(4000, 100.0);
        logarithmicPositions.put(5000, 108.33333333333334);
        logarithmicPositions.put(6000, 116.66666666666667);
        logarithmicPositions.put(7000, 125.0);
        logarithmicPositions.put(8000, 133.33333333333334);
        logarithmicPositions.put(9000, 141.66666666666669);
        logarithmicPositions.put(10000, 150.0);
        logarithmicPositions.put(12500, 166.66666666666669);
        logarithmicPositions.put(15000, 183.33333333333334);
        logarithmicPositions.put(20000, 200.0);
        LOGARITHMIC_POSITIONS = Collections.unmodifiableMap(logarithmicPositions);

        // Note Frequencies for two octaves
        Map<String, Double> noteFrequencies = new HashMap<>();
        noteFrequencies.put("C4", 261.63);
        noteFrequencies.put("C#4", 277.18);
        noteFrequencies.put("D4", 293.66);
        noteFrequencies.put("D#4", 311.13);
        noteFrequencies.put("E4", 329.63);
        noteFrequencies.put("F4", 349.23);
        noteFrequencies.put("F#4", 369.99);
        noteFrequencies.put("G4", 392.00);
        noteFrequencies.put("G#4", 415.30);
        noteFrequencies.put("A4", 440.00);
        noteFrequencies.put("A#4", 466.16);
        noteFrequencies.put("B4", 493.88);

        noteFrequencies.put("C5", 523.25);
        noteFrequencies.put("C#5", 554.37);
        noteFrequencies.put("D5", 587.33);
        noteFrequencies.put("D#5", 622.25);
        noteFrequencies.put("E5", 659.25);
        noteFrequencies.put("F5", 698.46);
        noteFrequencies.put("F#5", 739.99);
        noteFrequencies.put("G5", 783.99);
        noteFrequencies.put("G#5", 830.61);
        noteFrequencies.put("A5", 880.00);
        noteFrequencies.put("A#5", 932.33);
        noteFrequencies.put("B5", 987.77);
        noteFrequencies.put("C6", 1046.50);

        Map<String, Double> noteFrequenciess = new HashMap<>();
        String[] notes = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
        double baseFrequency = 261.63; // Frequency of C4

        for (int octave = 4; octave <= 6; octave++) {
            for (int i = 0; i < notes.length; i++) {
                String note = notes[i] + octave;
                double frequency = baseFrequency * Math.pow(2, (octave - 4 + (double) i / 12));
                noteFrequencies.put(note, frequency);
            }
        }

        NOTE_FREQUENCIES = Collections.unmodifiableMap(noteFrequencies);

        // Interval Numbers
        Map<String, Integer> intervalNumbers = new HashMap<>();
        intervalNumbers.put("perfect unison", 0);
        intervalNumbers.put("minor second", 1);
        intervalNumbers.put("major second", 2);
        intervalNumbers.put("minor third", 3);
        intervalNumbers.put("major third", 4);
        intervalNumbers.put("diminished fourth", 4);
        intervalNumbers.put("perfect fourth", 5);
        intervalNumbers.put("augmented fourth", 6); // also known as tritone
        intervalNumbers.put("diminished fifth", 6); // also known as tritone
        intervalNumbers.put("perfect fifth", 7);
        intervalNumbers.put("augmented fifth", 8);
        intervalNumbers.put("diminished sixth", 7);
        intervalNumbers.put("minor sixth", 8);
        intervalNumbers.put("major sixth", 9);
        intervalNumbers.put("augmented sixth", 10);
        intervalNumbers.put("diminished seventh", 9);
        intervalNumbers.put("minor seventh", 10);
        intervalNumbers.put("major seventh", 11);
        intervalNumbers.put("augmented seventh", 12);
        intervalNumbers.put("perfect octave", 12);

        INTERVAL_NUMBERS = Collections.unmodifiableMap(intervalNumbers);

        // Interval Qualities
        Map<String, List<String>> intervalQualities = new HashMap<>();
        intervalQualities.put("unison", Arrays.asList("perfect", "augmented"));
        intervalQualities.put("second", Arrays.asList("major", "minor", "diminished", "augmented"));
        intervalQualities.put("third", Arrays.asList("major", "minor", "diminished", "augmented"));
        intervalQualities.put("fourth", Arrays.asList("perfect", "diminished", "augmented"));
        intervalQualities.put("fifth", Arrays.asList("perfect", "diminished", "augmented"));
        intervalQualities.put("sixth", Arrays.asList("major", "minor", "diminished", "augmented"));
        intervalQualities.put("seventh", Arrays.asList("major", "minor", "diminished", "augmented"));
        intervalQualities.put("octave", Arrays.asList("perfect", "diminished", "augmented"));
        intervalQualities.put("perfect", Arrays.asList("unison", "fourth", "fifth", "octave"));
        intervalQualities.put("major", Arrays.asList("second", "third", "sixth", "seventh"));
        intervalQualities.put("minor", Arrays.asList("second", "third", "sixth", "seventh"));
        intervalQualities.put("augmented", Arrays.asList("unison", "second", "third", "fifth", "sixth", "seventh"));
        intervalQualities.put("diminished", Arrays.asList("second", "third", "fourth", "fifth", "sixth", "seventh"));


        INTERVAL_QUALITIES = Collections.unmodifiableMap(intervalQualities);


        // Interval Qualities
        Map<Integer, List<String>> semitones = new HashMap<>();
        semitones.put(1,Arrays.asList("minor second", "augmented unison"));
        semitones.put(2,Arrays.asList("major second", "diminished third"));
        semitones.put(3,Arrays.asList("minor third", "augmented second"));
        semitones.put(4,Arrays.asList("major third", "diminished fourth"));
        semitones.put(5,Arrays.asList("perfect fourth", "augmented third"));
        semitones.put(6,Arrays.asList("diminished fifth", "augmented fourth"));
        semitones.put(7,Arrays.asList("perfect fifth", "diminished sixth"));
        semitones.put(8,Arrays.asList("minor sixth", "augmented fifth"));
        semitones.put(9,Arrays.asList("major sixth", "diminished seventh"));
        semitones.put(10,Arrays.asList("minor seventh", "augmented sixth"));
        semitones.put(11,Arrays.asList("major seventh", "diminished octave"));
        semitones.put(12,Arrays.asList("perfect octave", "augmented seventh"));



        INTERVAL_SEMITONES = Collections.unmodifiableMap(semitones);

        Map<Integer, String> intervalButtonsIndex = new HashMap<>();
        intervalButtonsIndex.put(0, "unison");
        intervalButtonsIndex.put(1, "second");
        intervalButtonsIndex.put(2, "third");
        intervalButtonsIndex.put(3, "fourth");
        intervalButtonsIndex.put(4, "fifth");
        intervalButtonsIndex.put(5, "sixth");
        intervalButtonsIndex.put(6, "seventh");
        intervalButtonsIndex.put(7, "octave");
        intervalButtonsIndex.put(8, "perfect");
        intervalButtonsIndex.put(9, "major");
        intervalButtonsIndex.put(10, "minor");
        intervalButtonsIndex.put(11, "augmented");
        intervalButtonsIndex.put(12, "diminished");

        INTERVAL_BUTTONS = Collections.unmodifiableMap(intervalButtonsIndex);

    }

}
