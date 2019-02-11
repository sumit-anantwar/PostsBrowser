package com.sumitanantwar.mvp

import android.support.annotation.StringRes
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import butterknife.ButterKnife
import butterknife.Unbinder
import timber.log.Timber

/**
 * Created by Sumit Anantwar on 3/21/18.
 */
abstract class BaseActivity : AppCompatActivity() {

    // ButterKnife Unbinder handle
    private lateinit var unbinder: Unbinder;

    // ======= Activity Lifecycle =======
    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)

        unbinder = ButterKnife.bind(this)
    }

    override fun onDestroy() {
        super.onDestroy()

        // ButterKnife Unbind
        unbinder.unbind()
    }


    //======= Helper Methods =======
    fun showProgress(show: Boolean) {
        Timber.i(if (show) "Progress Show" else "Progreee Hide")
    }

    fun showMessage(message: String) {
        showToast(message)
    }

    private fun showToast(@StringRes stringResourceId: Int) {
        showToast(this.getString(stringResourceId))
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}