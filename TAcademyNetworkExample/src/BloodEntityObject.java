/*
  요청정보에 대한 값
  author PYO IN SOO
*/

public class BloodEntityObject {
	 public  int    bloodId; //테이블 PK
	 public  String patientName;
	 public String bloodType; //혈액형
	 public String statusText; //현재상태
	 public String donationType; //타입
	 public String bloodValue; //혈액수량
	 public String hospital; //입원한 병원
	 public String hospitalPhone; //병원전화번호
	 public String relationText; //환자와의 관계
	 public String careName; //환자보호자
	 public String carePhone; //보호자전화번호
	 public  String insertDate; //입력된 날짜
	 
	 public BloodEntityObject(){}
	  public BloodEntityObject(int bloodId,String patientName, String bloodType,
			String statusText, String donationType, String bloodValue,
			String hospital, String hospitalPhone, String relationText,
			String careName, String carePhone, String insertDate) {
		super();
		this.bloodId = bloodId;
		this.patientName = patientName;
		this.bloodType = bloodType;
		this.statusText = statusText;
		this.donationType = donationType;
		this.bloodValue = bloodValue;
		this.hospital = hospital;
		this.hospitalPhone = hospitalPhone;
		this.relationText = relationText;
		this.careName = careName;
		this.carePhone = carePhone;
		this.insertDate = insertDate;
	  }
}