 package com.audioquiz.core.domain.service;

/**
 * Interface for playing frequencies.
 */
public interface FrequencyPlayer {

    /**
     * Plays a tone at the specified frequency.
     *
     * @param freqOfTone the frequency of the tone to play
     */
    void playFrequency(double freqOfTone);

    /**
     * Stops playing the current frequency.
     */
    void stopFrequency();
}