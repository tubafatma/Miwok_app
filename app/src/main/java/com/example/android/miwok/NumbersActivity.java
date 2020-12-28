package com.example.android.miwok;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.MediaDataSource;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {
    //Handles playback of all sound files
    private MediaPlayer mMediaPlayer;
    //Handles audio focus when playing a sound file
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
        setContentView(R.layout.activity_numbers);

        //Create and setup audio manager to request for audio focus
        mAudioManager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);


        //Array list of numbers in English
        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("One","lutti",R.drawable.number_one,R.raw.number_one));
        words.add(new Word("Two","otiiko",R.drawable.number_two,R.raw.number_two));
        words.add(new Word("Three","tolookosu",R.drawable.number_three,R.raw.number_three));
        words.add(new Word("Four","oyyisa",R.drawable.number_four,R.raw.number_four));
        words.add(new Word("Five","massoka",R.drawable.number_five,R.raw.number_five));
        words.add(new Word("Six","temmoka",R.drawable.number_six,R.raw.number_six));
        words.add(new Word("Seven","kenekakku",R.drawable.number_seven,R.raw.number_seven));
        words.add(new Word("Eight","kawinta",R.drawable.number_eight,R.raw.number_eight));
        words.add(new Word("Nine","wo'e",R.drawable.number_nine,R.raw.number_nine));
        words.add(new Word("Ten","na'aacha",R.drawable.number_ten,R.raw.number_ten));
        //creating an array adapter
        WordAdapter Adapter=new WordAdapter(this,words,R.color.numbers_category);
        ListView listView=(ListView) findViewById(R.id.list);
        listView.setAdapter(Adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                //get the Word object at the given position user clicked on
                Word word=words.get(position);
                //Release the media file if it exists
                //play a different sound file
                releaseMediaPlayer();
                //Request audiofocus
                int result=mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if(result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    //We have Audio focus now
                            //Create Media player for audio associated with word
                            mMediaPlayer = MediaPlayer.create(NumbersActivity.this, word.getAudioId());
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