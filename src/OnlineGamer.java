import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;


/***
 * Time Complexity:
 * Each insertion/removal operation in the TreeMap takes O(log N).
 * The lookup for the closest entries using floorEntry and ceilingEntry also takes O(log N).
 * Overall, the time complexity per user
 * is O(log N), and for N users, the total complexity will be O(N log N).
 */
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
    var userWithLowScore = waitingUsers.floorEntry(user.score); // log n
    var userWithHighScore = waitingUsers.ceilingEntry(user.score); // log n

    if (userWithLowScore != null && isMatch(user, userWithLowScore.getValue())) {
      startGame(user, userWithLowScore.getValue());
      waitingUsers.remove(userWithLowScore.getKey()); // log n
    } else if (userWithHighScore != null && isMatch(userWithHighScore.getValue(), user)) {
      startGame(user, userWithHighScore.getValue());
      waitingUsers.remove(userWithHighScore.getKey()); // log n
    } else {
      waitingUsers.put(user.score, user); // log n
    }
  }

  private static boolean isMatch(User foundUser, User user) {
    return foundUser != null && user != null && Float.compare(foundUser.score - user.score, THRESHOLD) <= 0;
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