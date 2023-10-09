import java.util.List;

public interface Player {
    void throwCard();

    List<Card> getHand();

    void setHand(List<Card> hand);

    void addCard(Card card);

    int handSize();

     Card drawCard(List<OldMaidPlayers> players, int nextPlayerIndex, int currentPlayerIndex);

    Card getCardByIndex(int index);
}