package org.uet.library_management.tools;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.uet.library_management.api.search.SearchByCategory;
import org.uet.library_management.api.search.SearchByTopRated;
import org.uet.library_management.api.search.SearchContext;
import org.uet.library_management.api.search.SearchStrategy;
import org.uet.library_management.core.entities.documents.Book;
import org.uet.library_management.core.services.PreferenceService;
import org.uet.library_management.core.services.documents.BookService;

import java.util.*;

@AllArgsConstructor
@Data
public class RecommendationGenerator {
    private String searchTerm;
    private String title;
    private SearchStrategy searchStrategy;


    public static List<String> getRandomGenre() {
        List<String> genres = Arrays.asList(
                "Adventure",
                "Adult Fiction",
                "Art",
                "Biography",
                "Business",
                "Children's",
                "Classics",
                "Comics",
                "Cookbooks",
                "Dystopian",
                "Fantasy",
                "Fiction",
                "Graphic Novels",
                "Historical Fiction",
                "Horror",
                "Humor",
                "Literary Fiction",
                "Memoir",
                "Military Fiction",
                "Mystery",
                "Non-Fiction",
                "Parenting",
                "Philosophy",
                "Poetry",
                "Politics",
                "Religion",
                "Religion & Spirituality",
                "Romance",
                "Science",
                "Science Fiction",
                "Self-Help",
                "Sports",
                "Technology",
                "Thriller",
                "True Crime",
                "Young Adult"
        );
        Collections.shuffle(genres);
        return genres;
    }

    public static List<Book> getRandomTitleFromBookmarks() {
        BookService bookService = new BookService();
        List<Book> books = bookService.getRandomTitlesFromBookmarks();
        Collections.shuffle(books);
        return books;
    }

    public static List<Book> getRecommendationForUsers(int userId) {
        PreferenceService preferenceService = new PreferenceService();
        SearchContext searchByCategory = new SearchContext();
        searchByCategory.setStrategy(new SearchByCategory());
        String maxCategory = preferenceService.getMaxCategory(userId);
        if (maxCategory != null) {
            List<Book> books = searchByCategory.executeSearch(maxCategory);
            if (books.size() < 3) {
                searchByCategory.setStrategy(new SearchByTopRated());
                return searchByCategory.executeSearch("fiction");
            }
            else {
                return books;
            }
        } else {
            searchByCategory.setStrategy(new SearchByTopRated());
            return searchByCategory.executeSearch("fiction");
        }
    }


}
