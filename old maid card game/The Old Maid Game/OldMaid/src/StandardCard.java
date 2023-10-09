public class StandardCard extends Card {
    public StandardCard(String value, CardColor color) {
        super(value, color);
    }

    @Override
    public OldMaidPlayers playCard(Card card, OldMaid oldMaid) {
        // Check if this card can be played on top of the given card
        if (card.getColor() == this.getColor() || card.getValue().equals(this.getValue())) {
            // The card can be played, so return the next player to play (null for no special effects)
            return oldMaid.getOldMaidPlayers().get(oldMaid.getNextPlayerIndex(oldMaid.getCurrentPlayer()));
        } else {
            // The card cannot be played, so the player will draw a card
            return null;
        }
    }
}