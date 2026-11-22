# realtime-leaderboard

A leaderboard backed by a ranked skip list (the structure behind Redis sorted sets):
O(log n) score updates, rank lookups and range queries.

## Operations

| Method | Complexity (expected) |
|---|---|
| `setScore`, `incrementScore`, `remove` | O(log n) |
| `rank(player)` | O(log n) |
| `top(k)`, `around(player, n)` | O(log n + k) |

Ordering: higher score first, equal scores by player id.

## Usage

```java
var board = new Leaderboard();
board.incrementScore("alice", 50);
board.rank("alice");      // Optional[1]
board.top(10);
board.around("alice", 2); // two players above and below
```

## Build

```bash
mvn verify
```

## Roadmap

- [ ] Ranked skip list and leaderboard API
- [ ] Redis `ZADD` / `ZREVRANK` implementation behind the same interface
- [ ] Spring Boot + WebSocket live updates
- [ ] Sharding for very large boards and hot-player handling
- [ ] JMH benchmark: skip list vs `TreeMap`-based approach

## Design decisions

_TODO_
