package io.github.leaderboard;

/** @param rank 1-based; rank 1 is the highest score */
public record RankedEntry(String player, long score, int rank) {
}
