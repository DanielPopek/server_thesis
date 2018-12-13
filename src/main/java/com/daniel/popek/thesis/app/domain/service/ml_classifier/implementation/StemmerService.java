package com.daniel.popek.thesis.app.domain.service.ml_classifier.implementation;

import com.daniel.popek.thesis.app.domain.service.ml_classifier.IStemmerService;
import morfologik.stemming.WordData;
import morfologik.stemming.polish.PolishStemmer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class StemmerService implements IStemmerService{


    private String removeGeneralEnd(String word)
    {
        String[] twoLast= new String[]{"ia", "ie"};
        String[] oneLast= new String[]{"u", "ą", "i", "a", "ę", "y", "ę", "ł"};
        if(word.length()>4&&Arrays.asList(twoLast).contains(word.substring(word.length()-2)))
            return word.substring(0,word.length()-2);
        if(word.length()>4&&Arrays.asList(oneLast).contains(word.substring(word.length()-1)))
            return word.substring(0,word.length()-1);
        return word;

    }


    private String removeDeminutive(String word)
    {
        String[] fiveLast= new String[]{"eczek", "iczek", "iszek", "aszek", "uszek"};
        String[] fourLast= new String[]{"enek", "ejek", "erek"};
        String[] twoLast= new String[]{"ek", "ak"};
        if(word.length()>6)
        {
            if(Arrays.asList(fiveLast).contains(word.substring(word.length()-5)))
                return word.substring(0,word.length()-5);
            if(Arrays.asList(fourLast).contains(word.substring(word.length()-4)))
                return word.substring(0,word.length()-2);
        }
        if(word.length()>4&&Arrays.asList(twoLast).contains(word.substring(word.length()-2)))
            return word.substring(0,word.length()-2);
        return word;
    }

    private String removeVerbsEnds(String word)
    {
        String[] endings1= new String[]{"esz", "asz", "cie", "eść", "aść", "łem", "amy", "emy"};
        String[] endings2= new String[]{"esz", "asz", "eść", "aść", "eć", "ać"};
        String[] endings3= new String[]{"aj"};
        String[] endings4= new String[]{"ać", "em", "am", "ał", "ił", "ić", "ąc"};

        if(word.length()>5&&word.endsWith("bym"))
            return word.substring(0,word.length()-3);
        if(word.length()>5&&Arrays.asList(endings1).contains(word.substring(word.length()-3)))
            return word.substring(0,word.length()-3);
        if(word.length()>3&&Arrays.asList(endings2).contains(word.substring(word.length()-3)))
            return word.substring(0,word.length()-2);
        if(word.length()>3&&Arrays.asList(endings3).contains(word.substring(word.length()-2)))
            return word.substring(0,word.length()-1);
        if(word.length()>3&&Arrays.asList(endings4).contains(word.substring(word.length()-2)))
            return word.substring(0,word.length()-2);
        return word;
    }

    private String removeNouns(String word)
    {
        String[] endings1= new String[]{"zacja", "zacją", "zacji"};
        String[] endings2= new String[]{"acja", "acji", "acją", "tach", "anie", "enie","eniu", "aniu"};
        String[] endings3= new String[]{"ach", "ami", "nia", "niu", "cia", "ciu"};
        String[] endings4= new String[]{"cji", "cja", "cją"};
        String[] endings5= new String[]{"ce", "ta"};
        if(word.length()>7&&Arrays.asList(endings1).contains(word.substring(word.length()-5)))
            return word.substring(0,word.length()-5);
        if(word.length()>6&&Arrays.asList(endings2).contains(word.substring(word.length()-4)))
            return word.substring(0,word.length()-4);
        if(word.length()>6&&word.endsWith("tyka"))
            return word.substring(0,word.length()-2);
        if(word.length()>5&&Arrays.asList(endings3).contains(word.substring(word.length()-3)))
            return word.substring(0,word.length()-3);
        if(word.length()>5&&Arrays.asList(endings4).contains(word.substring(word.length()-3)))
            return word.substring(0,word.length()-2);
        if(word.length()>5&&Arrays.asList(endings5).contains(word.substring(word.length()-2)))
            return word.substring(0,word.length()-2);
        return word;
    }


private String removeAdjectiveEnds(String word)
{
    String[] endings1= new String[]{"owy", "owa", "owe", "ych", "ego"};
    if(word.length()>7&&word.startsWith("naj")&&(word.endsWith("sze")||word.endsWith("szy")))
        return word.substring(3,word.length()-3);
    if(word.length()>7&&word.startsWith("naj")&&word.endsWith("szych"))
        return word.substring(3,word.length()-5);
    if(word.length()>6&&word.endsWith("czny"))
        return word.substring(0,word.length()-4);
    if(word.length()>5&&Arrays.asList(endings1).contains(word.substring(word.length()-3)))
        return word.substring(0,word.length()-3);
    if(word.length()>5&&word.endsWith("ej"))
        return word.substring(0,word.length()-2);
    return word;
}

private String removeAdverbs(String word)
{
    String[] endings1= new String[]{"nie", "wie"};
    if(word.length()>4&&Arrays.asList(endings1).contains(word.substring(word.length()-3)))
        return word.substring(0,word.length()-2);
    if(word.length()>4&&word.endsWith("rze"))
        return word.substring(0,word.length()-2);
    return word;
}

private String removePluralForms(String word)
{
    if(word.length()>4&&(word.endsWith("ów")||word.endsWith("om")))
        return word.substring(0,word.length()-2);
    if(word.length()>4&&word.endsWith("ami"))
        return word.substring(0,word.length()-3);
    return word;
}


    public String preprocessManually(String input)
    {

        String stem = removeNouns(input);
        stem = removeDeminutive(stem);
        stem = removeAdjectiveEnds(stem);
        stem = removeVerbsEnds(stem);
        stem = removeAdverbs(stem);
        stem = removePluralForms(stem);
        stem = removeGeneralEnd(stem);
        return stem;
    }

    @Override
    public String stem(String input) {
        PolishStemmer stemmer=new PolishStemmer();
        List<WordData> wordData=stemmer.lookup(input);
        if(wordData!=null&&wordData.size()==1)
        return wordData.get(0).getStem().toString();
        else
        {
            String upperInput=Character.toUpperCase(input.charAt(0))+input.substring(1,input.length());
            wordData=stemmer.lookup(upperInput);
            if(wordData!=null&&wordData.size()==1)
                return wordData.get(0).getStem().toString();
            else return preprocessManually(input);
        }
    }

}
