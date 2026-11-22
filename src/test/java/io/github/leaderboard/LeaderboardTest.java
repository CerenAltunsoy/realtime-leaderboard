package io.github.leaderboard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

class LeaderboardTest {

    private Leaderboard board;

    @BeforeEach
    void setUp() {
        board = new Leaderboard(7);
        board.setScore("alice", 300);
        board.setScore("bob", 500);
        board.setScore("carol", 100);
        board.setScore("dave", 400);
    }

    @Test
    void rankIsOneBasedHighestFirst() {
        assertThat(board.rank("bob")).contains(1);
        assertThat(board.rank("dave")).contains(2);
        assertThat(board.rank("alice")).contains(3);
        assertThat(board.rank("carol")).contains(4);
        assertThat(board.rank("nobody")).isEmpty();
    }

    @Test
    void topReturnsHighestScoresInOrder() {
        assertThat(board.top(2)).containsExactly(
                new RankedEntry("bob", 500, 1),
                new RankedEntry("dave", 400, 2));
        assertThat(board.top(10)).hasSize(4);
        assertThat(board.top(0)).isEmpty();
    }

    @Test
    void equalScoresAreOrderedByPlayerId() {
        board.setScore("zed", 300);
        board.setScore("aaron", 300);
        assertThat(board.top(10)).extracting(RankedEntry::player)
                .containsExactly("bob", "dave", "aaron", "alice", "zed", "carol");
    }

    @Test
    void updatingScoreMovesPlayer() {
        board.setScore("carol", 1000);
        assertThat(board.rank("carol")).contains(1);
        assertThat(board.rank("bob")).contains(2);
        assertThat(board.size()).isEqualTo(4);
    }

    @Test
    void incrementAddsToScore() {
        assertThat(board.incrementScore("alice", 150)).isEqualTo(450);
        assertThat(board.rank("alice")).contains(2);
        assertThat(board.incrementScore("newbie", 5)).isEqualTo(5);
        assertThat(board.rank("newbie")).contains(5);
    }

    @Test
    void removeDropsPlayerAndShiftsRanks() {
        assertThat(board.remove("dave")).isTrue();
        assertThat(board.remove("dave")).isFalse();
        assertThat(board.rank("alice")).contains(2);
        assertThat(board.score("dave")).isEmpty();
    }

    @Test
    void aroundReturnsNeighbours() {
        assertThat(board.around("alice", 1)).extracting(RankedEntry::player)
                .containsExactly("dave", "alice", "carol");
        assertThat(board.around("bob", 2)).extracting(RankedEntry::player)
                .containsExactly("bob", "dave", "alice");
        assertThat(board.around("nobody", 1)).isEmpty();
    }

    @Test
    void negativeScoresAreSupported() {
        board.setScore("loser", -50);
        assertThat(board.rank("loser")).contains(5);
    }

    @Test
    void matchesNaiveImplementationUnderRandomOperations() {
        Leaderboard lb = new Leaderboard(42);
        Map<String, Long> naive = new HashMap<>();
        Random rnd = new Random(123);

        for (int op = 0; op < 20_000; op++) {
            String p = "p" + rnd.nextInt(500);
            switch (rnd.nextInt(4)) {
                case 0 -> {
                    long s = rnd.nextInt(1000);
                    lb.setScore(p, s);
                    naive.put(p, s);
                }
                case 1 -> {
                    long d = rnd.nextInt(21) - 10;
                    lb.incrementScore(p, d);
                    naive.merge(p, d, Long::sum);
                }
                case 2 -> assertThat(lb.remove(p)).isEqualTo(naive.remove(p) != null);
                default -> {
                    List<String> order = sorted(naive);
                    int expected = order.indexOf(p) + 1;
                    assertThat(lb.rank(p).orElse(0)).as("rank of %s", p).isEqualTo(expected);
                }
            }
        }
        List<String> order = sorted(naive);
        assertThat(lb.top(order.size())).extracting(RankedEntry::player).containsExactlyElementsOf(order);
    }

    private static List<String> sorted(Map<String, Long> m) {
        List<String> players = new ArrayList<>(m.keySet());
        players.sort(Comparator.comparing((String p) -> m.get(p)).reversed().thenComparing(p -> p));
        return players;
    }
}
