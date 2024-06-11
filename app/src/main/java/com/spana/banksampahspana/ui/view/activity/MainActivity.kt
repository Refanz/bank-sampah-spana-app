package com.spana.banksampahspana.ui.view.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationBarView
import com.spana.banksampahspana.R
import com.spana.banksampahspana.databinding.ActivityMainBinding
import com.spana.banksampahspana.ui.view.fragment.HomeFragment
import com.spana.banksampahspana.ui.view.fragment.ProfileFragment
import com.spana.banksampahspana.ui.view.fragment.TrashHistoryFragment

class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBinding()

        binding?.bottomNavigationView?.setOnItemSelectedListener(this)

        if (savedInstanceState == null) {
            replaceFragment(HomeFragment())
        } else {
            val currentFragment =
                supportFragmentManager.getFragment(savedInstanceState, FRAGMENT_KEY)
            if (currentFragment != null) {
                replaceFragment(currentFragment)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        val fragmentManager = supportFragmentManager

        val currentFragment = fragmentManager.findFragmentById(R.id.fragment_container)
        if (currentFragment != null) {
            fragmentManager.putFragment(outState, FRAGMENT_KEY, currentFragment)
        }
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

    companion object {
        private const val FRAGMENT_KEY = "currentFragment"
    }
}