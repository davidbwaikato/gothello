package gothello.gothelloserver.player;

import gothello.gothelloserver.Game;
import gothello.gothelloserver.artificial_intelligence.Search;
import gothello.gothelloserver.exceptions.IllegalMove;
import gothello.gothelloserver.messages.Message;
import gothello.gothelloserver.rules.GothelloRules;
import gothello.gothelloserver.rules.Stone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// A synthetic player or an AI
public class SyntheticPlayer extends GameObserver {
    private final Logger log = LoggerFactory.getLogger(SyntheticPlayer.class);
    private GothelloRules rules;
    private Stone player;
    private Game game;
    private Search search;


    private void doMyTurn() throws IllegalMove{
        long startTime = System.currentTimeMillis();
        int moveScore = search.performSearch(4);
        long endTime = System.currentTimeMillis();

        log.info("Evaluated {} positions in {}s, highest score {}\n Making move {}", 
            search.positionsEvaluated, 
            (endTime-startTime)/1000.0,
            moveScore, 
            search.getBestMove());
        rules.game.commitMoveToHistory(search.getBestMove());
    }
    
    @Override
    public void update() {
        if (rules.getTurn() == player && !rules.isGameOver()) {
            try {
                log.info("[{}] My turn, processing", game.id);
                doMyTurn();
                game.notifyAllObservers();
            } catch (IllegalMove e) {
                log.error(e.getMessage());
            }
        } else if (rules.isGameOver()) {
            log.info("[{}] Game over {} won", game.id, rules.getWinner());
        } else {
            log.info("[{}] Opponents turn waiting", game.id);
        }
    }

    @Override
    public void sendMessage(Message message) {
        log.info("Send {}", message.messageType);
    }
    
    public SyntheticPlayer(Game game, GothelloRules rules, Stone player){
        search = new Search(rules, player);
        this.game = game;
        this.rules = rules;
        this.player = player;
    }

    public int observerPriority(){
        return 1;
    }
}
