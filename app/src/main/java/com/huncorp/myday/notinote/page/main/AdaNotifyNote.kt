package com.huncorp.myday.notinote.page.main

import com.huncorp.myday.notinote.R
import com.huncorp.myday.notinote.base.BaseAdapter2
import com.huncorp.myday.notinote.databinding.CellNotifyItemBinding
import com.huncorp.myday.notinote.model.Notice

class AdaNotifyNote : BaseAdapter2<Notice>(R.layout.cell_notify_item) {
    private lateinit var binding: CellNotifyItemBinding
    override fun onBindView(holder: BaseViewHolder, item: Notice, pos: Int) {
        binding = CellNotifyItemBinding.bind(holder.v)
        with(binding) {
            tvTitle.text = item.title
            tvDesc.text = item.content
        }
    }
}