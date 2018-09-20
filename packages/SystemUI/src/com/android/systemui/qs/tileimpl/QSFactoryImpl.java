/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use mHost file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package com.android.systemui.qs.tileimpl;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.android.systemui.dagger.SysUISingleton;
import com.android.systemui.plugins.qs.QSFactory;
import com.android.systemui.plugins.qs.QSIconView;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.qs.QSTileView;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.external.CustomTile;
import com.android.systemui.qs.tiles.AODTile;
import com.android.systemui.qs.tiles.AirplaneModeTile;
import com.android.systemui.qs.tiles.AlarmTile;
import com.android.systemui.qs.tiles.AmbientDisplayTile;
import com.android.systemui.qs.tiles.BatterySaverTile;
import com.android.systemui.qs.tiles.BluetoothTile;
import com.android.systemui.qs.tiles.CaffeineTile;
import com.android.systemui.qs.tiles.CameraToggleTile;
import com.android.systemui.qs.tiles.CastTile;
import com.android.systemui.qs.tiles.CellularTile;
import com.android.systemui.qs.tiles.ColorInversionTile;
import com.android.systemui.qs.tiles.CompassTile;
import com.android.systemui.qs.tiles.DataSaverTile;
import com.android.systemui.qs.tiles.DataSwitchTile;
import com.android.systemui.qs.tiles.DeviceControlsTile;
import com.android.systemui.qs.tiles.DndTile;
import com.android.systemui.qs.tiles.EvolverTile;
import com.android.systemui.qs.tiles.FPSInfoTile;
import com.android.systemui.qs.tiles.FlashlightTile;
import com.android.systemui.qs.tiles.GamingModeTile;
import com.android.systemui.qs.tiles.HeadsUpTile;
import com.android.systemui.qs.tiles.HotspotTile;
import com.android.systemui.qs.tiles.InternetTile;
import com.android.systemui.qs.tiles.LocationTile;
import com.android.systemui.qs.tiles.MicrophoneToggleTile;
import com.android.systemui.qs.tiles.NfcTile;
import com.android.systemui.qs.tiles.NightDisplayTile;
import com.android.systemui.qs.tiles.OnTheGoTile;
import com.android.systemui.qs.tiles.PowerShareTile;
import com.android.systemui.qs.tiles.QuickAccessWalletTile;
import com.android.systemui.qs.tiles.RebootTile;
import com.android.systemui.qs.tiles.ReduceBrightColorsTile;
import com.android.systemui.qs.tiles.RotationLockTile;
import com.android.systemui.qs.tiles.ScreenRecordTile;
import com.android.systemui.qs.tiles.ScreenshotTile;
import com.android.systemui.qs.tiles.SoundTile;
import com.android.systemui.qs.tiles.SyncTile;
import com.android.systemui.qs.tiles.UiModeNightTile;
import com.android.systemui.qs.tiles.UsbTetherTile;
import com.android.systemui.qs.tiles.UserTile;
import com.android.systemui.qs.tiles.VolumeTile;
import com.android.systemui.qs.tiles.WifiTile;
import com.android.systemui.qs.tiles.WorkModeTile;
import com.android.systemui.util.leak.GarbageMonitor;

import javax.inject.Inject;
import javax.inject.Provider;

import dagger.Lazy;

@SysUISingleton
public class QSFactoryImpl implements QSFactory {

    private static final String TAG = "QSFactory";

    private final Provider<WifiTile> mWifiTileProvider;
    private final Provider<InternetTile> mInternetTileProvider;
    private final Provider<BluetoothTile> mBluetoothTileProvider;
    private final Provider<CellularTile> mCellularTileProvider;
    private final Provider<DndTile> mDndTileProvider;
    private final Provider<ColorInversionTile> mColorInversionTileProvider;
    private final Provider<AirplaneModeTile> mAirplaneModeTileProvider;
    private final Provider<WorkModeTile> mWorkModeTileProvider;
    private final Provider<RotationLockTile> mRotationLockTileProvider;
    private final Provider<FlashlightTile> mFlashlightTileProvider;
    private final Provider<LocationTile> mLocationTileProvider;
    private final Provider<CastTile> mCastTileProvider;
    private final Provider<HotspotTile> mHotspotTileProvider;
    private final Provider<UserTile> mUserTileProvider;
    private final Provider<BatterySaverTile> mBatterySaverTileProvider;
    private final Provider<DataSaverTile> mDataSaverTileProvider;
    private final Provider<NightDisplayTile> mNightDisplayTileProvider;
    private final Provider<NfcTile> mNfcTileProvider;
    private final Provider<GarbageMonitor.MemoryTile> mMemoryTileProvider;
    private final Provider<UiModeNightTile> mUiModeNightTileProvider;
    private final Provider<ScreenRecordTile> mScreenRecordTileProvider;
    private final Provider<ReduceBrightColorsTile> mReduceBrightColorsTileProvider;
    private final Provider<CameraToggleTile> mCameraToggleTileProvider;
    private final Provider<MicrophoneToggleTile> mMicrophoneToggleTileProvider;
    private final Provider<DeviceControlsTile> mDeviceControlsTileProvider;
    private final Provider<AlarmTile> mAlarmTileProvider;
    private final Provider<QuickAccessWalletTile> mQuickAccessWalletTileProvider;
    private final Provider<PowerShareTile> mPowerShareTileProvider;
    private final Provider<CaffeineTile> mCaffeineTileProvider;
    private final Provider<AmbientDisplayTile> mAmbientDisplayTileProvider;
    private final Provider<UsbTetherTile> mUsbTetherTileProvider;
    private final Provider<SyncTile> mSyncTileProvider;
    private final Provider<SoundTile> mSoundTileProvider;
    private final Provider<ScreenshotTile> mScreenshotTileProvider;
    private final Provider<HeadsUpTile> mHeadsUpTileProvider;
    private final Provider<GamingModeTile> mGamingModeTileProvider;
    private final Provider<RebootTile> mRebootTileProvider;
    private final Provider<OnTheGoTile> mOnTheGoTileProvider;
    private final Provider<EvolverTile> mEvolverTileProvider;
    private final Provider<VolumeTile> mVolumeTileProvider;
    private final Provider<AODTile> mAODTileProvider;
    private final Provider<DataSwitchTile> mDataSwitchTileProvider;
    private final Provider<FPSInfoTile> mFPSInfoTileProvider;
    private final Provider<CompassTile> mCompassTileProvider;

    private final Lazy<QSHost> mQsHostLazy;
    private final Provider<CustomTile.Builder> mCustomTileBuilderProvider;

    @Inject
    public QSFactoryImpl(
            Lazy<QSHost> qsHostLazy,
            Provider<CustomTile.Builder> customTileBuilderProvider,
            Provider<WifiTile> wifiTileProvider,
            Provider<InternetTile> internetTileProvider,
            Provider<BluetoothTile> bluetoothTileProvider,
            Provider<CellularTile> cellularTileProvider,
            Provider<DndTile> dndTileProvider,
            Provider<ColorInversionTile> colorInversionTileProvider,
            Provider<AirplaneModeTile> airplaneModeTileProvider,
            Provider<WorkModeTile> workModeTileProvider,
            Provider<RotationLockTile> rotationLockTileProvider,
            Provider<FlashlightTile> flashlightTileProvider,
            Provider<LocationTile> locationTileProvider,
            Provider<CastTile> castTileProvider,
            Provider<HotspotTile> hotspotTileProvider,
            Provider<UserTile> userTileProvider,
            Provider<BatterySaverTile> batterySaverTileProvider,
            Provider<DataSaverTile> dataSaverTileProvider,
            Provider<NightDisplayTile> nightDisplayTileProvider,
            Provider<NfcTile> nfcTileProvider,
            Provider<GarbageMonitor.MemoryTile> memoryTileProvider,
            Provider<UiModeNightTile> uiModeNightTileProvider,
            Provider<ScreenRecordTile> screenRecordTileProvider,
            Provider<ReduceBrightColorsTile> reduceBrightColorsTileProvider,
            Provider<CameraToggleTile> cameraToggleTileProvider,
            Provider<MicrophoneToggleTile> microphoneToggleTileProvider,
            Provider<DeviceControlsTile> deviceControlsTileProvider,
            Provider<AlarmTile> alarmTileProvider,
            Provider<QuickAccessWalletTile> quickAccessWalletTileProvider,
            Provider<PowerShareTile> powerShareTileProvider,
            Provider<CaffeineTile> caffeineTileProvider,
            Provider<AmbientDisplayTile> ambientDisplayTileProvider,
            Provider<UsbTetherTile> usbTetherTileProvider,
            Provider<SyncTile> syncTileProvider,
            Provider<SoundTile> soundTileProvider,
            Provider<ScreenshotTile> screenshotTileProvider,
            Provider<HeadsUpTile> headsUpTileProvider,
            Provider<GamingModeTile> gamingModeTileProvider,
            Provider<RebootTile> rebootTileProvider,
            Provider<OnTheGoTile> onTheGoTileProvider,
            Provider<EvolverTile> evolverTileProvider,
            Provider<VolumeTile> volumeTileProvider,
            Provider<AODTile> aodTileProvider,
            Provider<DataSwitchTile> dataSwitchTileProvider,
            Provider<FPSInfoTile> fpsInfoTileProvider,
            Provider<CompassTile> compassTileProvider) {
        mQsHostLazy = qsHostLazy;
        mCustomTileBuilderProvider = customTileBuilderProvider;

        mWifiTileProvider = wifiTileProvider;
        mInternetTileProvider = internetTileProvider;
        mBluetoothTileProvider = bluetoothTileProvider;
        mCellularTileProvider = cellularTileProvider;
        mDndTileProvider = dndTileProvider;
        mColorInversionTileProvider = colorInversionTileProvider;
        mAirplaneModeTileProvider = airplaneModeTileProvider;
        mWorkModeTileProvider = workModeTileProvider;
        mRotationLockTileProvider = rotationLockTileProvider;
        mFlashlightTileProvider = flashlightTileProvider;
        mLocationTileProvider = locationTileProvider;
        mCastTileProvider = castTileProvider;
        mHotspotTileProvider = hotspotTileProvider;
        mUserTileProvider = userTileProvider;
        mBatterySaverTileProvider = batterySaverTileProvider;
        mDataSaverTileProvider = dataSaverTileProvider;
        mNightDisplayTileProvider = nightDisplayTileProvider;
        mNfcTileProvider = nfcTileProvider;
        mMemoryTileProvider = memoryTileProvider;
        mUiModeNightTileProvider = uiModeNightTileProvider;
        mScreenRecordTileProvider = screenRecordTileProvider;
        mReduceBrightColorsTileProvider = reduceBrightColorsTileProvider;
        mCameraToggleTileProvider = cameraToggleTileProvider;
        mMicrophoneToggleTileProvider = microphoneToggleTileProvider;
        mDeviceControlsTileProvider = deviceControlsTileProvider;
        mAlarmTileProvider = alarmTileProvider;
        mQuickAccessWalletTileProvider = quickAccessWalletTileProvider;
        mPowerShareTileProvider = powerShareTileProvider;
        mCaffeineTileProvider = caffeineTileProvider;
        mAmbientDisplayTileProvider = ambientDisplayTileProvider;
        mUsbTetherTileProvider = usbTetherTileProvider;
        mSyncTileProvider = syncTileProvider;
        mSoundTileProvider = soundTileProvider;
        mScreenshotTileProvider = screenshotTileProvider;
        mHeadsUpTileProvider = headsUpTileProvider;
        mGamingModeTileProvider = gamingModeTileProvider;
        mRebootTileProvider = rebootTileProvider;
        mOnTheGoTileProvider = onTheGoTileProvider;
        mEvolverTileProvider = evolverTileProvider;
        mVolumeTileProvider = volumeTileProvider;
        mAODTileProvider = aodTileProvider;
        mDataSwitchTileProvider = dataSwitchTileProvider;
        mFPSInfoTileProvider = fpsInfoTileProvider;
        mCompassTileProvider = compassTileProvider;
    }

    public QSTile createTile(String tileSpec) {
        QSTileImpl tile = createTileInternal(tileSpec);
        if (tile != null) {
            tile.initialize();
            tile.postStale(); // Tile was just created, must be stale.
        }
        return tile;
    }

    private QSTileImpl createTileInternal(String tileSpec) {
        // Stock tiles.
        switch (tileSpec) {
            case "wifi":
                return mWifiTileProvider.get();
            case "internet":
                return mInternetTileProvider.get();
            case "bt":
                return mBluetoothTileProvider.get();
            case "cell":
                return mCellularTileProvider.get();
            case "dnd":
                return mDndTileProvider.get();
            case "inversion":
                return mColorInversionTileProvider.get();
            case "airplane":
                return mAirplaneModeTileProvider.get();
            case "work":
                return mWorkModeTileProvider.get();
            case "rotation":
                return mRotationLockTileProvider.get();
            case "flashlight":
                return mFlashlightTileProvider.get();
            case "location":
                return mLocationTileProvider.get();
            case "cast":
                return mCastTileProvider.get();
            case "hotspot":
                return mHotspotTileProvider.get();
            case "user":
                return mUserTileProvider.get();
            case "battery":
                return mBatterySaverTileProvider.get();
            case "saver":
                return mDataSaverTileProvider.get();
            case "night":
                return mNightDisplayTileProvider.get();
            case "nfc":
                return mNfcTileProvider.get();
            case "dark":
                return mUiModeNightTileProvider.get();
            case "screenrecord":
                return mScreenRecordTileProvider.get();
            case "reduce_brightness":
                return mReduceBrightColorsTileProvider.get();
            case "cameratoggle":
                return mCameraToggleTileProvider.get();
            case "mictoggle":
                return mMicrophoneToggleTileProvider.get();
            case "controls":
                return mDeviceControlsTileProvider.get();
            case "alarm":
                return mAlarmTileProvider.get();
            case "wallet":
                return mQuickAccessWalletTileProvider.get();
            // Additional tiles.
            case "powershare":
                return mPowerShareTileProvider.get();
            case "caffeine":
                return mCaffeineTileProvider.get();
            case "ambient_display":
                return mAmbientDisplayTileProvider.get();
            case "usb_tether":
                return mUsbTetherTileProvider.get();
            case "sync":
                return mSyncTileProvider.get();
            case "sound":
                return mSoundTileProvider.get();
            case "screenshot":
                return mScreenshotTileProvider.get();
            case "heads_up":
                return mHeadsUpTileProvider.get();
            case "gamingmode":
                return mGamingModeTileProvider.get();
            case "reboot":
                return mRebootTileProvider.get();
            case "onthego":
                return mOnTheGoTileProvider.get();
            case "evolver":
                return mEvolverTileProvider.get();
            case "volume_panel":
                return mVolumeTileProvider.get();
            case "aod":
                return mAODTileProvider.get();
            case "dataswitch":
                return mDataSwitchTileProvider.get();
            case "fpsinfo":
                return mFPSInfoTileProvider.get();
            case "compass":
                return mCompassTileProvider.get();
        }
        // Custom tiles
        if (tileSpec.startsWith(CustomTile.PREFIX)) {
            return CustomTile.create(
                    mCustomTileBuilderProvider.get(), tileSpec, mQsHostLazy.get().getUserContext());
        }

        // Debug tiles.
        if (Build.IS_DEBUGGABLE) {
            if (tileSpec.equals(GarbageMonitor.MemoryTile.TILE_SPEC)) {
                return mMemoryTileProvider.get();
            }
        }

        // Broken tiles.
        Log.w(TAG, "No stock tile spec: " + tileSpec);
        return null;
    }

    @Override
    public QSTileView createTileView(Context context, QSTile tile, boolean collapsedView) {
        QSIconView icon = tile.createTileView(context);
        return new QSTileViewImpl(context, icon, collapsedView);
    }
}
