import java.util.List;
import java.util.concurrent.TimeUnit;

public class PlayerThread extends Thread {
    private final OldMaidPlayers player;
    private final OldMaid game;
    private final int index;
    private final List<OldMaidPlayers> result;

    public PlayerThread(OldMaidPlayers player, OldMaid game, int index, List<OldMaidPlayers> result) {
        this.player = player;
        this.game = game;
        this.index = index;
        this.result = result;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (player) {
                if (game.getOldMaidPlayers().size() == 1) {
                    System.out.println("Player " + game.getOldMaidPlayers().get(0).getPlayerName() + " wins the game!");
                    result.add(game.getOldMaidPlayers().get(0));
                    return; // Exit the game loop
                }

                if (player != game.getOldMaidPlayers().get(game.getCurrentPlayer())) {
                    try {
                        player.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    continue;
                }
            }

            game.displayGameStatus();
            System.out.println("Player " + player.getPlayerName() + " hand:");
            game.showPlayerHand(player);

            player.drawCard(game.getOldMaidPlayers(), game.getPreviousPlayerIndex(index), index);
            player.throwCard();

            if (player.handSize() == 0) {
                synchronized (game) {
                    result.add(player);
                    game.getOldMaidPlayers().remove(player);
                    int nextPlayerIndex = game.getNextPlayerIndex(index);
                    game.setCurrentPlayer(nextPlayerIndex);
                    game.notifyAll();
                }
            }

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
