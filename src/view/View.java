package view;
import java.util.*;
import models.Book;
import controller.DataController;
import models.BookReaderManagement;
import models.Reader;

public class View {
	static Scanner scanner = new Scanner(System.in);
	static DataController datactrl = new DataController();
	static String bookFile = "BOOK.DAT";
	static String readerFile = "READER.DAT";
	static String brmFile = "BRM.DAT";
	static ArrayList<Reader> readers;
	static ArrayList<BookReaderManagement> brms;
	static ArrayList<Book> books;
	static int readerID, bookID , readerIndex, amout, numberOfBorrow, brmIndex, bookIndex;
	static String state;
	static Comparator<Reader> c1 = new Comparator<Reader>(){
		@Override
		public int compare(Reader o1, Reader o2) {
			return o1.getReaderID() - o2.getReaderID() ;
		}
	};
	static Comparator<Book> c2 = new Comparator<Book>(){

		public int compare(Book a, Book b){
			return a.getBookID() - b.getBookID();
		}
	};
	static Comparator<BookReaderManagement> c3 = new Comparator<BookReaderManagement>(){
		String getName(BookReaderManagement a){
			String[] fullName = a.getReader().getFullName().split(" ");
			return fullName[fullName.length - 1];
		}

		@Override
		public int compare(BookReaderManagement a, BookReaderManagement b){
			return getName(a).compareTo(getName(b));
		}
	};

	static Comparator<BookReaderManagement> c4 = new Comparator<BookReaderManagement>(){
		@Override
		public int compare(BookReaderManagement a, BookReaderManagement b){
			return a.getNumOfBorrow() - b.getNumOfBorrow();
		}
	};

	static int findBookFollowName(String name){
		for(int i = 0; i < books.size(); i++){
			if(books.get(i).getBookName().equals(name)) return i;
		}
		return -1;
	}

	static void printBooks() {
		books = datactrl.readBookFromFile(bookFile);

		for(Book it : books) {

			System.out.println("(BookID=" + it.getBookID() + ", Name='" + it.getBookName() + "', Author='" + it.getAuthor() + "', Specialization='" + it.getSpecialization() + "', PublishYear=" + it.getPublishYear() + ", Quantity=" + it.getQuantity() + ")");
		}

	}

	static void addBook() {
		Book book;
		String name, author, specialization;
		int publishYear, quantity;

		System.out.print("Name: ");
		name = scanner.nextLine();
		System.out.print("Author: ");
		author = scanner.nextLine();
		System.out.print("Specialization: ");
		specialization = scanner.nextLine();
		System.out.print("PublishYear: ");
		publishYear = scanner.nextInt();
		System.out.print("Quantity: ");
		quantity = scanner.nextInt();
		
		book = new Book(name, author, specialization, publishYear, quantity);

		books = datactrl.readBookFromFile(bookFile);

		// If new book is in file then update quatity
		if(findBookFollowName(name) >= 0){
			books.get(findBookFollowName(name)).setQuantity(books.get(findBookFollowName(name)).getQuantity() + quantity);

			datactrl.deleteContextFile(bookFile);
			for(Book it : books){
				datactrl.writeBookToFile(it, bookFile);
			}
		}else{
			datactrl.writeBookToFile(book, bookFile);
		}
		//End
	}

	static void printReaders(){
		readers = datactrl.readReadersFromFile(readerFile);

		for(Reader it : readers){

			System.out.println("(ReaderID=" + it.getReaderID() + ", Name='" + it.getFullName() + "', Address='" + it.getAddress() + "', Phone=" + it.getPhoneNumber() + ")");
		}
	}

	static void addReader(){
		String name, address, phonenum;

		System.out.print("Name: ");
		name = scanner.nextLine();
		System.out.print("Address: ");
		address = scanner.nextLine();
		System.out.print("Phone Number: ");
		phonenum = scanner.nextLine();

		Reader reader = new Reader(name, address, phonenum, 0);
		datactrl.writeReaderTofile(reader, readerFile);

	}


	static int findBookIdFollowReaderId(int readerID, int bookID){
		for(int i = 0; i < brms.size(); i++){
			if(brms.get(i).getBook().getBookID() == bookID && brms.get(i).getReader().getReaderID() == readerID){
				return i;
			}
		}

		return -1;
	}

	static void typeReaderID(){
		while(true){
			printReaders();
			System.out.print("Type a ReaderID: ");
			readerID = scanner.nextInt();
			readerIndex = Collections.binarySearch(readers,new Reader(readerID, null, null, null, 0) , c1);

			if(readerIndex >= 0){
				break;
			}else{
				System.out.println("Type a valid id!");
			}
		}
	}

	static void typeBookIDFollowReaderID(){
		while(true){
			printBooks();
			System.out.print("Type a bookID: ");
			bookID = scanner.nextInt();
			scanner.nextLine();

			bookIndex = Collections.binarySearch(books, new Book(bookID), c2);
			if(bookIndex < 0){
				System.out.println("Type a valid id!");
				continue;
			}

			brmIndex = findBookIdFollowReaderId(readerID, bookID);
			if(brmIndex < 0 && readers.get(readerIndex).getNumOfTitle() == 5){
				System.out.println("Over the allowed title!(Just allow borrow less or qual 3)!");
				continue;
			}else break;
		}
	}

	static void typeState(){
		System.out.print("Type state: ");
		state = scanner.nextLine();
	}

	static void updateBrmsToFile(){
		brms = datactrl.readBRMfromFile(brmFile);
		brms.get(brmIndex).setNumOfBorrow(amout + brms.get(brmIndex).getNumOfBorrow());
		brms.get(brmIndex).setState(state);

		datactrl.deleteContextFile(brmFile);
		for(BookReaderManagement it : brms){
			datactrl.writeBRMtoFile(it, brmFile);
		}
	}

	static void typeNumOfBooksForBorrowing(){
		if(brmIndex < 0){

			while(true){
				System.out.print("Type number of this title book want to borrow: ");
				amout = scanner.nextInt();

				if(amout > 3 || amout > books.get(bookIndex).getQuantity()){
					System.out.print("Type a valid number (max of borrowed books is 3 and make sure number less or qual the rest books of this title book)!");
				}else {
					break;
				}
			}

			datactrl.writeBRMtoFile(new BookReaderManagement(new Reader(readerID, readers.get(readerIndex).getFullName()), new Book(bookID, books.get(bookIndex).getBookName()), state, amout ), brmFile);
			//End
		}else{
			while (true){
				System.out.print("Type number of this title book want to borrow: ");
				amout = scanner.nextInt();

				if(amout + brms.get(brmIndex).getNumOfBorrow() > 3){
					System.out.println("Over the max number of this title book!(Max is 3)!");
				}else {
					break;
				}
			}

			updateBrmsToFile();
		}
	}

	static void updateQuantityOfTitleBook(){
		books.get(bookIndex).setQuantity(books.get(bookIndex).getQuantity() - amout);
		books = datactrl.readBookFromFile(bookFile);
		books.get(bookIndex).setQuantity(books.get(bookIndex).getQuantity() - amout);

		datactrl.deleteContextFile(bookFile);
		for(Book it : books){
			datactrl.writeBookToFile(it, bookFile);
		}
	}

	static void updateReaderInfor(){
		if(brmIndex < 0){
			readers.get(readerIndex).setNumOfTitle(readers.get(readerIndex).getNumOfTitle() + 1);
			datactrl.deleteContextFile(readerFile);
			for(Reader it : readers){
				datactrl.writeReaderTofile(it, readerFile);
			}
		}

	}

	static void printBRM(){
		brms = datactrl.readBRMfromFile(brmFile);

		for(BookReaderManagement it : brms){
			System.out.println("(BookID=" + it.getBook().getBookID() + ", ReaderID=" + it.getReader().getReaderID() + ", State='" + it.getState() + "', numOfBorrow=" + it.getNumOfBorrow() + ")");
		}
	}

	static void addInfor(){
		brms = datactrl.readBRMfromFile(brmFile);

		typeReaderID();
		typeBookIDFollowReaderID();
		typeState();
		typeNumOfBooksForBorrowing();
		updateQuantityOfTitleBook();
		updateReaderInfor();
		printBRM();

	}

	static void printBrmsMoreDetails(){
		for(BookReaderManagement it : brms){
			System.out.println("(BookID=" + it.getBook().getBookID() + ", BookName='" + it.getBook().getBookName() + "', ReaderID=" + it.getReader().getReaderID() + ", ReaderFullName='" + it.getReader().getFullName() + "', State='" + it.getState() + "', numOfBorrow=" + it.getNumOfBorrow() + ")");
		}
	}

	static void sortByName(){
		brms.sort(c3);
		printBrmsMoreDetails();
	}

	static void sortByNumOfBooks(){
		brms.sort(c4);
		printBrmsMoreDetails();
	}

	static void sort(){
		int sortChoice;
		brms = datactrl.readBRMfromFile(brmFile);
		printBrmsMoreDetails();
		do{

			System.out.println("	1. Sort by name");
			System.out.println("	2. Sort by number of books");
			System.out.println("	0. Exit");
			System.out.print("Type your choice: ");
			sortChoice = scanner.nextInt();

			switch(sortChoice){
				case 1:
					sortByName();
					break;
				case 2:
					sortByNumOfBooks();
					break;
				case 0:
					return;
			}

		}while(true);
	}

	static ArrayList<BookReaderManagement> getResult(String phrase){
		brms = datactrl.readBRMfromFile(brmFile);
		ArrayList<BookReaderManagement> result = new ArrayList<>();

		for(BookReaderManagement it : brms ){
			if(it.getReader().getFullName().toLowerCase().contains(phrase.toLowerCase())){
				result.add(it);
			}
		}

		return result;
	}

	static void searchByName(){
		String phrase;
		System.out.print("Type a phrase in the name");
		phrase = scanner.nextLine();

		ArrayList<BookReaderManagement> result = getResult(phrase);

		if(result.size() <= 0){
			System.out.println("NOT FOUND!");
		}else{
			for(BookReaderManagement it : result){
				System.out.println("(BookID=" + it.getBook().getBookID() + ", BookName='" + it.getBook().getBookName() + "', ReaderID=" + it.getReader().getReaderID() + ", ReaderFullName='" + it.getReader().getFullName() + "', State='" + it.getState() + "', numOfBorrow=" + it.getNumOfBorrow() + ")");
			}
		}

	}

	public static void main(String[] args) {
		int choice;
		
		do {
			System.out.println("1. Add a book to file");
			System.out.println("2. Print list of books");
			System.out.println("3. Add a reader to file");
			System.out.println("4. Print list of readers");
			System.out.println("5. Add information into book reader managenment ");
			System.out.println("6. Sort book reader management");
			System.out.println("7. Search book reader management by name");
			System.out.println("0. Exit");
			System.out.print("Type your choice: ");
			choice = scanner.nextInt();
			scanner.nextLine();
			
			switch(choice) {
			case 1:
				addBook();
				break;
			case 2:
				printBooks();
				break;
			case 3:
				addReader();
				break;
			case 4:
				printReaders();
				break;
			case 5:
				addInfor();
				break;
			case 6:
				sort();
				break;
			case 7:
				searchByName();
				break;
			case 0:
				System.out.println("You exited from the program");
				return;
			}
			
		}while(true);
		
		
		
		
	}
}
