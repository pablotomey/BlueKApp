package cl.konadev.bluekapp.ui.fragment

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import cl.konadev.bluekapp.R
import cl.konadev.bluekapp.ui.adapter.DeviceAdapter
import kotlinx.android.synthetic.main.fragment_device_list.*
import timber.log.Timber

const val REQUEST_ENABLED_BT = 1

class DeviceListFragment : Fragment() {

    private var bluetoothAdapter: BluetoothAdapter? = null
    lateinit var devices: Set<BluetoothDevice>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_device_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // obtenemos el bluetooth adapter
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        // si el dispositivo es compatible con bluetooth
        if (bluetoothAdapter == null) {
            Timber.d("DISPOSITIVO NO COMPATIBLE")
            return
        }
        // activar bluetooth si no esta habilitado
        if (!bluetoothAdapter!!.isEnabled) {
            val bluetoothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(bluetoothIntent, REQUEST_ENABLED_BT)
        }

        pairDevices()

    }

    private fun pairDevices() {
        // recupera la lista de dispositivos previamente emparejados
        devices = bluetoothAdapter!!.bondedDevices
        val deviceList = arrayListOf<BluetoothDevice>()
        if (!devices.isNullOrEmpty()) {
            for (device: BluetoothDevice in devices) {
                deviceList.add(device)
            }
        } else Timber.e("NO HAY DISPOSITIVOS EMPAREJADOS EN ESTE EQUIPO")

        rv_devices.layoutManager = LinearLayoutManager(requireContext())
        rv_devices.adapter = DeviceAdapter(requireContext(), deviceList)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_ENABLED_BT)
            if (resultCode == Activity.RESULT_OK)
                if (bluetoothAdapter!!.isEnabled) Timber.d("BLUETOOTH IS ENABLED")
                else Timber.d("BLUETOOTH IS DISABLED")
            else if (requestCode == Activity.RESULT_CANCELED) Timber.d("BLUETOOTH IS CANCELED")
    }
}