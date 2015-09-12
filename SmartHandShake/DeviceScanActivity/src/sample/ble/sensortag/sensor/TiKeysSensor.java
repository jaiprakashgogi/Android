package sample.ble.sensortag.sensor;

import static android.bluetooth.BluetoothGattCharacteristic.FORMAT_UINT8;

import android.bluetooth.BluetoothGattCharacteristic;

import sample.ble.sensortag.ble.BleGattExecutor;

/**
 * Created by steven on 9/3/13.
 */
public class TiKeysSensor extends TiSensor<String> {

    TiKeysSensor() {
        super();
    }

    @Override
    public String getName() {
        return "Simple Keys";
    }

    @Override
    public String getServiceUUID() {
        return "0000ffe0-0000-1000-8000-00805f9b34fb";
    }

    @Override
    public String getDataUUID() {
        return "0000ffe1-0000-1000-8000-00805f9b34fb";
    }

    @Override
    public String getConfigUUID() {
        return null;
    }


    @Override
    public BleGattExecutor.ServiceAction[] enable(boolean enable) {
        return new BleGattExecutor.ServiceAction[] {
                notify(enable)
        };
    }

    @Override
    public String getDataString() {
        final String data = getData();
        return data;
    }

    @Override
    public String parse(BluetoothGattCharacteristic c) {
    /*
     * The key state is encoded into 1 unsigned byte.
     * bit 0 designates the right key.
     * bit 1 designates the left key.
     * bit 2 designates the side key.
     *
     * Weird, in the userguide left and right are opposite.
     */
        return c.getStringValue(3);
    }
}
