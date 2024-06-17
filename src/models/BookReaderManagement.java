package models;

import java.util.Comparator;

public class BookReaderManagement  {
	private Book book;
	private Reader reader;
	private String state;
	private int numOfBorrow;
	
	public BookReaderManagement(Reader reader, Book book, String state, int numOfBorrow) {
		this.reader = reader;
		this.book = book;
		this.state = state;
		this.numOfBorrow = numOfBorrow;

	}

//	public BookReaderManagement(Book book, Reader reader, String state, Integer numOfBorrow, Integer totalBorrowed) {
//		this.book = book;
//		this.reader = reader;
//		this.state = state;
//		this.numOfBorrow = numOfBorrow;
//		this.totalBorrowed = totalBorrowed;
//
//	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Reader getReader() {
		return reader;
	}

	public void setReader(Reader reader) {
		this.reader = reader;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getNumOfBorrow() {
		return numOfBorrow;
	}

	public void setNumOfBorrow(int numOfBorrow) {
		this.numOfBorrow = numOfBorrow;
	}




	
	
}
