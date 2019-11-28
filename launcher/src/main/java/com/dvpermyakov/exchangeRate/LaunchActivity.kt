package com.dvpermyakov.exchangeRate

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dvpermyakov.exchangerate.android.RateListFragment

class LaunchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.content, RateListFragment.newInstance())
                .commit()
        }
    }
}
