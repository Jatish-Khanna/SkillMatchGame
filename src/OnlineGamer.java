import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class OnlineGamer {

  private static final float THRESHOLD = 5.0f;

  static class User {
    String name;
    Float score;

    User(String name, Float score) {
      this.name = name;
      this.score = score;
    }

    public String toString() {
      return name + " " + score;
    }
  }


  static TreeMap<Float, User> waitingUsers = new TreeMap<>();

  private static void matchScore(User user) {
    var userWithLowScoreEntry = waitingUsers.floorEntry(user.score); // log n
    var userWithHighScoreEntry = waitingUsers.ceilingEntry(user.score); // log n

    if (userWithLowScoreEntry != null && isMatch(user, userWithLowScoreEntry.getValue())) {
      startGame(user, userWithLowScoreEntry.getValue());
      waitingUsers.remove(userWithLowScoreEntry.getKey()); // log n
    } else if (userWithHighScoreEntry != null && isMatch(userWithHighScoreEntry.getValue(), user)) {
      startGame(userWithHighScoreEntry.getValue(), user);
      waitingUsers.remove(userWithHighScoreEntry.getKey()); // log n
    } else {
      waitingUsers.put(user.score, user);
    }
  }

  private static boolean isMatch(User foundUser, User user) {
    return foundUser != null &&
        user != null &&
        Float.compare(foundUser.score - user.score, THRESHOLD) <= 0;
  }

  private static void startGame(User secondUser, User user) {
    // game started
    System.out.println("Game started between: " + secondUser + " and " + user);

  }

  public static void main(String[] args) {

    List<User> users = new ArrayList<>();
    users.add(new User("Alice", 15.6f));
    users.add(new User("Bob", 6.2f));
    users.add(new User("Charlie", 4.5f));
    users.add(new User("David", 17.0f));
    users.add(new User("Eve", 5.8f));

    for (User user : users) {
      matchScore(user);
    }
  }
}