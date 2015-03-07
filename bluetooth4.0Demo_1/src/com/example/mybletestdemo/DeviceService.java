package com.example.mybletestdemo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.uplus.bluetooth.sdk.model.ThirdPartyModel;
import com.uplus.bluetooth.thirdapi.AbstractDevice;
import com.uplus.bluetooth.thirdapi.IDeviceService;
import com.uplus.bluetooth.thirdapi.IDeviceServiceCallback;

public class DeviceService implements IDeviceService {
	private BluetoothManager myBluetoothManager;
	private BluetoothAdapter myBluetoothAdapter;
	private BluetoothGatt myBluetoothGatt;
	private BluetoothGattService myGattService;

	private List<Device> bluetoothDeviceList;
	private IDeviceServiceCallback mCallback;
	public Device mDevice;

	// private static final UUID UUID_SERVICE = UUID
	// .fromString(SampleGattAttributes.MYCJ_BLE);
	public final static UUID UUID_HEART_RATE_MEASUREMENT = UUID
			.fromString(SampleGattAttributes.HEART_RATE_MEASUREMENT);
	private static final UUID uuid = UUID
			.fromString(SampleGattAttributes.MYCJ_BLE);
	private static final UUID UUID_READ = UUID
			.fromString(SampleGattAttributes.MYCJ_BLE_READ);
	private static final UUID UUID_WRITE = UUID
			.fromString(SampleGattAttributes.MYCJ_BLE_WRITE);
	private StringBuffer sbValues;
	private byte[] data;
	// private String strData;

	private final String TAG = "star";

	@Override
	public boolean initBluetooth(Context context) {
		// TODO Auto-generated method stub
		Log.d(TAG, "device start");
		myBluetoothManager = (BluetoothManager) context
				.getSystemService(Context.BLUETOOTH_SERVICE);
		myBluetoothAdapter = myBluetoothManager.getAdapter();

		bluetoothDeviceList = new ArrayList<Device>();
		if (myBluetoothAdapter.isEnabled()) {
			return true;
		} else {
			Intent enableIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_ENABLE);
			context.startActivity(enableIntent);
			return false;
		}
	}

	@Override
	public boolean close() {
		// TODO Auto-generated method stub

		disConnect(mDevice);
		return false;
	}

	@Override
	public boolean startScan(int arg0) {
		// TODO Auto-generated method stub
		Log.d(TAG, "device startScan");
		if (bluetoothDeviceList != null) {
			bluetoothDeviceList.clear();
		}
		if (myBluetoothAdapter != null) {
			myBluetoothAdapter.startLeScan(myReLeScanCallback);
			return true;
		}
		return false;
	}

	@Override
	public boolean stopScan() {
		// TODO Auto-generated method stub
		Log.d(TAG, "device stopScan");
		if (myBluetoothAdapter != null) {
			myBluetoothAdapter.stopLeScan(myReLeScanCallback);
			return true;
		}
		return false;
	}

	@Override
	public void connect(AbstractDevice device, Context context) {
		// TODO Auto-generated method stub

		Log.d(TAG, "device connect");
		BluetoothDevice localBluetoothDevice = myBluetoothAdapter
				.getRemoteDevice(device.getMac());
		if (localBluetoothDevice == null)
			return;
		// return false;
		myBluetoothGatt = localBluetoothDevice.connectGatt(context, true,
				myGattCallback);
		mDevice = (Device) device;
		((Device) device).setMyBluetoothGatt(myBluetoothGatt);
	}

	@Override
	public void disConnect(AbstractDevice device) {
		// TODO Auto-generated method stub
		myBluetoothGatt.disconnect();
	}


	@Override
	public void registerCallback(IDeviceServiceCallback callback) {
		// TODO Auto-generated method stub
		mCallback = callback;
	}

	private BluetoothAdapter.LeScanCallback myReLeScanCallback = new BluetoothAdapter.LeScanCallback() {

		@Override
		public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
			// TODO Auto-generated method stub
			Log.d(TAG, "device myReLeScanCallback");
			Device idevice = new Device(device);
			bluetoothDeviceList.add(idevice);
			mCallback.onDeviceFound(idevice);
		}
	};
	private BluetoothGattCallback myGattCallback = new BluetoothGattCallback() {

		@Override
		public void onConnectionStateChange(BluetoothGatt gatt, int status,
				int newState) {
			// TODO Auto-generated method stub
			super.onConnectionStateChange(gatt, status, newState);

			if (newState == BluetoothProfile.STATE_CONNECTED) {
				Log.d(TAG, "连接上GATT！");
				mCallback.onAttributeChanged(mDevice, null);
				myBluetoothGatt.getServices();
				myBluetoothGatt.discoverServices();
				// sbValues.insert(0, "connected...\n");
				Log.d(TAG, "device connected");
				if (myBluetoothGatt != null) {
					myGattService = myBluetoothGatt.getService(uuid);
				}
				if (myGattService != null) {
					String aaa = myGattService.getUuid().toString();
					Log.d(TAG, "获得BLE GATT Services 成功 : " + aaa);
					mDevice.setReadGattCharacteristic(myGattService
							.getCharacteristic(UUID_READ));

					mDevice.setWriteGattCharacteristic(myGattService
							.getCharacteristic(UUID_WRITE));
				}
				// BluetoothGattCharacteristic bc =
				// mDevice.getReadGattCharacteristic();
			} else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
				mCallback.onAttributeChanged(mDevice, null);
			}
		}

		@Override
		public void onServicesDiscovered(BluetoothGatt gatt, int status) {
			// TODO Auto-generated method stub
			super.onServicesDiscovered(gatt, status);
			if (myBluetoothGatt != null) {
				myGattService = myBluetoothGatt.getService(uuid);
			}
			if (myGattService != null) {
				String aaa = myGattService.getUuid().toString();
				mDevice.setReadGattCharacteristic(myGattService
						.getCharacteristic(UUID_READ));

				mDevice.setWriteGattCharacteristic(myGattService
						.getCharacteristic(UUID_WRITE));
			}
			if (mDevice.getReadGattCharacteristic() != null
					&& mDevice.getWriteGattCharacteristic() != null) {

				// mBluetoothGatt.setCharacteristicNotification(
				// mGattCharacteristicWrite, true);
				setCharacteristicNotification(
						mDevice.getReadGattCharacteristic(), true);

				boolean test = myBluetoothGatt.readCharacteristic(mDevice
						.getReadGattCharacteristic());
			}

		}

		@Override
		public void onCharacteristicRead(BluetoothGatt gatt,
				BluetoothGattCharacteristic characteristic, int status) {
			// TODO Auto-generated method stub
			super.onCharacteristicRead(gatt, characteristic, status);
			// DeviceAttribute d = new DeviceAttribute(status+"");
			// mCallback.onDeviceAttributeChanged(mDevice, d);
		}

		@Override
		public void onCharacteristicWrite(BluetoothGatt gatt,
				BluetoothGattCharacteristic characteristic, int status) {
			// TODO Auto-generated method stub
			super.onCharacteristicWrite(gatt, characteristic, status);
		}

		@Override
		public void onDescriptorRead(BluetoothGatt gatt,
				BluetoothGattDescriptor descriptor, int status) {
			// TODO Auto-generated method stub
			super.onDescriptorRead(gatt, descriptor, status);
		}

		@Override
		public void onCharacteristicChanged(BluetoothGatt gatt,
				BluetoothGattCharacteristic characteristic) {
			// TODO Auto-generated method stub
			super.onCharacteristicChanged(gatt, characteristic);
			data = characteristic.getValue();
			if (data != null && data.length > 0) {
				// strData = DataUtil.getStringByBytes(data) + "\n";

				// mCallback.onDeviceAttributeChanged(device, prevAttribute);
			}
		}

		@Override
		public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
			// TODO Auto-generated method stub
			super.onReadRemoteRssi(gatt, rssi, status);
		}
	};

	public void setCharacteristicNotification(
			BluetoothGattCharacteristic characteristic, boolean enabled) {
		if (myBluetoothAdapter == null || myBluetoothGatt == null) {
			return;
		}
		myBluetoothGatt.setCharacteristicNotification(characteristic, enabled);

		// This is specific to Heart Rate Measurement.
		if (UUID_HEART_RATE_MEASUREMENT.equals(characteristic.getUuid())) {
			BluetoothGattDescriptor descriptor = characteristic
					.getDescriptor(UUID
							.fromString(SampleGattAttributes.CLIENT_CHARACTERISTIC_CONFIG));
			descriptor
					.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
			myBluetoothGatt.writeDescriptor(descriptor);
		} else if (UUID_READ.equals(characteristic.getUuid())) {
			BluetoothGattDescriptor descriptor = characteristic
					.getDescriptor(UUID
							.fromString(SampleGattAttributes.CLIENT_CHARACTERISTIC_CONFIG));
			descriptor
					.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
			myBluetoothGatt.writeDescriptor(descriptor);
		} else if (UUID_WRITE.equals(characteristic.getUuid())) {
			BluetoothGattDescriptor descriptor = characteristic
					.getDescriptor(UUID
							.fromString(SampleGattAttributes.CLIENT_CHARACTERISTIC_CONFIG));
			descriptor
					.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
			myBluetoothGatt.writeDescriptor(descriptor);
		}
	}



//	@Override
//	public void connect(IDevice device, Context context) {
//		// TODO Auto-generated method stub
//		
//	}


	@Override
	public void unRegisterCallback() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getAPIVersion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getBluetoothModel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setThirdPartyInfo(ThirdPartyModel model) {
		// TODO Auto-generated method stub
		
	}


}
