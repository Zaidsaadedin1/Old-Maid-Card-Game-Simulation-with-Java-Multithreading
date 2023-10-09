import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OldMaidDeck implements Deck<Card> {
    private final List<Card> cards;

    public OldMaidDeck() {
        cards = new ArrayList<>();
        for (CardColor color : CardColor.values()) {
            for (int i = 2; i <= 10; i++) {
                cards.add(new StandardCard(Integer.toString(i), color)); // Assuming StandardCard is a concrete class
            }
            cards.add(new StandardCard("J", color));
            cards.add(new StandardCard("Q", color));
            cards.add(new StandardCard("K", color));
            cards.add(new StandardCard("A", color));
        }
        cards.add(new StandardCard("Joker", null));
    }

    @Override
    public List<Card> getCards() {
        for (Card card : cards) {
            System.out.println(card.getValue() + " - " + card.getColor());
        }
        return cards;
    }

    @Override
    public void shuffleCards() {
        Collections.shuffle(cards);
    }

    @Override
    public Card getTop() {
        if (!cards.isEmpty()) {
            return cards.remove(cards.size() - 1);
        }
        return null;
    }

    @Override
    public int getSize() {
        return cards.size();
    }

    @Override
    public void addCardsToDeck(List<Card> cardsToAdd) {
        cards.addAll(cardsToAdd);
    }
}
