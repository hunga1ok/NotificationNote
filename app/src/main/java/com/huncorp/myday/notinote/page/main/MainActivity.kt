package com.huncorp.myday.notinote.page.main

import android.app.Dialog
import android.content.res.ColorStateList
import android.content.res.Resources
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.huncorp.myday.notinote.base.onClick
import com.huncorp.myday.notinote.databinding.ActivityMainBinding
import com.huncorp.myday.notinote.databinding.DialogAddNoticeBinding
import com.huncorp.myday.notinote.utils.setupRecyclerViewOneUI
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import de.dlyt.yanndroid.oneui.R as R2

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val vm: MainViewModel by viewModels()
    private val adapter: AdaNotifyNote by lazy { AdaNotifyNote() }
    private var selected = HashMap<Int, Boolean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initData()
        initView()
        vm.onReady()
    }

    private fun initData() {
        vm.listItemLiveData.observe(this) {
            CoroutineScope(Dispatchers.Main).launch {
                adapter.clear()
                adapter.addItems(vm.listItem)
            }
        }
    }

    override fun onDestroy() {
        vm.listItemLiveData.removeObservers(this)
        super.onDestroy()
    }

    private fun initView() {
        setupRecyclerViewOneUI(binding.rvList, adapter)

        binding.flAddItem.apply {
            rippleColor = ContextCompat.getColor(this@MainActivity, R2.color.sesl_ripple_color)
            backgroundTintList =
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        this@MainActivity,
                        R2.color.sesl_swipe_refresh_background
                    )
                )
            supportImageTintList = ResourcesCompat.getColorStateList(
                resources,
                R2.color.sesl_tablayout_selected_indicator_color,
                theme
            )
            onClick {
                showPopupAddNotice()
            }
        }

    }

    private fun showPopupAddNotice() {
        // create custom dialog to add new notice
        val dialog = Dialog(this, R2.style.DialogTheme).apply {
            val dialogBinding = DialogAddNoticeBinding.inflate(layoutInflater)
            setContentView(dialogBinding.root)
            setCancelable(true)
            val layoutParams = WindowManager.LayoutParams().apply {
                copyFrom(window?.attributes)
                width = Resources.getSystem().displayMetrics.widthPixels - 32
                height = WindowManager.LayoutParams.WRAP_CONTENT
            }
            window?.attributes = layoutParams
            dialogBinding.btnAdd.setOnClickListener {
                vm.addNewNotify(
                    dialogBinding.edtTitle.text.toString(),
                    dialogBinding.edtContent.text.toString()
                )
                this.dismiss()
            }
        }
        dialog.show()
    }
}