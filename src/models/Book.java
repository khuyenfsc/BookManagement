package models;

public class Book {
	private int bookID;
	private String bookName;
	private String author;
	private String specialization;
	private int publishYear;
	private int quantity;
	public Book(int bookID, String bookName, String author, String specialization, int publishYear, int quantity){
		this.bookID = bookID;
		this.bookName = bookName;
		this.author = author;
		this.specialization = specialization;
		this.publishYear = publishYear;
		this.quantity = quantity;
	}

	public Book(String bookName, String author, String specialization, int publishYear, int quantity){
		this.bookName = bookName;
		this.author = author;
		this.specialization = specialization;
		this.publishYear = publishYear;
		this.quantity = quantity;
	}

	public Book(int bookID) {
		this.bookID = bookID;
	}

	public Book(int bookID, String bookName){
		this.bookID = bookID;
		this.bookName = bookName;
	}

	public int getBookID() {
		return this.bookID;
	}
	
	public void setBookID(int bookID) {
		this.bookID = bookID;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	public int getPublishYear() {
		return publishYear;
	}

	public void setPublishYear(int publishYear) {
		this.publishYear = publishYear;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
}
