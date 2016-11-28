package com.example.thaddeus.csci4100proj;

import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import static java.lang.Thread.sleep;


public class LeaderboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        final TextView textBox = (TextView)findViewById(R.id.textView2);
        textBox.setText("Players:\n");
        final TextView textBox2 = (TextView)findViewById(R.id.textView3);
        textBox2.setText("Score:\n");

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {

                    try {

                        URL url = new URL("http://toastermonkey.co.nf/highscores.xml");
                        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                        DocumentBuilder db = dbf.newDocumentBuilder();
                        Document doc = db.parse(new InputSource(url.openStream()));
                        doc.getDocumentElement().normalize();

                        NodeList nodeList = doc.getElementsByTagName("player");

                        for (int i = 0; i < nodeList.getLength(); i++) {

                            Node node = nodeList.item(i);

                            Element fstElmnt = (Element) node;
                            NodeList nameList = fstElmnt.getElementsByTagName("name");
                            Element nameElement = (Element) nameList.item(0);
                            nameList = nameElement.getChildNodes();
                            textBox.append(((Node) nameList.item(0)).getNodeValue() + "\n");

                            NodeList scoreList = fstElmnt.getElementsByTagName("score");
                            Element scoreElement = (Element) scoreList.item(0);
                            scoreList = scoreElement.getChildNodes();
                            textBox2.append(((Node) scoreList.item(0)).getNodeValue() + "\n");

                        }
                    } catch (Exception e) {
                        System.out.println("XML Pasing Excpetion = " + e);
                    }



                    //Your code goes here
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }
        /*
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.HORIZONTAL);

        TextView name[];
        TextView score[];

        try {

            URL url = new URL("http://toastermonkey.co.nf/highscores.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new InputSource(url.openStream()));
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("player");

            name = new TextView[nodeList.getLength()];
            score = new TextView[nodeList.getLength()];

            for (int i = 0; i < nodeList.getLength(); i++) {

                Node node = nodeList.item(i);

                name[i] = new TextView(this);
                score[i] = new TextView(this);

                Element fstElmnt = (Element) node;
                NodeList nameList = fstElmnt.getElementsByTagName("name");
                Element nameElement = (Element) nameList.item(0);
                nameList = nameElement.getChildNodes();
                name[i].setText(((Node) nameList.item(0)).getNodeValue());

                NodeList scoreList = fstElmnt.getElementsByTagName("score");
                Element scoreElement = (Element) scoreList.item(0);
                scoreList = scoreElement.getChildNodes();
                score[i].setText(((Node) scoreList.item(0)).getNodeValue());

                layout.addView(name[i]);
                layout.addView(score[i]);
            }
        } catch (Exception e) {
            System.out.println("XML Pasing Excpetion = " + e);
        }

        setContentView(layout);
    }




        /*
        List<Entry> data = null;
        EditText textBox = (EditText)findViewById(R.id.textView2);

        InputStream a = getResources().openRawResource(
                getResources().getIdentifier("highscores",
                        "raw", getPackageName()));
        try {
            data = parse(a);
            for (int i = 0 ; i < data.size() ; i++) {
                textBox.append(data.get(i).name + "\t\t\t\t\t" + data.get(i).score + "\n");
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private static final String ns = null;

    public List parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readFeed(parser);
        } finally {
            in.close();
        }
    }

    private List readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        List entries = new ArrayList();

        parser.require(XmlPullParser.START_TAG, ns, "highscores");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("player")) {
                entries.add(readEntry(parser));
            }
        }
        return entries;
    }

    public static class Entry {
        public final String name;
        public final String score;

        private Entry(String name, String score) {
            this.name = name;
            this.score = score;
        }
    }

    private Entry readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "player");
        String name = null;
        String score = null;
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String parserName = parser.getName();
            if (parserName.equals("name")) {
                name = readName(parser);
            } else if (name.equals("score")) {
                score = readScore(parser);
            }
        }
        return new Entry(name,score);
    }

    private String readName(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "name");
        String name = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "name");
        return name;
    }


    private String readScore(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "score");
        String score = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "score");
        return score;
    }

    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }
    */
    public void returnToMain(View view){
        finish();
    }
}
