package console;

import java.io.Serializable;
import java.text.DecimalFormat;

public class Person implements Serializable {
//■■■■색상용 코드■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
	public static final String BOLD     = "\u001B[30m\u001B[1m";
    public static final String DEFAULT  = "\u001B[0m"  ;
    public static final String GREENBOLD = "\u001B[35m\u001B[1m";
    public static final String RED      = "\u001B[31m" ;
//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
	
	//필드
	public String name;
	public int age;
	/*■■■■■새로 추가한 항목■■■■■*/
	public String addr;
	public String pNumber;
	
	
	//[기본 생성자]
	public Person() {}
	//[인자 생성자]
	public Person(String name, int age, String addr, String pNumber) {		
		this.name = name;
		this.age = age;
		this.addr = addr;
		this.pNumber = pNumber;
	}
	//[멤버 메소드]
	String get() {
		return String.format(
				GREENBOLD+"["+BOLD+"이름"+RED+":"+DEFAULT+"%s"+GREENBOLD+"]"
				+GREENBOLD+"ㅤ["+BOLD+"나이"+RED+":"+DEFAULT+"%03d"+GREENBOLD+"]"
				+GREENBOLD+"ㅤ["+BOLD+"주소"+RED+":"+DEFAULT+"%s"+GREENBOLD+"]"
				+GREENBOLD+"ㅤ["+BOLD+"전화번호"+RED+":"+DEFAULT+"%11s"+GREENBOLD+"]"+DEFAULT
				,CommonUtils.stringKoreaWord(name,4,false),age,CommonUtils.stringPaddingWord(addr,23), pNumberHypen(pNumber));
	}
	
	void print() {
		System.out.println(get());
	}
	
	public String pNumberHypen(String pNumber){
		return pNumber.substring(0,3)+"-"+pNumber.substring(3,7)+"-"+pNumber.substring(7);
	}
	
	//정렬용 정적 변수.
	public static final int SORT_BY_NAME=1;
	public static final int SORT_BY_AGE=2;
	public static final int SORT_BY_ADDR=3;
	public static final int SORT_BY_PHONENUMBER=4;
	public static final int SORT_BY_SUBJECT=5;


	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getpNumber() {
		return pNumber;
	}
	public void setpNumber(String pNumber) {
		this.pNumber = pNumber;
	}
	
	
}
