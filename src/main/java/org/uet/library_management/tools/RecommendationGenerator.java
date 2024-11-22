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

/**
 * RecommendationGenerator is responsible for generating book recommendations
 * based on various criteria such as genres, user preferences, and bookmarks.
 */
@AllArgsConstructor
@Data
public class RecommendationGenerator {
    private String searchTerm;
    private String title;
    private SearchStrategy searchStrategy;


    /**
     * Returns a list of genres in random order.
     *
     * @return a list of randomly shuffled genres
     */
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

    /**
     * Retrieves a list of random book titles from the bookmarks.
     * The list is shuffled to ensure the order of the books is random.
     *
     * @return a list of randomly shuffled books from the bookmarks.
     */
    public static List<Book> getRandomTitleFromBookmarks() {
        BookService bookService = new BookService();
        List<Book> books = bookService.getRandomTitlesFromBookmarks();

        if (books == null) {
            return new ArrayList<>();
        }

        Collections.shuffle(books);
        return books;
    }

    /**
     * Generates a list of recommended books for a user based on their preferences.
     * If the user's preferred category doesn't yield enough results,
     * a fallback recommendation based on top-rated fiction books is provided.
     *
     * @param userId The unique identifier of the user for whom the recommendations are to be generated.
     * @return A list of recommended books for the specified user.
     */
    public static List<Book> getRecommendationForUsers(int userId) {
        PreferenceService preferenceService = new PreferenceService();
        SearchContext searchByCategory = new SearchContext();
        String maxCategory = preferenceService.getMaxCategory(userId);

        searchByCategory.setStrategy(new SearchByCategory());

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
