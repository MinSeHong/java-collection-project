package console;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Pattern;

public class AcademyCollectionObjectInOutLogic {
//■■■■색상용 코드■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
	public static final String BOLD     		= "\u001B[1m"  ;
	public static final String NOBOLD   		= "\u001B[2m"  ;
    public static final String BLACK    		= "\u001B[30m" ;
    public static final String RED      		= "\u001B[31m" ;
    public static final String GREEN    		= "\u001B[32m" ;
    public static final String BLUE     		= "\u001B[34m" ;
    public static final String PURPLE   		= "\u001B[35m" ;
    public static final String CYAN     		= "\u001B[36m" ;
    public static final String WHITE            = "\u001B[37m" ;
    public static final String DEFAULT          = "\u001B[0m\u001B[2m"  ;
    public static final String FRAMED           = "\u001B[51m \u001B[47m";
    public static final String FRAMED_CLOCK     ="\u001B[21m\u001B[46m";
    public static final String FRAMED_CLOCK_TEXT="\u001B[21m\u001B[46m";
    public static final String DOUBLE_LINE      = "\u001B[21m";
//■■■■멤버 변수와 생성자■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
  	Map<Character,List<Person>> addressBook =new HashMap<>();
  	List<Person> person;
  	
  	public AcademyCollectionObjectInOutLogic() {
  		person = new Vector<>();
  		loadPerson();
  	}
//■■■■색상 메소드■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
	public String colorError(String colorError) {
		return RED+colorError+DEFAULT;
	}//●●에러용 글자 색깔●●
	
	public String colorInput(String colorInput) {
		return GREEN+BOLD+colorInput+DEFAULT;
	}//●●입력용 글자 색깔●●
	
	public String colorInsert(String colorInput) {
		return PURPLE+BOLD+colorInput+DEFAULT;
	}//●●수정용 글자 색깔●●
	
	public String colorExec(String colorInput) {
		return DOUBLE_LINE+PURPLE+BOLD+colorInput+DEFAULT;
	}//●●실행 결과 글자 색깔●●
	
	public String textFrame(String colorInput) {
		return FRAMED+BOLD+BLUE+colorInput+DEFAULT;
	}//●●표 제목 테두리●●
	
	public String downArrow(String title, int space) {
		return BOLD+BLUE+"▼"+BLACK+title+"ㅤ".repeat(space);
	}//●●표 속성 글자 색깔●●  (예:▼나이)
	
	public void colorCount(int searchCount) {
		System.out.print(String.format(" "+BOLD+BLACK+"["+RED+"%03d"+BOLD+BLACK+"] ",searchCount));
	}//●●표 레코드 번호 색깔●●  (예:[001])
	
	private String printClockStyle(int clock) {
		return FRAMED_CLOCK+String.format("%02d", clock)+DEFAULT;
	}//●●시계 색깔●●  (예:[2023])
	
	private String printClockStyle(String clockText) {
		return FRAMED_CLOCK_TEXT+clockText+DEFAULT;
	}//●●시계 색깔●●  (예:[년 월 일])
	
//■■■■출력용 메소드■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
	public void printMainMenu() {
		System.out.println(String.format("\n%48s %s","",textFrame("메인 메뉴")));
		System.out.println(" ".repeat(21)+CYAN+"╔════════════════════════════════════════════════════════════════╗");
		System.out.println(" ".repeat(21)+BLACK+BOLD+"  1.입력 2.출력 3.수정 4.삭제 5.검색 6.저장 7.리셋 8."+PURPLE+"기본 리스트 작성 "+BLACK+"9."+RED+"종료"+NOBOLD+CYAN+"");
		System.out.println(" ".repeat(21)+"╚════════════════════════════════════════════════════════════════╝"+DEFAULT);
		System.out.println(String.format("%46s%s", "",colorInput("메뉴번호를 입력하세요?")));
		System.out.printf(String.format("%52s▶", ""));
	}//●●메인 메뉴 출력 메소드●●

	private void printSubMenu() {
		System.out.println(String.format("\n%48s %s","",textFrame("서브 메뉴")));
		System.out.println(String.format("%39s %s","",CYAN+"╔══════════════════════════╗"));
		System.out.println(String.format("%41s %s","",BLACK+BOLD+"1.학생 2.교사 3.메인메뉴 이동"+CYAN+NOBOLD));
		System.out.println(String.format("%39s %s","","╚══════════════════════════╝"+DEFAULT));
		System.out.printf(String.format("%52s▶", ""));
	}//●●서브 메뉴 출력 메소드●●
	
	private void printOutSubMenu() {
		System.out.println(String.format("\n%48s %s","",textFrame("출력 메뉴")));
		System.out.println(" ".repeat(25)+CYAN+" ╔═══════════════════════════════════════════════════════╗");
		System.out.println(" ".repeat(25)+"    "+BLACK+BOLD+"1.초성 분류  2.분류 없이 출력  3.학생/교사 분류  4.메인메뉴 이동   "+CYAN+NOBOLD);
		System.out.println(" ".repeat(25)+" ╚═══════════════════════════════════════════════════════╝"+DEFAULT);
		System.out.printf(String.format("%52s▶", ""));
	}//●●분류 지정 출력 메소드●●
	
	private void printSortMenu() {
		System.out.println(String.format("\n%48s %s","",textFrame("정렬 메뉴")));
		System.out.println(" ".repeat(29)+CYAN+"  ╔═══════════════════════════════════════════╗");
		System.out.println(" ".repeat(30)+"      "+BLACK+BOLD+"1.이름  2.나이  3.주소  4.전화번호   5.종료"+CYAN+NOBOLD);
		System.out.println(" ".repeat(29)+"  ╚═══════════════════════════════════════════╝"+DEFAULT);
		System.out.printf(String.format("%52s▶", ""));
	}//●●정렬 지정 출력 메소드●●
	
	private void printSet(String title) {
		System.out.println();
		System.out.println(" ".repeat(29)+"    ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
		System.out.println(" ".repeat(29)+"      "+title+"을 입력하세요?  "+RED+"(종료 0번)"+DEFAULT);
		System.out.println(" ".repeat(29)+"    ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
		System.out.printf(String.format("%34s▶", ""));
	}//●●입력 메뉴 출력 메소드●●
	
	private void printYesOrNo() {
		System.out.println(String.format("%38s %s","",RED+"╔══════════════════════════╗"));
		System.out.println(String.format("%38s %s","",BLACK+BOLD+"   1.예             2.아니요"+RED+NOBOLD));
		System.out.println(String.format("%38s %s","","╚══════════════════════════╝"+DEFAULT));
		System.out.printf(String.format("%52s▶", ""));
	}//●●판단 메뉴 출력 메소드●●
	
	private void printList() {
		System.out.println(CYAN+"╔════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
		System.out.println(" ".repeat(2)+downArrow("NO",4)+downArrow("이름",6)+downArrow("나이",16)+downArrow("주소",20)+downArrow("전화번호",10)+downArrow("학번/과목",0));
		System.out.println(CYAN+NOBOLD+"╚════════════════════════════════════════════════════════════════════════════════════════════════════════════╝"+DEFAULT);
	}//●●테이블 출력 메소드●●
	
	public String printClock() {
		Calendar calendar = Calendar.getInstance();
		String terminal="폰트:Consolas  글자 크기:12 또는 16의 기준으로 줄 맞춤이 되어있습니다.\n\n";
		System.out.printf(" ".repeat(26));
		for(int i=0; i<terminal.length(); i++) {
			if(i==0 || i==13 || i==21 || i==27) {System.out.printf(BLACK+BOLD);}
			if(i==2 || i==18) {System.out.printf(RED+BOLD);}
			if(i==3 || i==19 || i==24) {System.out.printf(NOBOLD+CYAN);}
			threadSleep(30);
			String character=Character.toString(terminal.charAt(i));
			System.out.print(character);
		}
		threadSleep(1000);
		return " ".repeat(41)+
				printClockStyle(calendar.get(Calendar.YEAR))+printClockStyle("년")+" "+
				printClockStyle(calendar.get(Calendar.MONTH)+1)+printClockStyle("월")+" "+
				printClockStyle(calendar.get(Calendar.DATE))+printClockStyle("일")+" "+
				printClockStyle(calendar.get(Calendar.HOUR_OF_DAY))+printClockStyle("시")+" "+
				printClockStyle(calendar.get(Calendar.MINUTE))+printClockStyle("분");
	}//●●처음 출력 메소드●●
	
	public void threadSleep(int sleep) {
		try {
			Thread.sleep(sleep);
		} catch (InterruptedException e) {}
	}//●●스레드 시간 설정 메소드●●
	
//■■■■입력한 내용 판별■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
	private int getAges(Scanner sc, int space, int space2, int space3) {
		int years;
		while(true){
			try {
				years=Integer.parseInt(sc.nextLine().trim());
				if(years<=-1 || years>=120) {
					System.out.println(String.format("%"+(space+31)+"s%s", "",colorError("나이가 현실적이지 않아요")));
					System.out.printf(String.format("%"+(space3+29)+"s▶", ""));
					continue;
				}
				break;
			}
			catch(NumberFormatException e) {
				System.out.println(String.format("%"+(space+31)+"s%s", "",colorError("나이가 현실적이지 않아요")));
				System.out.println(String.format("%24s", ""));
				System.out.printf(String.format("%"+(space3+29)+"s▶", ""));
			}
		}
		return years;
	}//●●나이 입력값 판단 메소드●●

	private String getpNumber(Scanner sc, int space) {
		Set keys = addressBook.keySet();
		String number;
		double checked;
		while(true) {
			try {
				number=sc.nextLine();
				checked=Double.parseDouble(number);
				for(Object key:keys) {
					List<Person> value=addressBook.get(key);
					for(Person p:value) {
						if(p.pNumber.equals(number)) {
							System.out.println(RED+"\n╔════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
							System.out.printf("    ");p.print();
							System.out.println(RED+"╚════════════════════════════════════════════════════════════════════════════════════════════════════════════╝"+DEFAULT);;
							System.out.println(String.format("\n%42s%s", "",colorError("전화번호가 중복되는 사람이 존재합니다.")));
							System.out.printf(String.format("%"+space+"s▶", ""));
							throw new NullPointerException();
						}
					}
				}
				break;
			}
			catch(NumberFormatException e) {
				System.out.println(String.format("\n%42s%s", "",colorError("전화번호는 문자가 있으면 안돼요.")));
				System.out.printf(String.format("%"+space+"s▶", ""));
			}
			catch(NullPointerException e) {}
		}
		return number;
	}//●●전화번호 입력값 판단 메소드●●
	/////////////////////////////////////////////////////////////////////////////
	private int getstNumber(Scanner sc, int space) {
		Set keys = addressBook.keySet();
		String number;
		Long checked;
		while(true) {
			try {
				number=sc.nextLine();
				checked=Long.parseLong(number);
				if(checked>99999999) {
					System.out.println(String.format("%43s%s", "",colorError("학번은 최대 8자리 입니다.")));
					System.out.printf(" ".repeat(space)+BLACK+"▶");
					continue;
				}
				break;
			}
			catch(NumberFormatException e) {
				System.out.println(String.format("%44s%s", "",colorError("숫자를 입력해야 합니다.")));
				System.out.printf(String.format("%"+space+"s▶", ""));
			}
		}
		return Integer.parseInt(number);
	}//●●학번 입력값 판단 메소드●●
	
	private String getAddress(Scanner sc,int space) {
		String address;
		while(true) {
			try {
				address=sc.nextLine();
				if(address.length()>=24) {
					System.out.println(String.format("%42s%s","",colorError("주소는 최대23글자만 가능합니다.")));
					System.out.printf(String.format("%"+space+"s▶", ""));
					continue;
				}
				break;
			}
			catch(NumberFormatException e) {
				System.out.println(String.format("%44s%s", "",colorError("문자를 입력해야 합니다.")));
				System.out.printf(String.format("%"+space+"s▶", ""));
			}
		}
		return address;
	}//●●주소 입력값 판단 메소드●●
	
	private String getSubject(Scanner sc, int space) {
		String subject;
		while(true) {
			subject=sc.nextLine();
			if(subject.length()>=7) {
				System.out.println(String.format("%42s%s","",colorError("과목은 최대 6글자만 가능합니다.")));
				System.out.printf(String.format("%"+space+"s▶", ""));
				continue;
			}
			break;
		}
		return subject;
	}//●●과목 입력값 판단 메소드●●
			
	public int getMenuNumber() {
		Scanner sc = new Scanner(System.in);
		String menuStr;
		while(true) {
			menuStr= sc.nextLine().trim();
			if(!CommonUtils.isNumber(menuStr)) {
				System.out.println();
				System.out.println(String.format("%47s%s","",colorError("메뉴 번호는 숫자만")));
				System.out.printf(" ".repeat(52)+BLACK+"▶");
				continue;
			}
			break;
		}
		return Integer.parseInt(menuStr);
	}//●●메뉴번호 입력값 판단 메소드●●
	
//■■■■메인 메뉴 출력 메소드■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
	public void seperateMainMenu(int mainMenu) {
		switch(mainMenu) {
			case 0:
				System.out.println("음악용임");
				break;
			case 1://입력
				while(true) {
					try {
						printSubMenu();
						int subMenu=getMenuNumber();
						if(subMenu==3) break;
						switch(subMenu) {
							case 1:
							case 2:setPerson(subMenu);break;
							default:System.out.println(String.format("%41s%s", "",colorError("서브 메뉴에 없는 번호 입니다."))); continue;
						}//switch
					}catch(NumberFormatException e) {
						System.out.println(String.format("%44s%s", "",colorError("아무것도 입력하지 않았아요.")));
					}

				}///while
				break;
				
			case 2://출력
				if(addressBook.size()==0) {
					System.out.println(String.format("%46s%s", "",colorExec("저장된 내용이 없습니다\n\n")));
				}
				else {
					while(true) {
						printOutSubMenu();
						int subMenu=getMenuNumber();
						if(subMenu==4) break;
						switch(subMenu) {
							case 1:
							case 2:
							case 3:printSortMenu(subMenu);break;
							default:System.out.println(String.format("%44s%s", "",colorError("서브메뉴에 없는 번호입니다."))); continue;
						}//switch
						break;
					}///while
				}
				break;
			case 3://수정
				if(addressBook.size()==0) {
					System.out.println(String.format("%46s%s", "",colorExec("저장된 내용이 없습니다\n\n")));
				}
				else {
					updatePerson();
				}
				break;
			case 4://삭제
				if(addressBook.size()==0) {
					System.out.println(String.format("%46s%s", "",colorExec("저장된 내용이 없습니다\n\n")));
				}
				else {
					deletePerson();
				}
				break;
			case 5://검색
				if(addressBook.size()==0) {
					System.out.println(String.format("%46s%s", "",colorExec("저장된 내용이 없습니다\n\n")));
				}
				else {
					findPersonByName();
				}
				break;
			case 6://파일저장
				savePerson();
				break;
			case 7://파일리셋
				resetPerson();
				break;
			case 8://기본 리스트 작성
				defaultList();
				break;
			case 9://종료
				System.out.println(String.format("\n\n%46s%s","",colorExec("프로그램을 종료합니다")));
				System.exit(0);
				break;
			default:
				System.out.println(String.format("%48s%s", "",colorError("없는 번호입니다.")));
		}
	}
//■■■■사람 입력 메소드■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
	private void setPerson(int subMenu) {
		Scanner sc = new Scanner(System.in);
		List<Person> valueList=null;
		String name;
		
		while(true) {
			printSet("이름");
			name= sc.nextLine().trim();
			if("0".equalsIgnoreCase(name)) break;
			//2]입력한 이름에서 자음 (ㄱ,ㄴ,ㄷ,.....ㅎ)얻기
			char consonant = CommonUtils.getInitialConsonant(name);
			//System.out.println("consonant:"+consonant);
			if(consonant=='0') {
				System.out.println(String.format("%42s%s", "",colorError("한글 이름 1~4글자만 됩니다.")));
				continue;
			}
		
			printSet("나이");
			int age = getAges(sc,13,15,5);
			if(age==0) break;
			
			printSet("주소");
			String addr=getAddress(sc,34);
			if("0".equalsIgnoreCase(addr)) break;
		
			while(true) {
				System.out.println("\n"+" ".repeat(29)+"    ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
				System.out.println(" ".repeat(29)+"      전화번호 입력하세요   "+GREEN+"[예:00099990000]"+DEFAULT);
				System.out.println(" ".repeat(29)+"    ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
				System.out.printf(String.format("%34s▶", ""));
					String pNumber=getpNumber(sc,34);
					if(pNumber.length()!=11) {
						System.out.println(String.format("%41s%s\n", "",colorError("전화번호는 한국숫자만 가능합니다.")));
						continue;
					}
					
			if(!addressBook.containsKey(consonant)) {
				valueList = new Vector<>();
			}
			else {
				valueList=addressBook.get(consonant);
			}
			
				switch(subMenu) {
					case 1:
						printSet("학번");
						int stNumber= getstNumber(sc,34);
						if(stNumber==0)break;
						valueList.add(new Student(name, age, addr, pNumber, stNumber));
						addressBook.put(consonant, valueList);
						break;
					default:
						printSet("과목");
						String subject= getSubject(sc,34);
						if("0".equalsIgnoreCase(subject)) break;
						valueList.add(new Teacher(name, age, addr, pNumber, subject));
						addressBook.put(consonant, valueList);
					break;
					}///switch
				break;
			}
			break;
			}
		}
//■■■■정렬 메소드■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
	private void sortMethod(int sortList, List<Person> sortPerson) {
		switch(sortList) {
		case 1:sort(Person.SORT_BY_NAME,sortPerson);break;
		case 2:sort(Person.SORT_BY_AGE,sortPerson);break;
		case 3:sort(Person.SORT_BY_ADDR,sortPerson);break;
		default:sort(Person.SORT_BY_PHONENUMBER,sortPerson);break;
		}
	}//●●정렬 번호 선택 메소드●●
	
	public static void sort(int sortField,List<Person> listPrint) {
		Collections.sort(listPrint,new Comparator<Person>() {
			@Override
			public int compare(Person src, Person target) {
				switch(sortField) {
				case Person.SORT_BY_NAME:
					return src.getName().compareTo(target.getName());
				case Person.SORT_BY_AGE:
					return src.getAge()-target.getAge();
				case Person.SORT_BY_ADDR:
					return src.getAddr().compareTo(target.getAddr());
				default:return src.getpNumber().compareTo(target.getpNumber());
				}
			}
		});
	}//●●정렬 실행 메소드●●
//■■■■정렬 입력 메소드■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
	private void printSortMenu(int subMenu) {
		while(true) {
			printSortMenu();
			int sortList=getMenuNumber();
			if(sortList==5) break;
			switch(sortList) {
				case 1:
				case 2:
				case 3:
				case 4:printSortPerson(subMenu, sortList); break;
				default:System.out.println(String.format("%48s%s", "",colorError("없는 번호입니다."))); continue;
			}break;
		}
	}//●●정렬 속성 선택 메소드●●
	
	private void printSortPerson(int subMenu, int sortList) {
		Set keys = addressBook.keySet();
		int searchCount;
		switch(subMenu) {
			case 1:
				System.out.println("\n\n▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄");
				for(Object key:keys) {
					List<Person> value=addressBook.get(key);
					sortMethod(sortList, value);
					System.out.println("\n"+" ".repeat(50)+FRAMED+"\u001B[46m"+BLACK+BOLD+"  "+key+"  "+DEFAULT);
					printList();
					searchCount=0;
					for(Person p:value) {
						searchCount++;
						if(p instanceof Student) {
							colorCount(searchCount);
							((Student)p).print();
							}

						if(p instanceof Teacher) {
							colorCount(searchCount);
							((Teacher)p).print();
							}
						}
					}
				System.out.println(DEFAULT+"\n▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄\n\n\n");
				break;
			case 2:
				List<Person> listPrint= new ArrayList<Person>();
				for(Object key:keys) {
					List<Person> value=addressBook.get(key);
					for(Person p:value) {
						listPrint.add(p);
					}
				}
			sortMethod(sortList, listPrint);
			
			System.out.println(DEFAULT+"\n▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄\n");
			printList();
			searchCount=0;
			for(Person p:listPrint){
				searchCount++;
					if(p instanceof Student) {
						colorCount(searchCount);
						((Student)p).print();
					}
					if(p instanceof Teacher) {
						colorCount(searchCount);
						((Teacher)p).print();
					}
				}
			System.out.println(DEFAULT+"\n▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄\n\n\n");
			break;
			
			case 3:
				List<Person> studentList = new Vector<Person>();
				List<Person> teacherList = new Vector<Person>();
				
				
				for(Object key:keys) {
					List<Person> value=addressBook.get(key);
					for(Person p:value) {
						if(p instanceof Student) {
							studentList.add(p);
							}
						if(p instanceof Teacher) {
							teacherList.add(p);
							}
						}
					}
				System.out.println("\n\n▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄");
				if(studentList.size()!=0) {
					System.out.println(String.format("\n%48s %s","",textFrame("학생 분류")));
					printList();
					sortMethod(sortList, studentList);
					searchCount=0;
					
					for(Person p:studentList){
						searchCount++;
						colorCount(searchCount);
						((Student)p).print();
						}
				}

				if(teacherList.size()!=0) {
					System.out.println(String.format("\n%48s %s","",textFrame("교사 분류")));
					printList();
					sortMethod(sortList, teacherList);
					searchCount=0;
					for(Person p:teacherList){
						searchCount++;
						colorCount(searchCount);
						((Teacher)p).print();
						}
				}
				System.out.println(DEFAULT+"▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄\n\n\n");
				break;
			}
		}//●●정렬 방식에 따른 출력 메소드●●
//■■■■검색 입력■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
	private List<Person> searchPerson(String title) {
			List<Person> searching = new Vector<Person>();
			System.out.println(String.format("%41s%s", "",colorInput(title+"할 사람의 이름을 입력하세요?")));
			System.out.printf(String.format("%52s▶", ""));
			Scanner sc = new Scanner(System.in);
			String name = sc.nextLine().trim();
			char consonant = CommonUtils.getInitialConsonant(name);
			List<Person> value=addressBook.get(consonant);
			
			for(Person p:value) {
				//if(Pattern.matches("^가{n}.*", p.name))
				if(Pattern.matches("^"+name+".*", p.name))
					searching.add(p);
			}
			
			if(searching==null || searching.size()==0) {
				return null;
			}
			else return searching;
	}//●●사람 검색 메소드●●
	
	private void findPersonByName() {
		while(true) {
			try {
				List<Person> findListPerson=searchPerson("검색");
				if(findListPerson==null || findListPerson.size()==0) {
					System.out.println(String.format("%46s%s", "",colorError("검색된 사람이 없어요\n")));
					break;
				}
				else {
					int searchCount=1;
					System.out.println(DEFAULT+"\n\n▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄");
					for(Person findPerson : findListPerson) {
						colorCount(searchCount);
						findPerson.print();
						searchCount++;
					}
					System.out.println(DEFAULT+"▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀\n");
					System.out.println("\n");
					break;
				}
			}catch(NullPointerException e) {
				System.out.println(String.format("%46s%s", "",colorError("검색된 사람이 없어요\n")));
				break;
			}
		}
	}//●●사람 검색 출력 메소드●●
//■■■■수정 입력■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
	private void listPerson(List<Person> listPerson,String title) {
		int searchCount=1;
		System.out.println(DEFAULT+"\n\n▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄");
		System.out.println(CYAN+"╔════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
		System.out.println(String.format("%38s%s","ㅤ",textFrame("현재 "+title+" 가능한 사람 목록 (종료 0번)")));
		System.out.println(CYAN+"╚════════════════════════════════════════════════════════════════════════════════════════════════════════════╝"+DEFAULT);
		for(Person findListPerson :listPerson) {
			colorCount(searchCount);
			findListPerson.print();
			searchCount++;
		}
		System.out.println(DEFAULT+"▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄\n");
	}//●●사람 검색 리스트 출력 메소드●●
	
	private void updatePerson() {
		try {
			List<Person> updateListPerson =searchPerson("수정");
			Scanner sc = new Scanner(System.in);
			int listNumber;
			
			if(updateListPerson== null || updateListPerson.size()==0) {
				System.out.println(String.format("%45s%s", "",colorInsert("수정할 사람이 없습니다.\n\n")));
			}
			else {
				while(true) {
					if(updateListPerson != null) {
						listPerson(updateListPerson,"수정");
						System.out.println(" ".repeat(35)+colorInsert("수정을 하고 싶은 사람의 번호를 입력하세요? (종료 0번)"));
						System.out.printf(String.format("%52s▶", ""));
						listNumber= getMenuNumber();
						if(listNumber==0)break;
						if(!(1<= listNumber && listNumber<=updateListPerson.size())) {
							System.out.println(" ".repeat(36)+colorInsert("수정이 가능한 사람의 목록 번호를 입력해야 합니다."));
							continue;
						}
						
						Person findPerson = updateListPerson.get(listNumber-1);

						System.out.printf("\n"+" ".repeat(25)+"    ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
						System.out.println(String.format("\n"+" ".repeat(38)+BLACK+BOLD+"[현재 %s살]"+PURPLE+" 몇 살로 수정할래? (종료 0번)"+DEFAULT,findPerson.age));
						System.out.println(" ".repeat(25)+"    ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
						System.out.printf(String.format("%52s▶", ""));
						int updateAge=getAges(sc,1,1,1);
						if(updateAge==0)break;
						
						
						
						System.out.printf("\n"+" ".repeat(25)+"    ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
						System.out.printf("\n"+" ".repeat(31)+BLACK+BOLD+"[현재 주소"+RED+":"+BLACK+"%s]\n"+DEFAULT,CommonUtils.stringPaddingWord(findPerson.addr, 25));
						System.out.println(" ".repeat(41)+colorInsert("주소를 어떻게 수정할래? (종료 0번)"));
						System.out.println(" ".repeat(25)+"    ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
						System.out.printf(String.format("%52s▶", ""));
						String updateAddr= getAddress(sc,52);
						if("0".equalsIgnoreCase(updateAddr))break;

						
						while(true) {
							sc = new Scanner(System.in);
							System.out.printf("\n"+" ".repeat(25)+"    ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
							System.out.printf("\n"+" ".repeat(40)+BLACK+BOLD+" [현재 전화번호"+RED+":"+BLACK+"%s]\n"+" ".repeat(43)+colorInsert("전화번호를 어떻게 수정할래?%n"),findPerson.pNumber);
							System.out.println(" ".repeat(25)+"    ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
							System.out.printf(String.format("%52s▶", ""));
							String pNumber= getpNumber(sc,52);
							if(pNumber.length()!=11) {
								System.out.println(String.format("%42s%s","",colorError("전화번호는 한국숫자만 가능합니다.")));
								continue;
							}
							findPerson.pNumber=pNumber;
							break;
						}
						
						if(findPerson instanceof Student) {//학생
							System.out.printf("\n"+" ".repeat(25)+"    ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
							System.out.printf("\n"+" ".repeat(43)+BLACK+BOLD+"[현재 학번"+RED+":"+BLACK+"%-10s]\n"+DEFAULT,((Student)findPerson).stNumber);
							System.out.println(" ".repeat(40)+colorInsert("몇 학번으로 수정할래? (종료 0번)"));
							System.out.println(" ".repeat(25)+"    ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
							System.out.printf(String.format("%52s▶", ""));
							int updatestNumber=getstNumber(sc,52);
							if(updatestNumber==0)break;

							((Student)findPerson).stNumber=updatestNumber;
						}
						else {
							System.out.printf("\n"+" ".repeat(25)+"    ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
							System.out.printf("\n"+" ".repeat(43)+BLACK+BOLD+"[현재 과목"+RED+":"+BLACK+"%-10s]\n"+DEFAULT,((Teacher)findPerson).subject);
							System.out.println(" ".repeat(39)+colorInsert("무슨 과목으로 수정할래? (종료 0번)"));
							System.out.println(" ".repeat(25)+"    ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
							System.out.printf(String.format("%52s▶", ""));
							String updatesubject= getSubject(sc,34);
							if("0".equalsIgnoreCase(updatesubject))break;
							((Teacher)findPerson).subject=updatesubject;
						}
						
						findPerson.age = updateAge;
						findPerson.addr=updateAddr;
						System.out.printf("\n\n"+" ".repeat(36)+BLACK+BOLD+"["+CYAN+"%s"+BLACK+"가(이) 아래와 같이 수정되었습니다.  ]"+DEFAULT+"%n",CommonUtils.stringKoreaWord(findPerson.name,4,true));
						System.out.println(CYAN+"╔════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
						System.out.printf("    ");
						findPerson.print();//수정 내용을 확인하기 위한 출력
						System.out.println(CYAN+"╚════════════════════════════════════════════════════════════════════════════════════════════════════════════╝\n\n"+DEFAULT);;
						break;
						}
					}
				}
			}catch(NullPointerException e) {System.out.println(String.format("%45s%s", "",colorInsert("수정할 사람이 없습니다.\n\n")));
		}
	}//●●사람 수정 입력 메소드●●
//■■■■삭제 입력■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
	private void deletePerson() {
		try {
			List<Person> deleteListPerson =searchPerson("삭제");
			Scanner sc = new Scanner(System.in);
			int listNumber;
			while(true) {
				if(deleteListPerson== null || deleteListPerson.size()==0) {
					System.out.println(String.format("%45s%s", "",colorInsert("삭제할 사람이 없습니다.\n\n")));
					break;
				}
				else {
					listPerson(deleteListPerson,"삭제");
					System.out.printf(" ".repeat(39)+colorInsert("삭제를 하고 싶은 사람의 번호를 입력하세요\n"+" ".repeat(52)+BLACK+"▶"));
					listNumber= getMenuNumber();
					
					if(listNumber==0)break;
					if(!(1<= listNumber && listNumber<=deleteListPerson.size())) {
						System.out.println(String.format("%37s%s","",colorError("삭제가 가능한 사람의 목록 번호를 입력해야 합니다.")));
						continue;
					}
		
					Person findPerson = deleteListPerson.get(listNumber-1);
					
			        char consonant = CommonUtils.getInitialConsonant(findPerson.name);
		            List<Person> values= addressBook.get(consonant);
		            for(Person p:values)
		                if(findPerson.equals(p)) {
		                    values.remove(findPerson);
		                    System.out.println("\n\n"+" ".repeat(43)+colorInsert("["+findPerson.name+"(이)가 삭제되었습니다]\n"));
		                    break;
		                }
		            if(addressBook.get(consonant).size()==0) {
		                addressBook.remove(consonant);
		            }
		            break;
				}
			}
		}catch(NullPointerException e) {System.out.println(String.format("%45s%s", "",colorInsert("삭제할 사람이 없습니다.\n\n")));}
    }//●●사람 삭제 입력 메소드●●
//■■■■저장/불러오기/삭제 판별 메소드■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
	private void resetPerson() {
		if(addressBook.size()==0 || addressBook==null)System.out.println(String.format("%42s%s\n\n", "",colorExec("저장된 내용이 존재하지 않습니다.")));
		else{
			System.out.println(String.format("\n\n%43s%s","",colorExec("현재의 내용을 지우시겠습니까?")));
			printYesOrNo();
			int subMenu=getMenuNumber();
			switch(subMenu) {
				case 1:
					System.out.println(String.format("%46s%s", "",colorExec("내용이 리셋되었습니다\n\n")));
					addressBook.clear(); break;
				default:break;
			}
		}
	}//●●Map(AddressBook) 리스트 리셋 메소드●●
	
	private void savePerson() {
		if(addressBook.isEmpty()) {
			System.out.println(String.format("%46s%s", "",colorExec("저장할 내용이 없습니다")));
			return;
		}
		ObjectOutputStream out=null;
		
		try {
			System.out.println(String.format("\n\n%44s%s", "",colorExec("내용을 저장 하시겠습니까?")));
			printYesOrNo();
			int subMenu=getMenuNumber();
			if(subMenu!=1)return;
	
			out= new ObjectOutputStream(new FileOutputStream("Persons.dat"));
			out.writeObject(addressBook);
			System.out.println(String.format("%46s%s", "",colorExec("파일 저장되었습니다\n\n")));
		}
		catch(IOException e) {
			System.out.println(String.format("%44s%s", "",colorError("파일 저장시 오류 입니다")));
			System.out.println(e.getMessage());
		}
		finally {
			try {
				if(out !=null) out.close();
			}
			catch(Exception e) {}
		}
	}//●●Map(AddressBook) 리스트 저장 메소드●●
	
	private void loadPerson() {
		ObjectInputStream ois=null;
		try {
			ois = 
					new ObjectInputStream(
								new FileInputStream("Persons.dat"));
			addressBook=(Map<Character,List<Person>>)ois.readObject();
		}
		catch(Exception e) {}
		finally {
			try {
				if(ois !=null) ois.close();
			}
			catch(Exception e) {}
		}
	}//●●Map(AddressBook) 리스트 로딩 메소드●●
//■■■기본 리스트 작성 메소드■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
	private void defaultList() {
		System.out.println(String.format("\n\n%42s%s", "",colorExec("기본 테이블을 만드시겠습니까?")));
		printYesOrNo();
		int subMenu=getMenuNumber();
		switch(subMenu) {
			case 1:
				addressBook.clear();
				List<Person> valueList=null;
				valueList = new Vector<>();
				valueList.add(new Student("가희", 25, "부산광역시 동래구 명륜로139번길 43", "01053230123", 17038021));
				valueList.add(new Student("가은서", 25, "부산광역시 동래구 명륜로139번길 43", "01053230123", 17038021));
				valueList.add(new Student("가은주", 24, "부산광역시 수영구 연수로342번길", "01044884123", 16032023));
				valueList.add(new Teacher("감수희", 47, "Jung-gu, Ulsan Korea", "01073531368", "프로그래밍"));
				valueList.add(new Teacher("강서준", 43, "서울특별시 강북구 삼양로20길", "01058830113", "English"));
				addressBook.put('ㄱ', valueList);
				
				valueList = new Vector<>();
				valueList.add(new Student("남예준", 25, "경상남도 거제시 남부면 저구2길", "01054739123", 17034028));
				valueList.add(new Student("나채원", 23, "Gyeongsangbuk-do", "01093242353", 20084081));
				valueList.add(new Student("나길원", 23, "인천광역시 미추홀구 매소홀로", "01013272853", 20054021));
				valueList.add(new Teacher("낙채환", 53, "서울특별시 강북구 삼양로21길", "01073844183", "일본어-2"));
				addressBook.put('ㄴ', valueList);
				
				valueList = new Vector<>();
				valueList.add(new Student("태환", 27, "부산광역시 남구 남동천로 26-12", "01033731427", 15074013));
				valueList.add(new Student("태호영", 28, "충청남도 공주시 제민천1길 13-1", "01093113417", 14041086));
				addressBook.put('ㅌ', valueList);
				
				valueList = new Vector<>();
				valueList.add(new Student("하예준", 25, "Dongsan-gil, Miro-myeon", "01036738521", 18024071));
				valueList.add(new Student("훈채원", 20, "Waryong-ro 28beon-gil", "01093242353", 20084081));
				valueList.add(new Student("홍의연", 19, "제주특별자치도 서귀포시 남원읍", "01018972143", 21013051));
				valueList.add(new Teacher("하구준", 46, "53beonan-gil, Gwangju", "01038338491", "German"));
				valueList.add(new Teacher("하구원", 39, "경기도 성남시 중원구 은행로", "01081338413", "대수학"));
				System.out.println(String.format("%43s%s\n", "",colorExec("기본 리스트를 만들었습니다.")));
			default:break;
		}
	}//●●Map(AddressBook) 기본 리스트 입력 메소드●●
//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
}


