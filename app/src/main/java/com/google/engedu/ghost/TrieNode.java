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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


public class TrieNode {
    private HashMap<Character, TrieNode> children;
    private boolean isWord;

    public TrieNode() {
        children = new HashMap<>();
        isWord = false;
    }

    public void add(String s) {
        TrieNode current=this;
        for(int i=0;i<s.length();i++){
            char c=s.charAt(i);
            TrieNode node=current.children.get(c);
            if(node==null){
                node=new TrieNode();
                current.children.put(c,node);
                //System.out.println(current.children);

            }
            current=node;
        }
        current.isWord=true;
    }


    public boolean isWord(String s){
        TrieNode current=this;
        for(int i=0;i<s.length();i++){
            char c=s.charAt(i);
            TrieNode node=current.children.get(c);
            if(node==null){
                return  false;
            }
            current=node;
        }
        return current.isWord;
    }

    public String getAnyWordStartingWith(String s) {
        Random r = new Random();
        String word = "";
        TrieNode current = this;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            TrieNode node = current.children.get(c);
            if (node == null) {
                return null;
            }
            current = node;
            word += c;
        }

        while (!current.isWord) {
            ArrayList<Character> listnew = new ArrayList<>();
            ArrayList<Character> list = new ArrayList<>(current.children.keySet());
            for (int i = 0; i < list.size(); i++) {
                char ch = list.get(i);
                if (!isWord(word + ch)) {
                    listnew.add(ch);
                }
            }
            char c;
            if (!listnew.isEmpty()) {
                c = listnew.get(r.nextInt(listnew.size()));
            } else {
                c = list.get(r.nextInt(list.size()));
            }
            word += c;
            current=current.children.get(c);



        }
        return word;
    }

    public String getGoodWordStartingWith(String s) {
        return null;
    }
}
