package dev.tinelix.irc.android.legacy.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import java.nio.ByteBuffer;
import java.util.Random;

import dev.tinelix.holoexperience.library.app.HoloAlertDialog;
import dev.tinelix.irc.android.legacy.Global;
import dev.tinelix.irc.android.legacy.R;
import dev.tinelix.irc.android.legacy.core.activities.ProfileSettingsActivity;
import dev.tinelix.irc.android.support.models.ServerProfile;

public class IRCProfileManager {
    public static void showCreateProfileDialog(final Context ctx) {

        float dp = ctx.getResources().getDisplayMetrics().scaledDensity;

        View view = View.inflate(ctx, R.layout.dialog_enter_text, null);

        final EditText editText = view.findViewById(R.id.edittext);

        HoloAlertDialog dialog = new HoloAlertDialog.Builder(ctx)
                .setView(view)
                .setPositiveButton(R.string.ok_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        ServerProfile profile = makeProfile(ctx, editText.getText().toString());
                        startProfileSettingsActivity(ctx, profile);
                    }
                })
                .setNegativeButton(R.string.cancel_button, null)
                .show();

        if(dialog.getWindow() != null)
            dialog.getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE
            );
    }

    @SuppressWarnings("ConstantConditions")
    private static ServerProfile makeProfile(Context ctx, String fullProfileName) {
        Random random = new Random();
        int maxRand = 0xFFFFFF;
        boolean isExist;
        ServerProfile profile = null;
        String prefsName = fullProfileName.replaceAll("[^a-zA-Z0-9]", "");

        ByteBuffer buffer = ByteBuffer.allocate(4);

        if(prefsName.length() == 0) {
            do {    // the 'do {} while ()' cycle must be executed only once
                final byte[] bytes = buffer.putInt(random.nextInt(maxRand)).array();
                final String randNumber = Global.bytesToHex(bytes);
                prefsName = String.format("profile_%s", randNumber);

                SharedPreferences prefs = ctx.getSharedPreferences(prefsName, 0);
                isExist = prefs.contains("profileName");

                profile = new ServerProfile(
                        prefsName,
                        fullProfileName.length() > 0 ?
                                fullProfileName :
                                ctx.getResources().getString(R.string.profile_default_name, randNumber),
                        null, false
                );

                if (isExist) {
                    buffer.clear();
                } else {
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString(
                            "profileName",
                            profile.fullName
                    );
                    editor.putString("authMethod", "None");
                    editor.commit();
                    break;
                }
            } while (isExist);

            return profile;
        }

        return null;
    }

    public static void startProfileSettingsActivity(Context ctx, ServerProfile profile) {
        Intent intent = new Intent(ctx, ProfileSettingsActivity.class);
        intent.putExtra("prefsName", profile.name);
        ctx.startActivity(intent);
    }
}
