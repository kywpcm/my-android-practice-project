/*
  ��û������ ���� ��
  author PYO IN SOO
*/

public class BloodEntityObject {
	 public  int    bloodId; //���̺� PK
	 public  String patientName;
	 public String bloodType; //������
	 public String statusText; //�������
	 public String donationType; //Ÿ��
	 public String bloodValue; //���׼���
	 public String hospital; //�Կ��� ����
	 public String hospitalPhone; //������ȭ��ȣ
	 public String relationText; //ȯ�ڿ��� ����
	 public String careName; //ȯ�ں�ȣ��
	 public String carePhone; //��ȣ����ȭ��ȣ
	 public  String insertDate; //�Էµ� ��¥
	 
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