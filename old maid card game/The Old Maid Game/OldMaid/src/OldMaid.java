import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;



public class OldMaid extends Game {

    private static OldMaid instant;
    private final List<Thread> playerThreads = new ArrayList<>();
    private final int numberOfPlayers;
    private int currentPlayerIndex;
    private List<OldMaidPlayers> oldMaidPlayers;
    private OldMaidDeck deck;
    private OldMaidPlayers winner;


    private OldMaid() {
        System.out.println("\n\n\n");
        System.out.println("Please enter how many players you would like to play");
        Scanner scanner = new Scanner(System.in);
        this.numberOfPlayers = scanner.nextInt();
    }

    static public OldMaid getInstance() {
        if (instant == null) {
            instant = new OldMaid();
        }

        return instant;
    }
    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public List<OldMaidPlayers> getOldMaidPlayers() {
        return oldMaidPlayers;
    }


    public OldMaidPlayers getWinner() {
        return winner;
    }

    public void setWinner(OldMaidPlayers oldMaidPlayers) {
        for (OldMaidPlayers player : this.oldMaidPlayers) {
            if (player.handSize() == 0) {
                winner = player;
                break;
            }
        }
    }


    public void displayGameStatus() {
        for (OldMaidPlayers player : oldMaidPlayers) {
            System.out.println();
            System.out.println("Player " + player.getPlayerName()+ " hand");
            showPlayerHand(player);
            System.out.println("\n");
        }
    }

    public void showPlayerHand(OldMaidPlayers oldMaidPlayers) {

        for (Card card : oldMaidPlayers.getHand()) {
            System.out.print(" ( color: " + card.getColor() + " value: " + card.getValue() + " ) \n");
        }
    }

    public int getCurrentPlayer() {
        return currentPlayerIndex;
    }


    public int getNextPlayerIndex(int currentPlayerIndex) {
        return (currentPlayerIndex + 1) % oldMaidPlayers.size();
    }

    public int getPreviousPlayerIndex(int currentPlayerIndex) {
        if (currentPlayerIndex == 0) {
            return oldMaidPlayers.size() - 1;
        }
        return (currentPlayerIndex - 1) % oldMaidPlayers.size();
    }

    public void setCurrentPlayer(int nextPlayerIndex) {
        currentPlayerIndex = nextPlayerIndex;
    }


    @Override
    public void dealCards() {
        deck.shuffleCards();
        int numberOfCardsForEachPlayer = deck.getSize() / numberOfPlayers;
        Card card;
        for (OldMaidPlayers player : oldMaidPlayers) {
            List<Card> hand = new ArrayList<>();
            for (int i = 0; i < numberOfCardsForEachPlayer; i++) {
                card = deck.getTop();
                hand.add(card);
            }
            if (deck.getSize() == 1) {
                hand.add(deck.getTop());
            }
            player.setHand(hand);
        }

    }

    public void play() {
        Scanner scanner = new Scanner(System.in);
        this.oldMaidPlayers = new ArrayList<>();
        this.deck = new OldMaidDeck();
        this.winner = null;

        for (int i = 1; i <= numberOfPlayers; i++) {
            System.out.println("Please Enter the " + i + " player name: ");
            String playerName = scanner.nextLine(); // Read the player name
            OldMaidPlayers player = new OldMaidPlayers(playerName, i);
            oldMaidPlayers.add(player);
        }
        dealCards();
        displayGameStatus();
        int currentPlayerIndex = 0;

        while (oldMaidPlayers.size() > 1) {
            OldMaidPlayers currentPlayer = oldMaidPlayers.get(currentPlayerIndex);

            synchronized (currentPlayer) {
                displayGameStatus();
                currentPlayer.drawCard(oldMaidPlayers, getPreviousPlayerIndex(currentPlayerIndex), currentPlayerIndex);
                currentPlayer.throwCard();

                if (currentPlayer.handSize() == 0) {
                    synchronized (this) {

                        oldMaidPlayers.remove(currentPlayer);
                        int nextPlayerIndex = getNextPlayerIndex(currentPlayerIndex);
                        setCurrentPlayer(nextPlayerIndex);
                        notifyAll();
                    }
                }


                int nextPlayerIndex = getNextPlayerIndex(currentPlayerIndex);
                OldMaidPlayers nextPlayer = oldMaidPlayers.get(nextPlayerIndex);
                synchronized (nextPlayer) {
                    nextPlayer.notify();
                }
            }


            currentPlayerIndex = getNextPlayerIndex(currentPlayerIndex);


            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Print the result list
        System.out.println("Final result:");
        for (OldMaidPlayers player : oldMaidPlayers) {
            System.out.println("Player " + player.getPlayerName() + " has " + player.handSize() + " LOSER!!.");
        }
    }


}


