package com.example.android.miwok;


import android.widget.ImageView;

//It contains english and Miwok translation
public class Word {
    //Default Translation
    private  String mDefaultTranslation;
    //Miwok Translation
    private String mMiwokTranslation;
    //image resource id
    private int mImage=NO_IMAGEPROVIDED;

    private  static final int NO_IMAGEPROVIDED=-1;
    //Audio ResourceID is stored
    private int mAudioResourceId;

    public Word(String defaultTranslation,String miwokTranslation,int Img,int AudioResourceId)
    {
        mDefaultTranslation=defaultTranslation;
        mMiwokTranslation=miwokTranslation;
        mImage=Img;
        mAudioResourceId=AudioResourceId;
    }
    public Word(String defaultTranslation,String miwokTranslation,int AudioResourceId)
    {
        mDefaultTranslation=defaultTranslation;
        mMiwokTranslation=miwokTranslation;
        mAudioResourceId=AudioResourceId;
    }
    //Get Default translation
    public String getDefaultTranslation()
    {
        return mDefaultTranslation;
    }
    //Get Miwok translation
    public String getMiwokTranslation()
    {
        return mMiwokTranslation;
    }
    //Get Image Resource Id
    public int getImageResourceId()
    {
        return mImage;

    }
    //Returns whether or not there is an image
    public boolean hasImage() {
        return mImage != NO_IMAGEPROVIDED;
    }
    //Returns the Audio Id
    public int getAudioId()
    {
        return mAudioResourceId;
    }
}
