package org.ot5usk.get;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.ot5usk.models.add_new_author.AddNewAuthorRequest;
import org.ot5usk.models.add_new_author.AddNewAuthorResponse;
import org.ot5usk.models.add_new_book.AddNewBookRequest;
import org.ot5usk.models.get_all_books_by_author.GetAllBooksByAuthorResponse;
import org.ot5usk.models.negative_responses.DefaultNegativeResponse;
import org.ot5usk.steps.assertions.GetAllBooksByAuthorXmlAsserts;

import java.util.List;

import static org.ot5usk.steps.assertions.NegativeAsserts.assertNegativeResponse;
import static org.ot5usk.steps.specifications.Specifications.*;
import static org.ot5usk.utils.RequestBuilder.buildAddNewBookRequest;
import static org.ot5usk.utils.RequestBuilder.buildAddnewAuthorRequest;

@Epic("GET tests .xml")
@Story("Get all books by author .xml")
public class GetAllBooksByAuthorXmlTest {

    private static final AddNewAuthorRequest expectedAuthor = buildAddnewAuthorRequest();
    private static final AddNewAuthorResponse currentAuthor = requestSpecAddNewAuthor(expectedAuthor, 201);

    public void executeXmlAsserts(GetAllBooksByAuthorXmlAsserts xmlAsserts) {
        xmlAsserts.assertExpectedAuthorId();
        xmlAsserts.assertExpectedBookTitles();
        xmlAsserts.assertExpectedAuthorName();
        xmlAsserts.assertExpectedNumOfBooks();
    }

    @Tag("get")
    @Tag("positive")
    @DisplayName("Correct authorId .xml")
    @Description("status-code: 200")
    @ParameterizedTest(name = "bookTitle = {0}")
    @CsvFileSource(resources = "/test_cases/positive/correct_book_titles_values.csv")
    void testGetAllBooksByAuthorXml(String title) {
        AddNewBookRequest expectedBook = buildAddNewBookRequest(title, currentAuthor.getAuthorId());
        requestSpecAddNewBook(expectedBook, 201);
        List<GetAllBooksByAuthorResponse> currentBooks = requestSpecGetAllBooksByAuthorXml(currentAuthor.getAuthorId().toString(), 200).getBooks();
        GetAllBooksByAuthorXmlAsserts xmlAsserts = new GetAllBooksByAuthorXmlAsserts(expectedBook, expectedAuthor, currentAuthor, currentBooks);
        executeXmlAsserts(xmlAsserts);
    }

    @Tag("get")
    @Tag("negative")
    @DisplayName("Incorrect authorId .xml")
    @Description("status-code: 400")
    @ParameterizedTest(name = "authorId = {0}, statusCode = {1}, errCode = {2}, errMsg = {3}")
    @CsvFileSource(resources = "/test_cases/negative/get_all_books/by_incorrect_author_Id_xml.csv")
    void withIncorrectAuthor(String incorrectAuthorId, Integer expStCode, String expErrCode, String expErrMsg, String expErrDetails) {
        DefaultNegativeResponse defaultNegativeResponse = requestSpecGetAllBooksByIncorrectAuthorXml(incorrectAuthorId, expStCode);
        assertNegativeResponse(defaultNegativeResponse, expErrCode, expErrMsg, expErrDetails);
    }
}
