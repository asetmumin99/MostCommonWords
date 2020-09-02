import edu.duke.*;
/**
 * Write a description of WordLengths here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CaesarCipher {
    
    public void countWordLengths(FileResource resource, int[] counts){
        for(String word : resource.words()){
            
            if(!Character.isLetter(word.charAt(0)) || 
               !Character.isLetter(word.charAt(word.length()-1))){
                counts[word.length()-1]++;
                continue;
            }
            
            if(word.length() >= counts.length){
                counts[counts.length - 1]++;
                continue;
            }
            counts[word.length()]++;
        }
    }
    
    public void testCountWordLengths(){
        FileResource resource = new FileResource();
        int[] counts = new int[31];
        countWordLengths(resource, counts);
        
        for(int i = 0; i < counts.length; i++){
            if(counts[i] > 0){
                System.out.print(counts[i] + " words of length " + 
                                   i + ": ");
                for(String word : resource.words()){
                    if(!Character.isLetter(word.charAt(0)) || 
                        !Character.isLetter(word.charAt(word.length()-1))){
                            if(word.length()-1 == i)
                                System.out.print( word + "  ");
                    }
                    
                    else if(word.length() == i)
                        System.out.print( word + "  ");
                    
                }
                System.out.println();
            }
        }
        
        System.out.println("maxIndex = " + indexOfMax(counts));
    }
    
    public int indexOfMax (int[] values){
        int max = 0, index = 0;
        for(int i = 0; i < values.length; i++){
            if (max < values[i]){
                max = values[i];
                index = i;
            }
        }
        return index;
    }
    
    public int[] countLetters(String message){
        String alph = "abcdefghijklmnopqrstuvwxyz";
        int[] counts = new int[26];
        for(int i = 0; i < message.length(); i++){
            char ch = Character.toLowerCase(message.charAt(i));
            int dex = alph.indexOf(ch);
            if(dex != -1)
                counts[dex] += 1;
        }
        
        return counts;
    }
    
    public String encrypt(String input, int key) {
        //Make a StringBuilder with message (encrypted)
        StringBuilder encrypted = new StringBuilder(input);
        //Write down the alphabet
        String upAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowAlphabet = "abcdefghijklmnopqrstuvwxyz";
        //Compute the shifted alphabet
        String shiftedUpAlphabet = upAlphabet.substring(key)+
        upAlphabet.substring(0,key);
        String shiftedLowAlphabet = lowAlphabet.substring(key)+
        lowAlphabet.substring(0,key);
        //Count from 0 to < length of encrypted, (call it i)
        for(int i = 0; i < encrypted.length(); i++) {
            //Look at the ith character of encrypted (call it currChar)
            char currChar = encrypted.charAt(i);
            //Find the index of currChar in the alphabet (call it idx)
            int idxUp = upAlphabet.indexOf(currChar);
            int idxLow = lowAlphabet.indexOf(currChar);
            //If currChar is in the alphabet
            if(idxUp != -1){
                //Get the idxth character of shiftedAlphabet (newChar)
                char newChar = shiftedUpAlphabet.charAt(idxUp);
                //Replace the ith character of encrypted with newChar
                encrypted.setCharAt(i, newChar);
            }
            if(idxLow != -1){
                //Get the idxth character of shiftedAlphabet (newChar)
                char newChar = shiftedLowAlphabet.charAt(idxLow);
                //Replace the ith character of encrypted with newChar
                encrypted.setCharAt(i, newChar);
            }
        }
        //Your answer is the String inside of encrypted
        return encrypted.toString();
    }
    
    public String decrypt(String encrypted){
        CaesarCipher cc = new CaesarCipher();
        int[] freqs = countLetters(encrypted);
        int maxDex = indexOfMax(freqs);
        int dkey = maxDex - 4;
        if (maxDex < 4)
            dkey = 26 - (4-maxDex);
        return cc.encrypt(encrypted, 26-dkey);
    }
    
}
