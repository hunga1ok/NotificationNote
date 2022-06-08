package com.huncorp.myday.notinote.page.main

import com.huncorp.myday.notinote.R
import com.huncorp.myday.notinote.base.BaseAdapter2
import com.huncorp.myday.notinote.databinding.CellNotifyItemBinding
import com.huncorp.myday.notinote.models.NotifyData

class AdaNotifyNote : BaseAdapter2<NotifyData>(R.layout.cell_notify_item) {
    lateinit var binding: CellNotifyItemBinding
    override fun onBindView(holder: BaseViewHolder, item: NotifyData, pos: Int) {
        binding = CellNotifyItemBinding.bind(holder.v)
        binding.tvTitle.text = item.title
        binding.tvDesc.text = item.desc
    }
}