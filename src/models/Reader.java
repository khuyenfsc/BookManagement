package models;

public class Reader {
	private int readerID;
	private String fullName;
	private String address;
	private String phoneNumber;
	private int numOfTitle;

	public Reader(String fullName, String address, String phoneNumber,int numOfTitle) {
		this.fullName = fullName;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.numOfTitle = numOfTitle;
	}

	public Reader(int readerID,String fullName, String address, String phoneNumber, int numOfTitle) {
		this.readerID = readerID;
		this.fullName = fullName;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.numOfTitle = numOfTitle;
	}

	public Reader(int readerID) {
		this.readerID = readerID;
	}

	public Reader(int readerID, String fullName){
		this.readerID = readerID;
		this.fullName = fullName;
	}

	public int getReaderID() {
		return this.readerID;
	}

	public void setReaderID(int readerID) {
		this.readerID = readerID;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public int getNumOfTitle() {
		return numOfTitle;
	}

	public void setNumOfTitle(int numOfTitle) {
		this.numOfTitle = numOfTitle;
	}
}
