import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class OldMaidPlayers implements Player,Runnable {
    private  List<Card> hand;
    private String playerName;
    private int index;

    public OldMaidPlayers(String playerName, int index){
        this.playerName=playerName;
        this.index = index;
        this.hand=new ArrayList<>();
    }
    public int getIndex() {
        return index;
    }
    public boolean hasFinished() {
        return hand.isEmpty();
    }
    public void setIndex(int index) {
        this.index = index;
    }
    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
    public void setHand(List<Card> hand) {
        this.hand = hand;
    }

    @Override
    public void addCard(Card card) {
        hand.add(card);
    }



    @Override
    public List<Card> getHand() {
        return hand;
    }

    public void throwCard() {
        if (hand.size() < 2)
            return;
        for (int i = 0; i < hand.size(); i++) {
            Card card1 = hand.get(i);

            for (int j = i + 1; j < hand.size(); j++) {
                Card card2 = hand.get(j);
                if (isPair(card1, card2)) {

                    hand.remove(card1);
                    hand.remove(card2);
                    System.out.println("Cards throw is : " + card1.getColor() + "  " + card1.getValue() + "   AND   " + card2.getColor() + "  " + card2.getValue());


                }
            }
        }
    }



    private boolean isPair(Card card1, Card card2) {
        return card1.getValue().equals(card2.getValue()) && !areDifferentColors(card1.getColor(), card2.getColor());
    }


    private boolean areDifferentColors(CardColor color1, CardColor color2) {
        return (color1 == CardColor.Hearts || color1 == CardColor.Diamonds) != (color2 == CardColor.Hearts || color2 == CardColor.Diamonds) ||
                (color1 == CardColor.Spades || color1 == CardColor.Clubs) != (color2 == CardColor.Spades || color2 == CardColor.Clubs);
    }


    public int handSize(){
        return hand.size();
    }

    public Card drawCard(List<OldMaidPlayers> players, int previousPlayerIndex, int currentPlayerIndex) {
        synchronized (this) {
            if (hand.isEmpty()) {
                return null;
            }

            OldMaidPlayers previousPlayer = players.get(previousPlayerIndex);
            synchronized (previousPlayer) {
                if (!previousPlayer.getHand().isEmpty()) {
                    Collections.shuffle(previousPlayer.getHand());
                    Card drawnCard = previousPlayer.getCardByIndex(0);
                    hand.add(drawnCard);
                    previousPlayer.removeCardByIndex(0);

                    System.out.println("Player " + getPlayerName() + " draws a card"
                            + " ( color: " + drawnCard.getColor() + " value: " + drawnCard.getValue() + " ) "
                            + " from Player " + previousPlayer.getPlayerName());

                    return drawnCard;
                }
            }
        }
        return null;
    }





    private void removeCardByIndex(int index) {
        if (index >= 0 && index < hand.size()) {
            hand.remove(index);
        }
    }

    public Card getCardByIndex(int index){
        Card chosenCard=hand.get(index);
        return chosenCard;
    }


    /**
     * Runs this operation.
     */
    @Override
    public void run() {

    }
}
