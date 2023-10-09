import java.util.ArrayList;
import java.util.List;

public class OldMaidDiscardPile implements DiscardPile {

    private List<Card> discardPile;

    public OldMaidDiscardPile() {
        discardPile=new ArrayList<>();

    }

    @Override
    public Card getTop() {
       return discardPile.get(discardPile.size() - 1);
    }

    @Override
    public void addCardToPile(Card card) {
        discardPile.add(card);
    }



}
