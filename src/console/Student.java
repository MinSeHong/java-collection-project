package console;

public class Student extends Person {
//■■■■색상용 코드■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
	public static final String BOLD     = "\u001B[30m\u001B[1m";
    public static final String DEFAULT  = "\u001B[0m"  ;
    public static final String GREENBOLD = "\u001B[35m\u001B[1m";
    public static final String RED      = "\u001B[31m" ;
//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
	//[멤버변수(필드)]
	public int stNumber;//새롭게 확장한 멤버
	//[인자 생성자]
	public Student(String name, int age, String addr, String pNumber, int stNumber) {
		super(name, age, addr, pNumber);
		this.stNumber=stNumber;
	}
	//[멤버 메소드]
	@Override
	public String get() {		
		return String.format("%s "+
				GREENBOLD+"["+BOLD+"학번"+RED+":"+DEFAULT+"%-8s"+GREENBOLD+"ㅤ  ]",super.get(),stNumber);
	}
	@Override
	public void print() {		
		System.out.println(get());
	}
	
}
