package console;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class AcademyApp {
    public static final String RED= "\u001B[31m" ;
    public static final String BLACK= "\u001B[30m" ;
   
	public static void colorError(String colorError) {
		System.out.println(RED+colorError+BLACK);
	}////에러 글자
	
	public static void main(String[] args) {
		AcademyCollectionObjectInOutLogic logic = new AcademyCollectionObjectInOutLogic();
		System.out.println(logic.printClock());
		logic.threadSleep(1000);
		while(true) {
			//1.메인 메뉴 출력
			try {
				logic.printMainMenu();
				//2.메인메뉴 번호 입력받기
				int mainMenu=logic.getMenuNumber();
				//3. 메인메뉴에 따른 분기
				logic.seperateMainMenu(mainMenu);
			}
			catch(NumberFormatException e) {
				System.out.print(" ".repeat(44));colorError("아무것도 입력하지 않았아요");
			}
			
		}
	}////main

}///////class

