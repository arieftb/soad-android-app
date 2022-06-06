package id.my.arieftb.soad.presentation.page.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import id.my.arieftb.soad.R
import id.my.arieftb.soad.databinding.ActivityMainBinding
import id.my.arieftb.soad.presentation.base.page.BaseActivityImpl

class MainActivity : BaseActivityImpl<ActivityMainBinding>(), MainContract.View {
    override fun initViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    companion object {
        fun navigate(
            from: Context
        ): Intent {
            return Intent(from, MainActivity::class.java)
        }
    }

    override fun init() {
        initAppBar()
        showHomePage()
    }

    override fun initAppBar() {
        binding?.apply {
            appBarBottom.setOnMenuItemClickListener { item: MenuItem ->
                when (item.itemId) {
                    R.id.home -> {
                        showHomePage()
                        true
                    }
                    R.id.account -> {
                        showAccountPage()
                        true
                    }
                    else -> {
                        false
                    }
                }
            }
        }
    }

    override fun showAccountPage() {
        supportActionBar?.title = getString(R.string.menu_account)
    }

    override fun showHomePage() {
        supportActionBar?.title = getString(R.string.menu_home)
    }
}