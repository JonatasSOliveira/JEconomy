package com.example.jeconomy.activitys;

import android.os.Bundle;

import com.example.jeconomy.R;
import com.example.jeconomy.fragments.CategoriaFragment;
import com.example.jeconomy.fragments.DespesaFragment;
import com.example.jeconomy.fragments.HomeFragment;
import com.example.jeconomy.fragments.ReceitaFragment;

import com.example.jeconomy.models.Usuario;

import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.view.Menu;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ActionBar bar;
    private Usuario usuario;
    private TextView tvNome;
    private long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_home);
        bar = getSupportActionBar();
        bar.setTitle("Principal");

        View headerView = navigationView.getHeaderView(0);
        tvNome = headerView.findViewById(R.id.tv_nome_navheader);

        Bundle bundle = getIntent().getBundleExtra("tela_login");
        usuario = (Usuario) bundle.getSerializable("user");
        userId = bundle.getLong("user_id");

        tvNome.setText(usuario.getNome());

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fl_home, new HomeFragment(usuario, userId));
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fl_home, new HomeFragment(usuario, userId));
            ft.commit();
            bar = getSupportActionBar();
            bar.setTitle("Principal");
        } else if (id == R.id.nav_categoria) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fl_home, new CategoriaFragment());
            ft.commit();
            bar = getSupportActionBar();
            bar.setTitle("Categoria");
        } else if (id == R.id.nav_despesa) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fl_home, new DespesaFragment(usuario, userId));
            ft.commit();
            bar = getSupportActionBar();
            bar.setTitle("Despesa");
        } else if (id == R.id.nav_receita) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fl_home, new ReceitaFragment(usuario, userId));
            ft.commit();
            bar = getSupportActionBar();
            bar.setTitle("Receita");
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
