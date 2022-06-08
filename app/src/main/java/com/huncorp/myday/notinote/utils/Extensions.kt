package com.huncorp.myday.notinote.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.Drawable
import android.media.Image
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.format.DateFormat
import android.text.style.ForegroundColorSpan
import android.util.Base64
import android.util.Log
import android.util.Patterns
import android.util.TypedValue
import android.view.View
import android.view.ViewTreeObserver
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.getDrawableOrThrow
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.huncorp.myday.notinote.R
import java.io.ByteArrayOutputStream
import java.io.File
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern
import kotlin.math.*

val Any.TAG: String
    get() {
        val tag = javaClass.simpleName
        return if (tag.length <= 23) tag else tag.substring(0, 23)
    }

fun Context.getProgressBarDrawable(): Drawable {
    val value = TypedValue()
    theme.resolveAttribute(android.R.attr.progressBarStyleSmall, value, false)
    val progressBarStyle = value.data
    val attributes = intArrayOf(android.R.attr.indeterminateDrawable)
    val array = obtainStyledAttributes(progressBarStyle, attributes)
    val drawable = array.getDrawableOrThrow(0)
    array.recycle()
    return drawable
}

fun AppCompatActivity.registerForActivityResult(onResult: (result: ActivityResult) -> Unit) =
    this.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        onResult.invoke(
            it
        )
    }

fun Fragment.createOptions(v: View?) = v?.let {
    null
//    if (it.transitionName.isNullOrEmpty()) null
//    else ActivityOptionsCompat.makeSceneTransitionAnimation(
//        requireActivity(), it, it.transitionName
//    )
}

fun AppCompatActivity.createOptions(v: View?) = v?.let {
    null
//    if (it.transitionName.isNullOrEmpty()) null
//    else ActivityOptionsCompat.makeSceneTransitionAnimation(this, it, it.transitionName)
}

fun String?.isValidName(): Boolean = !this.isNullOrEmpty() &&
        this.matches(Regex("([a-zA-Z\\-'\\s]+)"))

fun String.isValidEmail(): Boolean = this.isNotEmpty() &&
        Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun String?.isAlphaNumeric(includeSpace: Boolean = true): Boolean =
    !this.isNullOrEmpty() && this.matches(Regex(if (includeSpace) "[A-Za-z0-9]+" else "^[A-Za-z0-9]+"))

fun String?.isNumeric(): Boolean =
    this != null && this.isNotEmpty() && this.matches(Regex("^[0-9]+"))

fun String.isValidAjaibConfirmationPassword(password: String): Boolean =
    this.isNotEmpty() && this == password

fun String.isValidOtp(): Boolean = this.isNotEmpty() && this.length == 6

fun String.emptyToNull(): String? = if (this.isEmpty()) null else this

fun String.blankToNull(): String? = if (this.isBlank()) null else this

fun String.extractLinks(): ArrayList<String> {
    val links = ArrayList<String>()
    val regex =
        "\\(?\\b(https?://|www[.]|ftp://)[-A-Za-z0-9+&@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&@#/%=~_()|]"

    val p = Pattern.compile(regex)
    val m = p.matcher(this)
    while (m.find()) {
        var urlStr = m.group()
        if (urlStr.startsWith("(") && urlStr.endsWith(")")) {
            urlStr = urlStr.substring(1, urlStr.length - 1)
        }
        links.add(urlStr)
    }
    return links
}

fun TextInputLayout.showErrorIf(condition: Boolean, message: String) {
    this.error = if (condition) message else null
}

fun AppCompatActivity.setupRecyclerView(
    rv: RecyclerView,
    adapter: RecyclerView.Adapter<*>,
    animate: Boolean = false
): LinearLayoutManager {
    val manager = LinearLayoutManager(this)
    rv.layoutManager = manager
    rv.adapter = adapter
    if (!animate) rv.itemAnimator = null
    return manager
}

fun AppCompatActivity.setupRecyclerView(rv: RecyclerView, adapter: RecyclerView.Adapter<*>) =
    setupRecyclerView(rv, adapter, false)

fun AppCompatActivity.toast(message: String) {
    runOnUiThread {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

fun Context?.toast(@StringRes textId: Int, duration: Int = Toast.LENGTH_SHORT) =
    this?.let { Toast.makeText(it, textId, duration).show() }

fun Context?.toast(text: String, duration: Int = Toast.LENGTH_SHORT) =
    this?.let { Toast.makeText(it, text, duration).show() }


fun AppCompatActivity.toast(res: Int) {
    toast(getString(res))
}


fun visible(vararg views: View?) = views.forEach { it?.visibility = View.VISIBLE }

fun gone(vararg views: View?) = views.forEach { it?.visibility = View.GONE }

fun enable(vararg views: View?) = views.forEach { it?.isEnabled = true }

fun disable(vararg views: View?) = views.forEach { it?.isEnabled = false }

fun setBackgroundColor(color: Int, vararg views: View?) =
    views.forEach { it?.setBackgroundColor(color) }

fun AppCompatActivity.showKeyboard(et: EditText?) {
    if (et == null) return
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT)
}

fun AppCompatActivity.showKeyboardWithFocus(et: EditText?) {
    try {
        if (et == null) return
        et.requestFocus()
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun NestedScrollView.setupLoadMore(manager: LinearLayoutManager, onLoadMode: () -> Unit) {
    setOnScrollChangeListener { v: NestedScrollView?, _: Int, scrollY: Int, _: Int, oldScrollY: Int ->
        v?.getChildAt(v.childCount - 1)?.let {
            if ((scrollY >= (it.measuredHeight - v.measuredHeight)) && scrollY > oldScrollY) {
                val visibleItemCount = manager.childCount
                val totalItemCount = manager.itemCount
                val pastVisibleItems = manager.findFirstVisibleItemPosition()
                if ((visibleItemCount + pastVisibleItems) >= totalItemCount) onLoadMode.invoke()
            }
        }
    }
}

fun NestedScrollView.scrollToTop(smooth: Boolean = true) {
    if (smooth) this.smoothScrollTo(0, 0)
    else this.scrollTo(0, 0)
}

fun AppBarLayout.onOffsetChanged(onChanged: (offset: Float) -> Unit) {
    addOnOffsetChangedListener(
        AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            val offset = abs(verticalOffset / appBarLayout.totalScrollRange.toFloat())
            onChanged.invoke(offset)
        })
}

fun AppCompatActivity.loadFragment(fragment: Fragment?, @IdRes into: Int): Boolean {
    if (fragment != null) {
        val fTransaction = supportFragmentManager.beginTransaction()
        fTransaction.setTransition(FragmentTransaction.TRANSIT_NONE)
        fTransaction.disallowAddToBackStack()
            .replace(into, fragment)
            .commitAllowingStateLoss()
        return true
    }
    return false
}


fun Double.round(digit: Int): Double {
    val factor = 10.0.pow(digit.toDouble())
    return (this * factor).roundToInt() / factor
}

fun Double?.orZero(): Double {
    return this ?: 0.0
}

fun Int?.orZero(): Int {
    return this ?: 0
}

fun Long?.orZero(): Long {
    return this ?: 0
}

fun Int?.orDefault(value: Int): Int {
    return this ?: value
}

fun Double?.formatString(): String = toPercent(0)

fun Double?.formatString(digit: Int?): String {
    val number = DecimalFormat.getInstance() as DecimalFormat
    val format = DecimalFormatSymbols()
    format.decimalSeparator = ','
    format.groupingSeparator = '.'
    number.decimalFormatSymbols = format
    number.maximumFractionDigits = digit ?: 0
    return number.format(this ?: 0.0)
}

fun Float?.toPercent(digit: Int? = 0): String = this?.toDouble().toPercent(digit)

fun Double?.toPercent(): String = toPercent(0)

fun Double?.toPercent(
    digit: Int?,
    usePrefix: Boolean = false,
    positivePrefix: String = "+",
    negativePrefix: String = "-"
): String {
    val number = DecimalFormat.getPercentInstance() as DecimalFormat
    val format = DecimalFormatSymbols()
    format.decimalSeparator = ','
    format.groupingSeparator = '.'
    number.decimalFormatSymbols = format
    number.maximumFractionDigits = digit ?: 0
    number.minimumFractionDigits = 0
    if (usePrefix) {
        number.positivePrefix = if (this.orZero() == 0.0) "" else positivePrefix
        number.negativePrefix = negativePrefix
    }
    return number.format(this ?: 0.0)
}

fun Double?.toPercent(
    maxDigit: Int?,
    minDigit: Int?,
    usePrefix: Boolean = false,
    positivePrefix: String = "+",
    negativePrefix: String = "-"
): String {
    val number = DecimalFormat.getPercentInstance() as DecimalFormat
    val format = DecimalFormatSymbols()
    format.decimalSeparator = ','
    format.groupingSeparator = '.'
    number.decimalFormatSymbols = format
    number.maximumFractionDigits = maxDigit ?: 0
    number.minimumFractionDigits = minDigit ?: 0
    if (usePrefix) {
        number.positivePrefix = if (this.orZero() == 0.0) "" else positivePrefix
        number.negativePrefix = negativePrefix
    }
    return number.format(this ?: 0.0)
}

fun Double?.toPercentStock(digit: Int?): String = this.toPercent(digit, true, "▴ ", "▾ ")

fun Double?.percentized(usePrefix: Boolean = false): String {
    if (this == null || this == 0.0) return "0 %"
    val prefix = if (!usePrefix) "" else (if (this >= 0) "+" else "-")
    return "$prefix${this.format(2)} %"
}

fun Double?.toDollar(usePrefix: Boolean = false, digit: Int = 0): String =
    toMoney("$ ", usePrefix, digit)

fun Double?.toRupiah(usePrefix: Boolean = false, digit: Int = 0): String =
    toMoney("Rp ", usePrefix, digit)

fun Double?.toMoney(): String = toMoney("")

fun Double?.toMoney(symbol: String?, usePrefix: Boolean = false, digit: Int = 0): String {
    val kurs = DecimalFormat.getCurrencyInstance() as DecimalFormat
    val format = DecimalFormatSymbols()
    format.currencySymbol = symbol ?: ""
    format.monetaryDecimalSeparator = ','
    format.groupingSeparator = '.'
    kurs.decimalFormatSymbols = format
    kurs.minimumFractionDigits = digit
    kurs.maximumFractionDigits = digit
    if (usePrefix) {
        kurs.positivePrefix = if (this.orZero() == 0.0) "$symbol" else "+ $symbol"
        kurs.negativePrefix = "- $symbol"
    }
    return kurs.format(this ?: 0.0)
}

fun BigDecimal.toMoney(): String = toMoney("")

fun BigDecimal.toMoney(symbol: String?, digit: Int = 0): String {
    val kurs = DecimalFormat.getCurrencyInstance() as DecimalFormat
    val format = DecimalFormatSymbols()
    format.currencySymbol = symbol ?: ""
    format.monetaryDecimalSeparator = ','
    format.groupingSeparator = '.'
    kurs.decimalFormatSymbols = format
    kurs.maximumFractionDigits = digit
    return kurs.format(this)
}

fun Long.toRupiah(): String {
    val kursIdr = DecimalFormat.getCurrencyInstance() as DecimalFormat
    val formatRp = DecimalFormatSymbols()
    formatRp.currencySymbol = "Rp "
    formatRp.monetaryDecimalSeparator = ','
    formatRp.groupingSeparator = '.'
    kursIdr.decimalFormatSymbols = formatRp
    kursIdr.isDecimalSeparatorAlwaysShown = false
    return kursIdr.format(this)
}

fun Float.format(digit: Int): String {
    val number = DecimalFormat.getInstance() as DecimalFormat
    val format = DecimalFormatSymbols()
    format.decimalSeparator = ','
    format.groupingSeparator = '.'
    number.decimalFormatSymbols = format
    number.maximumFractionDigits = digit
    return number.format(this)
}

fun Float.formatFloat(digit: Int = 10): String {
    val number = DecimalFormat.getInstance() as DecimalFormat
    val format = DecimalFormatSymbols()
    format.decimalSeparator = '.'
    number.decimalFormatSymbols = format
    number.maximumFractionDigits = digit
    return number.format(this)
}

fun Double?.format(digit: Int = 0, usePrefix: Boolean = false) = format(digit, null, usePrefix)
fun Double?.format(maxDigit: Int = 0, minDigit: Int? = null, usePrefix: Boolean = false): String {
    val number = DecimalFormat.getInstance() as DecimalFormat
    val format = DecimalFormatSymbols()
    format.decimalSeparator = ','
    format.groupingSeparator = '.'
    number.decimalFormatSymbols = format
    number.maximumFractionDigits = maxDigit
    minDigit?.let { number.minimumFractionDigits = it }
    if (usePrefix) {
        number.positivePrefix = if (this.orZero() == 0.0) "" else "+"
        number.negativePrefix = "-"
    }
    return number.format(this ?: 0.0)
}

fun Double?.formatUS(maxDigit: Int = 0, minDigit: Int? = null, usePrefix: Boolean = false): String {
    val number = DecimalFormat.getInstance() as DecimalFormat
    val format = DecimalFormatSymbols()
    format.decimalSeparator = '.'
    format.groupingSeparator = ','
    number.decimalFormatSymbols = format
    number.maximumFractionDigits = maxDigit
    minDigit?.let { number.minimumFractionDigits = it }
    if (usePrefix) {
        number.positivePrefix = if (this.orZero() == 0.0) "" else "+"
        number.negativePrefix = "-"
    }
    return number.format(this ?: 0.0)
}

fun Int?.format(digit: Int = 0): String {
    val number = DecimalFormat.getInstance() as DecimalFormat
    val format = DecimalFormatSymbols()
    format.decimalSeparator = ','
    format.groupingSeparator = '.'
    number.decimalFormatSymbols = format
    number.maximumFractionDigits = digit
    return number.format(this ?: 0)
}

fun Long?.format(digit: Int = 0): String {
    val number = DecimalFormat.getInstance() as DecimalFormat
    val format = DecimalFormatSymbols()
    format.decimalSeparator = ','
    format.groupingSeparator = '.'
    number.decimalFormatSymbols = format
    number.maximumFractionDigits = digit
    return number.format(this ?: 0)
}

fun Long?.formatIndoMBT(precision: Int = 0, isRupiah: Boolean = false) =
    this?.toDouble()
        .formatBigNumber(precision, arrayOf("", null, "juta", "miliar", "triliun"), isRupiah)

fun Long?.formatKMBT(precision: Int = 0, isRupiah: Boolean = false) =
    this?.toDouble().formatBigNumber(precision, arrayOf("", "K", "M", "B", "T"), isRupiah)

fun Long?.formatMBT(precision: Int = 0, isRupiah: Boolean = false) =
    this?.toDouble().formatBigNumber(precision, arrayOf("", null, "M", "B", "T"), isRupiah)

fun Long?.formatMB(precision: Int = 0, isRupiah: Boolean = false) =
    this?.toDouble().formatBigNumber(precision, arrayOf("", null, "M", "B"), isRupiah)

fun Long?.formatBT(precision: Int = 0, isRupiah: Boolean = false) =
    this?.toDouble().formatBigNumber(precision, arrayOf("", null, null, "B", "T"), isRupiah)


fun Double?.formatIndoMBT(precision: Int = 0, isRupiah: Boolean = false) =
    formatBigNumber(precision, arrayOf("", null, "juta", "miliar", "triliun"), isRupiah)

fun Double?.formatKMBT(precision: Int = 0, isRupiah: Boolean = false) =
    formatBigNumber(precision, arrayOf("", "K", "M", "B", "T"), isRupiah)

fun Double?.formatMBT(precision: Int = 0, isRupiah: Boolean = false) =
    formatBigNumber(precision, arrayOf("", null, "M", "B", "T"), isRupiah)

fun Double?.formatMB(precision: Int = 0, isRupiah: Boolean = false) =
    formatBigNumber(precision, arrayOf("", null, "M", "B"), isRupiah)

fun Double?.formatBT(precision: Int = 0, isRupiah: Boolean = false) =
    formatBigNumber(precision, arrayOf("", null, null, "B", "T"), isRupiah)

fun Double?.formatBigNumber(
    precision: Int = 0,
    abbrev: Array<String?> = arrayOf("", "K", "M", "B", "T"),
    rupiah: Boolean = false
): String {
    if (this == null || this.isNaN()) return (0.0).format(2)
    val realThousands = (floor(log(abs(this), 1000.0))).toInt()
    var availableThousands = maxOf(0, minOf(realThousands, abbrev.size - 1))
    while (abbrev.getOrNull(availableThousands) == null && availableThousands > 0) {
        availableThousands--
    }
    val unit = abbrev.getOrElse(availableThousands) { "" }
    val shortNumber = this / 1000.0.pow(availableThousands)
    val result = if (rupiah) "Rp ${shortNumber.format(precision)}"
    else shortNumber.format(precision)
    return "$result $unit"
}

fun File.encodeBase64(): String {
    return Base64.encodeToString(this.readBytes(), 0)
}

fun Bitmap.encodeBase64(compressFormat: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG): String {
    val baos = ByteArrayOutputStream()
    this.compress(compressFormat, 100, baos)
    val b: ByteArray = baos.toByteArray()
    return Base64.encodeToString(b, Base64.DEFAULT)
}

val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()
val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()


@Deprecated("hard to maintain", ReplaceWith("KTX SpannableStringBuilder()"))
fun String?.colorText(textToColored: String?, targetColor: Int?) =
    if (this.isNullOrEmpty() || textToColored.isNullOrEmpty() || targetColor == null)
        SpannableStringBuilder(this.orEmpty())
    else if (this.length < textToColored.length || this.indexOf(textToColored) == -1)
        SpannableStringBuilder(this.orEmpty())
    else SpannableStringBuilder(this).apply {
        setSpan(
            ForegroundColorSpan(targetColor),
            this.indexOf(textToColored),
            (this.indexOf(textToColored) + textToColored.length),
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }

@Deprecated("hard to maintain", ReplaceWith("KTX SpannableStringBuilder()"))
fun String?.colorText(fromIndex: Int, toIndex: Int, targetColor: Int?) =
    if (this.isNullOrEmpty() || targetColor == null || fromIndex !in this.indices || toIndex !in this.indices || toIndex < fromIndex)
        SpannableStringBuilder(this.orEmpty())
    else SpannableStringBuilder(this).apply {
        setSpan(
            ForegroundColorSpan(targetColor), fromIndex, toIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }

fun String?.colorSeparateText(separate: String?, vararg targetColor: Int) =
    if (this.isNullOrEmpty() || separate.isNullOrEmpty() || targetColor.isEmpty())
        SpannableStringBuilder(this.orEmpty())
    else SpannableStringBuilder(this).apply {
        val first = this.indexOf(separate)
        val second = this.indexOf(separate, first + 1)
        setSpan(
            ForegroundColorSpan(targetColor[0]),
            0,
            first,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        setSpan(
            ForegroundColorSpan(targetColor[1]),
            first + 1,
            second,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        setSpan(
            ForegroundColorSpan(targetColor[2]),
            second + 1,
            this.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }

fun String.getIndicesOf(substring: String): ArrayList<Int> {
    val indices = ArrayList<Int>()
    if (substring.isBlank() || this.length < substring.length) return indices
    for (index in 0..(this.length - substring.length)) {
        val subs = this.substring(index, index + substring.length)
        val match = index >= 0 && subs == substring
        if (match) indices.add(index)
    }
    return indices
}

fun String?.mask(): String {
    if (this.isNullOrEmpty()) return ""
    val masked = StringBuilder()
    val words = this.split(' ')
    words.forEach { word ->
        word.forEachIndexed { index, c ->
            masked.append(
                when {
                    index <= 1 -> c
                    index == word.length - 1 && word.length > 4 -> c
                    else -> '*'
                }
            )
        }
        masked.append(' ')
    }
    return masked.toString()
}

fun String?.maskPhone(): String {
    if (this.isNullOrEmpty()) return ""
    val masked = StringBuilder()
    val words = this.split(' ')
    words.forEach { word ->
        word.forEachIndexed { index, c ->
            masked.append(
                when {
                    index < 5 -> c
                    index > word.length - 4 && word.length > 10 -> c
                    else -> '*'
                }
            )
        }
        masked.append(' ')
    }
    return masked.toString()
}

fun Int?.isNullOrMinus() = this == null || this < 0
fun Int?.isNullOrZero() = this == null || this == 0
fun Int?.isLargerZero() = this != null && this > 0
fun Double?.isNullOrZero() = this == null || this == 0.0

fun String?.parseColor(default: Int = -1): Int {
    if (this.isNullOrEmpty()) return default
    return try {
        Color.parseColor(this)
    } catch (e: Exception) {
        default
    }
}


fun TextView.setDrawable(res: Int, direction: String) {
    this.compoundDrawablePadding = 8.dp
    this.viewTreeObserver
        .addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                ContextCompat.getDrawable(context, res)?.let {
                    it.setBounds(
                        0,
                        0,
                        it.intrinsicWidth * this@setDrawable.measuredHeight / it.intrinsicHeight,
                        this@setDrawable.measuredHeight
                    )
                    when (direction) {
                        "START" -> this@setDrawable.setCompoundDrawables(it, null, null, null)
                        "END" -> this@setDrawable.setCompoundDrawables(null, null, it, null)
                    }
                }

                this@setDrawable.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })
}

fun Rect.rotate(degrees: Int, px: Int = 0, py: Int = 0) {
    val rectF = RectF(this)
    val matrix = Matrix()
    matrix.setRotate(degrees.toFloat(), px.toFloat(), py.toFloat())
    matrix.mapRect(rectF)
    this[rectF.left.toInt(), rectF.top.toInt(), rectF.right.toInt()] = rectF.bottom.toInt()
}

fun Context.startActivity(
    direction: String,
    finish: Boolean = false,
    extra: Bundle? = null,
    flag: Int? = null
) {
    try {
        val uri = getString(R.string.base_scheme) + "://" + direction
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        extra?.let { intent.putExtras(it) }
        flag?.let { intent.flags = it }
        startActivity(intent)
        if (finish && this is AppCompatActivity) finish()
    } catch (e: Exception) {
        Log.e("startActivity", e.message, e.cause)
    }
}

fun AppCompatActivity.generateIntent(direction: String, bundler: ((b: Bundle) -> Unit)? = null) =
    Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.base_scheme) + "://" + direction))
        .apply { bundler?.let { this.putExtras(Bundle().apply { it.invoke(this) }) } }

fun AppCompatActivity.generateIntent(direction: String, bundle: Bundle?) =
    Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.base_scheme) + "://" + direction))
        .apply { bundle?.let { this.putExtras(bundle) } }

fun Fragment.generateIntent(direction: String, bundler: ((b: Bundle) -> Unit)? = null) =
    Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.base_scheme) + "://" + direction))
        .apply { bundler?.let { this.putExtras(Bundle().apply { it.invoke(this) }) } }

fun AppCompatActivity.alert(message: String) {
    alert(message, false)
}

fun AppCompatActivity.alert(title: String, message: String) {
    alert(title, message, false, null, null, null, null)
}

fun AppCompatActivity.alert(message: String, cancelable: Boolean) {
    alert(null, message, cancelable, null, null, null, null)
}

fun AppCompatActivity.alertAndFinish(message: String, @StringRes buttonPositive: Int) =
    alertAndFinish(message, getString(buttonPositive))

fun AppCompatActivity.alertAndFinish(message: String, buttonPositive: String?) {
    alert(null, message, false, null, buttonPositive, null, { finish() })
}

fun AppCompatActivity.alertOrFinish(
    message: String,
    buttonPositive: String?,
    onPositive: (() -> Unit)?
) {
    alert(null, message, false, getString(R.string.BACK), buttonPositive, { finish() }, onPositive)
}

fun AppCompatActivity.alertOrFinish(
    message: String,
    buttonPositive: Int,
    onPositive: (() -> Unit)?
) {
    alertOrFinish(message, getString(buttonPositive), onPositive)
}

fun AppCompatActivity.alertOrQuit(
    message: String,
    buttonPositive: String?,
    onPositive: (() -> Unit)?
) {
    alert(
        null,
        message,
        false,
        getString(R.string.EXIT),
        buttonPositive,
        { finishAffinity() },
        onPositive
    )
}

fun AppCompatActivity.alertOrQuit(
    message: String,
    buttonPositive: Int,
    onPositive: (() -> Unit)?
) {
    alertOrQuit(message, getString(buttonPositive), onPositive)
}

fun Fragment.alert(title: String, message: String) {
    alert(title, message, false, null, null, null, null)
}

fun Fragment.alert(message: String) {
    alert(null, message, false, null, null, null, null)
}

fun Fragment.alert(
    title: String?,
    message: String,
    cancelable: Boolean,
    buttonNegative: String?,
    buttonPositive: String?,
    onNegative: (() -> Unit)?,
    onPositive: (() -> Unit)?
) {
    val builder = AlertDialog.Builder(context ?: return)
    if (!title.isNullOrEmpty()) builder.setTitle(title)
    builder.setMessage(message)
    builder.setCancelable(cancelable)
    if (!buttonNegative.isNullOrEmpty())
        builder.setNegativeButton(buttonNegative) { d, _ ->
            onNegative?.invoke()
            d.dismiss()
        }
    builder.setPositiveButton(buttonPositive ?: getString(R.string.ALERT_POSITIVE_BUTTON))
    { d, _ ->
        onPositive?.invoke()
        d.dismiss()
    }
    builder.create().show()
}

fun Fragment.alert(
    message: String,
    buttonPositive: String?,
    onPositive: (() -> Unit)?
) {
    alert(null, message, true, null, buttonPositive, null, onPositive)
}

fun Fragment.alert(
    message: String,
    buttonNegative: String?,
    buttonPositive: String?,
    onPositive: (() -> Unit)?
) {
    alert(null, message, false, buttonNegative, buttonPositive, null, onPositive)
}

fun AppCompatActivity.alertOrBack(
    message: String,
    @StringRes buttonPositive: Int,
    onPositive: (() -> Unit)?
) {
    alert(
        null,
        message,
        false,
        getString(R.string.BACK),
        getString(buttonPositive),
        { onBackPressed() },
        onPositive
    )
}

fun AppCompatActivity.alertOrBack(
    message: String,
    buttonPositive: String?,
    onPositive: (() -> Unit)?
) {
    alert(
        null,
        message,
        false,
        getString(R.string.BACK),
        buttonPositive,
        { onBackPressed() },
        onPositive
    )
}

fun AppCompatActivity.alert(
    title: String?,
    message: String,
    cancelable: Boolean,
    buttonNegative: String?,
    buttonPositive: String?,
    onNegative: (() -> Unit)?,
    onPositive: (() -> Unit)?
) {
    val builder = AlertDialog.Builder(this)
    if (!title.isNullOrEmpty()) builder.setTitle(title)
    builder.setMessage(message)
    builder.setCancelable(cancelable)
    if (!buttonNegative.isNullOrEmpty())
        builder.setNegativeButton(buttonNegative) { d, _ ->
            onNegative?.invoke()
            d.dismiss()
        }
    builder.setPositiveButton(buttonPositive ?: getString(R.string.ALERT_POSITIVE_BUTTON))
    { d, _ ->
        onPositive?.invoke()
        d.dismiss()
    }
    builder.create().show()
}

fun AppCompatActivity.alert(
    message: String,
    buttonPositive: String?,
    onPositive: (() -> Unit)?
) {
    alert(null, message, true, null, buttonPositive, null, onPositive)
}

fun AppCompatActivity.alert(
    message: String,
    buttonNegative: String?,
    buttonPositive: String?,
    onPositive: (() -> Unit)?
) {
    alert(null, message, false, buttonNegative, buttonPositive, null, onPositive)
}

fun AppCompatActivity.fatalError(
    message: String,
    buttonPositive: String?,
    onPositive: (() -> Unit)?
) {
    alert(null, message, true, null, buttonPositive, null, onPositive)
}

fun Image.toBitmap(): Bitmap {
    val yBuffer = planes[0].buffer // Y
    val vuBuffer = planes[2].buffer // VU

    val ySize = yBuffer.remaining()
    val vuSize = vuBuffer.remaining()

    val nv21 = ByteArray(ySize + vuSize)

    yBuffer.get(nv21, 0, ySize)
    vuBuffer.get(nv21, ySize, vuSize)

    val yuvImage = YuvImage(nv21, ImageFormat.NV21, this.width, this.height, null)
    val out = ByteArrayOutputStream()
    yuvImage.compressToJpeg(Rect(0, 0, yuvImage.width, yuvImage.height), 100, out)
    val imageBytes = out.toByteArray()
    return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

//    if (planes.size == 1) {
//        val buffer = planes[0].buffer
//        buffer.rewind()
//        val bytes = ByteArray(buffer.capacity())
//        buffer.get(bytes)
//        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
//    } else {
//        val yuvBytes = arrayOfNulls<ByteArray>(3)
//        ImageUtils.fillBytes(planes, yuvBytes)
//        val rgbBytes = IntArray(width * height)
//        ImageUtils.convertYUV420ToARGB8888(
//            yuvBytes[0]!!,
//            yuvBytes[1]!!,
//            yuvBytes[2]!!,
//            width,
//            height,
//            planes[0].rowStride,
//            planes[1].rowStride,
//            planes[2].pixelStride,
//            rgbBytes
//        )
//        val rgbBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
//        rgbBitmap.setPixels(rgbBytes, 0, width, 0, 0, width, height)
//        return rgbBitmap
//    }
}

fun Bitmap.scaleTo(newWidth: Int, newHeight: Int): Bitmap? {
    val width = this.width
    val height = this.height
    val scaleWidth = newWidth.toFloat() / width
    val scaleHeight = newHeight.toFloat() / height
    // CREATE A MATRIX FOR THE MANIPULATION
    val matrix = Matrix()
    // RESIZE THE BIT MAP
    matrix.postScale(scaleWidth, scaleHeight)

    // "RECREATE" THE NEW BITMAP
    val resizedBitmap = Bitmap.createBitmap(
        this, 0, 0, width, height, matrix, false
    )
    return resizedBitmap
}

@SuppressLint("SimpleDateFormat")
fun Date.toSimpleString(): String {
    val format = SimpleDateFormat("yyyyMMddHHmmss")
    return format.format(this)
}

@SuppressLint("SimpleDateFormat")
fun Date.toSimpleStringDateOnly(): String {
    val format = SimpleDateFormat("yyyyMMdd")
    return format.format(this)
}

@SuppressLint("SimpleDateFormat")
fun Date.toFullString(): String {
    val format = SimpleDateFormat("HH:mm:ss dd/MM/yyyy")
    return format.format(this)
}

@SuppressLint("SimpleDateFormat")
fun String.toDate(): Date? {
    return try {
        val format = SimpleDateFormat("yyyyMMddHHmmss")
        format.parse(this)
    } catch (e: ParseException) {
        null
    }
}

@SuppressLint("SimpleDateFormat")
fun String.toDateFromOnlyDate(): Date? {
    return try {
        val format = SimpleDateFormat("yyyyMMdd")
        format.parse(this)
    } catch (e: ParseException) {
        null
    }
}

@SuppressLint("SimpleDateFormat")
fun Date.toDatePermission(): String? {
    val format = SimpleDateFormat("dd/MM/yyyy")
    return format.format(this)
}

@SuppressLint("SimpleDateFormat")
fun Date.toDateOnly(withSeparate: String = ""): String? {
    val format = SimpleDateFormat("dd${withSeparate}MM${withSeparate}yyyy")
    return format.format(this)
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Long.getDate(pattern: String = "HH:mm:ss dd/MM/yyyy"): String {
    val calendar = Calendar.getInstance(Locale.ROOT)
    calendar.timeInMillis = this
    return DateFormat.format(pattern, calendar).toString()
}

inline fun <reified T> Gson.fromJson(json: String?): T? =
    try {
        this.fromJson<T>(json, object : TypeToken<T>() {}.type)
    } catch (e: Exception) {
        null
    }