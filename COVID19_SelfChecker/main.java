/* 
 * 프로젝트명
 * > 광운대 COVID-19 자가진단 프로그램
 * ----------------
 * 인터페이스, 제네릭, 컬렉션 프레임워크, 람다 사용
 */ 

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

// 입력받은 학생 출력 인터페이스[추후에 람다식을 통해 정의]
interface PrintInfo<ID>{
	void printInfo(int age_,ID studentID_);
}

// 자가진단 인터페이스 [내국인 학생, 외국인 학생의 셀프체크 및 결과 출력 기능]
interface SelfChecker{	
	public void check();
	public int analysis();
}

// 내국인 학생, 외국인 학생의 부모 클래스
class Student<ID> {	// 제네릭 : 외국인 학생의 경우 student ID가 학번(int)이 아닐 시 String 으로 사용할 수 있도록 처리
	private int age;
	private ID studentID;
	
	public Student(int age, ID studentID) {
		this.age = age;
		this.studentID = studentID;
	}
	
	public int getAge() {return age;}
	public ID getStudentID() {return studentID;}
}

// 내국인 학생 클래스
class Korean<ID> extends Student implements SelfChecker{
	List<Integer> list = new ArrayList<>();	// 컬렉션 프레임워크 : List 이용 
											// [입력한 문항들을 ArrayList에 저장 후 결과출력 연산에 사용]
	
	public Korean(int age, ID studentID) {
		super(age, studentID);
	}
	// 자가진단 클래스 SelfChecker 인터페이스의 메소드 정의
	public void check() {
		int cntCheck= 0;
		Scanner sc = new Scanner(System.in);
		
		PrintInfo lamda = (age_, studentID_) -> { // 람다식을 이용해서 입력받은 학생 클래스 출력
			System.out.println("\n나이: " + age_ +"살 \n학번: "+studentID_);
		};
		lamda.printInfo(getAge(), getStudentID());	
		
		System.out.println("\n*** 해당하는 번호의 숫자를 입력해주세요. *** [ex) 1]");
		System.out.println("1. 코로나19 확진자의 접촉력 및 역한적 연관성");
		System.out.println("	1-1. 최근 14일 이내에 본인 또는 동거가족이 코로나19 확진환자와 접촉한 적이 있습니까?");
		System.out.println("		1. 접촉한 적 있음	2.접촉한 적 없음");
		System.out.print("		>> ");
		list.add(sc.nextInt());
		System.out.println("	1-2. 최근 14일 이내에 본인 또는 동거가족이 해외에 방문한 적이 있습니까?");
		System.out.println("		1. 방문한 적 있음	2.해당없음");
		System.out.print("		>> ");
		list.add(sc.nextInt());
		System.out.println("	1-3. 최근 14일 이내에 코로나19 확진자가 발생한 집단시설 및 밀폐공간을 이용한 적이 있습니까?");
		System.out.println("		1. 빙문한 적 있음	2.해당없음");
		System.out.print("		>> ");
		list.add(sc.nextInt());
		System.out.println("2. 발열여부확인 (*37.5도 이상시 2회 체크하시기 바랍니다.)");
		System.out.println("	1. 37.5도 이상	2.37.5도 미만");
		System.out.print("		>> ");
		list.add(sc.nextInt());
		System.out.println("3. 해열제 및 진통제 복용 여부");
		System.out.println("	1. 해열제	2.진통제 3.복용하지 않음");
		System.out.print("		>> ");
		list.add(sc.nextInt());
		System.out.println("4. 증상확인");
		System.out.println("	| 발열(주관적 호소 포함) | 호흡기증성(기침,가래,인후통,호흡곤란) | 호흡기증상 외(근육통,오한,권태감) | 폐렴");
		System.out.println("	위의 증상 중 하나라도 발생 시 1번 입력, 해당없는 경우 2번 입력");
		System.out.print("		>> ");
		list.add(sc.nextInt());
	}
	
	// 결과값 분석 후 출력
	// 반환 값은 검사 후 포함되는 그룹에 따라 다름 [비위험군:1, 위험군:2, 고위험군:3]
	public int analysis() {
		int cnt = 0;
		Iterator iter = list.iterator();
		while(iter.hasNext()){//다음값이 있는지 체크
		    if((int)iter.next() == 1) cnt++;
		}
		if(cnt == 0) {
			System.out.println("\n************************************************************************");
			System.out.println("*! 위험군에 속하진 않습니다. 마스크 착용 및 손씻기를 생활화해주시고 다중이용시설을 피해주세요.*");
			System.out.println("************************************************************************\n");
			System.out.println("\n------------------------------------------------------------------------------\n");
			return 1;
		}
		else if(cnt > 0 && cnt <4) {
			System.out.println("\n*******************************************************************************");
			System.out.println("*!! 위험군에 속합니다. 다중이용시설 이용을 삼가해주시고 코로나 검사 후 자가격리 여부 판단 부탁드립니다.*");
			System.out.println("*******************************************************************************\n");
			System.out.println("\n------------------------------------------------------------------------------\n");
			return 2;
		}
		else if(cnt > 3) {
			System.out.println("\n**********************************************************");
			System.out.println("*!!! 고위험군에 속합니다. 가까운 선별진료소에서 코로나 검사를 받기바랍니다.*");
			System.out.println("**********************************************************\n");
			System.out.println("\n------------------------------------------------------------------------------\n");
			return 3;
		}
		return 0;

	
	}
}

// 외국인 학생 클래스 [내국인 학생과 다르게 질문지 문항을 영어로 출력하며 studentID가 학번(int)이 아님]
class Foreigner<ID> extends Student implements SelfChecker{
	List<Integer> list = new ArrayList<>();
	
	public Foreigner(int age, ID studentID) {
		super(age, studentID);
	}
	
	public void check() {
		int cntCheck= 0;
		Scanner sc = new Scanner(System.in);
		
		PrintInfo lamda = (age_, studentID_) -> { // 람다식을 이용해서 입력받은 학생 클래스 출력
			System.out.println("\nAge: " + age_+" \nStudentID: "+ studentID_);
		};
		lamda.printInfo(getAge(), getStudentID());	
		
		System.out.println("\n*** Please enter the number that you belong to [ex) 1]***");
		System.out.println("1. Check the connection with COVID-19 patients");
		System.out.println("	1-1. Have you or your family member ever been in contact with COVID-19 patients within the last 14 days?");
		System.out.println("		1. yes	2.no");
		System.out.print("		>> ");
		list.add(sc.nextInt());
		System.out.println("	1-2. Have you or your family member ever visited any foreign country within the last 14 days?");
		System.out.println("		1. yes	2.no");
		System.out.print("		>> ");
		list.add(sc.nextInt());
		System.out.println("	1-3. Have you or your family member ever visited any place that COVID-19 patients already visited within the last 14 days?");
		System.out.println("		1. yes	2.no");
		System.out.print("		>> ");
		list.add(sc.nextInt());
		System.out.println("2. Check body temperature (*please double check if your temperature is over 37 Celsius)");
		System.out.println("	1. Over 37.5 Celsius	2.Under 37.5 Celsius");
		System.out.print("		>> ");
		list.add(sc.nextInt());
		System.out.println("3. Check taking fever reducer or analgesic");
		System.out.println("	1. fever reducer 2.analgesic 3.not take");
		System.out.print("		>> ");
		list.add(sc.nextInt());
		System.out.println("4. Check Symptom");
		System.out.println("	| Fever | Respiratory symptoms(Cough,Sputum,Headache,Dyspnea) | Other case(Muscle pain, feeling a cold fit) | Pneumonia");
		System.out.println("	Enter 1 if any of the above symptoms occur. if not, enter 2");
		System.out.print("		>> ");
		list.add(sc.nextInt());
	}
	public int analysis() {	
		int cnt = 0;
		Iterator iter = list.iterator();
		while(iter.hasNext()){//다음값이 있는지 체크
		    if((int)iter.next() == 1) cnt++;
		}
		if(cnt == 0) {
			System.out.println("\n***************************************************************");
			System.out.println("*! You are not in Danger Group. Please wear the mask whenever.*");
			System.out.println("***************************************************************\n");
			System.out.println("\n------------------------------------------------------------------------------\n");
			return 1;
		}
		else if(cnt > 0 && cnt <4) {
			System.out.println("\n******************************************************************");
			System.out.println("*!! You are in Danger Group. Please don't go to public facility. *");
			System.out.println("******************************************************************\n");
			System.out.println("\n------------------------------------------------------------------------------\n");
			return 2;
		}
		else if(cnt > 3) {
			System.out.println("\n*******************************************************************");
			System.out.println("*!!! You are in High-Risk Group. You need to have a COVID-19 TEST.*");
			System.out.println("*******************************************************************\n");
			System.out.println("\n------------------------------------------------------------------------------\n");
			return 3;
		}
		return 0;
	
	}
}

public class main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("광운대 COVID-19 자가진단 프로그램");
		System.out.println();
		
		// 내국인, 외국인용 클래스 배열 따로 생성
		Korean[] kor = new Korean[100];
		Foreigner[] frg = new Foreigner[100];
		
		int check,age = 0, cntKor = 0, cntFrg = 0,intStudentId = 0;
		int nomal = 0, danger = 0, highRisk = 0;		// 자가진단 실시 학생을 비위험군, 위험군, 고위험군으로 구분
		String strStudentId = null;
		while(true) {
			do {
				check = 0;
				System.out.println("지금까지 자가진단을 실시한 학생 수 " + (cntKor + cntFrg) + "명 중");
				System.out.println("위험군은 " + danger + "명 이며");
				System.out.println("고위험군은 " + highRisk + "명 입니다.\n");
				System.out.println("내국인이면 1번, 외국인이면 2번을 입력해주세요.\n(if you korean, enter 1. if not, enter 2)");
				System.out.print(">> ");
				check = sc.nextInt();
		
				if(check == 1) {
					System.out.println("학번을 입력해주세요. [ex) 2015405060]");
					System.out.print(">> ");
					intStudentId = sc.nextInt();
					System.out.println("나이를 입력해주세요. [ex) 26]");
					System.out.print(">> ");
					age = sc.nextInt();
					cntKor++;
				}
				else if(check == 2) {
					System.out.println("Enter your StudentID.[ex) James]");
					System.out.print(">> ");
					strStudentId = sc.next();
					System.out.println("Enter your age. [ex) 26]");
					System.out.print(">> ");
					age = sc.nextInt();
					cntFrg++;
				}
				else 
					System.out.println("다시 입력해주세요.(Please Enter again.)");
			} while(check != 1 && check != 2);
			
			if(check == 1) {
				kor[cntKor]= new Korean(age,intStudentId);
				kor[cntKor].check();	// 자가진단
				int rst = kor[cntKor].analysis();	// 자가진단 결과 출력
				if(rst == 2) danger++;
				else if(rst == 3) highRisk++;
			}
			
			else if(check == 2) {
				frg[cntFrg]= new Foreigner(age,strStudentId);
				frg[cntFrg].check();	// 자가진단
				int rst = frg[cntFrg].analysis();	// 자가진단 결과 출력
				if(rst == 2) danger++;
				else if(rst == 3) highRisk++;
			}
		}
	}

}
