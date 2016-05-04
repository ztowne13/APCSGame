package me.zm.apcsgame.sounds;

import me.zm.apcsgame.utils.FileUtils;

import javax.sound.sampled.AudioInputStream;

/**
 * Created by ztowne13 on 4/29/16.
 *
 * http://soundimage.org/fantasywonder/
 */
public enum Sound
{
	WALK(SoundType.EFFECT, FileUtils.loadSoundClip("walk.wav")),

	SONG_1(SoundType.SONG, FileUtils.loadSoundClip("song1.wav"));

	AudioInputStream soundClip;
	SoundType soundType;

	/**
	 * The constructor for the sounds
	 * @param soundType The type of sound it is (effect or song)
	 * @param soundClip The actualy, already loaded, sound clip.
	 */
	Sound(SoundType soundType, AudioInputStream soundClip)
	{
		this.soundClip = soundClip;
		this.soundType = soundType;
	}

	public AudioInputStream getSoundClip()
	{
		return soundClip;
	}

	public void setSoundClip(AudioInputStream soundClip)
	{
		this.soundClip = soundClip;
	}

	public SoundType getSoundType()
	{
		return soundType;
	}

	public void setSoundType(SoundType soundType)
	{
		this.soundType = soundType;
	}
}
