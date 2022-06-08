package com.huncorp.myday.notinote.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.ViewStub
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewbinding.BuildConfig
import com.huncorp.myday.notinote.R
import com.huncorp.myday.notinote.utils.*
import com.huncorp.myday.notinote.utils.ViewUtils.visibleOrGone
import kotlin.random.Random

/**
 * Created by korneliussendy on 2019-06-30,
 * at 00:40.
 * Ajaib
 */
abstract class BaseFragment : Fragment(), BaseView {
    protected var v: View? = null
    private val TAG = this.javaClass.simpleName
    private var vBaseError: View? = null
    private var tvBaseError: TextView? = null
    private var btnBaseError: Button? = null
    protected var vBaseFragmentContent: ViewStub? = null

    @LayoutRes
    abstract fun contentView(): Int

    abstract fun initInjector()
    abstract fun initView()
    override fun getView(): View? = v

    open fun setTitle(title: String) {
        view?.findViewById<TextView>(R.id.tvBaseTitle)?.text = title
    }

    open fun setTitle(@StringRes title: Int) {
        view?.findViewById<TextView>(R.id.tvBaseTitle)?.text = getString(title)
    }

    open fun setTitleColor(@ColorRes colorRes: Int) {
        view?.findViewById<TextView>(R.id.tvBaseTitle)
            ?.setTextColor(ContextCompat.getColor(this.requireContext(), colorRes))
    }

    open fun setToolbarColor(@ColorRes colorRes: Int) {
        view?.findViewById<View>(R.id.baseToolbar)
            ?.setBackgroundColor(ContextCompat.getColor(this.requireContext(), colorRes))
    }

    open fun setToolbarBackground(@DrawableRes drawableRes: Int) {
        view?.findViewById<View>(R.id.baseToolbar)
            ?.background = (ContextCompat.getDrawable(this.requireContext(), drawableRes))
    }

    open fun showLeftIcon(@DrawableRes imageRes: Int = R.drawable.ic_back) {
        val ivBaseLeftIcon = view?.findViewById<ImageView>(R.id.ivBaseLeft) ?: return
        gone(ivBaseLeftIcon)
        ivBaseLeftIcon.setImageResource(imageRes)
        ivBaseLeftIcon.onClick { activity?.onBackPressed() }
    }

    open fun showRightIcon(
        @DrawableRes imageRes: Int = R.drawable.ic_right,
        onClick: (() -> Unit)? = null
    ) {
        val ivBaseRight = view?.findViewById<ImageView>(R.id.ivBaseRight) ?: return
        visible(ivBaseRight)
        ivBaseRight.setImageResource(imageRes)
        ivBaseRight.onClick { onClick?.invoke() }
    }

    open fun showSecondRightIcon(
        @DrawableRes imageRes: Int = R.drawable.ic_right,
        onClick: (() -> Unit)? = null
    ) {
        val ivBaseRight = view?.findViewById<ImageView>(R.id.ivBaseRight2) ?: return
        visible(ivBaseRight)
        ivBaseRight.setImageResource(imageRes)
        ivBaseRight.onClick { onClick?.invoke() }
    }

    fun hideRightIcon() {
        view?.findViewById<ImageView?>(R.id.ivBaseRight)?.let {
            gone(it)
            it.removeOnClick()
        }
    }

    fun hideSecondRightIcon() {
        view?.findViewById<ImageView?>(R.id.ivBaseRight2)?.let {
            gone(it)
            it.removeOnClick()
        }
    }

    open fun lazyLoad() = false
    protected var contentInitiated = false

    override fun onStart() {
        super.onStart()
        // make it easy to find out fragment name
        if (BuildConfig.DEBUG) {
            Log.i("fragment_name", this.javaClass.simpleName)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.base_fragment, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupErrorLayout()
        vBaseFragmentContent = v?.findViewById(R.id.vBaseFragmentContent)
        vBaseFragmentContent?.layoutResource = contentView()
        vBaseFragmentContent?.inflate()
        initInjector()
        onPreLoad()
        if (!lazyLoad()) {
            contentInitiated = true
            invokeInit()
        }
    }

    override fun onResume() {
        super.onResume()
        if (lazyLoad() && !contentInitiated) {
            contentInitiated = true
            invokeInit()
        }
    }

    private val delay = Random.nextLong(25, 100)
    private fun invokeInit() {
        initView()
//        runBlocking {
//            delay(delay)
//            initView()
//        }
    }

    /**
     * preloding some default variable such as title & toolbars
     */
    open fun onPreLoad() {
        /* leave it open */
    }

    protected fun setupErrorLayout() {
        vBaseError = v?.findViewById(R.id.vBaseError)
        tvBaseError = v?.findViewById(R.id.tvBaseError)
        btnBaseError = v?.findViewById(R.id.btnBaseError)
    }

    fun getArrayString(@ArrayRes res: Int): Array<String> {
        return requireContext().resources.getStringArray(res)
    }

    override fun onMessage(message: String?) {
        Toast.makeText(context ?: return, message, Toast.LENGTH_LONG).show()
    }

    override fun onMessage(stringResId: Int) {
        onMessage(getString(stringResId))
    }

    override fun onAlert(message: String) {
        alert(message)
    }

    override fun onAlert(title: String, message: String) {
        alert(title, message)
    }

    fun toast(message: String?) {
        Toast.makeText(context ?: return, message, Toast.LENGTH_SHORT).show()
    }

    fun toast(@StringRes messageRes: Int?) {
        messageRes?.let { toast(getString(it)) }
    }

    override fun isNetworkConnect(): Boolean {
        return NetworkUtils.connection(context ?: return false)
    }

    override fun hideKeyboard() {
        return KeyboardUtils.hide(this)
    }

    override fun log(message: Any?) {
        if (!BuildConfig.DEBUG) return
        if (message?.toString()?.length ?: 0 > 2000) longLog(message)
        else Log.d(TAG, message?.toString() ?: "")
    }

    override fun log(message: Throwable?) {
        if (!BuildConfig.DEBUG) return
        if (message?.localizedMessage?.length ?: 0 > 2000) longLog(message)
        Log.e(TAG, message?.localizedMessage, message)
    }

    override fun longLog(any: Any?) {
        if (!BuildConfig.DEBUG) return
        val message = any.toString()
        val maxLogSize = 2000
        for (i in 0..message.length / maxLogSize) {
            val start = i * maxLogSize
            var end = (i + 1) * maxLogSize
            end = if (end > message.length) message.length else end
            Log.d(TAG, message.substring(start, end))
        }
    }

    override fun onUnknownError(errorMessage: String) {
        alert("Network Error", errorMessage)
    }

    override fun onNetworkTimeout() {
        alert("Connection Timeout", "Oops Connection Timeout")
    }

    override fun onNetworkError(message: String) {
        alert("Network Error", message)
    }

    override fun fatalError(message: String) {
        Log.e("FATAL ERROR", message)
        gone(v)
    }

    fun onUserNotAuthorized() {
        onMessage("User Not Authorized")
    }

    fun showError(resMessage: Int, onClick: (() -> Unit)? = null) =
        showError(getString(resMessage), getString(R.string.TRY_AGAIN), onClick)

    fun showError(message: String? = "Error", onClick: (() -> Unit)? = null) {
        showError(message, getString(R.string.TRY_AGAIN), onClick)
    }

    fun showError(message: String? = "Error", buttonRes: Int, onClick: (() -> Unit)? = null) {
        showError(message, getString(buttonRes), onClick)
    }

    fun showError(message: String? = "Error", buttonText: String?, onClick: (() -> Unit)? = null) {
        vBaseError?.visibility = VISIBLE
        vBaseFragmentContent?.visibility = GONE
        tvBaseError?.text = message ?: "Error"
        visibleOrGone(onClick != null, btnBaseError)
        if (onClick != null && btnBaseError != null) {
            btnBaseError?.text = buttonText
            btnBaseError?.visibility = VISIBLE
            btnBaseError?.onClick {
                vBaseError?.visibility = GONE
                vBaseFragmentContent?.visibility = VISIBLE
                onClick.invoke()
            }
        }
    }

    open fun hideError() {
        vBaseError?.visibility = GONE
        vBaseFragmentContent?.visibility = VISIBLE
    }

    fun setBackgroundError(@ColorRes color: Int) {
        vBaseError?.setBackgroundColor(ContextCompat.getColor(requireContext(), color))
    }

    override fun getCurrentContext() = context

    override fun onDestroy() {
        contentInitiated = false
        super.onDestroy()
    }

    open fun onRefresh() {
        //leave it open
    }
}