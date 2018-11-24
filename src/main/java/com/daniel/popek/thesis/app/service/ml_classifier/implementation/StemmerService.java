package com.daniel.popek.thesis.app.service.ml_classifier.implementation;

import com.daniel.popek.thesis.app.service.ml_classifier.IStemmerService;
import morfologik.stemming.WordData;
import morfologik.stemming.polish.PolishStemmer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class StemmerService implements IStemmerService{

    /*
     stem = remove_nouns(stem)
                stem = remove_diminutive(stem)
                stem = remove_adjective_ends(stem)
                stem = remove_verbs_ends(stem)
                stem = remove_adverbs_ends(stem)
                stem = remove_plural_forms(stem)
                stem = remove_general_ends(stem)
     */

    /*

def remove_general_ends(word):
    #print "DEBUG: END", word[-1:]
    if len(word) > 4 and word[-2:] in {"ia", "ie"}:
        return word[:-2]
    if len(word) > 4 and word[-1:] in {"u", u"ą", "i", "a", u"ę", "y", u"ę", u"ł"}:
        return word[:-1]
    return word

    */
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
    /*

def remove_diminutive(word):
    if len(word) > 6:
        if word[-5:] in {"eczek", "iczek", "iszek", "aszek", "uszek"}:
            return word[:-5]
        if word[-4:] in {"enek", "ejek", "erek"}:
            return word[:-2]
    if len(word) > 4:
        if word[-2:] in {"ek", "ak"}:
            return word[:-2]
    return word
    */

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
    /*
def remove_verbs_ends(word):
    if len(word) > 5 and word.endswith("bym"):
        return word[:-3]
    if len(word) > 5 and word[-3:] in {"esz", "asz", "cie", u"eść", u"aść", u"łem", "amy", "emy"}:
        return word[:-3]
    if len(word) > 3 and word[-3:] in {"esz", "asz", u"eść", u"aść", u"eć", u"ać"}:
        return word[:-2]
    if len(word) > 3 and word[-3:] in {"aj"}:
        return word[:-1]
    if len(word) > 3 and word[-2:] in {u"ać", "em", "am", u"ał", u"ił", u"ić", u"ąc"}:
        return word[:-2]
    return word

    */
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
    /*

def remove_nouns(word):
    if len(word) > 7 and word[-5:] in {"zacja", u"zacją", "zacji"}:
        return word[:-4]
    if len(word) > 6 and word[-4:] in {"acja", "acji", u"acją", "tach", "anie", "enie",
    "eniu", "aniu"}:
        return word[:-4]
    if len(word) > 6 and word.endswith("tyka"):
        return word[:-2]
    if len(word) > 5 and word[-3:] in {"ach", "ami", "nia", "niu", "cia", "ciu"}:
        return word[:-3]
    if len(word) > 5 and word[-3:] in {"cji", "cja", u"cją"}:
        return word[:-2]
    if len(word) > 5 and word[-2:] in {"ce", "ta"}:
        return word[:-2]
    return word
*/
    private String removeNouns(String word)
    {
        String[] endings1= new String[]{"zacja", "zacją", "zacji"};
        String[] endings2= new String[]{"acja", "acji", "acją", "tach", "anie", "enie","eniu", "aniu"};
        String[] endings3= new String[]{"ach", "ami", "nia", "niu", "cia", "ciu"};
        String[] endings4= new String[]{"cji", "cja", "cją"};
        String[] endings5= new String[]{"ce", "ta"};
        if(word.length()>7&&Arrays.asList(endings1).contains(word.substring(word.length()-5)))
            return word.substring(0,word.length()-4);
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

/*
def remove_adjective_ends(word):
    if len(word) > 7 and word.startswith("naj") and (word.endswith("sze")
    or word.endswith("szy")):
        return word[3:-3]
    if len(word) > 7 and word.startswith("naj") and word.endswith("szych"):
        return word[3:-5]
    if len(word) > 6 and word.endswith("czny"):
        return word[:-4]
    if len(word) > 5 and word[-3:] in {"owy", "owa", "owe", "ych", "ego"}:
        return word[:-3]
    if len(word) > 5 and word[-2:] in {"ej"}:
        return word[:-2]
    return word

*/
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
/*
def remove_adverbs_ends(word):
    if len(word) > 4 and word[:-3] in {"nie", "wie"}:
        return word[:-2]
    if len(word) > 4 and word.endswith("rze"):
        return word[:-2]
    return word
*/
private String removeAdverbs(String word)
{
    String[] endings1= new String[]{"nie", "wie"};
    if(word.length()>4&&Arrays.asList(endings1).contains(word.substring(word.length()-3)))
        return word.substring(0,word.length()-2);
    if(word.length()>4&&word.endsWith("rze"))
        return word.substring(0,word.length()-2);
    return word;
}
/*
def remove_plural_forms(word):
    if len(word) > 4 and (word.endswith(u"ów") or word.endswith("om")):
        return word[:-2]
    if len(word) > 4 and word.endswith("ami"):
        return word[:-3]
    return word
*/
private String removePluralForms(String word)
{
    if(word.length()>4&&(word.endsWith("ów")||word.endsWith("om")))
        return word.substring(0,word.length()-2);
    if(word.length()>4&&word.endsWith("ami"))
        return word.substring(0,word.length()-3);
    return word;
}
/*
def is_in_blacklist(word, blacklist):
    if word in blacklist:
        return True
    return False
     */

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
