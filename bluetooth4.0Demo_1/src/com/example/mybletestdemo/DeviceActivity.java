package com.example.mybletestdemo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.uplus.bluetooth.thirdapi.AbstractDevice;
import com.uplus.bluetooth.thirdapi.Alarm;
import com.uplus.bluetooth.thirdapi.Attribute;
import com.uplus.bluetooth.thirdapi.IDeviceServiceCallback;

public class DeviceActivity extends Activity {

	private String myBluetoothDeviceAddress;

	private static final UUID UUID_SERVICE = UUID
			.fromString(SampleGattAttributes.MYCJ_BLE);
	public final static UUID UUID_HEART_RATE_MEASUREMENT = UUID
			.fromString(SampleGattAttributes.HEART_RATE_MEASUREMENT);
	private static final UUID uuid = UUID
			.fromString(SampleGattAttributes.MYCJ_BLE);
	private static final UUID UUID_READ = UUID
			.fromString(SampleGattAttributes.MYCJ_BLE_READ);
	private static final UUID UUID_WRITE = UUID
			.fromString(SampleGattAttributes.MYCJ_BLE_WRITE);

	private Handler myHandler;

	private Button sendBtn2, sendBtn3;
	private ListView lv;
	private TextView textViewValues, tv_hint;
	private ProgressBar pbar;

	private StringBuffer sbValues;

	private DeviceService mDeviceService;
	private List<AbstractDevice> devices;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		sendBtn2 = (Button) findViewById(R.id.sendBtn2);
		sendBtn3 = (Button) findViewById(R.id.sendBtn3);

		sbValues = new StringBuffer();
		myHandler = new Handler();
		textViewValues = (TextView) findViewById(R.id.textViewValues);
		textViewValues.setMovementMethod(ScrollingMovementMethod.getInstance());
		mDeviceService = new DeviceService();
		mDeviceService.initBluetooth(this);
		mDeviceService.registerCallback(sdCallback);
		devices = new ArrayList<AbstractDevice>();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

		switch (item.getItemId()) {
		case R.id.menu_main:
			Toast.makeText(getApplicationContext(), "查找蓝牙设备",
					Toast.LENGTH_SHORT).show();
			reScanLeDevice(true);
			actionAlertDialog();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * 开机
	 * 
	 * @param view
	 */
	public void sendBtn3(View view) {
		new Thread(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				Attribute att = new Attribute();
				att.setName("000001");
				mDeviceService.mDevice.execOperation(att);
			}
			
		}.start();
//		Attribute att = new Attribute();
//		att.setName("000001");
//		mDeviceService.mDevice.write(att);
	}

	/**
	 * 关机
	 * 
	 * @param view
	 */
	public void sendBtn2(View view) {
		new Thread(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				Attribute att = new Attribute();
				att.setName("000002");
				mDeviceService.mDevice.execOperation(att);
			}
			
		}.start();
//		Attribute att = new Attribute();
//		att.setName("000002");
//		mDeviceService.mDevice.execOperation(att);
	}

	public void scanLeDevice() {
		myHandler.post(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				devices = new ArrayList<AbstractDevice>();
				mDeviceService.startScan(5);
			}
		});

	}

	public void reScanLeDevice(final boolean enable) {
		devices = new ArrayList<AbstractDevice>();
		mDeviceService.startScan(5);
		myHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				changeText("查找3秒后结束……");
				setView();
				mDeviceService.stopScan();
			}
		}, 3000);
		changeText("开始重新查找蓝牙设备");
	}

	public boolean connect(String paramString, AbstractDevice device) {
		mDeviceService.connect(device, this);
		myBluetoothDeviceAddress = paramString;
		return true;
	}

	public void disconnect() {
		mDeviceService.disConnect(mDeviceService.mDevice);
	}

	private void actionAlertDialog() {
		AlertDialog.Builder builder;
		final AlertDialog alertDialog;
		View view = getLayoutInflater().inflate(R.layout.device_list, null);

		lv = (ListView) view.findViewById(R.id.device_list);
		tv_hint = (TextView) view.findViewById(R.id.tv);
		pbar = (ProgressBar) view.findViewById(R.id.pbar);
		builder = new AlertDialog.Builder(DeviceActivity.this);
		builder.setView(view);
//		builder.setPositiveButton("ReTry",
//				new DialogInterface.OnClickListener() {
//					// 重试按钮
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						// TODO Auto-generated method stub
//						reScanLeDevice(true);
//						actionAlertDialog();
//					}
//				});
		builder.setNegativeButton("cancel",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						// 取消查找设备的操作
						changeText("取消查找");
						dialog.dismiss();
					}
				});
		alertDialog = builder.create();
		alertDialog.show();

		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				TextView tv = (TextView) view;
				// Toast.makeText(DeviceActivity.this, "地址：" + tv.getText(),
				// Toast.LENGTH_SHORT).show();
				myBluetoothDeviceAddress = tv.getText().toString().trim();
				alertDialog.cancel();
				// 通知服务去连接
				connect(myBluetoothDeviceAddress, devices.get(position));
			}
		});

	}

	public void setView() {
		if (devices.size() > 0) {
			lv.setVisibility(View.VISIBLE);
			tv_hint.setVisibility(View.GONE);
			pbar.setVisibility(View.GONE);
			List<String> macs = new ArrayList<String>();
			for(AbstractDevice d: devices){
				macs.add(d.getMac());
			}
			ArrayAdapter<String> adapter = new ArrayAdapter<>(
					DeviceActivity.this,
					android.R.layout.simple_list_item_single_choice, macs);
			lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
			lv.setAdapter(adapter);

		} else if (devices.size() == 0) {
			lv.setVisibility(View.GONE);
			tv_hint.setVisibility(View.VISIBLE);
			tv_hint.setText("device not found!");
			pbar.setVisibility(View.GONE);
		}

	}

	private void changeText(String text) {
		sbValues.insert(0, text + "\n");
		myHandler.post(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				textViewValues.setText(sbValues);
			}
		});
	}

	public IDeviceServiceCallback sdCallback = new IDeviceServiceCallback() {

		@Override
		public void onDeviceFound(AbstractDevice device) {
			// TODO Auto-generated method stub
			devices.add(device);
		}

		@Override
		public void onAttributeChanged(AbstractDevice device,
				Attribute prevAttribute) {
			// TODO Auto-generated method stub
			changeText("contected");
		}

		@Override
		public void onAlarmReported(AbstractDevice device,
				Alarm curAlarm) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onStateChanged(AbstractDevice device) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onConnectError(AbstractDevice arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}
	};

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mDeviceService.close();
	}
}
