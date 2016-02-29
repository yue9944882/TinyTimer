//package com.kimmin.es.plugin.demo;
//
//import org.apache.lucene.analysis.Analyzer;
//import org.apache.lucene.analysis.TokenStream;
//import org.apache.lucene.analysis.Tokenizer;
//import org.apache.lucene.analysis.core.WhitespaceTokenizer;
//import org.apache.lucene.util.Version;
//
//import java.io.Reader;
//
///**
// * Created by min.jin on 2016/2/26.
// */
//public class CustomAnalyzer extends Analyzer{
//
//    private final Version version;
//
//    public CustomAnalyzer(final Version version){
//        this.version = version;
//    }
//
//    @Override
//    protected TokenStreamComponents createComponents(String field,Reader reader){
//        final Tokenizer src = new WhitespaceTokenizer(this.version, reader);
//        return new TokenStreamComponents(src, new CustomFilter(src));
//    }
//
//}
