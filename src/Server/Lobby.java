package Server;

import Shared.MatchStatus;

import java.util.ArrayList;
import java.util.UUID;

public class Lobby {
    private ArrayList<Match> matches;

    public Lobby() {
        matches = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            matches.add(new Match());
        }
    }

    public UUID register(String username) {
        UUID id = UUID.randomUUID();
        addToMatch(new Player(username, id));
        return id;
    }

    private void addToMatch(Player player) {
        for (Match match : matches) {
            if (match.isOpen()) {
                match.add(player);
                return;
            }
        }
    }

    public MatchStatus hasMatch(UUID userID) {
        for (Match match : matches) {
            if(match.isClosed()) {
                MatchStatus status = match.playerStatus(userID);
                if (status != null) return status;
            }
        }
        return MatchStatus.NOT_FOUND;
    }

    public boolean isTurn(UUID userID) {
        Match match = findMatch(userID);
        return match != null && match.isTurn(userID);
    }

    private Match findMatch(UUID userID) {
        for (Match match: matches) {
            if (match.hasPlayer(userID)) {
                return match;
            }
        }
        return null;
    }

    public void makePlay(UUID userID) {
        Match match = findMatch(userID);
        if (match != null) {
            match.play();
        }
    }

    public String getBoard(UUID userID) {
        Match match = findMatch(userID);
        if (match == null) return "";
        return match.getEncodedBoard();
    }
}
