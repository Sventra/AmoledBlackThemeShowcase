package sventrapopizz.amoledblackthemeshowcase;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static sventrapopizz.amoledblackthemeshowcase.HomeFragment.isInFront;
import static sventrapopizz.amoledblackthemeshowcase.NavigationInvertedFragment.invertedIsInFront;
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
        Uri uri = Uri.parse("https://t.me/ABTheme");
        newIntent(uri);
    }
    // This method will open the chat with myself
    public void contactMe(View view) {
        Uri uri = Uri.parse("https://t.me/SventraPopizz");
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
            long verCode = pInfo.getLongVersionCode();
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

            public void run() {
                if (internet_connection()) {
                    try {
                        metodone();
                        Uri uri = Uri.parse("https://t.me/ABTheme/" + parts[partNumber]);
                        newIntent(uri);
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
        if (getSupportFragmentManager().getBackStackEntryCount() > 5){
            getSupportFragmentManager().popBackStack(root, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        getSupportFragmentManager().beginTransaction().addToBackStack(null).setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.fragment_container, fragment).commit();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setCheckedItem(nav);
    }

    public void openNavigation(Fragment fragment) {
        if (getSupportFragmentManager().getBackStackEntryCount() > 5){
            getSupportFragmentManager().popBackStack(root, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        getSupportFragmentManager().beginTransaction().addToBackStack(null).setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.fragment_container, fragment).commit();
    }

    public void openHome(View view) {
        openThemePage(new HomeFragment(), R.id.nav_home);
    }

    public void openNavPureBlack(View view) {
        openThemePage(new NavigationPureBlackFragment(), R.id.navigation_home);
    }

    public void openNavTGX(View view) {
        openThemePage(new NavigationTGXThemesFragment(), R.id.navigation_tgxThemes);
    }

    public void openNavInverted(View view) {
        openThemePage(new NavigationInvertedFragment(), R.id.navigation_invertedThemes);
    }

    public void openNavOther(View view) {
        openThemePage(new NavigationOtherThemesFragment(), R.id.navigation_otherThemes);
    }

    public void openSettings() {
        openThemePage(new SettingsFragment(), R.id.nav_home);
    }

    public void openStandard(View view) {
        openNavigation(new StandardFragment());
    }

    public void openAu(View view) {
        openNavigation(new AuFragment());
        /*InvertedInfernoFragment fragment = new InvertedInfernoFragment ();
        displaySelectedFragment(fragment, this, "invertedInferno");*/
    }

    public void openAutumn(View view) {
        openNavigation(new AutumnFragment());
    }

    public void openCyanogen(View view) {openNavigation(new CyanogenFragment());}

    public void openCrimson(View view) {
        openNavigation(new CrimsonFragment());
    }

    public void openDisrespect(View view) {
        openNavigation(new DisrespectFragment());
    }

    public void openGreenSunset(View view) {
        openNavigation(new GreenSunsetFragment());
    }

    public void openInfernoX(View view) {
        openNavigation(new InfernoXFragment());
    }

    public void openLeague(View view) {
        openNavigation(new LeagueFragment());
    }

    public void openMint(View view) {
        openNavigation(new MintFragment());
    }

    public void openMonster(View view) {
        openNavigation(new MonsterFragment());
    }

    public void openOrange(View view) {
        openNavigation(new OrangeFragment());
    }

    public void openOxygen(View view) {
        openNavigation(new OxygenFragment());
    }

    public void openPhlox(View view) {
        openNavigation(new PhloxFragment());
    }

    public void openPixelBlue(View view) {
        openNavigation(new PixelFragment());
    }

    public void openPixelBlueX(View view) {
        openNavigation(new PixelXFragment());
    }

    public void openQuetzal(View view) {
        openNavigation(new QuetzalFragment());
    }

    public void openRed(View view) {
        openNavigation(new RedFragment());
    }

    public void openRedleaf(View view) {
        openNavigation(new RedleafFragment());
    }

    public void openSaints(View view) {
        openNavigation(new SaintsFragment());
    }

    public void openSteam(View view) {
        openNavigation(new SteamFragment());
    }

    public void openTeal(View view) {
        openNavigation(new TealFragment());
    }

    public void openAospEx(View view) {
        openNavigation(new AospExFragment());
    }

    public void openInverted(View view) {
        openNavigation(new InvertedFragment());
    }

    public void openInvertedInferno(View view) {
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
            case R.id.navigation_invertedThemes:
                openNavInverted(findViewById(R.id.fragment_container));
                break;
            case R.id.navigation_otherThemes:
                openNavOther(findViewById(R.id.fragment_container));
                break;
            case R.id.nav_openchannel:
                uri = Uri.parse("https://t.me/ABTheme");
                newIntent(uri);
                break;
            case R.id.nav_contact:
                uri = Uri.parse("https://t.me/SventraPopizz");
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
            if (getSupportFragmentManager().getBackStackEntryCount() > 5){
                getSupportFragmentManager().popBackStack(root, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
            NavigationView navigationView = findViewById(R.id.nav_view);
            if (isInFront) {
                navigationView.setCheckedItem(R.id.nav_home);
                getSupportFragmentManager().popBackStack(root, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            } else if (pureBlackIsInFront) {
                navigationView.setCheckedItem(R.id.navigation_home);
            }else if (TGXIsInFront){
                navigationView.setCheckedItem(R.id.navigation_tgxThemes);
            } else if (invertedIsInFront) {
                navigationView.setCheckedItem(R.id.navigation_otherThemes);
            } else if (otherIsInFront) {
                navigationView.setCheckedItem(R.id.navigation_otherThemes);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getDelegate().onDestroy();
    }

    /*
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
    }*/
}
