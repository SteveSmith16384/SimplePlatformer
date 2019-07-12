package com.scs.libgdx;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class ResourceHandler {

    //HashMaps for storing resources
    private HashMap<String, Texture> textures;
    private HashMap<String, Music> music;
    private HashMap<String, Sound> sounds;

    public ResourceHandler() {
        textures = new HashMap<String, Texture>();
        music = new HashMap<String, Music>();
        sounds = new HashMap<String, Sound>();
    }

    public void loadTexture(String path, String key) {
        Texture texture = new Texture(Gdx.files.internal(path));
        textures.put(key, texture);
    }

    public void loadMusic(String path, String key) {
        Music musicfile = Gdx.audio.newMusic(Gdx.files.internal(path));
        music.put(key, musicfile);
    }

    public void loadSound(String path, String key) {
        Sound soundfile = Gdx.audio.newSound(Gdx.files.internal(path));
        sounds.put(key, soundfile);
    }

    public Texture getTexture(String key) {
        return textures.get(key);
    }

    public Music getMusic(String key) {
        return music.get(key);
    }

    public Sound getSound(String key) {
        return sounds.get(key);
    }


}