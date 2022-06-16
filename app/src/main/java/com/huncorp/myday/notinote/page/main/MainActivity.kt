package com.huncorp.myday.notinote.page.main

import android.app.Dialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.huncorp.myday.notinote.BuildConfig
import com.huncorp.myday.notinote.R
import com.huncorp.myday.notinote.base.onClick
import com.huncorp.myday.notinote.databinding.ActivityMainBinding
import com.huncorp.myday.notinote.databinding.DialogAddNoticeBinding
import com.huncorp.myday.notinote.model.Notice
import com.huncorp.myday.notinote.utils.TAG
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
        vm.newNoticeAdded.observe(this) {
            CoroutineScope(Dispatchers.Main).launch {
                createNotification(it)
            }
        }
    }

    override fun onDestroy() {
        vm.listItemLiveData.removeObservers(this)
        super.onDestroy()
    }

    private fun initView() {
        createNotificationChannel()
        setupRecyclerViewOneUI(binding.rvList, adapter, divider = true)
        binding.drawerLayout.setTitle("NotiNote")

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

    private fun createNotification(notice: Notice) {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(notice.title)
            .setContentText(notice.content)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setOngoing(true)

        with(NotificationManagerCompat.from(this)) {
            Log.e(TAG, "createNotification: $notice ${notice.id}")
            notify(notice.id.toInt(), builder.build())
        }
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        private const val CHANNEL_ID = BuildConfig.APPLICATION_ID
    }

    fun newLauncherIntent(context: Context?): Intent {
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.action = Intent.ACTION_MAIN
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        return intent
    }
}