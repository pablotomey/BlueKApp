package cl.konadev.bluekapp.ui.adapter

import android.bluetooth.BluetoothDevice
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cl.konadev.bluekapp.R
import cl.konadev.bluekapp.base.BaseViewHolder
import kotlinx.android.synthetic.main.device_item.view.*

class DeviceAdapter(private val context: Context, private val deviceList: List<BluetoothDevice>): RecyclerView.Adapter<BaseViewHolder<*>>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return DeviceViewHolder(LayoutInflater.from(context).inflate(R.layout.device_item, parent, false))
    }

    override fun getItemCount(): Int {
        return deviceList.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is DeviceViewHolder -> holder.bind(deviceList[position], position)
        }
    }

    inner class DeviceViewHolder(itemView: View): BaseViewHolder<BluetoothDevice>(itemView) {
        override fun bind(item: BluetoothDevice, position: Int) {

            itemView.device_name.text = item.name
            itemView.device_address.text = item.address
        }

    }
}