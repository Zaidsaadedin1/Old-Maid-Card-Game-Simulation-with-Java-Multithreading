public abstract class Card {

    private String value;
    private CardColor color;

    public Card(String value, CardColor color) {
        this.value = value;
        this.color = color;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public CardColor getColor() {
        return color;
    }

    public void setColor(CardColor color) {
        this.color = color;
    }

    public abstract OldMaidPlayers playCard(Card card, OldMaid oldMaid);

}