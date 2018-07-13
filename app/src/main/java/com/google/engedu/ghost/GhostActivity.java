/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.ghost;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import static android.R.attr.label;


public class GhostActivity extends AppCompatActivity {
    private Button button1,button2;
    private TextView t1,t2;
    private RelativeLayout ghost;
    private String wordFragment="";
    private static final String COMPUTER_TURN = "Computer's turn";
    private static final String USER_TURN = "Your turn";
    private GhostDictionary dictionary;
    private final String TAG=GhostActivity.class.getSimpleName();
    private boolean userTurn = false;
    private Random random = new Random();
    private String Key_Word="Hi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghost);
        AssetManager assetManager = getAssets();
        try{
            dictionary=new FastDictionary(assetManager.open("words.txt"));
        }catch(IOException e){
            e.printStackTrace();
        }
        button1=(Button)findViewById(R.id.button_challenge);
        button2=(Button)findViewById(R.id.button_restart);
        t1=(TextView)findViewById(R.id.ghostText);
        t2=(TextView)findViewById(R.id.gameStatus);
        ghost=(RelativeLayout)findViewById(R.id.ghost);
        if(savedInstanceState==null){
            onStart(null);
        }
        else {
            wordFragment = savedInstanceState.getString(Key_Word);
            t1.setText(wordFragment);
            String status = savedInstanceState.getString("Hey");
            t2.setText(status);
            if (status.equals("Computer Wins!") || status.equals("User Wins!")) {
                wordFragment = "";
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ghost, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Handler for the "Reset" button.
     * Randomly determines whether the game starts with a user turn or a computer turn.
     * @param view
     * @return true
     */
    public boolean onStart(View view) {
        wordFragment="";
        t1.setText(wordFragment);
        userTurn = random.nextBoolean();
        if (userTurn) {
            t2.setText(USER_TURN);
        } else {
            t2.setText(COMPUTER_TURN);
            computerTurn();
        }
        return true;
    }
    public void onChallenge(View view) {

        String word;
        if (!wordFragment.isEmpty()) {
            word = dictionary.getAnyWordStartingWith(wordFragment);
            if (dictionary.isWord(wordFragment)) {
                t2.setText("User Wins!");
                wordFragment = "";
            } else {
                t2.setText("Computer wins!");
                wordFragment = "";
                t1.setText(word);
            }
        }
    }

    private void computerTurn() {
        //TextView label = (TextView) findViewById(R.id.gameStatus);
        // Do computer turn stuff then make it the user's turn again
        String word;
        word=dictionary.getAnyWordStartingWith(wordFragment);

        if(dictionary.isWord(wordFragment)){
          t2.setText("Computer Wins!");
            wordFragment="";
        }
        else{
            if(word==null){
                t2.setText("Computer Wins!");
                wordFragment="";
            }
            else{
                int index=wordFragment.length();
                wordFragment+=word.charAt(index);
                t1.setText(wordFragment);
                userTurn = true;
                t2.setText(USER_TURN);
            }
        }




    }

    /**
     * Handler for user key presses.
     * @param keyCode
     * @param event
     * @return whether the key stroke was handled.
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if(t2.getText()=="Computer Wins!" || t2.getText()=="User Wins!"){
            return super.onKeyUp(keyCode, event);
        }
        char ch=(char)event.getUnicodeChar();
        if(Character.isLetter(ch)){
            wordFragment=wordFragment+ch;
            t1.setText(wordFragment);
            computerTurn();
            return true;
        }




        return super.onKeyUp(keyCode, event);
    }
    @Override
    protected void onSaveInstanceState(Bundle outState){
        outState.putString(Key_Word,
                !wordFragment.isEmpty()?wordFragment:t1.getText().toString());
        outState.putString("Hey",!wordFragment.isEmpty()?(userTurn?USER_TURN:COMPUTER_TURN):
                                    t2.getText().toString());
        super.onSaveInstanceState(outState);
    }
}
