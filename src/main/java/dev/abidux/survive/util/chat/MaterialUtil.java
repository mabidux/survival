package dev.abidux.survive.util.chat;

import org.bukkit.Material;

import java.util.Arrays;
import java.util.stream.Collectors;

public class MaterialUtil {

    public static String prettifyMaterial(Material material) {
        String name = material.toString().toLowerCase();
        String[] s = name.split("_");
        return Arrays.stream(s).map(l -> Character.toUpperCase(l.charAt(0)) + l.substring(1)).collect(Collectors.joining(" "));
    }

}