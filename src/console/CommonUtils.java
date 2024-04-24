package console;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class CommonUtils {
//■■■■한글 초성 ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
	public static boolean isNumber(String value) {
		for(int i=0;i < value.length();i++) {		
			int codeValue = Character.codePointAt(value, i);
			if(!(codeValue >='0' && codeValue<='9')) return false;
		}
		return true;
	}
	
	public static char getInitialConsonant(String value) {
		if(!Pattern.matches("^[가-힣]{1,4}$", value.trim())) return '0';
		char lastName=value.charAt(0);
		
		int index = (lastName-'가')/28/21;//초성의 인덱스 얻기
		char[] initialConsonants= {'ㄱ','ㄲ','ㄴ','ㄷ','ㄸ','ㄹ','ㅁ','ㅂ','ㅃ','ㅅ','ㅆ','ㅇ','ㅈ','ㅉ','ㅊ','ㅋ','ㅌ','ㅍ','ㅎ'};
		return initialConsonants[index];
	}
//■■■■한글 이름 간격■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
	private static int countKoreaWord(String word) {
	    int koreaCharacter = 0;
	    for (int i = 0 ; i < word.length() ; i++) {
	        if (word.charAt(i) >= 'ㄱ' && word.charAt(i) <= '힣') {
	        	koreaCharacter++;
	        }
	    } return koreaCharacter;
	}
	
	public static String stringKoreaWord(String word, int padding,boolean alternate) {
		if(alternate==true) {
			return "ㅤ".repeat(padding-countKoreaWord(word))+word;
		}
		return word+"ㅤ".repeat(padding-countKoreaWord(word));
	}
//■■■■한글/영어 맞추기 메소드■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■	
	private static int[] countCharacter(String word) {
		int countCharacterArray[] = new int[2];
	    int koreaCharacter = 0;
	    int englishCharacter =0;
	    for (int i = 0 ; i < word.length() ; i++) {
	        if (word.charAt(i) >= 'ㄱ' && word.charAt(i) <= '힣') {
	        	koreaCharacter++;
	        }
	        else {
	        	englishCharacter++;
	        }
	        
	    }
	    countCharacterArray[0]=koreaCharacter;
	    countCharacterArray[1]=englishCharacter-1;
	    return countCharacterArray;
	}
	
	public static String stringPaddingWord(String word, int padding) {
		int countCharacterArray[] = new int[2];
		int countFullCharacter=0;
		
		int spacing=0;
		countCharacterArray=countCharacter(word);
		countFullCharacter=countCharacterArray[0]+countCharacterArray[1];
		
		int countKoreaCharacter=countCharacterArray[0];///배열에 저장된 한글 갯수
		int countEnglishCharacter=countCharacterArray[1];///배열에 저장된 영어 갯수
		
		countFullCharacter=(countCharacterArray[0]+countCharacterArray[1]);
		
		//●●영어가 있고 나머지가 0●●
		if(countCharacterArray[1]!=-1 && (countCharacterArray[1]%4)==0) {
			return word+"ㅤ".repeat(padding-countFullCharacter)
					+"ㅤ".repeat((countCharacterArray[1]/4))+" ㅤ";//완료
		}
		//●●영어가 있고 나머지가 1●●
		if(countCharacterArray[1]!=-1 && (countCharacterArray[1]%4)==1) {
			return word+"ㅤ".repeat(padding-countFullCharacter)
					+"ㅤ".repeat((countCharacterArray[1]/4))+"ㅤㅤ";
		}
		
		//●●영어가 있고 나머지가 2●●
		if(countCharacterArray[1]!=-1 && (countCharacterArray[1]%4)==2) {
			return word+"ㅤ".repeat(padding-countFullCharacter)
					+"ㅤ".repeat((countCharacterArray[1]/4))+"   ";//완료
		}
		//●●영어가 있고 나머지가 3●●
		if(countCharacterArray[1]!=-1 && (countCharacterArray[1]%4)==3) {
			return word+"ㅤ".repeat(padding-countFullCharacter)
					+"ㅤ".repeat((countCharacterArray[1]/4))+"  ㅤ";//완료
		}
		//●●한국어만 있을 경우●●
		else {
			return word+"ㅤ".repeat(padding-countFullCharacter)+"  ";
		}
	}
	
//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
}



