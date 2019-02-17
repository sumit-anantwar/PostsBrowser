package com.sumitanantwar.library.managers;

import android.content.Context;
import android.graphics.Typeface;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

public class FontManager {
    private static Map<String, Typeface> FONT_MAP = new HashMap<>();

    public static Typeface getTypeFaceForFont(Context context, String fontFile)
    {
        if (fontFile.length() <= 0) throw new InvalidParameterException("Font filename cannot be null or empty");
        if (!FONT_MAP.containsKey(fontFile))
        {
            try
            {
                Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/"+fontFile);
                FONT_MAP.put(fontFile, typeface);
            }
            catch (Exception e)
            {
                throw new RuntimeException(String.format("Font file not found.\nMake sure that %s exists under \"assets/fonts/\" folder", fontFile));
            }
        }

        return FONT_MAP.get(fontFile);
    }
}
