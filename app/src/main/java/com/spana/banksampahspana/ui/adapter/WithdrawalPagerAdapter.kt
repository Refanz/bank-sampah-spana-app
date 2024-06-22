package com.spana.banksampahspana.ui.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.spana.banksampahspana.ui.view.fragment.withdrawal.CancelledWithdrawalFragment
import com.spana.banksampahspana.ui.view.fragment.withdrawal.CompletedWithdrawalFragment
import com.spana.banksampahspana.ui.view.fragment.withdrawal.ProcessingWithdrawalFragment
import com.spana.banksampahspana.ui.view.fragment.withdrawal.SubmittedWithdrawalFragment

class WithdrawalPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null

        when (position) {
            0 -> fragment = SubmittedWithdrawalFragment()

            1 -> fragment = ProcessingWithdrawalFragment()

            2 -> fragment = CompletedWithdrawalFragment()

            3 -> fragment = CancelledWithdrawalFragment()
        }

        return fragment as Fragment
    }
}