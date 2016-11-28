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

    public void returnToMain(View view){
        finish();
    }
}
