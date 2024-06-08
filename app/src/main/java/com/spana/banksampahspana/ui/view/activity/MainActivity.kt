package com.spana.banksampahspana.ui.view.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.navigation.NavigationBarView
import com.spana.banksampahspana.R
import com.spana.banksampahspana.databinding.ActivityMainBinding
import com.spana.banksampahspana.ui.view.fragment.HomeFragment
import com.spana.banksampahspana.ui.view.fragment.ProfileFragment
import com.spana.banksampahspana.ui.view.fragment.TrashHistoryFragment
import com.spana.banksampahspana.ui.viewmodel.AuthViewModel
import com.spana.banksampahspana.ui.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBinding()

        binding?.bottomNavigationView?.setOnItemSelectedListener(this)

        setHomeFragment()
    }

    private fun initBinding() {
        _binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding?.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.home -> {
                replaceFragment(HomeFragment())
                return true
            }

            R.id.history -> {
                replaceFragment(TrashHistoryFragment())
                return true
            }

            R.id.profile -> {
                replaceFragment(ProfileFragment())
                return true
            }

            else -> return false
        }
    }


    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment, fragmentManager::class.java.simpleName)
            commit()
        }
    }

    private fun setHomeFragment() {
        val fragmentManager = supportFragmentManager
        val homeFragment = HomeFragment()
        val fragment = fragmentManager.findFragmentByTag(homeFragment::class.java.simpleName)

        if (fragment !is HomeFragment) {
            fragmentManager.beginTransaction().apply {
                add(R.id.fragment_container, homeFragment, homeFragment::class.java.simpleName)
                commit()
            }
        }
    }
}