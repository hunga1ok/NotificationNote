package com.huncorp.myday.notinote.utils

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Rect
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.view.View
import android.view.WindowManager
import android.view.animation.*
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.*
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Transition
import com.google.android.material.button.MaterialButton
import com.huncorp.myday.notinote.R
import java.util.*
import kotlin.concurrent.timerTask

object ViewUtils {

    fun hideViewAfterSec(v: View, second: Long) {
        val hideViewTimer = Timer()
        hideViewTimer.schedule(timerTask {
            visible(v)
            hideViewTimer.cancel()
        }, second * 1000)
    }

    fun visibleOrInvisible(conditionForVisible: Boolean, vararg views: View?) {
        views.forEach { it?.visibility = if (conditionForVisible) View.VISIBLE else View.INVISIBLE }
    }

    fun visibleOrGone(conditionForVisible: Boolean, vararg views: View?) {
        views.forEach { it?.visibility = if (conditionForVisible) View.VISIBLE else View.GONE }
    }

    fun invisible(vararg views: View?) =
        views.forEach { if (it != null) it.visibility = View.INVISIBLE }

    fun visible(vararg views: View?) =
        views.forEach { if (it != null) it.visibility = View.VISIBLE }

    fun gone(vararg views: View?) = views.forEach { if (it != null) it.visibility = View.GONE }

    fun enable(vararg views: View?) = views.forEach { if (it != null) it.isEnabled = true }

    fun disable(vararg views: View?) = views.forEach { if (it != null) it.isEnabled = false }

    fun enableIf(conditionForEnable: Boolean, vararg views: View?) {
        views.forEach { it?.isEnabled = conditionForEnable }
    }

    fun goneIf(conditionForGone: Boolean, vararg views: View?) {
        if (conditionForGone) views.forEach { gone(it) }
    }

    fun disableNestedScroll(vararg views: View?) {
        views.forEach { if (it != null) ViewCompat.setNestedScrollingEnabled(it, false) }
    }

    fun View.disableNestedScroll() = ViewCompat.setNestedScrollingEnabled(this, false)


    fun animate(context: Context?, v: View?, @AnimRes res: Int, delay: Long? = null) {
        if (context == null || v == null) return
        v.startAnimation(AnimationUtils.loadAnimation(context, res).apply {
            delay?.let { startOffset = delay }
        })
    }

    fun slideUp(context: Context?, v: View?) {
        animate(context, v, R.anim.slide_up)
        gone(v)
    }

    fun slideDown(context: Context?, v: View?) {
        visible(v)
        animate(context, v, R.anim.slide_down)
    }

    private const val SLIDE_UP = R.anim.slide_up
    private const val SLIDE_DOWN = R.anim.slide_down
    private const val SLIDE_OUT_LEFT = R.anim.slide_out_left
    private const val SLIDE_IN_RIGHT = R.anim.slide_in_right
    const val DEFAULT_DELAY = 500L

    fun slideDownGone(context: Context, v: View, delay: Long? = null) {
        animate(context, v, SLIDE_DOWN, delay)
        gone(v)
    }

    fun slideUpVisible(context: Context, v: View, delay: Long? = null) {
        visible(v)
        animate(context, v, SLIDE_UP, delay)
    }

    fun slideRightVisible(context: Context, v: View, delay: Long? = null) {
        visible(v)
        animate(context, v, SLIDE_IN_RIGHT, delay)
    }

    fun slideLeftGone(context: Context, v: View, delay: Long? = null) {
        animate(context, v, SLIDE_OUT_LEFT, delay)
        gone(v)
    }

    fun expand(v: View, onAnim: ((started: Boolean) -> Unit)?) = expand(v, null, onAnim)
    fun expand(v: View) = expand(v, null, null)
    fun expand(v: View, duration: Long? = null, onAnim: ((started: Boolean) -> Unit)? = null) {
        v.forceLayout()
        v.measure(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        val targetHeight = v.measuredHeight
        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.layoutParams.height = 1
        v.visibility = View.VISIBLE

        val a = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
                v.layoutParams.height =
                    if (interpolatedTime == 1f) WindowManager.LayoutParams.WRAP_CONTENT
                    else (targetHeight * interpolatedTime).toInt()
                v.requestLayout()
            }

            override fun willChangeBounds() = true
        }
        a.interpolator = OvershootInterpolator()
        a.duration =
            duration ?: (2 * targetHeight / v.context.resources.displayMetrics.density).toLong()
        a.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
                /* No need to implement */
            }

            override fun onAnimationEnd(animation: Animation?) {
                onAnim?.invoke(false)
            }

            override fun onAnimationStart(animation: Animation?) {
                onAnim?.invoke(true)
            }

        })
        v.startAnimation(a)
    }

    fun collapse(v: View, onAnim: ((started: Boolean) -> Unit)?) = collapse(v, null, onAnim)
    fun collapse(v: View) = collapse(v, null, null)
    fun collapse(v: View, duration: Long? = null, onAnim: ((started: Boolean) -> Unit)?) {
        v.forceLayout()
        val initialHeight = v.measuredHeight
        val a = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
                if (interpolatedTime == 1f) v.visibility = View.GONE
                else {
                    v.layoutParams.height =
                        initialHeight - (initialHeight * interpolatedTime).toInt()
                    v.requestLayout()
                }
            }

            override fun willChangeBounds() = true
        }
        a.interpolator = AccelerateDecelerateInterpolator()
        // 1dp/ms
        a.duration =
            duration ?: (2 * initialHeight / v.context.resources.displayMetrics.density).toLong()
        a.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
                /* No need to implement */
            }

            override fun onAnimationEnd(animation: Animation?) {
                onAnim?.invoke(false)
            }

            override fun onAnimationStart(animation: Animation?) {
                onAnim?.invoke(true)
            }
        })
        v.startAnimation(a)
    }

    fun toggle(v: View?, animate: Boolean = true, onAnim: ((started: Boolean) -> Unit)? = null) {
        v?.let {
            if (v.visibility == View.VISIBLE) if (animate) collapse(v, onAnim) else {
                gone(v)
                onAnim?.invoke(true)
                onAnim?.invoke(false)
            }
            else {
                if (animate) expand(v, onAnim)
                else {
                    visible(v)
                    onAnim?.invoke(true)
                    onAnim?.invoke(false)
                }
            }
        }
    }

    fun toggle(v: View?, onAnim: ((started: Boolean) -> Unit)? = null) = toggle(v, null, onAnim)
    fun toggle(v: View?, duration: Long?, onAnim: ((started: Boolean) -> Unit)? = null) {
        v?.let {
            if (v.visibility == View.VISIBLE) collapse(v, duration, onAnim)
            else expand(v, duration, onAnim)
        }
    }

    fun View.circleBackground(@ColorRes colorRes: Int) {
        val gd = GradientDrawable()
        gd.setColor(ContextCompat.getColor(context, colorRes))
        gd.shape = GradientDrawable.OVAL
        gd.gradientRadius = this.width.toFloat() / 2
        this.background = gd
    }
}

fun RecyclerView?.snap() {
    LinearSnapHelper().attachToRecyclerView(this)
}

fun View?.findTextView(@IdRes id: Int) = this?.findViewById<TextView?>(id)
fun View?.findImageView(@IdRes id: Int) = this?.findViewById<ImageView?>(id)
fun View?.findView(@IdRes id: Int) = this?.findViewById<View?>(id)
fun View?.findButton(@IdRes id: Int) = this?.findViewById<Button?>(id)

private fun View.asEpicenter(): Transition.EpicenterCallback {
    val viewRect = Rect()
    getGlobalVisibleRect(viewRect)
    return object : Transition.EpicenterCallback() {
        override fun onGetEpicenter(transition: Transition): Rect {
            return viewRect
        }
    }
}

fun View.updateBackgroundColor(@ColorRes colorRes: Int) {
    this.setBackgroundColor(ContextCompat.getColor(context, colorRes))
}

fun View.updateBackgroundDrawable(@DrawableRes drawable: Int?) {
    drawable?.let { this.background = ContextCompat.getDrawable(context, drawable) }
}

fun View.stroke(@ColorRes color: Int) {
    background = DrawableUtils.stroke(ContextCompat.getColor(context, color))
}

fun View.stroke(@ColorRes color: Int, radius: Int) {
    background = DrawableUtils.stroke(ContextCompat.getColor(context, color), radius = radius)
}

fun View.strokeRound(@ColorRes color: Int) = stroke(color, 50)

fun View.fullRounded(@ColorRes color: Int, radius: Int = 4) {
    try {
        background = DrawableUtils.fullRounded(ContextCompat.getColor(context, color), radius)
    } catch (e: Exception) {
        // ignore when error
    }
}

fun View.fullRound(@ColorRes color: Int) = fullRounded(color, 50)

fun View.partialRounded(
    @ColorRes color: Int, topLeft: Int = 0,
    topRight: Int = 0,
    bottomRight: Int = 0,
    bottomLeft: Int = 0
) {
    try {
        background = DrawableUtils.partialRounded(
            ContextCompat.getColor(context, color),
            topLeft,
            topRight,
            bottomRight,
            bottomLeft
        )
    } catch (e: Exception) {
        // ignore when error
    }
}

fun View.fullRoundedInt(@ColorInt color: Int, radius: Int = 4) {
    try {
        background = DrawableUtils.fullRounded(color, radius)
    } catch (e: Exception) {
        // ignore when error
    }
}

fun View.partialRoundedInt(
    @ColorInt color: Int,
    topLeft: Int = 0,
    topRight: Int = 0,
    bottomRight: Int = 0,
    bottomLeft: Int = 0
) {
    try {
        background = DrawableUtils.partialRounded(color, topLeft, topRight, bottomRight, bottomLeft)
    } catch (e: Exception) {
        // ignore when error
    }
}

fun MaterialButton.updateTint(@ColorRes colorRes: Int) {
    this.rippleColor = ColorStateList.valueOf(ContextCompat.getColor(context, colorRes))
}

fun TextView.updateStyle(style: Int) {
    if (Build.VERSION.SDK_INT < 23)
        this.setTextAppearance(context, style)
    else
        this.setTextAppearance(style)
}

fun updateStyleTextViews(style: Int, vararg args: TextView?) =
    args.forEach { it?.updateStyle(style) }