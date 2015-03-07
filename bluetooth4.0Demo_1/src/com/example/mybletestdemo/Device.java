package com.example.mybletestdemo;

import java.util.List;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.util.Log;

import com.uplus.bluetooth.thirdapi.AbstractDevice;
import com.uplus.bluetooth.thirdapi.Alarm;
import com.uplus.bluetooth.thirdapi.Attribute;
import com.uplus.bluetooth.thirdapi.DeviceModel;
import com.uplus.bluetooth.thirdapi.Manufacturer;

public class Device extends AbstractDevice{

	private BluetoothDevice mBluetoothDevice;
	private BluetoothGatt myBluetoothGatt;
	private BluetoothGattService myGattService;
	private BluetoothGattCharacteristic readGattCharacteristic;
	private BluetoothGattCharacteristic writeGattCharacteristic;
	
	public Device(BluetoothDevice bluetoothDevice){
		mBluetoothDevice = bluetoothDevice;
	}
	
	
//	public BluetoothGatt getMyBluetoothGatt() {
//		return myBluetoothGatt;
//	}
	public void setMyBluetoothGatt(BluetoothGatt myBluetoothGatt) {
		this.myBluetoothGatt = myBluetoothGatt;
	}
	public BluetoothGattService getMyGattService() {
		return myGattService;
	}
	public void setMyGattService(BluetoothGattService myGattService) {
		this.myGattService = myGattService;
	}
	public BluetoothGattCharacteristic getReadGattCharacteristic() {
		return readGattCharacteristic;
	}
	public void setReadGattCharacteristic(
			BluetoothGattCharacteristic readGattCharacteristic) {
		this.readGattCharacteristic = readGattCharacteristic;
	}
	public BluetoothGattCharacteristic getWriteGattCharacteristic() {
		return writeGattCharacteristic;
	}
	public void setWriteGattCharacteristic(
			BluetoothGattCharacteristic writeGattCharacteristic) {
		this.writeGattCharacteristic = writeGattCharacteristic;
	}


	@Override
	public int getState() {
		// TODO Auto-generated method stub
		return mBluetoothDevice.getBondState();
	}


	@Override
	public List<Attribute> getAttributeList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Alarm> getAlarmList() {
		// TODO Auto-generated method stub
		return null;
	}


//	public int write(Attribute attrubite) {
//		// TODO Auto-generated method stub
//		Log.d("star", "setAttribute");
//		if (writeGattCharacteristic!=null &&myBluetoothGatt!=null) {
//			DataConversionUtil dcUtil = DataConversionUtil.getInstance();
//			Log.d("star", "attrubite : "+dcUtil.dataConversion(attrubite));
//			writeGattCharacteristic.setValue(dcUtil.dataConversion(attrubite));
//			myBluetoothGatt.writeCharacteristic(writeGattCharacteristic);
//		} else {
//			return -1;
//		}
//		return 0;
//	}


	public Object getBluetoothDevice() {
		// TODO Auto-generated method stub
		return mBluetoothDevice;
	}


	@Override
	public String getDeviceId() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getMac() {
		// TODO Auto-generated method stub
		return mBluetoothDevice.getAddress();
	}


	@Override
	public String getDeviceType() {
		// TODO Auto-generated method stub
		return mBluetoothDevice.getType()+"";
	}


	@Override
	public Manufacturer getManufacturer() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public DeviceModel getModel() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Attribute getAttribute(String name) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	protected int execOperation(List<Attribute> arg0, String arg1, Object arg2) {
		// TODO Auto-generated method stub
		Log.d("star", "setAttribute");
		if (arg0 !=null && !arg0.isEmpty()) {
			Attribute attrubite = arg0.get(0);
			if (writeGattCharacteristic!=null &&myBluetoothGatt!=null) {
				DataConversionUtil dcUtil = DataConversionUtil.getInstance();
				Log.d("star", "attrubite : "+dcUtil.dataConversion(attrubite));
				writeGattCharacteristic.setValue(dcUtil.dataConversion(attrubite));
				myBluetoothGatt.writeCharacteristic(writeGattCharacteristic);
			} else {
				return -1;
			}
		}
		
		return 0;
	}





}
