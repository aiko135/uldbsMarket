package com.mysoft.uldbsmarket

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.mysoft.uldbsmarket.model.User

class MainActivity : AppCompatActivity() {
    lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        findViewById<NavigationView>(R.id.nav_view)
            .setupWithNavController(navController)

        //Это те фрагменты в которых кнопка будет в виде полосок.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_login_fragment,
                R.id.nav_profile_fragment,
                R.id.nav_cart_fragment,
                R.id.nav_myorders_fragment,
                R.id.nav_catalog_fragment,
                R.id.nav_map_fragment
            ),
            drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

//        val navHostFragment : NavHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
//        val navController = navHostFragment.navController
//        val bottomNavigation =  findViewById<BottomNavigationView>(R.id.nav_view);
//        bottomNavigation.setupWithNavController(navController)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                drawerLayout.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    companion object {
        //Исходя из роли пользователя (админ, менеджер) делает видимыми соответств. разделы в меню
        public fun showMenuGroups(currentUser: User, navigationView: NavigationView){
            val MANAGER:Short = 2;
            val ADMIN:Short = 3;
            if(currentUser.role != MANAGER || currentUser.role != ADMIN)
                return;

            val nav_Menu: Menu = navigationView.menu
            if(currentUser.role == MANAGER){
                nav_Menu.findItem(R.id.manager_menu_group).isVisible = true;
            }
            if(currentUser.role == ADMIN){
                nav_Menu.findItem(R.id.manager_menu_group).isVisible = true;
                nav_Menu.findItem(R.id.admin_menu_group).isVisible = true;
            }
        }
    }
}