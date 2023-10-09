
public abstract class OldMaidCards {
    private  String value;
    private  CardColor color;

    public void setValue(String value) {
        this.value = value;
    }

    public void setColor(CardColor color) {
        this.color = color;
    }

    public OldMaidCards(String value, CardColor color) {
        this.value = value;
        this.color = color;
    }

    public String getValue() {
        return value;
    }

    public CardColor getColor() {
        return color;
    }



    public abstract OldMaidPlayers playCard(Card card, OldMaid oldMaid);
}
