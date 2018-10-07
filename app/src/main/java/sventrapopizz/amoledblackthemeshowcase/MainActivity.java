package sventrapopizz.amoledblackthemeshowcase;

import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import java.util.concurrent.ExecutionException;

import static sventrapopizz.amoledblackthemeshowcase.AospExFragment.aospIsInFront;
import static sventrapopizz.amoledblackthemeshowcase.AuFragment.auIsInFront;
import static sventrapopizz.amoledblackthemeshowcase.CrimsonFragment.crimsonIsInFront;
import static sventrapopizz.amoledblackthemeshowcase.CyanogenFragment.cyanoIsInFront;
import static sventrapopizz.amoledblackthemeshowcase.GreenSunsetFragment.greenSunIsInFront;
import static sventrapopizz.amoledblackthemeshowcase.HomeFragment.isInFront;
import static sventrapopizz.amoledblackthemeshowcase.InvertedFragment.invertedIsInFront;
import static sventrapopizz.amoledblackthemeshowcase.InvertedInfernoFragment.invertedInfIsInFront;
import static sventrapopizz.amoledblackthemeshowcase.LeagueFragment.leagueIsInFront;
import static sventrapopizz.amoledblackthemeshowcase.MintFragment.mintIsInFront;
import static sventrapopizz.amoledblackthemeshowcase.MonsterFragment.monsterIsInFront;
import static sventrapopizz.amoledblackthemeshowcase.OrangeFragment.orangeIsInFront;
import static sventrapopizz.amoledblackthemeshowcase.OxygenFragment.oxygenIsInFront;
import static sventrapopizz.amoledblackthemeshowcase.PhloxFragment.phloxIsInFront;
import static sventrapopizz.amoledblackthemeshowcase.PixelFragment.pixelIsInFront;
import static sventrapopizz.amoledblackthemeshowcase.QuetzalFragment.quetzallIsInFront;
import static sventrapopizz.amoledblackthemeshowcase.RedFragment.redIsInFront;
import static sventrapopizz.amoledblackthemeshowcase.RedleafFragment.redleafIsInFront;
import static sventrapopizz.amoledblackthemeshowcase.SaintsFragment.saintsIsInFront;
import static sventrapopizz.amoledblackthemeshowcase.StandardFragment.standardIsInFront;
import static sventrapopizz.amoledblackthemeshowcase.SteamFragment.steamIsInFront;

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

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        isConnected = activeNetwork != null /*&& activeNetwork.isConnectedOrConnecting()*/;
        try {
            stringaTemi = (String) new RetriveFeedTask().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
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
                    txtclose = (TextView) outdated.findViewById(R.id.txtclose);
                    btnOpen = (Button) outdated.findViewById(R.id.buttonUpdate);
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
        internet_connection();
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
        getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fragment_container, fragment).commit();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setCheckedItem(nav);
    }

    public void openHome(View view) {
        openThemePage(new HomeFragment(), R.id.nav_home);
    }

    public void openSettings() {
        openThemePage(new SettingsFragment(), R.id.nav_home);
    }

    public void openStandard(View view) {
        openThemePage(new StandardFragment(), R.id.nav_standard);
    }

    public void openAutumn(View view) {
        openThemePage(new AutumnFragment(), R.id.nav_autumn);
    }

    public void openAu(View view) {
        openThemePage(new AuFragment(), R.id.nav_au);
        /*InvertedInfernoFragment fragment = new InvertedInfernoFragment ();
        displaySelectedFragment(fragment, this, "invertedInferno");*/
    }

    public void openCyanogen(View view) {openThemePage(new CyanogenFragment(), R.id.nav_cyanogen);}

    public void openCrimson(View view) {
        openThemePage(new CrimsonFragment(), R.id.nav_crimson);
    }

    public void openGreenSunset(View view) {
        openThemePage(new GreenSunsetFragment(), R.id.nav_greenSunset);
    }

    public void openLeague(View view) {
        openThemePage(new LeagueFragment(), R.id.nav_league);
    }

    public void openMint(View view) {
        openThemePage(new MintFragment(), R.id.nav_mint);
    }

    public void openMonster(View view) {
        openThemePage(new MonsterFragment(), R.id.nav_monster);
    }

    public void openOrange(View view) {
        openThemePage(new OrangeFragment(), R.id.nav_orange);
    }

    public void openOxygen(View view) {
        openThemePage(new OxygenFragment(), R.id.nav_oxygen);
    }

    public void openPhlox(View view) {
        openThemePage(new PhloxFragment(), R.id.nav_phlox);
    }

    public void openPixelBlue(View view) {
        openThemePage(new PixelFragment(), R.id.nav_pixelBlue);
    }

    public void openQuetzal(View view) {
        openThemePage(new QuetzalFragment(), R.id.nav_quetzalRed);
    }

    public void openRed(View view) {
        openThemePage(new RedFragment(), R.id.nav_red);
    }

    public void openRedleaf(View view) {
        openThemePage(new RedleafFragment(), R.id.nav_redleaf);
    }

    public void openSaints(View view) {
        openThemePage(new SaintsFragment(), R.id.nav_saints);
    }

    public void openSteam(View view) {
        openThemePage(new SteamFragment(), R.id.nav_steam);
    }

    public void openAospEx(View view) {
        openThemePage(new SteamFragment(), R.id.nav_aospex);
    }

    public void openInverted(View view) {
        openThemePage(new InvertedFragment(), R.id.nav_inverted);
    }

    public void openInvertedInferno(View view) {
        openThemePage(new InvertedInfernoFragment(), R.id.nav_inverted_inferno);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
        Uri uri;
        switch (item.getItemId()) {
            case R.id.nav_home:
                openHome(findViewById(R.id.fragment_container));
                break;
            case R.id.nav_standard:
                openStandard(findViewById(R.id.fragment_container));
                break;
            case R.id.nav_au:
                openAu(findViewById(R.id.fragment_container));
                break;
            case R.id.nav_autumn:
                openAutumn(findViewById(R.id.fragment_container));
                break;
            case R.id.nav_cyanogen:
                openCyanogen(findViewById(R.id.fragment_container));
                break;
            case R.id.nav_crimson:
                openCrimson(findViewById(R.id.fragment_container));
                break;
            case R.id.nav_greenSunset:
                openGreenSunset(findViewById(R.id.fragment_container));
                break;
            case R.id.nav_league:
                openLeague(findViewById(R.id.fragment_container));
                break;
            case R.id.nav_mint:
                openMint(findViewById(R.id.fragment_container));
                break;
            case R.id.nav_monster:
                openMonster(findViewById(R.id.fragment_container));
                break;
            case R.id.nav_orange:
                openOrange(findViewById(R.id.fragment_container));
                break;
            case R.id.nav_oxygen:
                openOxygen(findViewById(R.id.fragment_container));
                break;
            case R.id.nav_pixelBlue:
                openPixelBlue(findViewById(R.id.fragment_container));
                break;
            case R.id.nav_quetzalRed:
                openQuetzal(findViewById(R.id.fragment_container));
                break;
            case R.id.nav_red:
                openRed(findViewById(R.id.fragment_container));
                break;
            case R.id.nav_redleaf:
                openRedleaf(findViewById(R.id.fragment_container));
                break;
            case R.id.nav_saints:
                openSaints(findViewById(R.id.fragment_container));
                break;
            case R.id.nav_steam:
                openSteam(findViewById(R.id.fragment_container));
                break;
            case R.id.nav_inverted:
                openInverted(findViewById(R.id.fragment_container));
                break;
            case R.id.nav_inverted_inferno:
                openInvertedInferno(findViewById(R.id.fragment_container));
                break;
            case R.id.nav_aospex:
                openAospEx(findViewById(R.id.fragment_container));
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
            } else if (standardIsInFront) {
                navigationView.setCheckedItem(R.id.nav_standard);
            } else if (auIsInFront) {
                navigationView.setCheckedItem(R.id.nav_au);
            } else if (crimsonIsInFront) {
                navigationView.setCheckedItem(R.id.nav_crimson);
            } else if (cyanoIsInFront) {
                navigationView.setCheckedItem(R.id.nav_cyanogen);
            } else if (greenSunIsInFront) {
                navigationView.setCheckedItem(R.id.nav_league);
            } else if (leagueIsInFront) {
                navigationView.setCheckedItem(R.id.nav_league);
            } else if (mintIsInFront) {
                navigationView.setCheckedItem(R.id.nav_mint);
            } else if (monsterIsInFront) {
                navigationView.setCheckedItem(R.id.nav_monster);
            } else if (orangeIsInFront) {
                navigationView.setCheckedItem(R.id.nav_orange);
            } else if (oxygenIsInFront) {
                navigationView.setCheckedItem(R.id.nav_oxygen);
            } else if (phloxIsInFront) {
                navigationView.setCheckedItem(R.id.nav_phlox);
            } else if (pixelIsInFront) {
                navigationView.setCheckedItem(R.id.nav_pixelBlue);
            } else if (quetzallIsInFront) {
                navigationView.setCheckedItem(R.id.nav_quetzalRed);
            } else if (redIsInFront) {
                navigationView.setCheckedItem(R.id.nav_red);
            } else if (redleafIsInFront) {
                navigationView.setCheckedItem(R.id.nav_redleaf);
            } else if (saintsIsInFront) {
                navigationView.setCheckedItem(R.id.nav_saints);
            } else if (invertedIsInFront) {
                navigationView.setCheckedItem(R.id.nav_inverted);
            } else if (invertedInfIsInFront) {
                navigationView.setCheckedItem(R.id.nav_inverted_inferno);
            } else if (aospIsInFront) {
                navigationView.setCheckedItem(R.id.nav_aospex);
            } else if (steamIsInFront) {
                navigationView.setCheckedItem(R.id.nav_steam);
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
