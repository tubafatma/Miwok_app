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

public class PhrasesActivity extends AppCompatActivity {
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
    //Instance of OnCompletionListener
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
        setContentView(R.layout.activity_phrases);
        //Create and setup audio manager to request for audio focus
        mAudioManager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
        //Array list of numbers in English
        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("Where are You Going?","minto wuksus",R.raw.phrase_where_are_you_going));
        words.add(new Word("What is your Name?","tinne oyaase'ne",R.raw.phrase_what_is_your_name));
        words.add(new Word("My name is...","oyaaset",R.raw.phrase_my_name_is));
        words.add(new Word("How are you feeling?","michekses",R.raw.phrase_how_are_you_feeling));
        words.add(new Word("I'm feeling good.","kuchi achit",R.raw.phrase_im_feeling_good));
        words.add(new Word("Are you coming?","eenes'aa?",R.raw.phrase_are_you_coming));
        words.add(new Word("Yes,I'm coming.","hee'eenem",R.raw.phrase_yes_im_coming));
        words.add(new Word("I'm coming.","eenem",R.raw.phrase_im_coming));
        words.add(new Word("Let's Go.","yoowutis",R.raw.phrase_lets_go));
        words.add(new Word("Come Here.","enni'nem",R.raw.phrase_come_here));
        //creating an array adapter
        WordAdapter Adapter=new WordAdapter(this,words,R.color.phrases_category);
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
                    mMediaPlayer = MediaPlayer.create(PhrasesActivity.this, word.getAudioId());
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