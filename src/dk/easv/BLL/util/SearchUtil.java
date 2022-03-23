package dk.easv.BLL.util;

import dk.easv.BE.User;

import java.util.ArrayList;
import java.util.List;

public class SearchUtil {

    public List<User> search(List<User> searchBase, String query){
        List<User> searchResult = new ArrayList<>();

        for (User user: searchBase){
            if (compareToName(query, user)) {
            searchResult.add(user);
            }
            }
        return searchResult;
        }

    private boolean compareToName(String query, User user){
        return user.getName().toLowerCase().contains(query.toLowerCase());
    }
}
