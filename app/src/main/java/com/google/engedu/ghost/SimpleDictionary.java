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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class SimpleDictionary implements GhostDictionary {
    private ArrayList<String> words,evenWords,oddWords;
    private Random random=new Random();

    public SimpleDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        words = new ArrayList<>();
        evenWords=new ArrayList<>();
        oddWords=new ArrayList<>();
        String line = null;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            if (word.length() >= MIN_WORD_LENGTH) {
                words.add(line.trim());
                if (word.length() % 2 == 0) {
                    evenWords.add(line.trim());
                } else {
                    oddWords.add(line.trim());
                }
            }


        }
    }

    @Override
    public boolean isWord(String word) {
        return words.contains(word);
    }

    @Override
    public String getAnyWordStartingWith(String prefix) {
        String word=null;
        Collections.sort(words);
        if(prefix.isEmpty()){
            word=words.get(random.nextInt(words.size()));
        }
        else {
            if (prefix.length() % 2 == 0) {
                Collections.sort(evenWords);
                int low = 0;
                int high = evenWords.size() - 1;
                String s;
                int mid;
                while (high >= low) {
                    mid = (low + high) / 2;
                    s = evenWords.get(mid);
                    if (evenWords.get(mid).startsWith(prefix)) {
                        word = s;
                        break;
                    } else {
                        if (prefix.compareTo(s) < 0) {
                            high = mid - 1;
                        } else
                            low = mid + 1;

                    }


                }
            }
            else{
                if (prefix.length()%2 != 0) {
                    Collections.sort(oddWords);
                    int low = 0;
                    int high = oddWords.size() - 1;
                    String s;
                    int mid;
                    while (high >= low) {
                        mid = (low + high) / 2;
                        s = oddWords.get(mid);
                        if (oddWords.get(mid).startsWith(prefix)) {
                            word = s;
                            break;
                        } else {
                            if (prefix.compareTo(s) < 0) {
                                high = mid - 1;
                            } else
                                low = mid + 1;

                        }


                    }
                }

            }
        }
        return word;
    }

    @Override
    public String getGoodWordStartingWith(String prefix) {

        String selected = null;
        return selected;
    }
}
