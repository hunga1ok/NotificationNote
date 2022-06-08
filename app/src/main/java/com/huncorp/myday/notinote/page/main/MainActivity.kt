package com.huncorp.myday.notinote.page.main

import android.app.Dialog
import android.content.res.ColorStateList
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import com.huncorp.myday.notinote.R
import de.dlyt.yanndroid.oneui.R as R2
import com.huncorp.myday.notinote.base.onClick
import com.huncorp.myday.notinote.databinding.ActivityMainBinding
import com.huncorp.myday.notinote.utils.setupRecyclerView
import com.huncorp.myday.notinote.utils.setupRecyclerViewOneUI
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val vm: MainViewModel by viewModels()
    private val adapter: AdaNotifyNote by lazy { AdaNotifyNote() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()

    }

    private fun initView() {
        setupRecyclerViewOneUI(binding.rvList, adapter)
        adapter.addItems(vm.listItem)
        binding.flAddItem.apply {
            rippleColor = resources.getColor(R2.color.sesl_ripple_color)
            backgroundTintList = ColorStateList.valueOf(resources.getColor(R2.color.sesl_swipe_refresh_background))
            supportImageTintList = ResourcesCompat.getColorStateList(resources, de.dlyt.yanndroid.oneui.R.color.sesl_tablayout_selected_indicator_color, theme)
            onClick {
                showPopupAddNotice()
            }
        }

    }

    private fun showPopupAddNotice() {
        // create custom dialog to add new notice
        val dialog = Dialog(this).apply {
            setContentView(R.layout.dialog_add_notice)
            setCancelable(true)
            val layoutParams = WindowManager.LayoutParams().apply {
                copyFrom(window?.attributes)
                width = Resources.getSystem().displayMetrics.widthPixels - 32
                height = WindowManager.LayoutParams.WRAP_CONTENT
            }
            window?.attributes = layoutParams
        }
        dialog.show()
    }
}