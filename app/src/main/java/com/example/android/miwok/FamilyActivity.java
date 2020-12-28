package com.example.android.miwok;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class FamilyActivity extends AppCompatActivity {
    //Handles playback of all sound files
    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;
    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener=new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focuschange) {
            if (focuschange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focuschange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {//Pause playback
                mMediaPlayer.pause();
                //restarts at 0 secs
                mMediaPlayer.seekTo(0);
            } else if (focuschange == AudioManager.AUDIOFOCUS_GAIN) {
                //Resume playback
                mMediaPlayer.start();

            } else if (focuschange == AudioManager.AUDIOFOCUS_LOSS) {
                //Stop playback
                releaseMediaPlayer();
            }
        }
    };



    //This listener gets triggered when media player has finished playing the Audio file
    private MediaPlayer.OnCompletionListener mCompletion=new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            //Now when sound file has finished,release the resources
            releaseMediaPlayer();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family);
        //Create and setup audio manager to request for audio focus
        mAudioManager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
        //Array list of numbers in English
        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("Father","ede",R.drawable.family_father,R.raw.family_father));
        words.add(new Word("Mother","eta",R.drawable.family_mother,R.raw.family_mother));
        words.add(new Word("Son","angsi",R.drawable.family_son,R.raw.family_son));
        words.add(new Word("Daughter","tune",R.drawable.family_daughter,R.raw.family_daughter));
        words.add(new Word("Elder Brother","taachi",R.drawable.family_older_brother,R.raw.family_older_brother));
        words.add(new Word("Younger Brother","chalitti",R.drawable.family_younger_brother,R.raw.family_younger_brother));
        words.add(new Word("Elder Sister","tete",R.drawable.family_older_sister,R.raw.family_older_sister));
        words.add(new Word("Younger Sister","kolliti",R.drawable.family_younger_sister,R.raw.family_younger_sister));
        words.add(new Word("Grandmother","ama",R.drawable.family_grandmother,R.raw.family_grandmother));
        words.add(new Word("Grandfather","paapa",R.drawable.family_grandfather,R.raw.family_grandfather));
        //creating an array adapter
        WordAdapter Adapter=new WordAdapter(this,words,R.color.family_category);
        ListView listView=(ListView) findViewById(R.id.list);
        listView.setAdapter(Adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //Release the media file if it exists
                //play a different sound file
                releaseMediaPlayer();
                //get the Word object at the given position user clicked on
                Word word=words.get(position);

                //Request audiofocus
                int result=mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if(result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    //We have Audio focus now
                    //Create Media player for audio associated with word
                    mMediaPlayer = MediaPlayer.create(FamilyActivity.this, word.getAudioId());
                    mMediaPlayer.start();
                    mMediaPlayer.setOnCompletionListener(mCompletion);
                }
            }
        });
    }
    @Override
    protected void onStop() {
        super.onStop();
        //when activity is stopped release the media resources
        releaseMediaPlayer();
    }
    //clean mediaplayer by releasing resources
    private void releaseMediaPlayer()
    {
        //if media player is not null then it is currently playing a sound
        if(mMediaPlayer!=null)
        {
            //Regardless of the current state of media player,release because we no longer need it
            mMediaPlayer.release();
            //set audio to null as we not configured to play audio at that moment
            mMediaPlayer=null;
            //Abandon Audio focus when playback completes
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }
}