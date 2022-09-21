package com.openclassrooms.realestatemanager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.WifiManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Philippe on 21/02/2018.
 */

public class Utils {

    /**
     * Conversion d'un prix d'un bien immobilier (Dollars vers Euros)
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     *
     * @return dollars
     */
    public static int convertDollarToEuro(int dollars) {
        return (int) Math.round(dollars * 0.812);
    }

    /**
     * Conversion de la date d'aujourd'hui en un format plus approprié
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     *
     * @return date
     */
    public static String getTodayDate() {
        @SuppressLint
                ("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        return dateFormat.format(new Date());
    }

    /**
     * Vérification de la connexion réseau
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     *
     * @return context
     */
    public static Boolean isInternetAvailable(Context context) {
        @SuppressLint("WifiManagerPotentialLeak")
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        return wifi.isWifiEnabled();
    }
}
