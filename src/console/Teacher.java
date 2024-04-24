package console;

public class Teacher extends Person {
//■■■■색상용 코드■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
	public static final String BOLD     = "\u001B[30m\u001B[1m";
    public static final String DEFAULT  = "\u001B[0m"  ;
    public static final String GREENBOLD = "\u001B[35m\u001B[1m";
    public static final String RED      = "\u001B[31m" ;
//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
	//[멤버변수(필드)]
	String subject;//새롭게 확장한 멤버
	//[인자 생성자]
	public Teacher(String name, int age, String addr, String pNumber, String subject) {
		super(name, age, addr, pNumber);
		this.subject=subject;
	}
	//[멤버 메소드]
	@Override
	String get() {		
		return String.format("%s "+
				GREENBOLD+"["+BOLD+"과목"+RED+":"+DEFAULT+"%s"+GREENBOLD+"]",super.get(),CommonUtils.stringPaddingWord(subject,6));
	}
	@Override
	void print() {		
		System.out.println(get());
	}
	
}
