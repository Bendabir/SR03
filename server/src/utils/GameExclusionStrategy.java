package utils;
 
import beans.Game;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

// Building exclusion strategy for games (useful for using Game objects in Order and not returning all field)
public class GameExclusionStrategy implements ExclusionStrategy {
 
    public boolean shouldSkipField(FieldAttributes f) {
        return (f.getDeclaringClass() == Game.class && f.getName().equals("id") && f.getName().equals("price") && f.getName().equals("releaseDate") && f.getName().equals("stock") && f.getName().equals("genres"));
    }
 
    public boolean shouldSkipClass(Class<?> clazz) {
        return false;
    }
}