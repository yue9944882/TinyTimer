package com.kimmin.es.plugin.demo;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;


/**
 * Created by min.jin on 2016/2/26.
 */
public class CustomFilter extends TokenFilter {

    private final CharTermAttribute termAttribute = addAttribute(CharTermAttribute.class);

    protected CustomFilter(TokenStream input){
        super(input);
    }

    @Override
    public boolean incrementToken() throws IOException {
        if(input.incrementToken()){
            char[] originalTerm = termAttribute.buffer();
            if(originalTerm.length>0){
                StringBuilder builder = new StringBuilder(new String(originalTerm).trim()).reverse();
                termAttribute.setEmpty();
                termAttribute.append(builder.toString());
            }
            return true;
        }else{
            return false;
        }
    }



}
