package com.example.thaddeus.csci4100proj;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import java.net.URL;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class LeaderboardActivity extends AppCompatActivity {
    private String url = "http://toastermonkey.co.nf/highscores.xml";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        DownloadXMLTask downloadXMLTask = new DownloadXMLTask();
        downloadXMLTask.execute(url);
    }

    public void showScores(NodeList nodeList){
        TextView textBox = (TextView) findViewById(R.id.txtPlayers);
        TextView textBox2 = (TextView) findViewById(R.id.txtScores);
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
    }

    class DownloadXMLTask extends AsyncTask<String, Void, NodeList> {
        private Exception exception = null;
        private NodeList nodeList = null;

        @Override
        public NodeList doInBackground(String... params) {

            try {

                URL url = new URL(params[0]);
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.parse(new InputSource(url.openStream()));
                doc.getDocumentElement().normalize();

                nodeList = doc.getElementsByTagName("player");



            } catch (Exception e) {
                e.printStackTrace();
            }

            return nodeList;
        }

        @Override
        protected void onPostExecute(NodeList result){
            if(exception != null) {
                exception.printStackTrace();
                return;
            }

            showScores(result);
        }
    }

    public void returnToMain(View view){
        finish();
    }
}
