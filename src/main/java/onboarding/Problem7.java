package onboarding;

import java.util.*;
import java.util.stream.Collectors;


public class Problem7 {

    private static final int NEIGHBOR_POINT = 10;
    private static final int VISITOR_POINT = 1;
    private static final List<String> USER_FRIENDS = new ArrayList<>();
    private static final Map<String, Integer> RECOMMEND_SCORE = new HashMap<>();


    public static List<String> solution(String user, List<List<String>> friends, List<String> visitors) {
        getUserFriends(user, friends);
        getRecommendScoreByRelationship(user, friends);
        getRecommendScoreByVisitor(visitors);
        return getTotalRecommendUsers();
    }

    private static void getUserFriends(String user, List<List<String>> friends) {
        for (List<String> info : friends) {
            String userA = info.get(0);
            String userB = info.get(1);

            if (userA.equals(user)) {
                USER_FRIENDS.add(userB);
            }
            if (userB.equals(user)) {
                USER_FRIENDS.add(userA);
            }
        }
    }

    private static void getRecommendScoreByRelationship(String user, List<List<String>> friends) {
        for (List<String> info : friends) {
            String userA = info.get(0);
            String userB = info.get(1);

            if (userA.equals(user) || userB.equals(user)) {
                continue;
            }

            if (USER_FRIENDS.contains(userA)) {
                if (!RECOMMEND_SCORE.containsKey(userB)) {
                    RECOMMEND_SCORE.put(userB,NEIGHBOR_POINT);
                } else {
                    RECOMMEND_SCORE.put(userB,RECOMMEND_SCORE.get(userB) + NEIGHBOR_POINT);
                }
            } else {
                if (!RECOMMEND_SCORE.containsKey(userA)) {
                    RECOMMEND_SCORE.put(userA,NEIGHBOR_POINT);
                } else {
                    RECOMMEND_SCORE.put(userA,RECOMMEND_SCORE.get(userA) + NEIGHBOR_POINT);
                }
            }
        }
    }

    private static void getRecommendScoreByVisitor(List<String> visitors) {
        for (String visitor : visitors) {
            if (USER_FRIENDS.contains(visitor)) {
                continue;
            }
            if (!RECOMMEND_SCORE.containsKey(visitor)) {
                RECOMMEND_SCORE.put(visitor, VISITOR_POINT);
            } else {
                RECOMMEND_SCORE.put(visitor, RECOMMEND_SCORE.get(visitor) + VISITOR_POINT);
            }
        }
    }

    private static List<String> getTotalRecommendUsers() {
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(RECOMMEND_SCORE.entrySet());
        return entries.stream()
                .sorted((o1, o2) -> {
                    int num = Integer.compare(o2.getValue(), o1.getValue());
                    if (num == 0) {
                        num  = o1.getKey().compareTo(o2.getKey());
                    }
                    return num;
                })
                .limit(5)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
