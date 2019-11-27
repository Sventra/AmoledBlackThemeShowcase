package sventrapopizz.amoledblackthemeshowcase;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import static sventrapopizz.amoledblackthemeshowcase.HomeFragment.isInFront;
import static sventrapopizz.amoledblackthemeshowcase.NavigationOtherThemesFragment.otherIsInFront;
import static sventrapopizz.amoledblackthemeshowcase.NavigationPureBlackFragment.pureBlackIsInFront;
import static sventrapopizz.amoledblackthemeshowcase.NavigationTGXThemesFragment.TGXIsInFront;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    String stringaTemi = null;
    String[] parts;
    Dialog outdated;
    String root;

    public void openDrawer(View view) {
        drawer.openDrawer(GravityCompat.START);
    }

    public void newIntent(Uri uri) {
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    // Open Channel
    public void browser1(View view) {
        Uri uri = Uri.parse("https://telegram.dog/ABTheme");
        newIntent(uri);
    }

    // This method will open the chat with myself
    public void contactMe(View view) {
        Uri uri = Uri.parse("https://telegram.dog/SventraPopizz");
        newIntent(uri);
    }


    boolean internet_connection() {
        //Check if connected to internet, output accordingly
        boolean isConnected;
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = null;
        if (cm != null) {
            activeNetwork = cm.getActiveNetworkInfo();
        }
        isConnected = activeNetwork != null /*&& activeNetwork.isConnectedOrConnecting()*/;

        return isConnected;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }

        // Check for app version
        showPopup();
        /*
        Menu menuNav=navigationView.getMenu();

        MenuItem item = menuNav.findItem(R.id.navigation_tgxThemes);
        try {
            boolean tgxEnabled = (boolean) new RetriveFeedTask3().execute().get();

            if (tgxEnabled) {
                item.setEnabled(true);
            } else{
                item.setEnabled(false);
                item.setVisible(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        */
        Thread megaMethod = new Thread() {
            public void run() {

                metodone();
            }
        };
        megaMethod.start();
    }

    // Method to check if the app is updated or not
    public void showPopup() {
        outdated = new Dialog(this);
        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            int verCode = pInfo.versionCode;
            if (internet_connection()) {
                String newVersString = (String) new RetriveFeedTask2().execute().get(); // This will acquire from a specific message in a telegram channel, the most up to date version of the app
                int newVers = Integer.parseInt(newVersString);
                if (verCode < newVers) {
                    TextView txtclose;
                    Button btnOpen;
                    outdated.setContentView(R.layout.outdatedpopup);
                    txtclose = outdated.findViewById(R.id.txtclose);
                    btnOpen = outdated.findViewById(R.id.buttonUpdate);
                    btnOpen.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=sventrapopizz.amoledblackthemeshowcase");
                            newIntent(uri);
                        }
                    });
                    txtclose.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            outdated.dismiss();
                        }
                    });
                    outdated.show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void metodone() {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "", Snackbar.LENGTH_SHORT);
        try {
            if (internet_connection()) {
                //If there is internet, get the links.
                snackbar.dismiss();
                Thread getLinks = new Thread() {
                    public void run() {
                        gettaIlLink();
                    }
                };
                getLinks.start();
            } else {
                //if there's no internet do this
                //create a snackbar telling the user there is no internet connection and issuing a chance to reconnect
                snackbar = Snackbar.make(findViewById(android.R.id.content),
                        "No internet connection.", Snackbar.LENGTH_INDEFINITE);
                //snackbar.setActionTextColor(getResources().getColor(R.color.colorAccent));
                snackbar.setAction(R.string.try_again, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //recheck internet connection and call DownloadJson if there is internet
                        if (internet_connection()) {
                            metodone();
                        }
                    }
                }).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void gettaIlLink() {
        //SharedPreferences pref = getApplicationContext().getSharedPreferences("Preferences", 0);
        if (internet_connection()) {
            try {
                stringaTemi = (String) new RetriveFeedTask().execute().get();
                parts = stringaTemi.split("-");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void downloadParser(final View view) {
        Thread download = new Thread() {
            int partNumber = Integer.parseInt(view.getTag().toString());
            Uri uri;
            public void run() {
                if (internet_connection()) {
                    try {
                        switch(partNumber){
                            default:
                                metodone();
                                uri = Uri.parse("https://telegram.dog/ABTheme/" + parts[partNumber]);
                                newIntent(uri);
                                break;
                            case 0:
                                uri = Uri.parse("https://telegram.dog/addtheme/abtheme");
                                newIntent(uri);
                                break;
                            case 2:
                                uri = Uri.parse("https://telegram.dog/addtheme/cyanogen");
                                newIntent(uri);
                                break;
                            case 4:
                                uri = Uri.parse("https://telegram.dog/addtheme/abtorange");
                                newIntent(uri);
                                break;
                            case 5:
                                uri = Uri.parse("https://telegram.dog/addtheme/oxygen");
                                newIntent(uri);
                                break;
                            case 8:
                                uri = Uri.parse("https://telegram.dog/addtheme/inverted");
                                newIntent(uri);
                                break;
                            case 9:
                                uri = Uri.parse("https://telegram.dog/addtheme/invertedinferno");
                                newIntent(uri);
                                break;
                            case 10:
                                uri = Uri.parse("https://telegram.dog/addtheme/aospextended");
                                newIntent(uri);
                                break;
                            case 11:
                                uri = Uri.parse("https://telegram.dog/addtheme/steam");
                                newIntent(uri);
                                break;
                            case 12:
                                uri = Uri.parse("https://telegram.dog/addtheme/monster");
                                newIntent(uri);
                                break;
                            case 13:
                                uri = Uri.parse("https://telegram.dog/addtheme/league");
                                newIntent(uri);
                                break;
                            case 14:
                                uri = Uri.parse("https://telegram.dog/addtheme/greenSunset");
                                newIntent(uri);
                                break;
                            case 15:
                                uri = Uri.parse("https://telegram.dog/addtheme/pixelblue");
                                newIntent(uri);
                                break;
                            case 16:
                                uri = Uri.parse("https://telegram.dog/addtheme/crimson");
                                newIntent(uri);
                                break;
                            case 17:
                                uri = Uri.parse("https://telegram.dog/addtheme/quetzal");
                                newIntent(uri);
                                break;
                            case 18:
                                uri = Uri.parse("https://telegram.dog/addtheme/redleaf");
                                newIntent(uri);
                                break;
                            case 19:
                                uri = Uri.parse("https://telegram.dog/addtheme/autumn");
                                newIntent(uri);
                                break;
                            case 20:
                                uri = Uri.parse("https://telegram.dog/addtheme/phlox");
                                newIntent(uri);
                                break;
                            case 21:
                                uri = Uri.parse("https://telegram.dog/addtheme/abtTeal");
                                newIntent(uri);
                                break;
                            case 22:
                                uri = Uri.parse("https://telegram.dog/addtheme/disrespect");
                                newIntent(uri);
                                break;


                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("Exception occurred " + e);
                    }
                } else {
                    metodone();
                }
            }
        };
        download.start();
    }

    /*public static void displaySelectedFragment(Fragment fragment, AppCompatActivity activity, String tag) {
        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commit();
    }*/


    public void openThemePage(Fragment fragment, int nav) {
        if (getSupportFragmentManager().getBackStackEntryCount() > 5) {
            getSupportFragmentManager().popBackStack(root, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        getSupportFragmentManager().beginTransaction().addToBackStack(null).setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.fragment_container, fragment).commit();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setCheckedItem(nav);
    }

    public void openNavigation(Fragment fragment) {
        if (getSupportFragmentManager().getBackStackEntryCount() > 5) {
            getSupportFragmentManager().popBackStack(root, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        getSupportFragmentManager().beginTransaction().addToBackStack(null).setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.fragment_container, fragment).commit();
    }

    public void openHome(View view) {
        if (getSupportFragmentManager().getBackStackEntryCount() > 5) {
            getSupportFragmentManager().popBackStack(root, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        getSupportActionBar().setTitle("ABTheme Showcase");
        getSupportFragmentManager().beginTransaction().addToBackStack(null).setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.fragment_container, new HomeFragment()).commit();
        NavigationView navigationView = findViewById(R.id.nav_view);

    }

    /*public void openHome(View view) {
        openThemePage(new HomeFragment(), R.id.nav_home);
    }*/

    public void openNavPureBlack(View view) {
        getSupportActionBar().setTitle(R.string.pure_black_themes);
        openThemePage(new NavigationPureBlackFragment(), R.id.navigation_home);
    }

    public void openNavTGX(View view) {
        getSupportActionBar().setTitle(R.string.tgx_themes);
        openThemePage(new NavigationTGXThemesFragment(), R.id.navigation_tgxThemes);
    }

    public void openNavOther(View view) {
        getSupportActionBar().setTitle(R.string.other_themes);
        openThemePage(new NavigationOtherThemesFragment(), R.id.navigation_otherThemes);
    }

    public void openSettings() {
        getSupportActionBar().setTitle(R.string.action_settings);
        openThemePage(new SettingsFragment(), R.id.nav_home);
    }

    public void openStandard(View view) {
        getSupportActionBar().setTitle("Standard");
        openNavigation(new StandardFragment());
    }

    public void openAu(View view) {
        getSupportActionBar().setTitle("Au");
        openNavigation(new AuFragment());
        /*InvertedInfernoFragment fragment = new InvertedInfernoFragment ();
        displaySelectedFragment(fragment, this, "invertedInferno");*/
    }

    public void openAutumn(View view) {
        getSupportActionBar().setTitle("Autumn");
        openNavigation(new AutumnFragment());
    }

    public void openCyanogen(View view) {
        getSupportActionBar().setTitle("Cyanogen");
        openNavigation(new CyanogenFragment());
    }

    public void openCrimson(View view) {
        getSupportActionBar().setTitle("Crimson");
        openNavigation(new CrimsonFragment());
    }

    public void openDisrespect(View view) {
        getSupportActionBar().setTitle("Disrespect");
        openNavigation(new DisrespectFragment());
    }

    public void openDefPurple(View view) {
        getSupportActionBar().setTitle("DefinetlyPurple");
        openNavigation(new DefPurpleFragment());
    }


    public void openGreenSunset(View view) {
        getSupportActionBar().setTitle("Green Sunset");
        openNavigation(new GreenSunsetFragment());
    }

    public void openInfernoX(View view) {
        getSupportActionBar().setTitle("Inferno X");
        openNavigation(new InfernoXFragment());
    }

    public void openJamX(View view) {
        getSupportActionBar().setTitle("Jam X");
        openNavigation(new JamXFragment());
    }

    public void openLeague(View view) {
        getSupportActionBar().setTitle("LoL Chat");
        openNavigation(new LeagueFragment());
    }

    public void openMint(View view) {
        getSupportActionBar().setTitle("Mint");
        openNavigation(new MintFragment());
    }

    public void openMonster(View view) {
        getSupportActionBar().setTitle("Monster");
        openNavigation(new MonsterFragment());
    }

    public void openOrange(View view) {
        getSupportActionBar().setTitle("Orange");
        openNavigation(new OrangeFragment());
    }

    public void openOxygen(View view) {
        getSupportActionBar().setTitle("Oxygen");
        openNavigation(new OxygenFragment());
    }

    public void openPhlox(View view) {
        getSupportActionBar().setTitle("Phlox");
        openNavigation(new PhloxFragment());
    }

    public void openPixelBlue(View view) {
        getSupportActionBar().setTitle("Pixel Blue");
        openNavigation(new PixelFragment());
    }

    public void openPixelBlueX(View view) {
        getSupportActionBar().setTitle("Pixel Blue X");
        openNavigation(new PixelXFragment());
    }

    public void openQuetzal(View view) {
        getSupportActionBar().setTitle("Quetzal");
        openNavigation(new QuetzalFragment());
    }

    public void openRed(View view) {
        getSupportActionBar().setTitle("Red");
        openNavigation(new RedFragment());
    }

    public void openRedleaf(View view) {
        getSupportActionBar().setTitle("Redleaf");
        openNavigation(new RedleafFragment());
    }

    public void openSaints(View view) {
        getSupportActionBar().setTitle("Saints");
        openNavigation(new SaintsFragment());
    }

    public void openSteam(View view) {
        getSupportActionBar().setTitle("Steam");
        openNavigation(new SteamFragment());
    }

    public void openTeal(View view) {
        getSupportActionBar().setTitle("Teal");
        openNavigation(new TealFragment());
    }

    public void openTealX(View view) {
        getSupportActionBar().setTitle("Teal X");
        openNavigation(new TealXFragment());
    }

    public void openAospEx(View view) {
        getSupportActionBar().setTitle("AOSP Extended");
        openNavigation(new AospExFragment());
    }

    public void openInverted(View view) {
        getSupportActionBar().setTitle("Inverted");
        openNavigation(new InvertedFragment());
    }

    public void openInvertedX(View view) {
        getSupportActionBar().setTitle("Inverted X");
        openNavigation(new InvertedXFragment());
    }

    public void openInvertedInferno(View view) {
        getSupportActionBar().setTitle("Inverted Inferno");
        openNavigation(new InvertedInfernoFragment());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
        Uri uri;
        switch (item.getItemId()) {
            case R.id.nav_home:
                openHome(findViewById(R.id.fragment_container));
                break;
            case R.id.navigation_home:
                openNavPureBlack(findViewById(R.id.fragment_container));
                break;
            case R.id.navigation_tgxThemes:
                openNavTGX(findViewById(R.id.fragment_container));
                break;
            case R.id.navigation_otherThemes:
                openNavOther(findViewById(R.id.fragment_container));
                break;
            case R.id.nav_openchannel:
                uri = Uri.parse("https://telegram.dog/ABTheme");
                newIntent(uri);
                break;
            case R.id.nav_contact:
                uri = Uri.parse("https://telegram.dog/SventraPopizz");
                newIntent(uri);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            if (getSupportFragmentManager().getBackStackEntryCount() > 5) {
                getSupportFragmentManager().popBackStack(root, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
            NavigationView navigationView = findViewById(R.id.nav_view);
            if (isInFront) {
                getSupportActionBar().setTitle("ABTheme Showcase");
                navigationView.setCheckedItem(R.id.nav_home);
                getSupportFragmentManager().popBackStack(root, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            } else if (pureBlackIsInFront) {
                getSupportActionBar().setTitle(R.string.pure_black_themes);
                navigationView.setCheckedItem(R.id.navigation_home);
            } else if (TGXIsInFront) {
                getSupportActionBar().setTitle(R.string.tgx_themes);
                navigationView.setCheckedItem(R.id.navigation_tgxThemes);
            } else if (otherIsInFront) {
                getSupportActionBar().setTitle(R.string.other_themes);
                navigationView.setCheckedItem(R.id.navigation_otherThemes);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getDelegate().onDestroy();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            openSettings();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
