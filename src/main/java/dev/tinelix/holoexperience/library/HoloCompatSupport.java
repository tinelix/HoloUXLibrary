/*
 *  Copyright (C) 2013 The Android Open Source Project
 *  Copyright (C) 2026 Dmitry Tretyakov
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *
 *  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package dev.tinelix.holoexperience.library;

import android.content.Context;
import android.content.res.TypedArray;

public class HoloCompatSupport {

    public static boolean isAppCompatTheme(Context context) {
        boolean isAppCompat;

        int[] attrs = {R.attr.windowActionBar};
        TypedArray typedArray = context.obtainStyledAttributes(attrs);

        isAppCompat = typedArray.hasValue(0);

        typedArray.recycle();
        return isAppCompat;
    }

}
