version=$(awk 'BEGIN{FS="\""}; NR==2 { print $4 }' config.xml)
cordova build --release android
jarsigner -verbose -sigalg SHA1withRSA -digestalg SHA1 -keystore ganzhornfest-release-key.keystore platforms/android/build/outputs/apk/android-release-unsigned.apk ganzhornfest
/home/sebo/projekte/android-sdk-linux/build-tools/22.0.1/zipalign -fv 4 platforms/android/build/outputs/apk/android-release-unsigned.apk /home/sebo/projekte/ganzhornfest/platforms/android/build/outputs/apk/ganzhornfest.apk
cp /home/sebo/projekte/ganzhornfest/platforms/android/build/outputs/apk/ganzhornfest.apk /home/sebo/projekte/ganzhornfest/platforms/android/build/outputs/apk/ganzhornfest-v${version}.apk
