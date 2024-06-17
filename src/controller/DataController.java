package controller;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import models.Book;
import models.Reader;
import models.BookReaderManagement;

public class DataController {
	private Scanner scanner;
	private FileWriter writer;

	ArrayList<Book> books;
	ArrayList<Reader> readers;
	Comparator<Reader> c1 = new Comparator<Reader>(){
		@Override
		public int compare(Reader o1, Reader o2) {
			return o1.getReaderID() - o2.getReaderID() ;
		}
	};
	Comparator<Book> c2 = new Comparator<Book>(){

		public int compare(Book a, Book b){
			return a.getBookID() - b.getBookID();
		}
	};
	void openFileToWrite(String filename,boolean appendPermission) {
		try {
			writer = new FileWriter(filename, appendPermission);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	void closeAfterWrite(String filename) {
		
		try {
			writer.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	void openFileToRead(String filename) {
		try {
			scanner = new Scanner(Paths.get(filename));
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	void closeFileAfterRead(String filename) {
		try {
			scanner.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	
	public void writeBookToFile(Book book, String filename) {
		//set id for book from id of last book in file
		ArrayList<Book> listBook = readBookFromFile(filename);
		if(listBook.size() == 0){
			book.setBookID(100000);
		}else{
			book.setBookID(listBook.get(listBook.size() - 1).getBookID() + 1);
		}
		// end

		openFileToWrite(filename, true);
		try {
			writer.write(book.getBookID() + "|" + book.getBookName() + "|" + book.getAuthor() + "|" + book.getSpecialization() + "|" + book.getPublishYear() + "|" + book.getQuantity() + "\n");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		closeAfterWrite(filename);

		

	}

	public void writeReaderTofile(Reader reader, String filename) {
		

		ArrayList<Reader> listReader =  readReadersFromFile(filename);
		if(listReader.isEmpty()){
			reader.setReaderID(10000000);
		}else{
			reader.setReaderID(listReader.getLast().getReaderID() + 1);
		}

		openFileToWrite(filename, true);
		try {
			writer.write(reader.getReaderID() + "|" + reader.getFullName() + "|" + reader.getAddress() + "|" + reader.getPhoneNumber() + "|" + reader.getNumOfTitle() + "\n");
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		closeAfterWrite(filename);
	}

	public void deleteContextFile(String filename){
		openFileToWrite(filename, false);

		try{
			writer.write("");
		}catch(IOException e){
			e.printStackTrace();
		}

		closeAfterWrite(filename);
	}

	public void writeBRMtoFile(BookReaderManagement brm, String filename) {

		openFileToWrite(filename, true);
		
		try {
			writer.write(brm.getReader().getReaderID() + "|" + brm.getBook().getBookID() + "|" +  brm.getState() + "|" + brm.getNumOfBorrow() + "\n");
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		closeAfterWrite(filename);
	}
	
	
	Reader createReaderFromData(String data) {
		String[] datas = data.split("\\|");
		Reader reader = new Reader(Integer.parseInt(datas[0]), datas[1], datas[2], datas[3], Integer.parseInt(datas[4]));
		return reader;
	}
	
	public ArrayList<Reader> readReadersFromFile(String filename){
		
		openFileToRead(filename);
		ArrayList<Reader> readers = new ArrayList<Reader>();
		
		while(scanner.hasNextLine()) {
			
			String data = scanner.nextLine();
			if(data.isEmpty())break;
			Reader reader = createReaderFromData(data);
			readers.add(reader);
			
		}
		
		closeFileAfterRead(filename);
		
		return readers;
	}
	
	Book createBookFromData(String data) {
		String[] datas = data.split("\\|");
		Book book = new Book(Integer.parseInt(datas[0]), datas[1], datas[2], datas[3], Integer.parseInt(datas[4]), Integer.parseInt(datas[5]));
		return book;
	}
	
	public ArrayList<Book> readBookFromFile(String filename){
		openFileToRead(filename);
		ArrayList<Book> books = new ArrayList<Book>();
		
		while(scanner.hasNextLine()) {
			String data = scanner.nextLine();
			if(data.isEmpty()) break;
			Book book = createBookFromData(data);
			books.add(book);
		}
		
		closeFileAfterRead(filename);
		
		return books;
	}

	int findReaderIndex(int readerID){
		return Collections.binarySearch(readers, new Reader(readerID), c1);
	}

	int finBookIndex(int bookID){
		return Collections.binarySearch(books, new Book(bookID), c2);
	}

	BookReaderManagement createBRMfromData(String data) {
		String[] datas = data.split("\\|");
		BookReaderManagement brm = new BookReaderManagement(new Reader(Integer.parseInt(datas[0]), readers.get(findReaderIndex(Integer.parseInt(datas[0]))).getFullName()), new Book(Integer.parseInt(datas[1]), books.get(finBookIndex(Integer.parseInt(datas[1]))).getBookName()), datas[2], Integer.parseInt(datas[3]));
		
		return brm;
	}

	public ArrayList<BookReaderManagement> readBRMfromFile(String filename){

		ArrayList<BookReaderManagement> brms = new ArrayList<BookReaderManagement>();
		books = readBookFromFile("BOOK.DAT");
		readers = readReadersFromFile("READER.DAT");
		openFileToRead(filename);

		while(scanner.hasNextLine()) {
			String data = scanner.nextLine();
			BookReaderManagement brm = createBRMfromData(data);
			brms.add(brm);
		}
		
		closeFileAfterRead(filename);
		
		return brms;
	}
	
}
