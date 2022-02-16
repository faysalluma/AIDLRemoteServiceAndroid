package com.example.aidlservice;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;


import java.util.ArrayList;
import java.util.List;

public class AdditionService extends Service {

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	private final IAdd.Stub mBinder = new IAdd.Stub() {
		@Override
		public int addNumbers(int num1, int num2) throws RemoteException {
			return num1+num2;
		}

		@Override
		public List<String> getStringList() throws RemoteException {
			List<String> country = new ArrayList<>();
			country.add("India");
			country.add("Bhutan");
			country.add("Nepal");
			country.add("USA");
			country.add("Canada");
			country.add("China");
			return country;
		}

	};
}