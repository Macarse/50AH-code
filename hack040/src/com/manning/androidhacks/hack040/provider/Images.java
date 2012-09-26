/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.manning.androidhacks.hack040.provider;

import com.manning.androidhacks.hack040.util.ImageWorker.ImageWorkerAdapter;

/**
 * Some simple test data to use for this sample app.
 */
public class Images {

  /**
   * This are PicasaWeb URLs and could potentially change. Ideally the PicasaWeb
   * API should be used to fetch the URLs.
   */
  public final static String[] imageUrls = new String[] {
      "https://lh6.googleusercontent.com/-jZgveEqb6pg/T3R4kXScycI/AAAAAAAAAE0/xQ7CvpfXDzc/s1024/sample_image_01.jpg",
      "https://lh4.googleusercontent.com/-K2FMuOozxU0/T3R4lRAiBTI/AAAAAAAAAE8/a3Eh9JvnnzI/s1024/sample_image_02.jpg",
      "https://lh5.googleusercontent.com/-SCS5C646rxM/T3R4l7QB6xI/AAAAAAAAAFE/xLcuVv3CUyA/s1024/sample_image_03.jpg",
      "https://lh6.googleusercontent.com/-f0NJR6-_Thg/T3R4mNex2wI/AAAAAAAAAFI/45oug4VE8MI/s1024/sample_image_04.jpg",
      "https://lh3.googleusercontent.com/-n-xcJmiI0pg/T3R4mkSchHI/AAAAAAAAAFU/EoiNNb7kk3A/s1024/sample_image_05.jpg",
      "https://lh3.googleusercontent.com/-X43vAudm7f4/T3R4nGSChJI/AAAAAAAAAFk/3bna6D-2EE8/s1024/sample_image_06.jpg",
      "https://lh5.googleusercontent.com/-MpZneqIyjXU/T3R4nuGO1aI/AAAAAAAAAFg/r09OPjLx1ZY/s1024/sample_image_07.jpg",
      "https://lh6.googleusercontent.com/-ql3YNfdClJo/T3XvW9apmFI/AAAAAAAAAL4/_6HFDzbahc4/s1024/sample_image_08.jpg",
      "https://lh5.googleusercontent.com/-Pxa7eqF4cyc/T3R4oasvPEI/AAAAAAAAAF0/-uYDH92h8LA/s1024/sample_image_09.jpg",
      "https://lh4.googleusercontent.com/-Li-rjhFEuaI/T3R4o-VUl4I/AAAAAAAAAF8/5E5XdMnP1oE/s1024/sample_image_10.jpg",
      "https://lh5.googleusercontent.com/-_HU4fImgFhA/T3R4pPVIwWI/AAAAAAAAAGA/0RfK_Vkgth4/s1024/sample_image_11.jpg",
      "https://lh6.googleusercontent.com/-0gnNrVjwa0Y/T3R4peGYJwI/AAAAAAAAAGU/uX_9wvRPM9I/s1024/sample_image_12.jpg",
      "https://lh3.googleusercontent.com/-HBxuzALS_Zs/T3R4qERykaI/AAAAAAAAAGQ/_qQ16FaZ1q0/s1024/sample_image_13.jpg",
      "https://lh4.googleusercontent.com/-cKojDrARNjQ/T3R4qfWSGPI/AAAAAAAAAGY/MR5dnbNaPyY/s1024/sample_image_14.jpg",
      "https://lh3.googleusercontent.com/-WujkdYfcyZ8/T3R4qrIMGUI/AAAAAAAAAGk/277LIdgvnjg/s1024/sample_image_15.jpg",
      "https://lh6.googleusercontent.com/-FMHR7Vy3PgI/T3R4rOXlEKI/AAAAAAAAAGs/VeXrDNDBkaw/s1024/sample_image_16.jpg",
      "https://lh4.googleusercontent.com/-mrR0AJyNTH0/T3R4rZs6CuI/AAAAAAAAAG0/UE1wQqCOqLA/s1024/sample_image_17.jpg",
      "https://lh6.googleusercontent.com/-z77w0eh3cow/T3R4rnLn05I/AAAAAAAAAG4/BaerfWoNucU/s1024/sample_image_18.jpg",
      "https://lh5.googleusercontent.com/-aWVwh1OU5Bk/T3R4sAWw0yI/AAAAAAAAAHE/4_KAvJttFwA/s1024/sample_image_19.jpg",
      "https://lh6.googleusercontent.com/-q-js52DMnWQ/T3R4tZhY2sI/AAAAAAAAAHM/A8kjp2Ivdqg/s1024/sample_image_20.jpg",
      "https://lh5.googleusercontent.com/-_jIzvvzXKn4/T3R4t7xpdVI/AAAAAAAAAHU/7QC6eZ10jgs/s1024/sample_image_21.jpg",
      "https://lh3.googleusercontent.com/-lnGi4IMLpwU/T3R4uCMa7vI/AAAAAAAAAHc/1zgzzz6qTpk/s1024/sample_image_22.jpg",
      "https://lh5.googleusercontent.com/-fFCzKjFPsPc/T3R4u0SZPFI/AAAAAAAAAHk/sbgjzrktOK0/s1024/sample_image_23.jpg",
      "https://lh4.googleusercontent.com/-8TqoW5gBE_Y/T3R4vBS3NPI/AAAAAAAAAHs/EZYvpNsaNXk/s1024/sample_image_24.jpg",
      "https://lh6.googleusercontent.com/-gc4eQ3ySdzs/T3R4vafoA7I/AAAAAAAAAH4/yKii5P6tqDE/s1024/sample_image_25.jpg",
      "https://lh5.googleusercontent.com/--NYOPCylU7Q/T3R4vjAiWkI/AAAAAAAAAH8/IPNx5q3ptRA/s1024/sample_image_26.jpg",
      "https://lh6.googleusercontent.com/-9IJM8so4vCI/T3R4vwJO2yI/AAAAAAAAAIE/ljlr-cwuqZM/s1024/sample_image_27.jpg",
      "https://lh4.googleusercontent.com/-KW6QwOHfhBs/T3R4w0RsQiI/AAAAAAAAAIM/uEFLVgHPFCk/s1024/sample_image_28.jpg",
      "https://lh4.googleusercontent.com/-z2557Ec1ctY/T3R4x3QA2hI/AAAAAAAAAIk/9-GzPL1lTWE/s1024/sample_image_29.jpg",
      "https://lh5.googleusercontent.com/-LaKXAn4Kr1c/T3R4yc5b4lI/AAAAAAAAAIY/fMgcOVQfmD0/s1024/sample_image_30.jpg",
      "https://lh4.googleusercontent.com/-F9LRToJoQdo/T3R4yrLtyQI/AAAAAAAAAIg/ri9uUCWuRmo/s1024/sample_image_31.jpg",
      "https://lh4.googleusercontent.com/-6X-xBwP-QpI/T3R4zGVboII/AAAAAAAAAIs/zYH4PjjngY0/s1024/sample_image_32.jpg",
      "https://lh5.googleusercontent.com/-VdLRjbW4LAs/T3R4zXu3gUI/AAAAAAAAAIw/9aFp9t7mCPg/s1024/sample_image_33.jpg",
      "https://lh6.googleusercontent.com/-gL6R17_fDJU/T3R4zpIXGjI/AAAAAAAAAI8/Q2Vjx-L9X20/s1024/sample_image_34.jpg",
      "https://lh3.googleusercontent.com/-1fGH4YJXEzo/T3R40Y1B7KI/AAAAAAAAAJE/MnTsa77g-nk/s1024/sample_image_35.jpg",
      "https://lh4.googleusercontent.com/-Ql0jHSrea-A/T3R403mUfFI/AAAAAAAAAJM/qzI4SkcH9tY/s1024/sample_image_36.jpg",
      "https://lh5.googleusercontent.com/-BL5FIBR_tzI/T3R41DA0AKI/AAAAAAAAAJk/GZfeeb-SLM0/s1024/sample_image_37.jpg",
      "https://lh4.googleusercontent.com/-wF2Vc9YDutw/T3R41fR2BCI/AAAAAAAAAJc/JdU1sHdMRAk/s1024/sample_image_38.jpg",
      "https://lh6.googleusercontent.com/-ZWHiPehwjTI/T3R41zuaKCI/AAAAAAAAAJg/hR3QJ1v3REg/s1024/sample_image_39.jpg", };

  /**
   * This are PicasaWeb thumbnail URLs and could potentially change. Ideally the
   * PicasaWeb API should be used to fetch the URLs.
   */
  public final static String[] imageThumbUrls = new String[] {
      "https://lh6.googleusercontent.com/-jZgveEqb6pg/T3R4kXScycI/AAAAAAAAAE0/xQ7CvpfXDzc/s160-c/sample_image_01.jpg",
      "https://lh4.googleusercontent.com/-K2FMuOozxU0/T3R4lRAiBTI/AAAAAAAAAE8/a3Eh9JvnnzI/s160-c/sample_image_02.jpg",
      "https://lh5.googleusercontent.com/-SCS5C646rxM/T3R4l7QB6xI/AAAAAAAAAFE/xLcuVv3CUyA/s160-c/sample_image_03.jpg",
      "https://lh6.googleusercontent.com/-f0NJR6-_Thg/T3R4mNex2wI/AAAAAAAAAFI/45oug4VE8MI/s160-c/sample_image_04.jpg",
      "https://lh3.googleusercontent.com/-n-xcJmiI0pg/T3R4mkSchHI/AAAAAAAAAFU/EoiNNb7kk3A/s160-c/sample_image_05.jpg",
      "https://lh3.googleusercontent.com/-X43vAudm7f4/T3R4nGSChJI/AAAAAAAAAFk/3bna6D-2EE8/s160-c/sample_image_06.jpg",
      "https://lh5.googleusercontent.com/-MpZneqIyjXU/T3R4nuGO1aI/AAAAAAAAAFg/r09OPjLx1ZY/s160-c/sample_image_07.jpg",
      "https://lh6.googleusercontent.com/-ql3YNfdClJo/T3XvW9apmFI/AAAAAAAAAL4/_6HFDzbahc4/s160-c/sample_image_08.jpg",
      "https://lh5.googleusercontent.com/-Pxa7eqF4cyc/T3R4oasvPEI/AAAAAAAAAF0/-uYDH92h8LA/s160-c/sample_image_09.jpg",
      "https://lh4.googleusercontent.com/-Li-rjhFEuaI/T3R4o-VUl4I/AAAAAAAAAF8/5E5XdMnP1oE/s160-c/sample_image_10.jpg",
      "https://lh5.googleusercontent.com/-_HU4fImgFhA/T3R4pPVIwWI/AAAAAAAAAGA/0RfK_Vkgth4/s160-c/sample_image_11.jpg",
      "https://lh6.googleusercontent.com/-0gnNrVjwa0Y/T3R4peGYJwI/AAAAAAAAAGU/uX_9wvRPM9I/s160-c/sample_image_12.jpg",
      "https://lh3.googleusercontent.com/-HBxuzALS_Zs/T3R4qERykaI/AAAAAAAAAGQ/_qQ16FaZ1q0/s160-c/sample_image_13.jpg",
      "https://lh4.googleusercontent.com/-cKojDrARNjQ/T3R4qfWSGPI/AAAAAAAAAGY/MR5dnbNaPyY/s160-c/sample_image_14.jpg",
      "https://lh3.googleusercontent.com/-WujkdYfcyZ8/T3R4qrIMGUI/AAAAAAAAAGk/277LIdgvnjg/s160-c/sample_image_15.jpg",
      "https://lh6.googleusercontent.com/-FMHR7Vy3PgI/T3R4rOXlEKI/AAAAAAAAAGs/VeXrDNDBkaw/s160-c/sample_image_16.jpg",
      "https://lh4.googleusercontent.com/-mrR0AJyNTH0/T3R4rZs6CuI/AAAAAAAAAG0/UE1wQqCOqLA/s160-c/sample_image_17.jpg",
      "https://lh6.googleusercontent.com/-z77w0eh3cow/T3R4rnLn05I/AAAAAAAAAG4/BaerfWoNucU/s160-c/sample_image_18.jpg",
      "https://lh5.googleusercontent.com/-aWVwh1OU5Bk/T3R4sAWw0yI/AAAAAAAAAHE/4_KAvJttFwA/s160-c/sample_image_19.jpg",
      "https://lh6.googleusercontent.com/-q-js52DMnWQ/T3R4tZhY2sI/AAAAAAAAAHM/A8kjp2Ivdqg/s160-c/sample_image_20.jpg",
      "https://lh5.googleusercontent.com/-_jIzvvzXKn4/T3R4t7xpdVI/AAAAAAAAAHU/7QC6eZ10jgs/s160-c/sample_image_21.jpg",
      "https://lh3.googleusercontent.com/-lnGi4IMLpwU/T3R4uCMa7vI/AAAAAAAAAHc/1zgzzz6qTpk/s160-c/sample_image_22.jpg",
      "https://lh5.googleusercontent.com/-fFCzKjFPsPc/T3R4u0SZPFI/AAAAAAAAAHk/sbgjzrktOK0/s160-c/sample_image_23.jpg",
      "https://lh4.googleusercontent.com/-8TqoW5gBE_Y/T3R4vBS3NPI/AAAAAAAAAHs/EZYvpNsaNXk/s160-c/sample_image_24.jpg",
      "https://lh6.googleusercontent.com/-gc4eQ3ySdzs/T3R4vafoA7I/AAAAAAAAAH4/yKii5P6tqDE/s160-c/sample_image_25.jpg",
      "https://lh5.googleusercontent.com/--NYOPCylU7Q/T3R4vjAiWkI/AAAAAAAAAH8/IPNx5q3ptRA/s160-c/sample_image_26.jpg",
      "https://lh6.googleusercontent.com/-9IJM8so4vCI/T3R4vwJO2yI/AAAAAAAAAIE/ljlr-cwuqZM/s160-c/sample_image_27.jpg",
      "https://lh4.googleusercontent.com/-KW6QwOHfhBs/T3R4w0RsQiI/AAAAAAAAAIM/uEFLVgHPFCk/s160-c/sample_image_28.jpg",
      "https://lh4.googleusercontent.com/-z2557Ec1ctY/T3R4x3QA2hI/AAAAAAAAAIk/9-GzPL1lTWE/s160-c/sample_image_29.jpg",
      "https://lh5.googleusercontent.com/-LaKXAn4Kr1c/T3R4yc5b4lI/AAAAAAAAAIY/fMgcOVQfmD0/s160-c/sample_image_30.jpg",
      "https://lh4.googleusercontent.com/-F9LRToJoQdo/T3R4yrLtyQI/AAAAAAAAAIg/ri9uUCWuRmo/s160-c/sample_image_31.jpg",
      "https://lh4.googleusercontent.com/-6X-xBwP-QpI/T3R4zGVboII/AAAAAAAAAIs/zYH4PjjngY0/s160-c/sample_image_32.jpg",
      "https://lh5.googleusercontent.com/-VdLRjbW4LAs/T3R4zXu3gUI/AAAAAAAAAIw/9aFp9t7mCPg/s160-c/sample_image_33.jpg",
      "https://lh6.googleusercontent.com/-gL6R17_fDJU/T3R4zpIXGjI/AAAAAAAAAI8/Q2Vjx-L9X20/s160-c/sample_image_34.jpg",
      "https://lh3.googleusercontent.com/-1fGH4YJXEzo/T3R40Y1B7KI/AAAAAAAAAJE/MnTsa77g-nk/s160-c/sample_image_35.jpg",
      "https://lh4.googleusercontent.com/-Ql0jHSrea-A/T3R403mUfFI/AAAAAAAAAJM/qzI4SkcH9tY/s160-c/sample_image_36.jpg",
      "https://lh5.googleusercontent.com/-BL5FIBR_tzI/T3R41DA0AKI/AAAAAAAAAJk/GZfeeb-SLM0/s160-c/sample_image_37.jpg",
      "https://lh4.googleusercontent.com/-wF2Vc9YDutw/T3R41fR2BCI/AAAAAAAAAJc/JdU1sHdMRAk/s160-c/sample_image_38.jpg",
      "https://lh6.googleusercontent.com/-ZWHiPehwjTI/T3R41zuaKCI/AAAAAAAAAJg/hR3QJ1v3REg/s160-c/sample_image_39.jpg", };

  private final static String[] moreUrls = {
      "http://a3.twimg.com/profile_images/670625317/aam-logo-v3-twitter.png",
      "http://a3.twimg.com/profile_images/740897825/AndroidCast-350_normal.png",
      "http://a3.twimg.com/profile_images/121630227/Droid_normal.jpg",
      "http://a1.twimg.com/profile_images/957149154/twitterhalf_normal.jpg",
      "http://a1.twimg.com/profile_images/97470808/icon_normal.png",
      "http://a3.twimg.com/profile_images/511790713/AG.png",
      "http://a3.twimg.com/profile_images/956404323/androinica-avatar_normal.png",
      "http://a1.twimg.com/profile_images/909231146/Android_Biz_Man_normal.png",
      "http://a3.twimg.com/profile_images/72774055/AndroidHomme-LOGO_normal.jpg",
      "http://a1.twimg.com/profile_images/349012784/android_logo_small_normal.jpg",
      "http://a1.twimg.com/profile_images/841338368/ea-twitter-icon.png",
      "http://a3.twimg.com/profile_images/64827025/android-wallpaper6_2560x160_normal.png",
      "http://a3.twimg.com/profile_images/77641093/AndroidPlanet_normal.png",
      "http://a1.twimg.com/profile_images/850960042/elandroidelibre-logo_300x300_normal.jpg",
      "http://a1.twimg.com/profile_images/655119538/andbook.png",
      "http://a3.twimg.com/profile_images/768060227/ap4u_normal.jpg",
      "http://a1.twimg.com/profile_images/74724754/android_logo_normal.png",
      "http://a3.twimg.com/profile_images/681537837/SmallAvatarx150_normal.png",
      "http://a1.twimg.com/profile_images/63737974/2008-11-06_1637_normal.png",
      "http://a3.twimg.com/profile_images/548410609/icon_8_73.png",
      "http://a1.twimg.com/profile_images/612232882/nexusoneavatar_normal.jpg",
      "http://a1.twimg.com/profile_images/213722080/Bugdroid-phone_normal.png",
      "http://a1.twimg.com/profile_images/645523828/OT_icon_090918_android_normal.png",
      "http://a3.twimg.com/profile_images/64827025/android-wallpaper6_2560x160_normal.png",
      "http://a3.twimg.com/profile_images/77641093/AndroidPlanet.png",
      "http://a1.twimg.com/profile_images/850960042/elandroidelibre-logo_300x300_normal.jpg",
      "http://a1.twimg.com/profile_images/655119538/andbook_normal.png",
      "http://a3.twimg.com/profile_images/511790713/AG_normal.png",
      "http://a3.twimg.com/profile_images/956404323/androinica-avatar.png",
      "http://a1.twimg.com/profile_images/909231146/Android_Biz_Man_normal.png",
      "http://a3.twimg.com/profile_images/72774055/AndroidHomme-LOGO_normal.jpg",
      "http://a1.twimg.com/profile_images/349012784/android_logo_small_normal.jpg",
      "http://a1.twimg.com/profile_images/841338368/ea-twitter-icon_normal.png",
      "http://a3.twimg.com/profile_images/64827025/android-wallpaper6_2560x160_normal.png",
      "http://a3.twimg.com/profile_images/77641093/AndroidPlanet.png",
      "http://a3.twimg.com/profile_images/64827025/android-wallpaper6_2560x160_normal.png",
      "http://a3.twimg.com/profile_images/77641093/AndroidPlanet_normal.png",
      "http://a1.twimg.com/profile_images/850960042/elandroidelibre-logo_300x300.jpg",
      "http://a1.twimg.com/profile_images/655119538/andbook_normal.png",
      "http://a3.twimg.com/profile_images/511790713/AG_normal.png",
      "http://a3.twimg.com/profile_images/956404323/androinica-avatar_normal.png",
      "http://a1.twimg.com/profile_images/909231146/Android_Biz_Man_normal.png",
      "http://a3.twimg.com/profile_images/121630227/Droid.jpg",
      "http://a1.twimg.com/profile_images/957149154/twitterhalf_normal.jpg",
      "http://a1.twimg.com/profile_images/97470808/icon_normal.png",
      "http://a3.twimg.com/profile_images/511790713/AG_normal.png",
      "http://a3.twimg.com/profile_images/956404323/androinica-avatar_normal.png",
      "http://a1.twimg.com/profile_images/909231146/Android_Biz_Man.png",
      "http://a3.twimg.com/profile_images/72774055/AndroidHomme-LOGO_normal.jpg",
      "http://a1.twimg.com/profile_images/349012784/android_logo_small_normal.jpg",
      "http://a1.twimg.com/profile_images/841338368/ea-twitter-icon_normal.png",
      "http://a3.twimg.com/profile_images/64827025/android-wallpaper6_2560x160_normal.png",
      "http://a3.twimg.com/profile_images/77641093/AndroidPlanet.png",
      "http://a3.twimg.com/profile_images/670625317/aam-logo-v3-twitter_normal.png",
      "http://a3.twimg.com/profile_images/740897825/AndroidCast-350_normal.png",
      "http://a3.twimg.com/profile_images/121630227/Droid_normal.jpg",
      "http://a1.twimg.com/profile_images/957149154/twitterhalf_normal.jpg",
      "http://a1.twimg.com/profile_images/97470808/icon.png",
      "http://a3.twimg.com/profile_images/511790713/AG_normal.png",
      "http://a3.twimg.com/profile_images/956404323/androinica-avatar_normal.png",
      "http://a1.twimg.com/profile_images/909231146/Android_Biz_Man_normal.png",
      "http://a3.twimg.com/profile_images/72774055/AndroidHomme-LOGO_normal.jpg",
      "http://a1.twimg.com/profile_images/349012784/android_logo_small_normal.jpg",
      "http://a1.twimg.com/profile_images/841338368/ea-twitter-icon.png",
      "http://a3.twimg.com/profile_images/64827025/android-wallpaper6_2560x160_normal.png",
      "http://a3.twimg.com/profile_images/77641093/AndroidPlanet_normal.png",
      "http://a1.twimg.com/profile_images/850960042/elandroidelibre-logo_300x300_normal.jpg",
      "http://a1.twimg.com/profile_images/655119538/andbook_normal.png",
      "http://a3.twimg.com/profile_images/768060227/ap4u_normal.jpg",
      "http://a1.twimg.com/profile_images/74724754/android_logo.png",
      "http://a3.twimg.com/profile_images/681537837/SmallAvatarx150_normal.png",
      "http://a1.twimg.com/profile_images/63737974/2008-11-06_1637_normal.png",
      "http://a3.twimg.com/profile_images/548410609/icon_8_73_normal.png",
      "http://a1.twimg.com/profile_images/612232882/nexusoneavatar_normal.jpg",
      "http://a1.twimg.com/profile_images/213722080/Bugdroid-phone_normal.png",
      "http://a1.twimg.com/profile_images/645523828/OT_icon_090918_android.png",
      "http://a3.twimg.com/profile_images/64827025/android-wallpaper6_2560x160_normal.png",
      "http://a3.twimg.com/profile_images/77641093/AndroidPlanet_normal.png",
      "http://a1.twimg.com/profile_images/850960042/elandroidelibre-logo_300x300_normal.jpg",
      "http://a1.twimg.com/profile_images/655119538/andbook.png",
      "http://a3.twimg.com/profile_images/511790713/AG_normal.png",
      "http://a3.twimg.com/profile_images/956404323/androinica-avatar_normal.png",
      "http://a1.twimg.com/profile_images/909231146/Android_Biz_Man_normal.png",
      "http://a3.twimg.com/profile_images/72774055/AndroidHomme-LOGO_normal.jpg",
      "http://a1.twimg.com/profile_images/349012784/android_logo_small_normal.jpg",
      "http://a1.twimg.com/profile_images/841338368/ea-twitter-icon.png",
      "http://a3.twimg.com/profile_images/64827025/android-wallpaper6_2560x160_normal.png",
      "http://a3.twimg.com/profile_images/77641093/AndroidPlanet_normal.png",
      "http://a3.twimg.com/profile_images/64827025/android-wallpaper6_2560x160_normal.png",
      "http://a3.twimg.com/profile_images/77641093/AndroidPlanet_normal.png",
      "http://a1.twimg.com/profile_images/850960042/elandroidelibre-logo_300x300_normal.jpg",
      "http://a1.twimg.com/profile_images/655119538/andbook_normal.png",
      "http://a3.twimg.com/profile_images/511790713/AG_normal.png",
      "http://a3.twimg.com/profile_images/956404323/androinica-avatar_normal.png",
      "http://a1.twimg.com/profile_images/909231146/Android_Biz_Man_normal.png",
      "http://a3.twimg.com/profile_images/121630227/Droid_normal.jpg",
      "http://a1.twimg.com/profile_images/957149154/twitterhalf.jpg",
      "http://a1.twimg.com/profile_images/97470808/icon_normal.png",
      "http://a3.twimg.com/profile_images/511790713/AG_normal.png",
      "http://a3.twimg.com/profile_images/956404323/androinica-avatar_normal.png",
      "http://a1.twimg.com/profile_images/909231146/Android_Biz_Man_normal.png",
      "http://a3.twimg.com/profile_images/72774055/AndroidHomme-LOGO_normal.jpg",
      "http://a1.twimg.com/profile_images/349012784/android_logo_small.jpg",
      "http://a1.twimg.com/profile_images/841338368/ea-twitter-icon_normal.png",
      "http://a3.twimg.com/profile_images/64827025/android-wallpaper6_2560x160_normal.png",
      "http://a3.twimg.com/profile_images/77641093/AndroidPlanet_normal.png" };

  /**
   * Simple static adapter to use for images.
   */
  public final static ImageWorkerAdapter imageWorkerUrlsAdapter = new ImageWorkerAdapter() {
    @Override
    public Object getItem(int num) {
      return Images.imageUrls[num];
    }

    @Override
    public int getSize() {
      return Images.imageUrls.length;
    }
  };

  /**
   * Simple static adapter to use for image thumbnails.
   */
  public final static ImageWorkerAdapter imageThumbWorkerUrlsAdapter = new ImageWorkerAdapter() {
    @Override
    public Object getItem(int num) {
      return Images.imageThumbUrls[num];
    }

    @Override
    public int getSize() {
      return Images.imageThumbUrls.length;
    }
  };

  /**
   * Another simple static adapter to use for image thumbnails, with a different
   * set of images.
   */
  public final static ImageWorkerAdapter otherUrlAdapter = new ImageWorkerAdapter() {
    @Override
    public Object getItem(int num) {
      return Images.moreUrls[num];
    }

    @Override
    public int getSize() {
      return Images.moreUrls.length;
    }
  };
}
