package com.spana.banksampahspana.ui.view.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationBarView
import com.spana.banksampahspana.R
import com.spana.banksampahspana.databinding.ActivityAdminBinding
import com.spana.banksampahspana.ui.view.fragment.AdminHomeFragment
import com.spana.banksampahspana.ui.view.fragment.AdminProfileFragment
import com.spana.banksampahspana.ui.view.fragment.AdminTrashCategoryFragment
import com.spana.banksampahspana.ui.view.fragment.AdminWithdrawalFragment
import com.spana.banksampahspana.ui.view.fragment.UsersFragment

class AdminActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {

    private var _binding: ActivityAdminBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBinding()

        binding?.bottomNavigationView?.setOnItemSelectedListener(this)

        if (savedInstanceState == null) {
            replaceFragment(AdminHomeFragment())
        } else {
            val currentFragment =
                supportFragmentManager.getFragment(savedInstanceState, FRAGMENT_KEY)
            if (currentFragment != null) {
                replaceFragment(currentFragment)
            }
        }
    }

    private fun initBinding() {
        _binding = ActivityAdminBinding.inflate(LayoutInflater.from(this))
        setContentView(binding?.root)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        val fragmentManager = supportFragmentManager

        val currentFragment = fragmentManager.findFragmentById(R.id.fragment_container)
        if (currentFragment != null) {
            fragmentManager.putFragment(outState, AdminActivity.FRAGMENT_KEY, currentFragment)
        }
    }


    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment, fragmentManager::class.java.simpleName)
            commit()
        }
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.home -> {
                replaceFragment(AdminHomeFragment())
                return true
            }

            R.id.trashCategory -> {
                replaceFragment(AdminTrashCategoryFragment())
                return true
            }

            R.id.customer -> {
                replaceFragment(UsersFragment())
                return true
            }

            R.id.withdrawal -> {
                replaceFragment(AdminWithdrawalFragment())
                return true
            }

            R.id.profile -> {
                replaceFragment(AdminProfileFragment())
                return true
            }

            else -> return false
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val FRAGMENT_KEY = "currentFragment"
    }
}