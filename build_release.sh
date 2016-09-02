version=$(awk 'BEGIN{FS="\""}; NR==2 { print $4 }' config.xml)
cordova build --release android
# alternative:
# ionic build --release android

# don't use the defaults, some older devices don't support >SHA1 and >SHA1withRSA
jarsigner -verbose -sigalg SHA1withRSA -digestalg SHA1 -keystore ganzhornfest-release-key.keystore platforms/android/build/outputs/apk/android-release-unsigned.apk ganzhornfest

# we have to always adjust the build-tools version
#/home/sebo/projekte/android-sdk-linux/build-tools/22.0.1/zipalign -fv 4 platforms/android/build/outputs/apk/android-release-unsigned.apk /home/sebo/projekte/ganzhornfest/platforms/android/build/outputs/apk/ganzhornfest.apk
/home/sebo/projekte/android-sdk-linux/build-tools/23.0.3/zipalign -fv 4 platforms/android/build/outputs/apk/android-release-unsigned.apk platforms/android/build/outputs/apk/ganzhornfest.apk

cp platforms/android/build/outputs/apk/ganzhornfest.apk platforms/android/build/outputs/apk/ganzhornfest-v${version}.apk
