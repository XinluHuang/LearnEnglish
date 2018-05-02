package com.example.nietzche.test3.wordmemorize;

import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.nietzche.test3.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class WordMemorizing_Act extends AppCompatActivity{
    private MyList wordlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_memorizing);
        copyDB();
        Fragment_WordList fra_wordlist=new Fragment_WordList();
        Bundle b=new Bundle();
        b.putSerializable("wordlist",getWordList());
        fra_wordlist.setArguments(b);
        getSupportFragmentManager().beginTransaction().add(R.id.act_memorize,fra_wordlist).commit();
    }

    public MyList getWordList() {
        if (wordlist == null) {
            int tag = getIntent().getFlags();
            XmlResourceParser parser=null;
            switch (tag) {
                case 1:
                    parser = getResources().getXml(R.xml.custom_1);
                    break;
                case 2:
                    parser = getResources().getXml(R.xml.custom_2);
                    break;
                case 3:
                    parser = getResources().getXml(R.xml.custom_3);
                    break;
                default:
                    break;
            }

            wordlist = new MyList();
            try {
                for (int event = parser.getEventType(); event != XmlPullParser.END_DOCUMENT; event = parser.next()) {
                    if (event == XmlPullParser.TEXT) {

                        wordlist.add(parser.getText());
                    }
                }
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return wordlist;
    }

    private void copyDB() {
        File mkdir = new File(getFilesDir().getParent(), "databases");
        if (!mkdir.exists()) {
            mkdir.mkdirs();
        }
        File destination = new File(mkdir, "dict.db");
        FileOutputStream fos = null;
        InputStream resinput = null;
        if (!destination.exists()) {
            try {
                fos = new FileOutputStream(destination);
                resinput = getAssets().open("dict.db");
                byte[] bytes = new byte[2048];
                int len = 0;
                while ((len = resinput.read(bytes)) != -1) {
                    fos.write(bytes, 0, len);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    fos.close();
                    resinput.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
