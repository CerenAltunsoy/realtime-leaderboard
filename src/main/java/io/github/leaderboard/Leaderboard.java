package io.github.leaderboard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

/**
 * Leaderboard with O(log n) updates and rank queries.
 * Ordering: higher score first; equal scores are ordered by player id (ascending).
 * Thread-safe via a single lock (a simple starting point; see TASKS for sharding).
 */
public class Leaderboard {

    private final Map<String, Long> scores = new HashMap<>();
    private final RankedSkipList list;

    public Leaderboard() {
        this(new Random());
    }

    /** Deterministic skip list levels, for reproducible tests. */
    public Leaderboard(long seed) {
        this(new Random(seed));
    }

    private Leaderboard(Random random) {
        this.list = new RankedSkipList(random);
    }

    public synchronized void setScore(String player, long score) {
        // TODO
        throw new UnsupportedOperationException("not implemented yet");
    }

    public synchronized long incrementScore(String player, long delta) {
        // TODO
        throw new UnsupportedOperationException("not implemented yet");
    }

    public synchronized boolean remove(String player) {
        // TODO
        throw new UnsupportedOperationException("not implemented yet");
    }

    public synchronized Optional<Long> score(String player) {
        // TODO
        throw new UnsupportedOperationException("not implemented yet");
    }

    /** 1-based rank, empty if the player is unknown. */
    public synchronized Optional<Integer> rank(String player) {
        // TODO
        throw new UnsupportedOperationException("not implemented yet");
    }

    public synchronized List<RankedEntry> top(int k) {
        // TODO
        throw new UnsupportedOperationException("not implemented yet");
    }

    /** The player plus up to {@code n} entries directly above and below. */
    public synchronized List<RankedEntry> around(String player, int n) {
        // TODO
        throw new UnsupportedOperationException("not implemented yet");
    }

    public synchronized int size() {
        // TODO
        throw new UnsupportedOperationException("not implemented yet");
    }
}
