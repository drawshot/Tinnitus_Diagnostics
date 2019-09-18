package ivis.barkscale;

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Build;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by wizard on 2018. 3. 12..
 */

enum EarDirType {
    LEFT, RIGHT, BOTH
}

enum SoundType {
    TONE, NOISE
}

public class PureTone {
    private Context mContext;
    private static PureTone INSTANCE;
    private int sampleRate = 44100;

    public static PureTone getInstance(Context context) {
        if(INSTANCE == null) {
            INSTANCE = new PureTone(context.getApplicationContext()); }
        return INSTANCE;
    }

    private PureTone(Context context) {
        mContext = context;
//        int sampleRate = 44100;// 44.1 KHz
        int bufferSize = AudioTrack.getMinBufferSize(sampleRate, AudioFormat.CHANNEL_OUT_STEREO,
                AudioFormat.ENCODING_PCM_16BIT);
        audioTrack =
                new AudioTrack(AudioManager.STREAM_MUSIC, sampleRate, AudioFormat.CHANNEL_OUT_STEREO,
                        AudioFormat.ENCODING_PCM_16BIT, bufferSize, AudioTrack.MODE_STREAM);
    }

    private boolean isPlaying = false;
    public EarDirType earDir = EarDirType.LEFT;
    private AudioTrack audioTrack = null;
    byte[] geneatedSound = null;

    static final int byte_length = 2; //16 bit pcm sound
    static final int ch = 2;     // Stereo 채널


    void generate(int frequency, int duration, float volume, EarDirType dir, SoundType soundType, int nid ) {
        earDir = dir;
        if(audioTrack != null) {
            Log.d("audioTrack is " , "not null");
            audioTrack.stop();
        }
        if (!isPlaying) {
            isPlaying = true;
            double dnumSamples = (double)  ch * duration * sampleRate; //total buffer length
            dnumSamples = Math.ceil(dnumSamples);
            int numSamples = (int) dnumSamples;
            double[] sample = new double[numSamples];


            try {
                audioTrack.setNotificationMarkerPosition(numSamples);
//                Log.d("bufferSize " , "=" + bufferSize);
//                Log.d("numSamples " , "=" + numSamples);
                audioTrack.setPlaybackPositionUpdateListener(
                        new AudioTrack.OnPlaybackPositionUpdateListener() {
                            @Override public void onPeriodicNotification(AudioTrack track) {
                                // nothing to do
                            }

                            @Override public void onMarkerReached(AudioTrack track) {
//                                toneStoppedListener.onToneStopped();
                            }
                        });

                // Sanity Check for max volume, set after write method to handle issue in android
                // v 4.0.3
                float maxVolume = AudioTrack.getMaxVolume();

                if (volume > maxVolume) {
                    volume = maxVolume;
                } else if (volume < 0) {
                    volume = 0;
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    audioTrack.setVolume(volume);
                } else {
                    audioTrack.setStereoVolume(volume, volume);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            geneatedSound = new byte[byte_length * numSamples]; //total length * 16bit

            if (soundType == SoundType.TONE) {
                for (int i = 0; i < numSamples; ++i) {      // Fill the sample array
                    sample[i] = Math.sin(2 * frequency * Math.PI * i / (sampleRate * 2));
                }
                int idx = 0;

                for (int i = 0; i < numSamples; i+=2) {                        // Max amplitude for most of the samples
                    double dVal = sample[i];
                    // scale to maximum amplitude
                    final short val = (short) (dVal * 32767);
                    // in 16 bit wav PCM, first byte is the low order byte

                    switch(earDir) {
                        case LEFT:
                            geneatedSound[idx++] = (byte) (val & 0x00ff);
                            geneatedSound[idx++] = (byte) ((val & 0xff00) >>> 8);
                            geneatedSound[idx++] = 0;
                            geneatedSound[idx++] = 0;
                            break;
                        case RIGHT:
                            geneatedSound[idx++] = 0;
                            geneatedSound[idx++] = 0;
                            geneatedSound[idx++] = (byte) (val & 0x00ff);
                            geneatedSound[idx++] = (byte) ((val & 0xff00) >>> 8);
                            break;
                        case BOTH:
                            geneatedSound[idx++] = (byte) (val & 0x00ff);
                            geneatedSound[idx++] = (byte) ((val & 0xff00) >>> 8);
                            geneatedSound[idx++] = (byte) (val & 0x00ff);
                            geneatedSound[idx++] = (byte) ((val & 0xff00) >>> 8);
                            break;
                        default: break;
                    }
                }
            } else {
                InputStream inputStream = mContext.getResources().openRawResource(nid);
                int i = 0;
                try {
                    while((i = inputStream.read(geneatedSound)) != -1)
                        audioTrack.write(geneatedSound, 0, i);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }



            stop();

        }
    }

    void generate(int nid,  float volume, EarDirType dir ) {
        earDir = dir;
        if(audioTrack != null) {
            Log.d("audioTrack is " , "not null");
            audioTrack.stop();
        }
        if (!isPlaying) {
            isPlaying = true;
            double dnumSamples = (double)  ch * 2 * sampleRate; //total buffer length
            dnumSamples = Math.ceil(dnumSamples);
            int numSamples = (int) dnumSamples;
            double[] sample = new double[numSamples];


            try {
                audioTrack.setNotificationMarkerPosition(numSamples);
//                Log.d("bufferSize " , "=" + bufferSize);
//                Log.d("numSamples " , "=" + numSamples);
                audioTrack.setPlaybackPositionUpdateListener(
                        new AudioTrack.OnPlaybackPositionUpdateListener() {
                            @Override public void onPeriodicNotification(AudioTrack track) {
                                // nothing to do
                            }

                            @Override public void onMarkerReached(AudioTrack track) {
//                                toneStoppedListener.onToneStopped();
                            }
                        });

                // Sanity Check for max volume, set after write method to handle issue in android
                // v 4.0.3
                float maxVolume = AudioTrack.getMaxVolume();

                if (volume > maxVolume) {
                    volume = maxVolume;
                } else if (volume < 0) {
                    volume = 0;
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    audioTrack.setVolume(volume);
                } else {
                    audioTrack.setStereoVolume(volume, volume);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }



//            int bufferSize = 512;
//            geneatedSound = new byte[bufferSize];

            InputStream inputStream = mContext.getResources().openRawResource(nid);
            int i = 0;
            try {
                while((i = inputStream.read(geneatedSound)) != -1)
                    audioTrack.write(geneatedSound, 0, i);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    void play() {
        Thread th = new Thread() {
            @Override
            public void run() {
            audioTrack.play();
            audioTrack.write(geneatedSound, 0, geneatedSound.length);
            }
        };
        th.start();
    }



    void stop() {
        audioTrack.stop();
        isPlaying = false;
    }

    void genAndPlay(int frequency, int duration, float volume, EarDirType dir, SoundType soundType, int nid){
        generate(frequency, duration, volume, dir, soundType, nid);
        play();
    }

}
