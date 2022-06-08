package com.huncorp.myday.notinote.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import com.huncorp.myday.notinote.R
import com.huncorp.myday.notinote.utils.findButton
import com.huncorp.myday.notinote.utils.findTextView
import com.huncorp.myday.notinote.utils.isLargerZero
import de.dlyt.yanndroid.oneui.view.RecyclerView
import org.joda.time.DateTime

abstract class BaseAdapter<I, VH : RecyclerView.ViewHolder>(@LayoutRes var cell: Int) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    protected var items = ArrayList<I?>()
    private val TYPE_ERROR = -1
    private val TYPE_LOAD = 0
    private val TYPE_HEADER = 2
    protected val TYPE_ITEM = 1

    private var msg: String? = null

    private var errorMsg: String? = null
    private var errorButtonText: String? = null
    protected var error = false
    private var onErrorClick: (() -> Unit)? = null

    var cache = false
    private var headerInitialized = false
    open var onClick: ((v: View) -> Unit)? = null
    open var onItemClick: ((item: I, pos: Int) -> Unit)? = null
    protected open var setupHeader: ((v: View) -> Unit)? = null

    var updatedAt: DateTime? = null

    override fun getItemViewType(position: Int): Int {
        return if (items[position] == null) {
            if (error)
                TYPE_ERROR
            else if (withHeader() && position == 0)
                TYPE_HEADER
            else
                TYPE_LOAD
        } else {
            TYPE_ITEM
        }
    }

    fun getAllItems(): ArrayList<I?> = items

    override fun getItemCount(): Int = items.size

    fun isEmpty() = itemCount == 0

    fun getItem(pos: Int): I? {
        return items.getOrNull(pos)
    }

    open fun addItem(item: I?) {
        initHeader()
        items.add(item)
        notifyItemInserted(itemCount - 1)
    }

    open fun addItem(item: I?, pos: Int) {
        if (pos >= items.size) return
        items.add(pos, item)
        notifyItemInserted(pos)
    }

    open fun addItems(items: List<I>) {
        if (items.isNotEmpty()) initHeader()
        val count = itemCount
        this.items.addAll(items)
        notifyItemRangeInserted(count, items.size)
    }

    open fun updateItem(item: I?, position: Int) {
        if (position !in items.indices) return
        items[position] = item
        notifyItemChanged(position)
    }

    fun notifyConfigChanged() {
        notifyItemRangeChanged(0, items.size)
    }

    fun showLoader() {
        hideError()
        for (i in 1..(loadingCount ?: loadingItemCount()))
            addItem(null)
        msg = "Memuat"
    }

    fun hideLoader() {
        removeLast()
        msg = ""
    }

    fun showError() = showError("Error", "", null)

    fun showError(errorMessage: String?) = showError(errorMessage, "", null)

    fun showError(errorMessage: String?, btnText: String?, onErrorClick: (() -> Unit)?) {
        hideError()
        this.errorMsg = errorMessage
        this.onErrorClick = onErrorClick
        this.errorButtonText = btnText
        error = true
        addItem(null)
    }

    fun showError(error: Throwable?, btnText: String?, onErrorClick: (() -> Unit)?) {
        hideError()
        this.errorMsg = error?.localizedMessage ?: "Ada yang salah, cobalah beberapa saat lagi."
        this.onErrorClick = onErrorClick
        this.errorButtonText = btnText
        this.error = true
        addItem(null)
    }

    fun hideError() {
        if (!error) return
        this.errorMsg = ""
        error = false
//        if (items[itemCount - 1] == null)
        removeLast()
    }

    fun showHeader(@LayoutRes headerRes: Int, setupHeader: ((v: View) -> Unit)? = null) {
        this.headerLayout = headerRes
        this.setupHeader = setupHeader
    }

    fun positionExist(pos: Int) = pos >= 0 && pos < items.size

    open fun remove(position: Int) {
        if (items.isEmpty() || !positionExist(position)) return
        items.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount)
    }

    fun removeLast() {
        var firstIndex = 0
        if (withHeader() && headerInitialized) firstIndex = 1
        for (i in 1..(loadingCount ?: loadingItemCount()))
            if (itemCount > firstIndex) {
                items.removeAt(itemCount - 1)
                notifyItemRemoved(itemCount)
            }
    }

    open fun clear() {
        items.clear()
        if (headerInitialized) headerInitialized = false
        notifyDataSetChanged()
    }

    private fun initHeader() {
        if (withHeader() && !headerInitialized) {
            this.items.add(0, null)
            headerInitialized = true
            notifyItemInserted(0)
        }
    }

    open fun headerLayout(): Int? = null

    protected open var headerLayout: Int? = null

    open fun loadingLayout(): Int? = null

    fun errorLayout(): Int = 0

    open var loadingLayout: Int? = null

    open fun loadingItemCount(): Int = 1
    var loadingCount: Int? = null

    fun getColor(context: Context, @ColorRes colorRes: Int): Int {
        return ContextCompat.getColor(context, colorRes)
    }

    protected open fun getItemLayout(viewType: Int) = when (viewType) {
        TYPE_ERROR -> if (errorLayout() > 0) errorLayout() else R.layout.simple_item_error
        TYPE_LOAD -> loadingLayout() ?: loadingLayout ?: R.layout.simple_item_loading
        TYPE_HEADER -> headerLayout() ?: headerLayout ?: cell
        else -> cell
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val resLayout = getItemLayout(viewType)
        val v = inflater.inflate(resLayout, parent, false)
        return when (viewType) {
            TYPE_ERROR -> ErrorViewHolder(v, errorLayout() > 0)
            TYPE_LOAD -> LoaderViewHolder(
                v, loadingLayout().isLargerZero() || loadingLayout.isLargerZero()
            )
            TYPE_HEADER -> HeaderViewHolder(v, setupHeader)
            else -> createView(v, viewType)
        }
    }

    abstract fun createView(view: View, viewType: Int): VH

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is LoaderViewHolder) {
            holder.bind(msg)
        } else if (holder is ErrorViewHolder) {
            holder.bind(errorMsg, errorButtonText, onErrorClick)
        } else if (withHeader() && holder is HeaderViewHolder) {
            holder.bind()
        } else {
            val item = items[position]
            @Suppress("UNCHECKED_CAST")
            if (item != null) onBindView(holder as VH, item, position)
        }
    }

    private fun withHeader(): Boolean {
        return headerLayout.isLargerZero() || headerLayout().isLargerZero()
    }

    override fun getItemId(position: Int): Long {
        return items[position].hashCode().toLong()
    }

    abstract fun onBindView(holder: VH, item: I, pos: Int)

    class LoaderViewHolder(private var v: View, private var custom: Boolean) :
        RecyclerView.ViewHolder(v) {
        fun bind(msg: String?) {
            if (custom) return
            v.findTextView(R.id.tvInfo)?.visibility =
                if (msg.isNullOrEmpty()) View.GONE else View.VISIBLE
            v.findTextView(R.id.tvInfo)?.text = msg
        }
    }

    class ErrorViewHolder(var v: View, private var custom: Boolean) : RecyclerView.ViewHolder(v) {
        fun bind(msg: String?, btnText: String?, onErrorClick: (() -> Unit)?) {
            if (custom) return
            v.findTextView(R.id.tvInfoError)?.visibility =
                if (msg.isNullOrEmpty()) View.GONE else View.VISIBLE
            v.findTextView(R.id.tvInfoError)?.text = msg
            v.findButton(R.id.btnAction)?.text = btnText
            if (onErrorClick != null) {
                v.findButton(R.id.btnAction)?.visibility = View.VISIBLE
                v.findButton(R.id.btnAction)?.setOnClickListener { onErrorClick() }
            } else {
                v.findButton(R.id.btnAction)?.visibility = View.GONE
            }
        }
    }

    class HeaderViewHolder(private var v: View, private var custom: ((View) -> Unit)?) :
        RecyclerView.ViewHolder(v) {
        fun bind() {
            custom?.invoke(v)
        }
    }


    /***
     * DRAG IMPLEMENTATION
     */

    fun showLoadUpdatedData() {
        if (items.isNullOrEmpty() || items[0] == null) {
            clear()
            showLoader()
        } else {
            cache = true
            notifyConfigChanged()
        }
    }

    fun addUpdatedData(items: List<I>) {
        if (this.items != items) {
            clear()
            cache = false
            addItems(items)
        }
    }

    fun showCacheOrError(msg: String?, btnText: String?, onErrorClick: (() -> Unit)?) {
        if (items.isNullOrEmpty() || items[0] == null) {
            removeLast()
            return showError(msg, btnText, onErrorClick)
        }
        hideError()
        cache = true
        notifyConfigChanged()
    }
}