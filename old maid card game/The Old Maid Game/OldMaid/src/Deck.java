import java.util.List;

public interface Deck<Card> {


    List<Card> getCards();

    void shuffleCards();

    Card getTop();

    int getSize();

    void addCardsToDeck(List<Card> cards);
}
