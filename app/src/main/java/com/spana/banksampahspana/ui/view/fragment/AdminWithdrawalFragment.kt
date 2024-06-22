package com.spana.banksampahspana.ui.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.spana.banksampahspana.R
import com.spana.banksampahspana.databinding.FragmentAdminWithdrawalBinding
import com.spana.banksampahspana.ui.adapter.WithdrawalPagerAdapter

class AdminWithdrawalFragment : Fragment() {

    private var _binding: FragmentAdminWithdrawalBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAdminWithdrawalBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViewPager()
    }

    private fun setViewPager() {
        val withdrawalViewPager = WithdrawalPagerAdapter(requireActivity() as AppCompatActivity)
        val viewPager: ViewPager2 = binding?.viewPagerWithdrawal as ViewPager2
        viewPager.adapter = withdrawalViewPager

        val tabs: TabLayout = binding?.tabsWithdrawal as TabLayout
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2,
            R.string.tab_text_3,
            R.string.tab_text_4
        )
    }
}