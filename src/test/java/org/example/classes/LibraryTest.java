package org.example.classes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LibraryTest {
    private Library library;
    private Book book1, book2;
    private Student student1, student2;

    @BeforeEach
    public void setUp() {
        library = new Library();
        book1 = new Book("Book 1", "Author 1", 123);
        book2 = new Book("Book 2", "Author 2", 456);
        student1 = new Student("Student 1", 1001);
        student2 = new Student("Student 2", 1002);
    }

    @Test
    public void testAddBook() {
        library.addBook(book1);
        assertTrue(library.searchBooks(SearchByType.TITLE, new ArrayList<>(List.of("Book 1"))).contains(book1));
    }

    @Test
    public void testAddStudent() {
        library.addStudent(student1);
        assertTrue(library.searchStudents(SearchByType.NAME, new ArrayList<>(List.of("Student 1"))).contains(student1));
    }

    @Test
    public void testLendBookSuccess() {
        library.addBook(book1);
        library.addStudent(student1);
        assertTrue(library.lendBook(book1, student1));
        assertTrue(student1.hasBook(book1));
        assertNull(library.searchBooks(SearchByType.TITLE, new ArrayList<>(List.of("Book 1"))));
    }

    @Test
    public void testLendBookFailureBookNotRegistered() {
        library.addStudent(student1);
        assertFalse(library.lendBook(book1, student1));
    }

    @Test
    public void testLendBookFailureStudentNotRegistered() {
        library.addBook(book1);
        assertFalse(library.lendBook(book1, student1));
    }

    @Test
    public void testLendBookFailureStudentAlreadyHasBook() {
        library.addBook(book1);
        library.addStudent(student1);
        library.lendBook(book1, student1);
        assertFalse(library.lendBook(book1, student1));
    }

    @Test
    public void testReturnBookSuccess() {
        library.addBook(book1);
        library.addStudent(student1);
        library.lendBook(book1, student1);
        assertTrue(library.returnBook(book1, student1));
        assertTrue(library.searchBooks(SearchByType.TITLE, new ArrayList<>(List.of("Book 1"))).contains(book1));
        assertFalse(student1.hasBook(book1));
    }

    @Test
    public void testReturnBookFailureStudentNotRegistered() {
        assertFalse(library.returnBook(book1, student1));
    }

    @Test
    public void testReturnBookFailureStudentDoesntHaveBook() {
        library.addBook(book1);
        library.addStudent(student1);
        assertFalse(library.returnBook(book1, student1));
    }

    @Test
    public void testSearchStudentsById() {
        library.addStudent(student1);
        library.addStudent(student2);
        ArrayList<Student> result = library.searchStudents(SearchByType.ID, new ArrayList<>(List.of(1001, 1003)));
        assertEquals(1, result.size());
        assertEquals(student1, result.get(0));
    }

    @Test
    public void testSearchStudentsByName() {
        library.addStudent(student1);
        library.addStudent(student2);
        ArrayList<Student> result = library.searchStudents(SearchByType.NAME, new ArrayList<>(List.of("Student 1", "Student 3")));
        assertEquals(1, result.size());
        assertEquals(student1, result.get(0));
    }

    @Test
    public void testSearchStudentsInvalidType() {
        assertNull(library.searchStudents(SearchByType.TITLE, new ArrayList<>(List.of("Student 1"))));
    }

    @Test
    public void testSearchBooksById() {
        library.addBook(book1);
        library.addBook(book2);
        ArrayList<Book> result = library.searchBooks(SearchByType.ID, new ArrayList<>(List.of(123, 789)));
        assertEquals(1, result.size());
        assertEquals(book1, result.get(0));
    }

    @Test
    public void testSearchBooksByTitle() {
        library.addBook(book1);
        library.addBook(book2);
        ArrayList<Book> result = library.searchBooks(SearchByType.TITLE, new ArrayList<>(List.of("Book 1", "Book 3")));
        assertEquals(1, result.size());
        assertEquals(book1, result.get(0));
    }

    @Test
    public void testSearchBooksByAuthor() {
        library.addBook(book1);
        library.addBook(book2);
        ArrayList<Book> result = library.searchBooks(SearchByType.AUTHOR, new ArrayList<>(List.of("Author 1", "Author 3")));
        assertEquals(1, result.size());
        assertEquals(book1, result.get(0));
    }

    @Test
    public void testSearchBooksInvalidType() {
        assertNull(library.searchBooks(SearchByType.NAME, new ArrayList<>(List.of("Book 1"))));
    }
}
