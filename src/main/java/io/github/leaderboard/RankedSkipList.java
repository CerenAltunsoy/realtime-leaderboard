package io.github.leaderboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Skip list ordered by (score desc, player asc) where every forward pointer also stores
 * its <em>span</em>: how many level-0 nodes it jumps over. Summing spans along a search
 * path gives a node's rank, so rank lookup and access-by-rank are O(log n) expected.
 * This is the same idea as Redis sorted sets (ZRANK / ZRANGE).
 */
class RankedSkipList {

    private static final int MAX_LEVEL = 32;
    private static final double P = 0.25;

    private final Node header = new Node(null, 0, MAX_LEVEL);
    private final Random random;
    private int level = 1;
    private int length = 0;

    RankedSkipList(Random random) {
        this.random = random;
    }

    void insert(String player, long score) {
        // TODO
        throw new UnsupportedOperationException("not implemented yet");
    }

    boolean delete(String player, long score) {
        // TODO
        throw new UnsupportedOperationException("not implemented yet");
    }

    /** 1-based rank, or 0 if not present. */
    int rank(String player, long score) {
        // TODO
        throw new UnsupportedOperationException("not implemented yet");
    }

    /** Entries with ranks [fromRank, fromRank + count), 1-based. */
    List<RankedEntry> range(int fromRank, int count) {
        // TODO
        throw new UnsupportedOperationException("not implemented yet");
    }

    int size() {
        return length;
    }

    private static final class Node {
        final String player;
        final long score;
        final Node[] forward;
        final int[] span;

        Node(String player, long score, int level) {
            this.player = player;
            this.score = score;
            this.forward = new Node[level];
            this.span = new int[level];
        }
    }
}
