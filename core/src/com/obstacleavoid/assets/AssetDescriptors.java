package com.obstacleavoid.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Recommended: As well as centralising paths, use of descriptors shows type mismatch errors when assets are loaded
 * and retrieved.
 */
public class AssetDescriptors
{
    public static final AssetDescriptor< BitmapFont > UI_FONT_32 =
            new AssetDescriptor<>( AssetPaths.UI_FONT_32, BitmapFont.class );
    public static final AssetDescriptor< TextureAtlas > GAMEPLAY_ATlAS =
            new AssetDescriptor<>( AssetPaths.GAMEPLAY_ATLAS, TextureAtlas.class );
    public static final AssetDescriptor< Skin > UI_SKIN =
            new AssetDescriptor<>( AssetPaths.UI_SKIN, Skin.class );
    public static final AssetDescriptor< Sound > CRASH_WAV =
            new AssetDescriptor<>( AssetPaths.CRASH_WAV, Sound.class );

    private AssetDescriptors(){}
}
